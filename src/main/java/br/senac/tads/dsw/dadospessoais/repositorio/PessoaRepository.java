package br.senac.tads.dsw.dadospessoais.repositorio;


import br.senac.tads.dsw.dadospessoais.entidade.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaEntity, Integer> {

	Optional<PessoaEntity> findByUsername(String username);

	boolean existsByUsername(String username);
}
