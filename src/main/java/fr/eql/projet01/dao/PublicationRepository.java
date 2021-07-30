package fr.eql.projet01.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import fr.eql.projet01.entity.Publication;
import fr.eql.projet01.entity.Utilisateur;

public interface PublicationRepository extends CrudRepository<Publication, Long> {
	List<Publication> findByTitreIgnoreCase(String titre);

	public Page<Publication> findByTitreIgnoreCaseContains(String mc, Pageable pageable);

	List<Publication> findByUtilisateur(Utilisateur utilisateur);

	public Page<Publication> findByUtilisateurAndTitreIgnoreCaseContains(Utilisateur utilisateur, String titre, Pageable pageable);
}
