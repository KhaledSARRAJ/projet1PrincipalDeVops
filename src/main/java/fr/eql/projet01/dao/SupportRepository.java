package fr.eql.projet01.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eql.projet01.entity.Annonce;
import fr.eql.projet01.entity.Publication;
import fr.eql.projet01.entity.Support;


public interface SupportRepository extends JpaRepository<Support, Long>{

	List<Support>  findByPublicationSupport(Publication publicationSupport);
	List<Support> findByAnnonceSupport(Annonce annonceSupport);
}
