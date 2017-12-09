package br.com.xpto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.xpto.entidades.Estado;

@Repository
public interface EstadoRepositoy extends JpaRepository<Estado, Integer> {

	Estado findByNome(String nome);

}
