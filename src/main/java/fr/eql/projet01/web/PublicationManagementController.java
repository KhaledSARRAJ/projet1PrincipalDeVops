package fr.eql.projet01.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.eql.projet01.beans.SignalementBean;
import fr.eql.projet01.entity.Publication;
import fr.eql.projet01.entity.Support;
import fr.eql.projet01.entity.Utilisateur;
import fr.eql.projet01.proxies.GestionMotifSignalementProxy;
import fr.eql.projet01.proxies.GestionSignalementProxy;
import fr.eql.projet01.service.PublicationService;
import fr.eql.projet01.service.SupportService;
import fr.eql.projet01.service.UtilisateurService;

@Controller
//@RequestMapping("/user/product")
@SessionAttributes("utilisateur")
public class PublicationManagementController {

	private PublicationService productService;
	@Autowired
	private UtilisateurService utilisateurService; 
	@Autowired
	public void setProductService(PublicationService productService) {
		this.productService = productService;
	}
	@Autowired
	private SupportService supportService;

	@Autowired
	private SupportService supportservice;

	@Autowired
	private GestionMotifSignalementProxy gmsp;

	@Autowired
	private GestionSignalementProxy gsp;

	@GetMapping("/signalement")
	public String motifSignalement(Model model, @RequestParam("idpublication") Long idpublication, @RequestParam("userid") Long userid) {
		try {
			model.addAttribute("idpublication", idpublication);
			model.addAttribute("userid", userid);
			model.addAttribute("listeMotifSignalement", gmsp.getAll());
			model.addAttribute("message", "Console de signalement");
		} catch(Exception e) {
			model.addAttribute( "message", " Oups ! un problème est survenu, veuillez rééssayer ultérieurement");
		}
		return "/signalement";
	}

	@RequestMapping("/traitement-signalement")
	public String traitementSignalement(Model model,
			@Valid SignalementBean signalementBean, @Valid Long motifSignalementBeanId) {
		signalementBean.setType(Publication.NAME);
		try {
			signalementBean.setMotifSignalement(gmsp.getOne(motifSignalementBeanId));
			gsp.create(signalementBean);
			Publication publication = productService.findById(signalementBean.getIdentifiant());
			publication.setNbSignalement(gsp.countForMe(signalementBean.getType(), signalementBean.getIdentifiant()));
			productService.saveOrUpdate(publication);
			model.addAttribute("message", "Opération réussit");
			model.addAttribute("success", true);
		} catch(RuntimeException e) {
			model.addAttribute("message", e.getMessage().toString() + "Oups ! un problème est survenu, veuillez rééssayer !");	
		}
		return "/signalement";
	}

	@GetMapping(path = "/Publications")
	public String publications(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "motCle", defaultValue = "") String motCle,
			HttpSession session) {
		//		Utilisateur uti = utilisateurService.findInfoUtilisateur(id);
		//		       
		//		    	List<Publication> p = uti.getListPublication();
		//		    	List<Publication> listpub = new ArrayList<Publication>();
		//		    	 for(Publication m: p) {
		//		      	    	List<Support> listSupport = supportService.findSupportByPublication(m);
		//		      	    	m.setSupport(listSupport);
		//		              }
		//		    	 listpub.addAll(p);
		//		    
		//		Utilisateur utilisateur= new Utilisateur();
		//		utilisateur=utilisateurService.findInfoUtilisateur(id);
		//System.out.println(utilisateur.getNom());

		Page<Publication> publicationsList = productService.findByUtilisateurAndTitreIgnoreCaseContains((Utilisateur) session.getAttribute("utilisateur"),motCle,	PageRequest.of(page, size));

		model.addAttribute("user", (Utilisateur) session.getAttribute("utilisateur"));
		model.addAttribute("publicationsList", publicationsList);
		for (Publication publication : publicationsList) {
			System.out.println(publication.getTitre());
		}
		model.addAttribute("currentPage", page);
		model.addAttribute("size", size);
		model.addAttribute("motCle", motCle);
		model.addAttribute("pages", new int[publicationsList.getTotalPages()]); // 

		return "publicationManagement"; 
	}


	/*	@GetMapping("/find")
	public String getProductById(HttpSession session, Model model) {
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
		System.out.println("ID: " + utilisateur.getId());
		Publication optionalProductDto = productService.findById(utilisateur.getId());

		List<Support> listSupport = supportservice.findByPublicationSupport(optionalProductDto);
		optionalProductDto.setSupport(listSupport);

		model.addAttribute("productDto", optionalProductDto);
		return "adminProductDetails";
	}*/

	@RequestMapping("/find/{id}")
	public String getProductById( @PathVariable("id") Long id, HttpSession session, Model model) {
		//Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
		//System.out.println("ID: " + utilisateur.getId());
		Publication optionalProductDto = productService.findById(id);

		List<Support> listSupport = supportservice.findByPublicationSupport(optionalProductDto);
		optionalProductDto.setSupport(listSupport);
		model.addAttribute("productDto", optionalProductDto);
		return "adminProductDetails";
	}

	@RequestMapping(path = "/deletePublications")
	public String delete(long id, String motCle, String page, String size, RedirectAttributes redirectAttributes) {
		productService.deleteById(id);
		redirectAttributes.addFlashAttribute("message", "Votre publication a bien été supprimée");
		redirectAttributes.addFlashAttribute("alertClass", "alert-info");
		return "redirect:/Publications?page=" + page + "&motCle=" + motCle + "&size=" + size;

	}

	@GetMapping(value = "/FormPublication")
	public String formPublication(Model model) {
		model.addAttribute("productDto", new Publication());
		return "FormPublication";
	}

	@RequestMapping("/save")
	public String save(Model model, @ModelAttribute("productDto") Publication publication, 
			HttpSession session, BindingResult bindingresult) {
		if (bindingresult.hasErrors()) {
			return "publicationAddForm";
		}
		publication.setUtilisateur((Utilisateur) session.getAttribute("utilisateur"));
		productService.saveOrUpdate(publication);
		model.addAttribute("productDto", publication);
		return "adminProductDetails";

	}
	@RequestMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id, HttpSession session) {
		Publication p = productService.findById(id);
		// .orelse(null)
		List<Support> listSupport = supportservice.findByPublicationSupport(p);
		p.setSupport(listSupport);

		model.addAttribute("productDto", p);
		System.out.println(p);
		return "EditPublication";
	}

	@GetMapping("/uploadImageForm/{id}")
	public String showImageForm(@PathVariable("id") Long id,Model model){
		//productService.findById(id);
		model.addAttribute("id",id);
		return "publicationAddImageFrom";    }

	@PostMapping("/upload/{id}")
	public String uploadFile(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file,Model model, RedirectAttributes redirectAttributes){
		System.out.println("id = " + id);
		System.out.println("file.getSize() = "+file.getSize());
		System.out.println("Image Size KB:" + file.getSize() / 1024);
		if (file.getSize() == 0){
			redirectAttributes.addFlashAttribute("message","Select Image file (jpg/png)");
			redirectAttributes.addFlashAttribute("alertClass","alert-danger");
			return "redirect:/uploadImageForm/"+id;
		}

		if (file.getSize()/1024 >= 300){
			redirectAttributes.addFlashAttribute("message","Image size is not valid ( max size should be less than 300 KB )");
			redirectAttributes.addFlashAttribute("alertClass","alert-danger");
			return "redirect:/uploadImageForm/"+id;
		}

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (!(fileName.endsWith(".jpg") || fileName.endsWith(".png")) ){
			redirectAttributes.addFlashAttribute("message","Image type is not valid");
			redirectAttributes.addFlashAttribute("alertClass","alert-danger");
			return "redirect:/uploadImageForm/"+id;
		}
		Publication dto= new Publication();
		Path path= Paths.get("src/main/resources/static/"+fileName);
		try {

			Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
			dto = productService.findById(id);
			//  dto.getSupport().setImage(file.getBytes());
			List<Support> listSupport = supportservice.findByPublicationSupport(dto);
			dto.setSupport(listSupport);


			//rajout pour la démo 
			System.out.println(fileName);
			Support sup = new Support();
			sup.setChemin(fileName);
			sup.setPublicationSupport(dto);
			sup.setTypeSupport("image");
			supportservice.enregisterSupport(sup);

			model.addAttribute("productDto", dto);
			productService.saveOrUpdate(dto);
			redirectAttributes.addFlashAttribute("message","Image successfully uploaded. file name : " + fileName);
			redirectAttributes.addFlashAttribute("alertClass","alert-success");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/Publications";
	}
}