package fr.eql.projet01.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.eql.projet01.entity.Theme;
import fr.eql.projet01.exception.AecServiceException;
import fr.eql.projet01.exception.NotValidObjectException;
import fr.eql.projet01.exception.ResourceNotFoundException;

public interface ThemeService {
	Theme findOne(Long id) throws ResourceNotFoundException;
	Page<Theme> findAllByPage(Pageable page);
	List<Theme> findAll();
	Iterable<Theme> search(String search);
	Theme save(Theme theme) throws NotValidObjectException, ResourceNotFoundException; 
	void delete(Long id) throws AecServiceException;
	long count();
}