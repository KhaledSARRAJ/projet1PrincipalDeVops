package fr.eql.projet01.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.eql.projet01.entity.Sexe;
import fr.eql.projet01.exception.AecServiceException;
import fr.eql.projet01.exception.NotValidObjectException;
import fr.eql.projet01.exception.ResourceNotFoundException;

public interface SexeService {
	Sexe findOne(Long id) throws ResourceNotFoundException;
	Page<Sexe> findAllByPage(Pageable page);
	List<Sexe> findAll();
	Sexe save(Sexe sexe) throws NotValidObjectException, ResourceNotFoundException; 
	void delete(Long id) throws AecServiceException;
	long count();
}