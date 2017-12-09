package br.com.xpto.restcontroller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.com.xpto.entidades.Capital;
import br.com.xpto.entidades.Cidade;
import br.com.xpto.entidades.auxiliares.EstadoAndQuantidadeDeCidades;
import br.com.xpto.services.CidadeService;

@RestController
public class CidadeRest {
	
	
	@Autowired
	private CidadeService cidadeService;

	@RequestMapping(value="/cidadeCapitais", method = RequestMethod.GET)
	public List<Cidade> cidadePorNomeOrdenado(){
		return cidadeService.getCidadeCapitalOrdemByNome(Capital.SIM);
	}
	
	@RequestMapping(value="/estadoMaiorMenor", method = RequestMethod.GET)
	public List<EstadoAndQuantidadeDeCidades> getEstadoMaiorMenor(){
		return cidadeService.findEstadoMaiorMenor();
	}
	
	@RequestMapping(value="/cidadesPorEstado", method = RequestMethod.GET)
	public List<EstadoAndQuantidadeDeCidades> getCidadesPorEstado(){
		return cidadeService.findcidadesPorEstado();
	}
	
	@RequestMapping(value="/cidade/{idIbge}", method = RequestMethod.GET)
	public Cidade getCidade(@PathVariable(value = "idIbge") Integer idIbge){
		return  cidadeService.findByCidadeByIdIbge(idIbge);
		
	}
	
	@RequestMapping(value="/cidadePorEstado/{estado}", method = RequestMethod.GET)
	public List<String> getNomeCidade(@PathVariable(value = "estado") String estado){
		return  cidadeService.findByEstado(estado);
		
	}
	
	
	@RequestMapping(value="/adicionarCidade", method = RequestMethod.POST)
	public Cidade addCidade(@RequestBody Cidade cidade){
		return  cidadeService.adicionarCidade(cidade);
		
	}
	
	@RequestMapping(value="/cidade/{idIbge}", method = RequestMethod.DELETE)
	public void deleteCidade(@PathVariable(value = "idIbge") Integer idIbge){
		cidadeService.deleteCidade(idIbge);
	}
	
	//9
	@RequestMapping(value="/cidade/{coluna}/{conteudo}", method = RequestMethod.GET)
	public List<Cidade> getCidadePorColunaAndConteudo(@PathVariable(value = "coluna") String coluna,@PathVariable(value = "conteudo") String conteudo){
		return cidadeService.getDados(coluna, conteudo);
	}
	
	//10
	@RequestMapping(value="/count/{coluna}", method = RequestMethod.GET)
	public Long geRegistrosPorColuna(@PathVariable(value = "coluna") String coluna){
		return cidadeService.getRegistrosPorColuna(coluna);
	}
	
	//11
	@RequestMapping(value="/countRegistros", method = RequestMethod.GET)
	public Long geRegistros(){
		return cidadeService.getRegistros();
	}
	
	//12
	@RequestMapping(value="/cidadesMaisDistantes", method = RequestMethod.GET)
	public List<Cidade> getCidadesMaisDistantes(){
		return cidadeService.getCidadesMaisDistantes();
	}
	
	
	@RequestMapping(value="/reiniciar", method = RequestMethod.GET)
	public String reiniciarAplicacao(){
		return cidadeService.reinicar();
	}

}
