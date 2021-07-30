package fr.eql.projet01.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import fr.eql.projet01.dao.DroitsRepository;
import fr.eql.projet01.dao.SexeRepository;
import fr.eql.projet01.dao.UtilisateurRepository;
import fr.eql.projet01.dao.VilleRepository;
import fr.eql.projet01.entity.Droits;
import fr.eql.projet01.entity.Sexe;
import fr.eql.projet01.entity.Utilisateur;
import fr.eql.projet01.entity.Ville;
import fr.eql.projet01.service.UtilisateurService;

@Controller
public class DefaultController {
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private UtilisateurService utilisateurService;
	@Autowired
	private SexeRepository sexeRepository;
	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private DroitsRepository droitRepository;
	@Autowired
	private PasswordEncoder PasswordEncoder;
	
    @GetMapping("/")
    public String home1() {
        return "/home";
    }

    @GetMapping("/home")
    public String home() {
        return "/home";
    }

    @GetMapping("/espaceConnect")
    public RedirectView espaceConnect(HttpSession session) {
    	Utilisateur uti = null;
    	RedirectView rv = new RedirectView();

    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	if (principal instanceof UserDetails) {
    	   String username = ((UserDetails)principal).getUsername();
    	   uti = utilisateurService.rechercherUtilisateurParProfil(username);
    	   session.setAttribute("utilisateur", uti);
    	}
    	
        rv.setContextRelative(true);
        rv.setUrl("/mur?id="+ uti.getId());
        //rv.setUrl("/s?id="+ uti.getId());
        return rv;
    }
    
    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }

    @GetMapping("/user")
    public String user(Model model) {
        model.addAttribute("users", utilisateurRepository.findAll());
        return "user";
    }

    @GetMapping("/about")
    public String about() {
        return "/about";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }
    
    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
    
    @ModelAttribute("idSession")
	public String idSession(HttpSession session) {
		return session.getId();
	}
    
    @RequestMapping("/session-end")
    public RedirectView finSession(Model model,HttpSession session) {
    	RedirectView rv = new RedirectView();
    	SecurityContextHolder.clearContext(); //RAZ context Spring security
	    session.invalidate();
	    rv.setContextRelative(false);
        rv.setUrl("/home");
        return rv; 
    }
    
    @ModelAttribute("utilisateur")
    public Utilisateur addUtilisateurInModel() {
    	return new Utilisateur();
    }
    
    @GetMapping("/inscription")
    public String inscription(Model model) {
    	model.addAttribute("listeSexe", sexeRepository.findAll());
    	model.addAttribute("listeVille", villeRepository.findAll());
    	model.addAttribute("message", "Inscription");
    	return "/inscription";
    }
      
    @RequestMapping("/traitement-inscription")
    public String traitementInscription(Model model, @Valid Long idsexe, @Valid Long idville, @Valid String password, @Valid String passwordConfirm,
    		@Valid Utilisateur utilisateur, BindingResult bindingResult) {
    	//RedirectView rv = new RedirectView();
    	Date dateInscription = new Date();
    	//rv.setContextRelative(true);
    	String passwordEncode = null;
    	String redirect = null;
    	
    	model.addAttribute("listeSexe", sexeRepository.findAll());
    	model.addAttribute("listeVille", villeRepository.findAll());
    	
    	if(utilisateurRepository.existsByMail(utilisateur.getMail())) {
    		model.addAttribute("message", "L'adresse mail " + utilisateur.getMail() + " est déja utilisée !");
    		return "/inscription";
    	}
    	
    	if(utilisateurRepository.existsByProfile(utilisateur.getProfile())) {
    		model.addAttribute("message", "Le login " + utilisateur.getProfile() + " est déja utilisé !");
    		return "/inscription";
    	}
    	
    	if(password.equals(passwordConfirm)) {
    		passwordEncode = PasswordEncoder.encode(password);
    	} else {
    		model.addAttribute("message", "Les mots de passes sont différents !");
    		return "/inscription";
    	}
    	
    	Sexe sexe = sexeRepository.getById(idsexe);
    	Ville ville = villeRepository.getById(idville);
    	Droits droit = droitRepository.getById((long) 2);
    	utilisateur.setSexe(sexe);
    	utilisateur.setVille(ville);
    	utilisateur.setDroits(droit);
    	utilisateur.setDateInscription(dateInscription);
    	utilisateur.setPasseWord(passwordEncode);
    	
    	if(bindingResult.hasErrors()) {
    		redirect = "/inscription";
    		//rv.setUrl("/inscription"); 
    	} else {
    		try {
    			utilisateurRepository.save(utilisateur);
    			//rv.setUrl("/login");
    			redirect = "/login";
    		} catch (Exception e) {
    			model.addAttribute("message", "Oups, quelque chose c'est mal passé ! :-)");
    			//rv.setUrl("/inscription");
    			redirect = "/inscription";
;    		}
    	}
    	return redirect;
    }
    
    @InitBinder
    public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null,  new CustomDateEditor(dateFormat, true));
    }
}