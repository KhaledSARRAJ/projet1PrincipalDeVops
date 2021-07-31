package fr.eql.projet01.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.eql.projet01.beans.SignalementBean;
import fr.eql.projet01.entity.Abonnement;
import fr.eql.projet01.entity.Annonce;
import fr.eql.projet01.entity.Publication;
import fr.eql.projet01.entity.Support;
import fr.eql.projet01.entity.Utilisateur;
import fr.eql.projet01.exception.AecServiceException;
import fr.eql.projet01.exception.IllegalOperationException;
import fr.eql.projet01.exception.ResourceNotFoundException;
import fr.eql.projet01.proxies.GestionSignalementProxy;
import fr.eql.projet01.service.AbonnementService;
import fr.eql.projet01.service.AnnonceService;
import fr.eql.projet01.service.PublicationService;
import fr.eql.projet01.service.SupportService;
import fr.eql.projet01.service.UtilisateurService;

@RestController
@CrossOrigin (origins = { "http://localhost:4300"}) 
@RequestMapping(value = "/administrateur", headers = "Accept=application/json")
public class AdministrateurController {

	@Autowired
	private UtilisateurService utilisateurService;
	@Autowired
	private PublicationService publicationService;
	@Autowired
	private AnnonceService annoceService;
	@Autowired
	private AbonnementService aboService;
	@Autowired
	private SupportService supportService;
	
	// Ici l'admin peut voir tous les utilisateurs inscrits
	//http://localhost:8085/administrateur/users
	@GetMapping("/users")
	public List<Utilisateur> getUsers() {
		List<Utilisateur> utilisateurs = null;
		utilisateurs = utilisateurService.rechercherUtilisateur();
		return utilisateurs;
	}
	// Ici l'admin peut voir tous les publications
	//http://localhost:8085/administrateur/publications
	@GetMapping("/publications")
	public List<Publication> getPublications() {
		List<Publication> publications = null;
		publications = publicationService.getAll();
		return publications;
	}


//
//	@RequestMapping(path = "/deletePublications/{id}", method = RequestMethod.DELETE)
//	public void deletePublications(@PathVariable(value = "id") Long publicationId) {
//
//			Publication publication = publicationService.findById(publicationId);  
//			publicationService.deleteById(publication.getId());
//	}

	// Ici l'admin peut voir tous les annonces
	//http://localhost:8085/administrateur/annonces
	@GetMapping("/annonces")
	public List<Annonce> getAnnoces(HttpSession session) {
		List<Annonce> annonces = null;
		annonces = annoceService.findAll();
		return annonces;
	}

	// Ici l'admin peut voir tous les publications d'un utilisateur à l'aide de son
	// http://localhost:8085/administrateur/publications/1
	@GetMapping("/publications/{id}")
	public List<Publication> AffichePublications(@PathVariable("id") Long id) {
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
			for (Publication m : p) {
				List<Support> listSupport = supportService.findSupportByPublication(m);
				m.setSupport(listSupport);
			}
			listPublication.addAll(p);
		}
		return listPublication;

	}

	// Ici l'admin peut voir tous les abonnées d'un utilisateur à l'aide de son id
	//http://localhost:8085/administrateur/Abonnees/1
	@GetMapping("/Abonnees/{id}")
	public List<Abonnement> AfficheAbonnees(@PathVariable("id") Long id) {
		Utilisateur uti = utilisateurService.findInfoUtilisateur(id);
		List<Abonnement> listeAbo = aboService.findAllFollowerByUtilisateur(uti);
		return listeAbo;

	}


	@DeleteMapping("/deletePublications/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) throws AecServiceException {
		Map<String,Object> mapRes = new HashMap<>();
		try {
			publicationService.deleteById(id);
			mapRes.put("message", "publication bien supprimée pour l'id "+id);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (IllegalOperationException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.FORBIDDEN);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<Object>(mapRes,HttpStatus.OK);
	}
	@DeleteMapping("/deleteAnnonce/{id}")
	public ResponseEntity<?> deleteAnnonce(@PathVariable("id") Long id) throws AecServiceException {
		Map<String,Object> mapRes = new HashMap<>();
		try {
			annoceService.delete(id);
			mapRes.put("message", "Annonce bien supprimée pour l'id "+id);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (IllegalOperationException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.FORBIDDEN);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<Object>(mapRes,HttpStatus.OK);
	}
	@DeleteMapping("/deleteUsers/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) throws AecServiceException {
		Map<String,Object> mapRes = new HashMap<>();
		try {
			utilisateurService.deleteById(id);
			mapRes.put("message", "User bien supprimée pour l'id "+id);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (IllegalOperationException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.FORBIDDEN);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<Object>(mapRes,HttpStatus.OK);
	}
}
