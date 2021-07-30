package fr.eql.projet01.ui.response;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class AnnonceResponse {
	private Long id;
	private String titre;
	private String texte;
	private Double prix;
	@Temporal(TemporalType.DATE)
	private Date dateParution;
	private String prenomDupublisher;
	//private SupportResponse supportResponses;
	private List<SupportResponse> listeDesSupports;
	private List<ThemeResponse> listeDesThemes;
}
