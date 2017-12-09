package br.com.xpto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.xpto.entidades.Arquivo;
import br.com.xpto.repository.ArquivoRepository;


@Service
public class ArquivoService {
	
	@Autowired
	private ArquivoRepository arquivoRepository;
	
	public void salvar(Arquivo arquivo){
		arquivoRepository.save(arquivo);
	}

}
