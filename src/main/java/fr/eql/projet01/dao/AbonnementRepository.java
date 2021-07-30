package fr.eql.projet01.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eql.projet01.entity.Abonnement;
import fr.eql.projet01.entity.Utilisateur;

public interface AbonnementRepository extends JpaRepository<Abonnement, Long>{

	List<Abonnement> findByFollowing(Utilisateur following); 
	List<Abonnement> findByFollower(Utilisateur follower);//liste d'abonnements de l'utilisateur
}
