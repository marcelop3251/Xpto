package br.com.xpto.services;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.xpto.entidades.Capital;
import br.com.xpto.entidades.Cidade;
import br.com.xpto.entidades.auxiliares.EstadoAndQuantidadeDeCidades;
import br.com.xpto.repository.CidadeRepository;
import br.com.xpto.repository.EstadoRepositoy;
import br.com.xpto.repository.myrepository.CidadesRepository;

@Service
public class CidadeService {
	
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CidadesRepository customRepository;
	
	@Autowired
	private EstadoService estadoService;
	
	
	@Autowired
	private RegiaoService regiaoService;
	
	
	public Cidade salvar(Cidade cidade){
		return cidadeRepository.save(cidade);
	}
	
	/**
	 * 
	 * @param capital
	 * 			Informa se a cidade deverá ser capital ou não através de um Enum
	 * @return
	 * 			Retorna somente as cidades que são capitais ordenadas por nome
	 */
	public List<Cidade> getCidadeCapitalOrdemByNome(Capital capital){
		return cidadeRepository.findByCapitalOrderByNomeDesc(capital);
		
	}

	/**
	 * 
	 * @return
	 * 		Retorna o nome do estado com maior número de estados com a maior e menor quantidade de cidades e a quantidade de cidades
	 */
	public List<EstadoAndQuantidadeDeCidades> findEstadoMaiorMenor() {
		 List<EstadoAndQuantidadeDeCidades> maiorMenor = cidadeRepository.findEstadoMaiorMenor();
		return Arrays.asList(maiorMenor
				.stream()
				.max(Comparator.comparingLong(EstadoAndQuantidadeDeCidades::getQuantidadeDeCidades))
				.map(e -> e).get(),maiorMenor
				.stream()
				.min(Comparator.comparingLong(EstadoAndQuantidadeDeCidades::getQuantidadeDeCidades))
				.map(e -> e).get());
	}
	
	 /**
	  * 
	  * @return
	  * 	Retorna a quantidade de cidades por estado
	  */
	public List<EstadoAndQuantidadeDeCidades> findcidadesPorEstado() {
		return cidadeRepository.findEstadoMaiorMenor();
	}

	/**
	 * 
	 * @param idIbge
	 * 		Obtem os dados da cidade informando o id do IBGE
	 * @return
	 * 		Uma cidade
	 */
	public Cidade findByCidadeByIdIbge(Integer idIbge) {		
		return cidadeRepository.findByIdIbge(idIbge);
	}

	public List<String> findByEstado(String estado) {		
		return cidadeRepository.findByEstado(estado);
	}

	public Cidade adicionarCidade(Cidade cidade) {
		
		Optional<Cidade> c = cidadeRepository.findByNomeAndEstado(cidade.getNome(),cidade.getEstado()); 
		if(c.isPresent()){
			throw new IllegalArgumentException("Não é possível adicionar uma cidade já existente no estado");
		}
		Integer idIbge = cidadeRepository.findFirstByOrderByIdIbgeDesc().getIdIbge();
		cidade.setIdIbge(++idIbge);
		return cidadeRepository.save(cidade);
	}

	@Transactional
	public void deleteCidade(Integer idIbge) {
		cidadeRepository.delete(idIbge);		
	}
	
	public List<Cidade> getDados(String coluna, String conteudo){
		return customRepository.findByColunaAndConteudo(coluna, conteudo);
	}

	/**
	 * 
	 * @param coluna
	 *  		a coluna que será utilizada para contagem dos registros
	 * @return
	 *			a quantidade de registros no banco dado uma coluna
	 */		
	public Long getRegistrosPorColuna(String coluna) {
		return customRepository.findByCountRegistroPorColuna(coluna);
	}
	
	/**
	 * @return
	 *			a quantidade de registros no banco
	 */		
	public Long getRegistros() {
		return cidadeRepository.count();
	}

	public List<Cidade> getCidadesMaisDistantes() {
		List<Cidade> cidadeList = cidadeRepository.findAll();
		
		Cidade cidade1Encontrada= null;
		Cidade cidad2Encontrada = null;
		
		double distanciaAtual,distanciaAnterior = 0;
		
		for(int i = 0; i < cidadeList.size(); i++){
			Cidade cidade1 = cidadeList.get(i);
			for(int j = i+1; j < cidadeList.size(); j++){
				Cidade cidade2 = cidadeList.get(j);
				distanciaAtual = calculaDistancia(cidade1.getLatitude(), cidade1.getLongitude(), cidade2.getLatitude(), cidade2.getLongitude());
				if(distanciaAtual > distanciaAnterior){
					cidade1Encontrada = cidade1;
					cidad2Encontrada = cidade2;
				}
			}
		}
		return Arrays.asList(cidade1Encontrada,cidad2Encontrada);
	}
	
	
	private Double calculaDistancia(double lat1, double long1, double lat2, double long2){
		double raioDaTerra = 6371.0;
		double thetaLong= long2 - long1;
		double thetaLat = lat2 - lat1;
		double dist = Math.pow(Math.sin(degForRad(thetaLat/2)),2) + 
				Math.pow(Math.sin(degForRad(thetaLong/2)),2) * Math.cos(degForRad(lat1)) * Math.cos(degForRad(lat2));
		return raioDaTerra * (2* Math.atan2(Math.sqrt(dist), Math.sqrt(1-dist)));
		
		
	}
	
	private double degForRad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	

	public String reinicar() {
		cidadeRepository.deleteAll();
		estadoService.deleteAll();
		regiaoService.deleteAll();		 
		return "Banco de dados deletado com sucesso";
		
	}
	
}
