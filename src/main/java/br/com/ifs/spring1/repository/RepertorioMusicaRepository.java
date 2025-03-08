package br.com.ifs.spring1.repository;

import br.com.ifs.spring1.model.Musicas;
import br.com.ifs.spring1.model.RepertorioMusica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RepertorioMusicaRepository extends JpaRepository<RepertorioMusica, Integer> {
    boolean existsByIdMusicaAndIdRepertorio(Integer idMusica, Integer idRepertorio);

    List<RepertorioMusica> findByIdRepertorio(Integer idRepertorio);

    @Modifying
    @Transactional
    @Query("UPDATE RepertorioMusica rm SET rm.status = false WHERE rm.idRepertorio = :idRepertorio AND rm.idMusica = :idMusica")
    int excluirLogicamente(@Param("idMusica") Integer idMusica, @Param("idRepertorio") Integer idRepertorio);

    @Modifying
    @Transactional
    @Query("UPDATE RepertorioMusica rm SET rm.status = true WHERE rm.idRepertorio = :idRepertorio AND rm.idMusica = :idMusica")
    int ativarMusica(@Param("idMusica") Integer idMusica, @Param("idRepertorio") Integer idRepertorio);
}
