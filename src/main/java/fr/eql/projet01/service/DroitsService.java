package fr.eql.projet01.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.eql.projet01.entity.Droits;
import fr.eql.projet01.exception.AecServiceException;
import fr.eql.projet01.exception.NotValidObjectException;
import fr.eql.projet01.exception.ResourceNotFoundException;

public interface DroitsService {
	Droits findOne(Long id) throws ResourceNotFoundException;
	Page<Droits> findAllByPage(Pageable page);
	List<Droits> findAll();
	Droits save(Droits droits) throws NotValidObjectException, ResourceNotFoundException; 
	void delete(Long id) throws AecServiceException;
	long count();
}