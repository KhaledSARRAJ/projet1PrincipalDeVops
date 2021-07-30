package fr.eql.projet01.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eql.projet01.entity.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Long>{

}
