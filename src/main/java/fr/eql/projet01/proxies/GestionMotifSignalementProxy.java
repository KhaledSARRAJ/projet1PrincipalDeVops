package fr.eql.projet01.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.eql.projet01.beans.MotifSignalementBean;

@FeignClient(name = "gestion-signalement", url = "http://18.224.51.251:8080/aec-api-rest/motifSignalements")
public interface GestionMotifSignalementProxy {

	@GetMapping
	public Page<MotifSignalementBean> findAllByPage(Pageable page);
	
	@GetMapping("/simpleListe")
	public List<MotifSignalementBean> getAll();
	
	@GetMapping("/{id}")
	public MotifSignalementBean getOne(@PathVariable(required = true) Long id);
}