package fr.eql.projet01.service;

import java.util.List;

import fr.eql.projet01.entity.Abonnement;
import fr.eql.projet01.entity.Utilisateur;

public interface AbonnementService {

	List<Abonnement> findAllFollowingByUtilisateur(Utilisateur uti);
	List<Abonnement> findAllFollowerByUtilisateur(Utilisateur uti);
}
