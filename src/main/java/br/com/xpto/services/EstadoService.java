package br.com.xpto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.xpto.entidades.Estado;
import br.com.xpto.repository.EstadoRepositoy;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepositoy estadoRepository;
	
	public Estado salvar(Estado estado){
		return estadoRepository.save(estado);
		
	}
	
	public Estado finByNome(String nome){
		return estadoRepository.findByNome(nome);
	}
	
	public List<Estado> findAll(){
		return estadoRepository.findAll();
	}
	
	
	public void deleteAll(){
		estadoRepository.deleteAll();
	}


}
