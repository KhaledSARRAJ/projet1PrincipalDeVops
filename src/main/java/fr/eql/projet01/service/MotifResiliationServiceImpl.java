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

import fr.eql.projet01.dao.MotifResiliationRepository;
import fr.eql.projet01.entity.MotifResiliation;
import fr.eql.projet01.exception.AecServiceException;
import fr.eql.projet01.exception.NotValidObjectException;
import fr.eql.projet01.exception.ResourceNotFoundException;

@Service
@Transactional
public class MotifResiliationServiceImpl implements MotifResiliationService{
	@Autowired
	private MotifResiliationRepository motifResiliationRepository;
	@Autowired
	private Validator validator;
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public MotifResiliation findOne(Long id) throws ResourceNotFoundException {
		if(!motifResiliationRepository.existsById(id))		
			throw new ResourceNotFoundException("MotifResiliation introuvable avec cet id : "+id);
		return motifResiliationRepository.findById(id).get();
	}	
		
	@Override
	public Page<MotifResiliation> findAllByPage(Pageable page) {
		return motifResiliationRepository.findAll(page);
	}

	@Override
	public List<MotifResiliation> findAll() {
		return motifResiliationRepository.findAll();
	}

	@Override
	public MotifResiliation save(MotifResiliation motifResiliation) throws NotValidObjectException, ResourceNotFoundException {
		Assert.notNull(motifResiliation, "Ne peut pas être null.");
		if(motifResiliation.getId() != null)
			this.findOne(motifResiliation.getId());
		Set<ConstraintViolation<MotifResiliation>> errors = validator.validate(motifResiliation);
		if(errors.size() > 0) {
			List<String> l = new ArrayList<String>();
			for(ConstraintViolation<MotifResiliation> cv : errors)
				l.add(cv.getMessage());
			throw new NotValidObjectException(l);
		}
		return motifResiliationRepository.save(motifResiliation);
	}

	@Override
	public void delete(Long id) throws AecServiceException {
		if(!motifResiliationRepository.existsById(id))
			throw new ResourceNotFoundException("MotifResiliation inexistante (pas supprimable) pour l'id : "+id);
		motifResiliationRepository.findById(id).get();
		motifResiliationRepository.deleteById(id);
	}

	@Override
	public long count() {
		return motifResiliationRepository.count();
	}
}