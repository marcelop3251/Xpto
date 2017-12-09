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
import br.com.xpto.repository.myrepository.CidadesRepository;

@Service
public class CidadeService {
	
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CidadesRepository customRepository;
	
	
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

	public Double getCidadesMaisDistantes() {
		List<Cidade> first10 = cidadeRepository.findByNomeLike("%abadia%");
		Cidade c1 = first10.get(0);
		Cidade c2 = first10.get(1);
		
		return CalculaDistancia(c1.getLatitude(),c1.getLongitude(), c2.getLatitude(), c2.getLongitude());
	}
	
	
	private Double CalculaDistancia(double lat1, double long1, double lat2, double long2){
		

/* esse algorítimo é valido		
		double raioDaTerra = 6371.0;
		double theta = long2 - long1;
		double thetaLong= long2 - long1;
		double thetaLat = lat2 - lat1;
		double dist = Math.sin(degForRad(lat1)) * Math.sin(degForRad(lat2)) + Math.cos(degForRad(lat1)) * Math.cos(degForRad(lat2)) * Math.cos(degForRad(theta));
		dist = Math.acos(dist);
		dist = radForDeg(dist);
		dist = dist * 60 * 1.1515*1.609344;
		*/
		
	/*	dlon = lon2 - lon1 
				dlat = lat2 - lat1 
				a = (sin(dlat/2))^2 + cos(lat1) * cos(lat2) * (sin(dlon/2))^2 
				c = 2 * atan2( sqrt(a), sqrt(1-a) ) 
				d = R * c (where R is the radius of the Earth)
	 */
		
		double raioDaTerra = 6371.0;
		double theta = long2 - long1;
		double thetaLong= long2 - long1;
		double thetaLat = lat2 - lat1;
		double dist = Math.pow(Math.sin(degForRad(thetaLat/2)),2) + 
				Math.pow(Math.sin(degForRad(thetaLong/2)),2) * Math.cos(degForRad(lat1)) * Math.cos(degForRad(lat2));
		return raioDaTerra * (2* Math.atan2(Math.sqrt(dist), Math.sqrt(1-dist)));
		
		
	}
	
	private double degForRad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	
	private double radForDeg(double rad) {
		return (rad * 180 / Math.PI);
	}
	
}
