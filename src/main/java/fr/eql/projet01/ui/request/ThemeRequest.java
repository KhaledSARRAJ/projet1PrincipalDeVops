package fr.eql.projet01.ui.request;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class ThemeRequest {
	private Long id;
	private String titre;
	private String description;
	List<Long> annoncesId;
}