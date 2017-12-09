package br.com.xpto.repository.myrepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import br.com.xpto.entidades.Cidade;

@Repository
public class CidadesRepositoryImpl implements CidadesRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	private Map<String, String> colunaEntidade = new HashMap<>();

	@PostConstruct
	private void initializador() {

		colunaEntidade.put("ibge_id", "idIbge");
		colunaEntidade.put("uf", "estado.nome");
		colunaEntidade.put("name", "nome");
		colunaEntidade.put("capital", "capital");
		colunaEntidade.put("lon", "longitude");
		colunaEntidade.put("lat", "latitude");
		colunaEntidade.put("alternative_names", "nomeAlternativo");
		colunaEntidade.put("microregion", "regiao.microRegion");
		colunaEntidade.put("mesoregion", "regiao.mesoRegion");
	}

	@Override
	public List<Cidade> findByColunaAndConteudo(String coluna, String conteudo) {

		StringBuilder sql = new StringBuilder();

		sql.append("Select c from Cidade c where CAST( c.").append(colunaEntidade.get(coluna))
				.append(" AS string) like :conteudo ");
		
		TypedQuery<Cidade> cidades = em.createQuery(sql.toString(), Cidade.class);

		if (coluna.equals("ibge_id")) {
			cidades.setParameter("conteudo", "%" + new Integer(conteudo) + "%");
		} else if (coluna.equals("lon") || coluna.equals("lat")) {
			cidades.setParameter("conteudo", "%" + new Double(conteudo) + "%");
		} else {
			cidades.setParameter("conteudo", "%" + conteudo + "%");
		}

		return cidades.getResultList();

	}

	@Override
	public Long findByCountRegistroPorColuna(String coluna) {
		StringBuilder sql = new StringBuilder();
		sql.append("Select count(distinct c.").append(colunaEntidade.get(coluna)).append(") from Cidade c");
		return em.createQuery(sql.toString(), Long.class).getSingleResult();
	}

}
