package fr.eql.projet01.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eql.projet01.entity.Ville;

public interface VilleRepository extends JpaRepository<Ville, Long>{

}
