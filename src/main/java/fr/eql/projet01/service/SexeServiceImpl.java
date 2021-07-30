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

import fr.eql.projet01.dao.SexeRepository;
import fr.eql.projet01.entity.Sexe;
import fr.eql.projet01.exception.AecServiceException;
import fr.eql.projet01.exception.NotValidObjectException;
import fr.eql.projet01.exception.ResourceNotFoundException;

@Service
@Transactional
public class SexeServiceImpl implements SexeService{
	@Autowired
	private SexeRepository sexeRepository;
	@Autowired
	private Validator validator;
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public Sexe findOne(Long id) throws ResourceNotFoundException {
		if(!sexeRepository.existsById(id))		
			throw new ResourceNotFoundException("Sexe introuvable avec cet id : "+id);
		return sexeRepository.findById(id).get();
	}	
		
	@Override
	public Page<Sexe> findAllByPage(Pageable page) {
		return sexeRepository.findAll(page);
	}

	@Override
	public List<Sexe> findAll() {
		return sexeRepository.findAll();
	}

	@Override
	public Sexe save(Sexe sexe) throws NotValidObjectException, ResourceNotFoundException {
		Assert.notNull(sexe, "Ne peut pas être null.");
		if(sexe.getId() != null)
			this.findOne(sexe.getId());
		Set<ConstraintViolation<Sexe>> errors = validator.validate(sexe);
		if(errors.size() > 0) {
			List<String> l = new ArrayList<String>();
			for(ConstraintViolation<Sexe> cv : errors)
				l.add(cv.getMessage());
			throw new NotValidObjectException(l);
		}
		return sexeRepository.save(sexe);
	}

	@Override
	public void delete(Long id) throws AecServiceException {
		if(!sexeRepository.existsById(id))
			throw new ResourceNotFoundException("Sexe inexistante (pas supprimable) pour l'id : "+id);
		sexeRepository.findById(id).get();
		sexeRepository.deleteById(id);
	}

	@Override
	public long count() {
		return sexeRepository.count();
	}
}