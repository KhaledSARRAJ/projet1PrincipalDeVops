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

import fr.eql.projet01.dao.AnnonceRepository;
import fr.eql.projet01.entity.Annonce;
import fr.eql.projet01.exception.AecServiceException;
import fr.eql.projet01.exception.NotValidObjectException;
import fr.eql.projet01.exception.ResourceNotFoundException;

@Service
@Transactional
public class AnnonceServiceImpl implements AnnonceService{
	@Autowired
	private AnnonceRepository annonceRepository;
	@Autowired
	private Validator validator;
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public Annonce findOne(Long id) throws ResourceNotFoundException {
		if(!annonceRepository.existsById(id))		
			throw new ResourceNotFoundException("Annonce introuvable avec cet id : "+id);
		return annonceRepository.findById(id).get();
	}	

	@Override
	public List<Annonce> search(Long userId) throws ResourceNotFoundException {
		if(annonceRepository.findAllByUserId(userId).equals(null) || annonceRepository.findAllByUserId(userId).isEmpty())
			throw new ResourceNotFoundException("Il n'y a pas d'annonces pour cet utilisateur : id : "+userId);	
		return annonceRepository.findAllByUserId(userId);
	}	

	@Override
	public Page<Annonce> findAllByPage(Pageable page) {
		return annonceRepository.findAll(page);
	}

	@Override
	public List<Annonce> findAll() {
		return annonceRepository.findAll();
	}

	@Override
	public Annonce save(Annonce annonce) throws NotValidObjectException, ResourceNotFoundException {
		Assert.notNull(annonce, "Ne peut pas être null.");
		if(annonce.getId() != null)
			this.findOne(annonce.getId());
		Set<ConstraintViolation<Annonce>> errors = validator.validate(annonce);
		if(errors.size() > 0) {
			List<String> l = new ArrayList<String>();
			for(ConstraintViolation<Annonce> cv : errors)
				l.add(cv.getMessage());
			throw new NotValidObjectException(l);
		}
		return annonceRepository.save(annonce);
	}

	@Override
	public void delete(Long id) throws AecServiceException {
		if(!annonceRepository.existsById(id))
			throw new ResourceNotFoundException("Annonce inexistante (pas supprimable) pour l'id : "+id);
		annonceRepository.findById(id).get().detachWithTheme();
		annonceRepository.deleteById(id);
	}

	@Override
	public long count() {
		return annonceRepository.count();
	}
}