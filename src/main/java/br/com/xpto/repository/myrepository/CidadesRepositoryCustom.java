package br.com.xpto.repository.myrepository;

import java.util.List;

import br.com.xpto.entidades.Cidade;

public interface CidadesRepositoryCustom {
	
	public List<Cidade> findByColunaAndConteudo(String coluna, String conteudo);
	
	public Long findByCountRegistroPorColuna(String coluna);
	

}
