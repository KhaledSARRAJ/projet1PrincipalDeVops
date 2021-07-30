package fr.eql.projet01.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eql.projet01.entity.Annonce;

public interface AnnonceRepository extends JpaRepository<Annonce, Long>{
	public List<Annonce> findAllByUserId(Long userId);
}
