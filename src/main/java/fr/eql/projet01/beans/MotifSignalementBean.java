package fr.eql.projet01.beans;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class MotifSignalementBean {
	
	private Long id;
	private String libelle_motif_signalement;
}
