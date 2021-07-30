package fr.eql.projet01.ui.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class ThemeResponse {
	private Long id;
	private String titre;
	private String description;
}