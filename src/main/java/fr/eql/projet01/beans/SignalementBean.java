package fr.eql.projet01.beans;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class SignalementBean implements Serializable {
		private static final long serialVersionUID = 1L;
		public static final String NAME = "signalement";
		
		private Long id;
		private String type;
		private Long identifiant;
		private Long userid;
		private MotifSignalementBean motifSignalement;
}