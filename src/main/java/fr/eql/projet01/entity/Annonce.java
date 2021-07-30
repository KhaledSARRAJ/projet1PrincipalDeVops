package fr.eql.projet01.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NamedQueries({
	@NamedQuery(name="Annonce.findAllByUserId", query="SELECT a FROM Annonce a  WHERE a.utilisateur.id=:userId")})
@Entity
@Getter @Setter @NoArgsConstructor 
public class Annonce implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final String NAME = "annonce";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titre;
	private String texte;
	private Double prix;
	@Temporal(TemporalType.DATE)
	private Date dateParution;
	
	@JsonIgnore
	//@OneToMany
	//@JoinColumn(referencedColumnName = "id")
	@OneToMany(mappedBy = "annonceSupport", cascade = CascadeType.ALL)
	private List<Support> support;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "listAnnonceTheme")
	private List<Theme> theme;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private Utilisateur utilisateur;	
	
	public void detachWithTheme() {
		for(Theme t : this.theme) {
			t.getListAnnonceTheme().remove(this);
		}
	}
}