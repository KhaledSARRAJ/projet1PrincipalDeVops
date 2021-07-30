package fr.eql.projet01.ui;

import lombok.Data;

@Data
public class Erreur {
	private String message;
	//...

	public Erreur(String message) {
		super();
		this.message = message;
	}
	
}