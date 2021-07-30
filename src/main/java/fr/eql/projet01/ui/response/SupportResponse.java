package fr.eql.projet01.ui.response;

import javax.persistence.Lob;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class SupportResponse {
	private Long id;
	private String typeSupport;
	private String chemin;
	@Lob // save files to db
	private byte[] image;
}
