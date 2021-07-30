package fr.eql.projet01.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eql.projet01.entity.Utilisateur;
import fr.eql.projet01.service.UtilisateurService;

@RestController
@CrossOrigin(origins = { "http://localhost:8090"})
@RequestMapping(value="/chat-api-rest" , headers="Accept=application/json")
@SessionAttributes("utilisateur")
public class ChatController {
    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/users")
	public List<Utilisateur> getUsers(HttpSession session){
		List<Utilisateur> utilisateurs = null;
//		if(idUtilisateur!=null) {
//			utilisateurs= (List<Utilisateur>) utilisateurService.findInfoUtilisateur(idUtilisateur) ;
//		for (Utilisateur uti : utilisateurs) {System.out.println("on peut discuter avec ces personnes" + uti.getNom());}}
//		else {
			utilisateurs = utilisateurService.rechercherUtilisateur();

		return utilisateurs;
	}

}
