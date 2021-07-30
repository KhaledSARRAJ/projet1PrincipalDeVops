package fr.eql.projet01.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor 
public class Theme implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "thème";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titre;
	private String description;
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "publication_theme",
	joinColumns = {@JoinColumn(name = "theme_id")},
	inverseJoinColumns = {@JoinColumn(name = "publication_id")})
	private List<Publication> listPublicationTheme;
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "annonce_theme",
	joinColumns = {@JoinColumn(name = "theme_id")},
	inverseJoinColumns = {@JoinColumn(name = "annonce_id")})
	private List<Annonce> listAnnonceTheme;
	
	public void addPublication(Publication p) {
		if (listPublicationTheme == null) {
			listPublicationTheme = new ArrayList<Publication>();
		}
		this.listPublicationTheme.add(p);
	}
	
	public void addAnnonce(Annonce a) {
		if (listAnnonceTheme == null) {
			listAnnonceTheme = new ArrayList<Annonce>();
		}
		this.listAnnonceTheme.add(a);
	}
}
