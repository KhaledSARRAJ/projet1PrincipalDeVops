package fr.eql.projet01.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import fr.eql.projet01.beans.SignalementBean;

@FeignClient(name = "gestion-signalement", url = "localhost:8081/aec-api-rest/signalements")
public interface GestionSignalementProxy {
	
	@GetMapping("/countForMe/{type}/{identifiant}")
	public Long countForMe(@PathVariable(required = true) String type, @PathVariable(required = true) Long identifiant);
	
	@GetMapping
	public Page<SignalementBean> findAllByPage(Pageable page);
	
	@GetMapping("/simpleListe")
	public List<SignalementBean> getAll();
	
	@GetMapping("/{id}")
	public SignalementBean getOne(@PathVariable(required = true) Long id);
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody(required = true) SignalementBean signalementBean);
}