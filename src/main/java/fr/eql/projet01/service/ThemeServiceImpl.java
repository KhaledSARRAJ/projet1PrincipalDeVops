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

import fr.eql.projet01.dao.ThemeRepository;
import fr.eql.projet01.entity.Theme;
import fr.eql.projet01.exception.AecServiceException;
import fr.eql.projet01.exception.NotValidObjectException;
import fr.eql.projet01.exception.ResourceNotFoundException;

@Service
@Transactional
public class ThemeServiceImpl implements ThemeService{
	@Autowired
	private ThemeRepository themeRepository;
	@Autowired
	private Validator validator;
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public Theme findOne(Long id) throws ResourceNotFoundException {
		if(!themeRepository.existsById(id))		
			throw new ResourceNotFoundException("Theme introuvable avec cet id : "+id);
		return themeRepository.findById(id).get();
	}	

	@Override
	public Iterable<Theme> search(String search) {
		return null;
	}	

	@Override
	public Page<Theme> findAllByPage(Pageable page) {
		return themeRepository.findAll(page);
	}

	@Override
	public List<Theme> findAll() {
		return themeRepository.findAll();
	}

	@Override
	public Theme save(Theme theme) throws NotValidObjectException, ResourceNotFoundException {
		Assert.notNull(theme, "Ne peut pas être null.");
		if(theme.getId() != null)
			this.findOne(theme.getId());
		Set<ConstraintViolation<Theme>> errors = validator.validate(theme);
		if(errors.size() > 0) {
			List<String> l = new ArrayList<String>();
			for(ConstraintViolation<Theme> cv : errors)
				l.add(cv.getMessage());
			throw new NotValidObjectException(l);
		}
		return themeRepository.save(theme);
	}

	@Override
	public void delete(Long id) throws AecServiceException {
		if(!themeRepository.existsById(id))
			throw new ResourceNotFoundException("Theme inexistant (pas supprimable) pour l'id : "+id);
		themeRepository.deleteById(id);
	}

	@Override
	public long count() {
		return themeRepository.count();
	}
}