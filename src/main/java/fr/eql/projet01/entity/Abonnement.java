package fr.eql.projet01.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor 
public class Abonnement implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "Abonnement";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.DATE)
	private Date dateDébut;
	@Temporal(TemporalType.DATE)
	private Date dateFin;
	
	@ManyToOne
	@JoinColumn(name="follower_id")
	private Utilisateur follower;
	
	@ManyToOne
	@JoinColumn(name="following_id")
	private Utilisateur following;
	
	
	
}
