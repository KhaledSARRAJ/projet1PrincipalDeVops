package fr.eql.projet01.web.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.eql.projet01.entity.Annonce;
import fr.eql.projet01.entity.Support;
import fr.eql.projet01.entity.Theme;
import fr.eql.projet01.exception.AecServiceException;
import fr.eql.projet01.exception.IllegalOperationException;
import fr.eql.projet01.exception.NotValidObjectException;
import fr.eql.projet01.exception.ResourceNotFoundException;
import fr.eql.projet01.service.AnnonceService;
import fr.eql.projet01.service.SupportService;
import fr.eql.projet01.service.ThemeService;
import fr.eql.projet01.service.UtilisateurService;
import fr.eql.projet01.ui.request.AnnonceRequest;
import fr.eql.projet01.ui.response.AnnonceResponse;
import fr.eql.projet01.ui.response.SupportResponse;
import fr.eql.projet01.ui.response.ThemeResponse;



@RestController
@RequestMapping(value="/aec-api-rest/annonces", headers="Accept=application/json")
public class AnnonceRestController {
	@Autowired
	private AnnonceService annonceService;
	@Autowired
	private SupportService supportService;
	@Autowired
	private UtilisateurService utilisateurService;
	@Autowired
	private ThemeService themeService;
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@GetMapping("/infos")
	public ResponseEntity<?> getAllWithinfos() {
		ArrayList<AnnonceResponse> annoncesResponse = new ArrayList<AnnonceResponse>();
		try {
			for(Annonce a : annonceService.findAll()) {
				AnnonceResponse annonceResponse = new AnnonceResponse();
				annonceResponse.setId(a.getId());
				annonceResponse.setTitre(a.getTitre());
				annonceResponse.setTexte(a.getTexte());
				annonceResponse.setDateParution(a.getDateParution());
				annonceResponse.setPrix(a.getPrix());
				annonceResponse.setPrenomDupublisher(a.getUtilisateur().getPrenom());
				ArrayList<SupportResponse> tab = new ArrayList<SupportResponse>();
				for(Support s : supportService.findSupportByAnnonce(a)) {
					SupportResponse supportResponse = new SupportResponse();
					supportResponse.setId(s.getId());
					supportResponse.setChemin(s.getChemin());
					supportResponse.setImage(s.getImage());
					supportResponse.setTypeSupport(s.getTypeSupport());
					tab.add(supportResponse);
					annonceResponse.setListeDesSupports(tab);
				}
				ArrayList<ThemeResponse> tab1 = new ArrayList<ThemeResponse>();
				for(Theme t : themeService.findAll()) {
					ThemeResponse themeResponse = new ThemeResponse();
					if(a.getTheme().contains(t)) {
						themeResponse.setId(t.getId());
						themeResponse.setTitre(t.getTitre());
						themeResponse.setDescription(t.getDescription());
						tab1.add(themeResponse);
						annonceResponse.setListeDesThemes(tab1);
					}
				}
				annoncesResponse.add(annonceResponse);
			}
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(annoncesResponse,HttpStatus.OK);
	}

	@GetMapping("infos/{id}")
	public ResponseEntity<?> getOneWithInfos(@PathVariable(required = true) Long id) {
		ArrayList<SupportResponse> tab = new ArrayList<SupportResponse>();     
		AnnonceResponse annonceResponse = new AnnonceResponse();
		Annonce result;
		try {
			result = annonceService.findOne(id);
			annonceResponse.setId(result.getId());
			annonceResponse.setTitre(result.getTitre());
			annonceResponse.setTexte(result.getTexte());
			annonceResponse.setDateParution(result.getDateParution());
			annonceResponse.setPrix(result.getPrix());
			annonceResponse.setPrenomDupublisher(result.getUtilisateur().getPrenom());	
			for(Support s : supportService.findSupportByAnnonce(result)) {
				SupportResponse supportResponse = new SupportResponse();
				supportResponse.setId(s.getId());
				supportResponse.setChemin(s.getChemin());
				supportResponse.setImage(s.getImage());
				supportResponse.setTypeSupport(s.getTypeSupport());
				tab.add(supportResponse);
				annonceResponse.setListeDesSupports(tab);
			}
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(annonceResponse,HttpStatus.OK);
	}

	@GetMapping("/simpleListe")
	public List<Annonce> getAll() {
		return annonceService.findAll();
	}

	@GetMapping
	public Page<Annonce> findAllByPage(Pageable page) {
		return annonceService.findAllByPage(page);
	}

	@GetMapping("/mesAnnonces/{userId}")
	public ResponseEntity<?> getMyAnnonces(@PathVariable(required = true) Long userId) {
		List<Annonce> result;
		try {
			result = annonceService.search(userId);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(result,HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable(required = true) Long id) {
		Annonce result;
		try {
			result = annonceService.findOne(id);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(result,HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody(required = true) AnnonceRequest annonceRequest, @PathVariable(required = true) Long id) {
		Annonce saved;
		try {
			Assert.notNull(annonceRequest, "Ne peut pas être null.");
			Assert.notNull(annonceRequest.getId(), "L'identifiant ne peut être null");
			Assert.isTrue(id.equals(annonceRequest.getId()), "L'id de l'url ne correspond pas à celui de l'objet envoyé.");
			Annonce annonce = annonceService.findOne(annonceRequest.getId());
			annonce.setTitre(annonceRequest.getTitre());
			annonce.setTexte(annonceRequest.getTexte());
			annonce.setPrix(annonceRequest.getPrix());
			annonce.setUtilisateur(utilisateurService.findOne(annonceRequest.getUserId()));
			saved = annonceService.save(annonce);
		}catch(ResourceNotFoundException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);
		}catch(NotValidObjectException e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(saved,HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody(required = true) AnnonceRequest annonceRequest){
		Annonce a;
		try {
			Assert.isNull(annonceRequest.getId(), "L'identifiant doit être null");
			Assert.notNull(annonceRequest, "Ne peut pas être null.");
			Annonce annonce = new Annonce();
			annonce.setTitre(annonceRequest.getTitre());
			annonce.setTexte(annonceRequest.getTexte());
			annonce.setPrix(annonceRequest.getPrix());
			annonce.setDateParution(new Date());
			annonce.setUtilisateur(utilisateurService.findOne(annonceRequest.getUserId()));
			a = annonceService.save(annonce);
		} catch (NotValidObjectException e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(a, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) throws AecServiceException {
		Map<String,Object> mapRes = new HashMap<>();
		try {
			annonceService.delete(id);
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
}