package fr.eql.projet01.ui.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class AnnonceRequest {
	private Long id;
	private String titre;
	private String texte;
	private Double prix;
	private Long userId;
}