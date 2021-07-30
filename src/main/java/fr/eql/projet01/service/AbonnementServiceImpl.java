package fr.eql.projet01.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eql.projet01.dao.AbonnementRepository;
import fr.eql.projet01.entity.Abonnement;
import fr.eql.projet01.entity.Utilisateur;

@Service
@Transactional
public class AbonnementServiceImpl implements AbonnementService {

	@Autowired
	private AbonnementRepository abonnementRepository;
	
	@Override
	public List<Abonnement> findAllFollowingByUtilisateur(Utilisateur uti) {
		return abonnementRepository.findByFollowing(uti);
	}

	@Override
	public List<Abonnement> findAllFollowerByUtilisateur(Utilisateur uti) {
		return abonnementRepository.findByFollower(uti);
	}
}
