package br.com.ifs.spring1.repository;

import br.com.ifs.spring1.model.Musicas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicasRepository extends JpaRepository<Musicas, Integer> {
    List<Musicas> findByIdUsuario(Integer idUsuario); // Busca músicas pelo ID do usuário
    Musicas findByIdMusica(Integer idMusica);
}


