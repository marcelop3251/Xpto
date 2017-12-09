package br.com.xpto.repository.myrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.xpto.entidades.Cidade;

@Repository
public interface CidadesRepository extends JpaRepository<Cidade, Integer>, CidadesRepositoryCustom{

}
