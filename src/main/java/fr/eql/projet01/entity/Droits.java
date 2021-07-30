package fr.eql.projet01.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor 
public class Droits implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "droit";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String typeDroit;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="droits")
	@JsonIgnore
	private List<Utilisateur> listeUtilisateur;
}