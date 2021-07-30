package fr.eql.projet01.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.eql.projet01.entity.MotifResiliation;
import fr.eql.projet01.exception.AecServiceException;
import fr.eql.projet01.exception.NotValidObjectException;
import fr.eql.projet01.exception.ResourceNotFoundException;

public interface MotifResiliationService {
	MotifResiliation findOne(Long id) throws ResourceNotFoundException;
	Page<MotifResiliation> findAllByPage(Pageable page);
	List<MotifResiliation> findAll();
	MotifResiliation save(MotifResiliation motifResiliation) throws NotValidObjectException, ResourceNotFoundException; 
	void delete(Long id) throws AecServiceException;
	long count();
}