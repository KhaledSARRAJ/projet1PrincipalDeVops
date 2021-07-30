package fr.eql.projet01.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eql.projet01.dao.SexeRepository;
import fr.eql.projet01.dao.VilleRepository;
import fr.eql.projet01.entity.Utilisateur;
import fr.eql.projet01.service.UtilisateurService;

@Controller
public class UtilisateurControler {

	@Autowired
	private UtilisateurService utilisateurService;
	
	@Autowired
	private SexeRepository sexeRepository;

	@Autowired
	private VilleRepository villeRepository;
	
	// l'url affInfoUti?id=1 @RequestParam("id")Long id
	// autre solution @PathParam("id")Long id
	

	@GetMapping("/affInfoUti")
	public String affInfoUti(Model model, @RequestParam("id") Long id) {
		
		if (id != null) {
			Utilisateur uti = utilisateurService.rechercheUtiParId(id);
			if (uti != null) {
				model.addAttribute("utilisateur", uti);
				return "profil";
			} else {
				return "profilErreur";
			}
		} else {
			return "profilErreur";
		}
	}
	
	@GetMapping("/majUti")
	public String majUti(Model model, @RequestParam("id") Long id) {

		model.addAttribute("listeSexe",sexeRepository.findAll() );
		model.addAttribute("listeVille",villeRepository.findAll() );
		
		if (id != null) {
			Utilisateur uti = utilisateurService.rechercheUtiParId(id);
			if (uti != null) {
				model.addAttribute("utilisateur", uti);
				return "profilMaj";
			} else {
				return "profilErreur";
			}
		} else {
			return "profilErreur";
		}
	}
	
	@PostMapping("/sauvegardeUti")
	public String SauvegardeUti(Model model, @ModelAttribute("utilisateur") Utilisateur uti) {	
		model.addAttribute("utilisateur", utilisateurService.sauvegardeUtilisatuer(uti));
		return "profil";
	}



}
