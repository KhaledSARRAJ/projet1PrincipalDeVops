package fr.eql.projet01.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eql.projet01.entity.Abonnement;
import fr.eql.projet01.entity.Publication;
import fr.eql.projet01.entity.Support;
import fr.eql.projet01.entity.Utilisateur;
import fr.eql.projet01.service.AbonnementService;
import fr.eql.projet01.service.SupportService;
import fr.eql.projet01.service.UtilisateurService;

@Controller
//@RequestMapping("/mur")
//@SessionAttributes(value = "idUtilisateurConnecte")
public class MurPrincipalController {

	@Autowired
	private AbonnementService aboService;

	@Autowired
	private UtilisateurService utilisateurService; 

	@Autowired
	private SupportService supportService;

	//request param sera l'utilisateur connecté
	@GetMapping("/mur")
	public String AffichePublications(Model model,@RequestParam("id")long id) {
		Utilisateur uti = utilisateurService.findInfoUtilisateur(id);
		List<Abonnement> listeAbo = aboService.findAllFollowerByUtilisateur(uti);
		List<Utilisateur> listUtilisateur = new ArrayList<Utilisateur>();
		List<Publication> listPublication = new ArrayList<Publication>();

		for (Abonnement abo : listeAbo) {
			Utilisateur a = abo.getFollowing();
			listUtilisateur.add(a);
		}

		for (Utilisateur u : listUtilisateur) {
			List<Publication> p = u.getListPublication();
			for(Publication m : p) {
				List<Support> listSupport = supportService.findSupportByPublication(m);
				m.setSupport(listSupport);
			}
			listPublication.addAll(p);
		}

		model.addAttribute("user", utilisateurService.findInfoUtilisateur(id));
		if(listeAbo!=null && !listeAbo.isEmpty()){
			model.addAttribute("uti",uti);
			model.addAttribute("listeAbo",listeAbo);
			model.addAttribute("listPublication",listPublication);
		}
		return "mur"; 
	}

	@GetMapping("/profilUti")
	public String affInfoUti(Model model, @RequestParam("id") Long id) {

		if (id != null) {
			Utilisateur uti = utilisateurService.rechercheUtiParId(id);
			if (uti != null) {
				List<Publication> p = uti.getListPublication();
				for(Publication m: p) {
					List<Support> listSupport = supportService.findSupportByPublication(m);
					m.setSupport(listSupport);
				}
				model.addAttribute("utilisateur", uti);
				model.addAttribute("publications", p);
				return "ProfilAutreUtilisateur";
			} else {
				return "profilErreur";
			}
		} else {
			return "profilErreur";
		}
	}
}