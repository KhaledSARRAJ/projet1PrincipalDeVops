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

import fr.eql.projet01.dao.SupportRepository;
import fr.eql.projet01.entity.Annonce;
import fr.eql.projet01.entity.Publication;
import fr.eql.projet01.entity.Support;
import fr.eql.projet01.exception.AecServiceException;
import fr.eql.projet01.exception.NotValidObjectException;
import fr.eql.projet01.exception.ResourceNotFoundException;

@Service
@Transactional
public class SupportServiceImpl implements SupportService {
	@Autowired
	private SupportRepository supportRepository;
	@Autowired
	private Validator validator;
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public List<Support> findByPublicationSupport(Publication publication) {
		return supportRepository.findByPublicationSupport(publication);
	}
	
	@Override
	public List<Support> findSupportByPublication(Publication publicationSupport) {
		return supportRepository.findByPublicationSupport(publicationSupport);
	}

	@Override
	public Support enregisterSupport(Support support) {
		return supportRepository.save(support);
	}

	@Override
	public List<Support> findSupportByAnnonce(Annonce annonceSupport) {
		return supportRepository.findByAnnonceSupport(annonceSupport);
	}
	
	@Override
	public Support findOne(Long id) throws ResourceNotFoundException {
		if(!supportRepository.existsById(id))		
			throw new ResourceNotFoundException("Support introuvable avec cet id : "+id);
		return supportRepository.findById(id).get();
	}
	
	@Override
	public Iterable<Support> search(String search) {
		return null;
	}	

	@Override
	public Page<Support> findAllByPage(Pageable page) {
		return supportRepository.findAll(page);
	}

	@Override
	public List<Support> findAll() {
		return supportRepository.findAll();
	}
		
	@Override
	public Support save(Support support) throws NotValidObjectException, ResourceNotFoundException {
		Assert.notNull(support, "Ne peut pas être null.");
		if(support.getId() != null)
			this.findOne(support.getId());
		Set<ConstraintViolation<Support>> errors = validator.validate(support);
		if(errors.size() > 0) {
			List<String> l = new ArrayList<String>();
			for(ConstraintViolation<Support> cv : errors)
				l.add(cv.getMessage());
			throw new NotValidObjectException(l);
		}
		return supportRepository.save(support);
	}
	
	@Override
	public void delete(Long id) throws AecServiceException {
		if(!supportRepository.existsById(id))
			throw new ResourceNotFoundException("Support inexistant (pas supprimable) pour l'id : "+id);
		supportRepository.deleteById(id);
	}

	@Override
	public long count() {
		return supportRepository.count();
	}
}