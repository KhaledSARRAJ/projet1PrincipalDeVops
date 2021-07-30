package fr.eql.projet01.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.eql.projet01.entity.Annonce;
import fr.eql.projet01.exception.AecServiceException;
import fr.eql.projet01.exception.NotValidObjectException;
import fr.eql.projet01.exception.ResourceNotFoundException;

public interface AnnonceService {
	Annonce findOne(Long id) throws ResourceNotFoundException;
	Page<Annonce> findAllByPage(Pageable page);
	List<Annonce> findAll();
	List<Annonce> search(Long userId) throws ResourceNotFoundException;
	Annonce save(Annonce annonce) throws NotValidObjectException, ResourceNotFoundException; 
	void delete(Long id) throws AecServiceException;
	long count();
}