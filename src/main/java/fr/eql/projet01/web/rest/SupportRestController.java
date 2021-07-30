package fr.eql.projet01.web.rest;

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

import fr.eql.projet01.entity.Support;
import fr.eql.projet01.exception.AecServiceException;
import fr.eql.projet01.exception.IllegalOperationException;
import fr.eql.projet01.exception.NotValidObjectException;
import fr.eql.projet01.exception.ResourceNotFoundException;
import fr.eql.projet01.service.AnnonceService;
import fr.eql.projet01.service.PublicationService;
import fr.eql.projet01.service.SupportService;
import fr.eql.projet01.ui.request.SupportRequest;

@RestController
@RequestMapping(value="/aec-api-rest/supports", headers="Accept=application/json")
public class SupportRestController {
	@Autowired
	private SupportService supportService;
	@Autowired
	private AnnonceService annonceService;
	@Autowired
	private PublicationService publicationService;
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/simpleListe")
	public List<Support> getAll() {
		return supportService.findAll();
	}

	@GetMapping
	public Page<Support> findAllByPage(Pageable page) {
		return supportService.findAllByPage(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable(required = true) Long id) {
		Support result;
		try {
			result = supportService.findOne(id);
	} catch (ResourceNotFoundException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(result,HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody(required = true) SupportRequest supportRequest, @PathVariable(required = true) Long id) {
		Support saved;
		try {
			Assert.notNull(supportRequest, "Ne peut pas être null.");
			Assert.notNull(supportRequest.getId(), "L'identifiant ne peut être null");
			Assert.isTrue(id.equals(supportRequest.getId()), "L'id de l'url ne correspond pas à celui de l'objet envoyé.");
			Support support = supportService.findOne(supportRequest.getId());
			support.setTypeSupport(supportRequest.getTypeSupport());
			support.setChemin(supportRequest.getChemin());
			support.setImage(supportRequest.getImage());
			// controle si l'annonce existe
			if (supportRequest.getAnnonceId()!=null) {
				support.setAnnonceSupport(annonceService.findOne(supportRequest.getAnnonceId()));
			} else {
				support.setAnnonceSupport(null);
			}
			// controle si la publication existe
			if (supportRequest.getPublicationId()!=null) {
				support.setPublicationSupport(publicationService.findById(supportRequest.getPublicationId()));
			} else {
				support.setPublicationSupport(null);
			}
			saved = supportService.save(support);
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
	public ResponseEntity<?> create(@RequestBody(required = true) SupportRequest supportRequest){
		Support s;
		try {
			Assert.isNull(supportRequest.getId(), "L'identifiant doit être null");
			Assert.notNull(supportRequest, "Ne peut pas être null.");
			Support support = new Support();
			support.setTypeSupport(supportRequest.getTypeSupport());
			support.setChemin(supportRequest.getChemin());
			support.setImage(supportRequest.getImage());
			// controle si l'annonce existe
			if (supportRequest.getAnnonceId()!=null) {
				support.setAnnonceSupport(annonceService.findOne(supportRequest.getAnnonceId()));
			} else {
				support.setAnnonceSupport(null);
			}
			// controle si la publication existe
			if (supportRequest.getPublicationId()!=null) {
				support.setPublicationSupport(publicationService.findById(supportRequest.getPublicationId()));
			} else {
				support.setPublicationSupport(null);
			}
			s = supportService.save(support);
		} catch (NotValidObjectException e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(s, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) throws AecServiceException {
		Map<String,Object> mapRes = new HashMap<>();
		try {
			supportService.delete(id);
			mapRes.put("message", "Support bien supprimée pour l'id "+id);
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