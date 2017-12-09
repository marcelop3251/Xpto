package br.com.xpto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.xpto.entidades.Regiao;
import br.com.xpto.repository.RegiaoRepository;

@Service
public class RegiaoService {

	
	@Autowired
	private RegiaoRepository regiaoRepository;
	
	public Regiao salvar(Regiao regiao){
		return regiaoRepository.save(regiao);
	}

	public Regiao findByMicroRegionAndMesoRegion(String micro, String meso) {
		
		return regiaoRepository.findByMicroRegionAndMesoRegion(micro,meso);
	}
	
	public void deleteAll(){
		regiaoRepository.deleteAll();
	}
}
