package fr.eql.projet01.service;



import java.util.ArrayList;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import fr.eql.projet01.dao.PublicationRepository;
import fr.eql.projet01.entity.Publication;
import fr.eql.projet01.entity.Utilisateur;



@Service
@Transactional
public class PublicationServiceImpl implements PublicationService {

	
	@Autowired
	private PublicationRepository publicationRepository;

	@Override
	public List<Publication> findPublicationByUser(Utilisateur u) {
		return publicationRepository.findByUtilisateur(u);
	}


 
    @Override
    public Publication saveOrUpdate(Publication productDto) {
        if (productDto == null) throw new IllegalArgumentException("ProductDto is not valid");
        Publication savedObject = publicationRepository.save(productDto);
        return savedObject;
    }

    @Override
    public List<Publication> getAll() {
        Iterable<Publication> iterable = publicationRepository.findAll();
        List<Publication> productList = new ArrayList<>();
        iterable.iterator().forEachRemaining(productList::add);

        // convert list of product to list of dto
        return productList;
    }

    
    public Publication findById(Long id) {
        if (id == 0) throw new IllegalArgumentException("Id should not be null");
        Publication product = publicationRepository.findById(id).get();
        return product;
    }

    @Override
    public void deleteById(Long id) {
        if (id == 0) throw new IllegalArgumentException("Id should not be null");
        publicationRepository.deleteById(id); 
    }
//
//    @Override
//    public List<Publication> findBySupportTypeSupport(String name) {
//        if (name == null) throw new IllegalArgumentException("name should not be null");
//        List<Publication> productList = productRepository.findByTitreIgnoreCase(name);
//        return productList;
//    }

	@Override
	public Page<Publication> findByTitreIgnoreCaseContains(String mc, Pageable pageable) {
		return publicationRepository.findByTitreIgnoreCaseContains( mc,  pageable);
	}



	public Page<Publication> findByUtilisateurAndTitreIgnoreCaseContains(Utilisateur utilisateur, String titre, Pageable pageable) {
		return publicationRepository.findByUtilisateurAndTitreIgnoreCaseContains(utilisateur, titre,pageable);
	}

//	@Override
//	public List<Publication> findBySupportDetailsName(String name) {
//		return productRepository.findBySupportDetailsName( name);
//	}

}
