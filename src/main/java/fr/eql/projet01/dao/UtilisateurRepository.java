package fr.eql.projet01.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eql.projet01.entity.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{
	public Utilisateur findByProfile(String profil) ;
	public List<Utilisateur> findByNom(String mc);
	public boolean existsByMail(String mail);
	public boolean existsByProfile(String profile);

}
