package br.com.ifs.spring1.repository;

import br.com.ifs.spring1.model.RepertorioMusica;
import br.com.ifs.spring1.model.Repertorios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepertoriosRepository extends JpaRepository<Repertorios, Integer> {
    List<Repertorios> findByIdBanda(Integer idBanda);
}
