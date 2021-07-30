package fr.eql.projet01.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.eql.projet01.entity.Annonce;
import fr.eql.projet01.entity.Publication;
import fr.eql.projet01.entity.Support;
import fr.eql.projet01.exception.AecServiceException;
import fr.eql.projet01.exception.NotValidObjectException;
import fr.eql.projet01.exception.ResourceNotFoundException;

public interface SupportService {


	List<Support> findSupportByPublication(Publication publicationSupport);
	List<Support> findByPublicationSupport(Publication publication);
	Support enregisterSupport(Support support);
	List<Support> findSupportByAnnonce(Annonce annonceSupport);
	Support findOne(Long id) throws ResourceNotFoundException;
	Page<Support> findAllByPage(Pageable page);
	List<Support> findAll();	
	Iterable<Support> search(String search);
	Support save(Support support) throws NotValidObjectException, ResourceNotFoundException;
	void delete(Long id) throws AecServiceException;
	long count();
}
