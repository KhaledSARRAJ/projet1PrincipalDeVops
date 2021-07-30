package fr.eql.projet01.web.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

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
import fr.eql.projet01.entity.Theme;
import fr.eql.projet01.exception.AecServiceException;
import fr.eql.projet01.exception.IllegalOperationException;
import fr.eql.projet01.exception.NotValidObjectException;
import fr.eql.projet01.exception.ResourceNotFoundException;
import fr.eql.projet01.service.AnnonceService;
import fr.eql.projet01.service.ThemeService;
import fr.eql.projet01.ui.request.ThemeRequest;

@RestController
@RequestMapping(value="/aec-api-rest/themes", headers="Accept=application/json")
public class ThemeRestController {
	@Autowired
	private ThemeService themeService;
	@Autowired
	private AnnonceService annonceService;
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/simpleListe")
	public List<Theme> getAll() {
		return themeService.findAll();
	}

	@GetMapping
	public Page<Theme> findAllByPage(Pageable page) {
		return themeService.findAllByPage(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable(required = true) Long id) {
		Theme result;
		try {
			result = themeService.findOne(id);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(result,HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody(required = true) ThemeRequest themeRequest, @PathVariable(required = true) Long id) {
		Theme saved;
		try {
			Assert.notNull(themeRequest, "Ne peut pas être null.");
			Assert.notNull(themeRequest.getId(), "L'identifiant ne peut être null");
			Assert.isTrue(id.equals(themeRequest.getId()), "L'id de l'url ne correspond pas à celui de l'objet envoyé.");
			Theme theme = themeService.findOne(themeRequest.getId());
			theme.setTitre(themeRequest.getTitre());
			theme.setDescription(themeRequest.getDescription());
			ArrayList<Annonce> listeAnnonce = new ArrayList<Annonce>();
			for(Long annonceId : themeRequest.getAnnoncesId())
				listeAnnonce.add(annonceService.findOne(annonceId));
				theme.setListAnnonceTheme(listeAnnonce);
			saved = themeService.save(theme);
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
	public ResponseEntity<?> create(@RequestBody(required = true) ThemeRequest themeRequest) {
		Theme t;;
		try {
			Assert.isNull(themeRequest.getId(), "L'identifiant doit être null");
			Assert.notNull(themeRequest, "Ne peut pas être null.");
			Theme theme = new Theme();
			theme.setTitre(themeRequest.getTitre());
			theme.setDescription(themeRequest.getDescription());
			ArrayList<Annonce> listeAnnonce = new ArrayList<Annonce>();
			for(Long annonceId : themeRequest.getAnnoncesId())
				listeAnnonce.add(annonceService.findOne(annonceId));
			theme.setListAnnonceTheme(listeAnnonce);
			t = themeService.save(theme);
		} catch (NotValidObjectException e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(t, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) throws AecServiceException {
		Map<String,Object> mapRes = new HashMap<>();
		try {
			themeService.delete(id);
			mapRes.put("message", "theme bien supprimée pour l'id "+id);
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