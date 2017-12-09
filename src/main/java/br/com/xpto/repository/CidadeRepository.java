package br.com.xpto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.xpto.entidades.Capital;
import br.com.xpto.entidades.Cidade;
import br.com.xpto.entidades.Estado;
import br.com.xpto.entidades.auxiliares.EstadoAndQuantidadeDeCidades;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {


	List<Cidade> findByCapitalOrderByNomeDesc(Capital string);

	@Query("select "
			+ "new br.com.xpto.entidades.auxiliares.EstadoAndQuantidadeDeCidades(e.nome, count(*) as contagem) "
			+ "from Cidade c join c.estado e group by e.id order by contagem desc")
	List<EstadoAndQuantidadeDeCidades> findEstadoMaiorMenor();
	
	Cidade findByIdIbge(Integer idIbge);

	@Query("Select c.nome from Cidade c where c.estado.nome = :nome")
	List<String> findByEstado(@Param("nome") String nome);

	Optional<Cidade> findByNome(String nome);

	Optional<Cidade> findByNomeAndEstado(String nome, Estado estado);

	Cidade  findFirstByOrderByIdIbgeDesc();

	List<Cidade> findByNomeLike(String nome);

	
	

}
