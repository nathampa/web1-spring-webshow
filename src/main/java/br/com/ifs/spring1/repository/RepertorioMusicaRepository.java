package br.com.ifs.spring1.repository;

import br.com.ifs.spring1.model.RepertorioMusica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepertorioMusicaRepository extends JpaRepository<RepertorioMusica, Integer> {
    boolean existsByIdMusicaAndIdRepertorio(Integer idMusica, Integer idRepertorio);
}
