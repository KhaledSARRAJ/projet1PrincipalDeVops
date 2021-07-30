package fr.eql.projet01.springSecurity;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.eql.projet01.entity.Utilisateur;
import fr.eql.projet01.service.UtilisateurService;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UtilisateurService utilisateurService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		String password=null;
		try {

			Utilisateur utilisateur = utilisateurService.rechercherUtilisateurParProfil(username); 
			if (utilisateur.getDroits().getTypeDroit().equals("Administrateur")) {
				authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			} else if(utilisateur.getDroits().getTypeDroit().equals("Utilisateur Premium")) {
				authorities.add(new SimpleGrantedAuthority("ROLE_PREMIUM"));
			} else {
				authorities.add(new SimpleGrantedAuthority("ROLE_USER"));	
			}
			password=utilisateur.getPasseWord();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return new User(username, password, authorities);
	}
}