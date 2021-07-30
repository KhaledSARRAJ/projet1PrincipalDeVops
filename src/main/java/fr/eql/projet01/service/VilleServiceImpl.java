package fr.eql.projet01.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import fr.eql.projet01.dao.VilleRepository;
import fr.eql.projet01.entity.Ville;
import fr.eql.projet01.exception.AecServiceException;
import fr.eql.projet01.exception.NotValidObjectException;
import fr.eql.projet01.exception.ResourceNotFoundException;

@Service
@Transactional
public class VilleServiceImpl implements VilleService{
	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private Validator validator;
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public Ville findOne(Long id) throws ResourceNotFoundException {
		if(!villeRepository.existsById(id))		
			throw new ResourceNotFoundException("Ville introuvable avec cet id : "+id);
		return villeRepository.findById(id).get();
	}	
		
	@Override
	public Page<Ville> findAllByPage(Pageable page) {
		return villeRepository.findAll(page);
	}

	@Override
	public List<Ville> findAll() {
		return villeRepository.findAll();
	}

	@Override
	public Ville save(Ville ville) throws NotValidObjectException, ResourceNotFoundException {
		Assert.notNull(ville, "Ne peut pas être null.");
		if(ville.getId() != null)
			this.findOne(ville.getId());
		Set<ConstraintViolation<Ville>> errors = validator.validate(ville);
		if(errors.size() > 0) {
			List<String> l = new ArrayList<String>();
			for(ConstraintViolation<Ville> cv : errors)
				l.add(cv.getMessage());
			throw new NotValidObjectException(l);
		}
		return villeRepository.save(ville);
	}

	@Override
	public void delete(Long id) throws AecServiceException {
		if(!villeRepository.existsById(id))
			throw new ResourceNotFoundException("Ville inexistante (pas supprimable) pour l'id : "+id);
		villeRepository.findById(id).get();
		villeRepository.deleteById(id);
	}

	@Override
	public long count() {
		return villeRepository.count();
	}
}