package br.com.xpto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.xpto.entidades.Arquivo;


@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Integer> {

}
