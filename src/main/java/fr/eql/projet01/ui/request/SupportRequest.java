package fr.eql.projet01.ui.request;

import javax.persistence.Lob;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class SupportRequest {
	private Long id;
	private String typeSupport;
	private String chemin;
	@Lob // save files to db
	private byte[] image;
	private Long annonceId;
	private Long publicationId;
}
