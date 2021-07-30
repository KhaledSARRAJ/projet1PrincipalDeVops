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
public class PublicationResponse {

	private Long id;
	private String titre;
	private String texte;
	@Temporal(TemporalType.DATE)
	private Date dateDebut;
	private Long userId;
	private List<SupportResponse> listeDesSupports;
	private List<ThemeResponse> listeDesThemes;
	
}
