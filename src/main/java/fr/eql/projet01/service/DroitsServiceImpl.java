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

import fr.eql.projet01.dao.DroitsRepository;
import fr.eql.projet01.entity.Droits;
import fr.eql.projet01.exception.AecServiceException;
import fr.eql.projet01.exception.NotValidObjectException;
import fr.eql.projet01.exception.ResourceNotFoundException;

@Service
@Transactional
public class DroitsServiceImpl implements DroitsService{
	@Autowired
	private DroitsRepository droitsRepository;
	@Autowired
	private Validator validator;
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public Droits findOne(Long id) throws ResourceNotFoundException {
		if(!droitsRepository.existsById(id))		
			throw new ResourceNotFoundException("Droits introuvable avec cet id : "+id);
		return droitsRepository.findById(id).get();
	}	
		
	@Override
	public Page<Droits> findAllByPage(Pageable page) {
		return droitsRepository.findAll(page);
	}

	@Override
	public List<Droits> findAll() {
		return droitsRepository.findAll();
	}

	@Override
	public Droits save(Droits droits) throws NotValidObjectException, ResourceNotFoundException {
		Assert.notNull(droits, "Ne peut pas être null.");
		if(droits.getId() != null)
			this.findOne(droits.getId());
		Set<ConstraintViolation<Droits>> errors = validator.validate(droits);
		if(errors.size() > 0) {
			List<String> l = new ArrayList<String>();
			for(ConstraintViolation<Droits> cv : errors)
				l.add(cv.getMessage());
			throw new NotValidObjectException(l);
		}
		return droitsRepository.save(droits);
	}

	@Override
	public void delete(Long id) throws AecServiceException {
		if(!droitsRepository.existsById(id))
			throw new ResourceNotFoundException("Droits inexistante (pas supprimable) pour l'id : "+id);
		droitsRepository.findById(id).get();
		droitsRepository.deleteById(id);
	}

	@Override
	public long count() {
		return droitsRepository.count();
	}
}