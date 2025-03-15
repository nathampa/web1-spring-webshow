package br.com.ifs.spring1.repository;

import br.com.ifs.spring1.model.Musicas;
import br.com.ifs.spring1.model.RepertorioMusica;
import br.com.ifs.spring1.model.RepertorioMusicaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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

    @Modifying
    @Transactional
    @Query("UPDATE RepertorioMusica rm SET rm.ordem = :ordem WHERE rm.id.idRepertorio = :idRepertorio AND rm.id.idMusica = :idMusica")
    void atualizarOrdem(@Param("idRepertorio") Integer idRepertorio, @Param("idMusica") Integer idMusica, @Param("ordem") int ordem);

    @Query("SELECT COALESCE(MAX(rm.ordem), 0) + 1 FROM RepertorioMusica rm WHERE rm.id.idRepertorio = :idRepertorio")
    int getNextOrdem(@Param("idRepertorio") Integer idRepertorio);

    @Modifying
    @Transactional
    @Query("DELETE FROM RepertorioMusica rm WHERE rm.id.idRepertorio = :idRepertorio AND rm.id.idMusica = :idMusica")
    void excluirMusica(@Param("idRepertorio") Integer idRepertorio, @Param("idMusica") Integer idMusica);


    @Query("SELECT rm FROM RepertorioMusica rm WHERE rm.id.idRepertorio = :idRepertorio AND rm.status = true ORDER BY rm.ordem")
    List<RepertorioMusica> findAtivasByIdRepertorioOrderByOrdem(@Param("idRepertorio") Integer idRepertorio);

    @Query("SELECT new map(m as musica, rm.status as status) FROM Musicas m " +
            "JOIN RepertorioMusica rm ON m.id = rm.idMusica " +
            "WHERE rm.idRepertorio = :idRepertorio")
    List<Map<String, Object>> findMusicasWithStatusByRepertorioId(@Param("idRepertorio") Integer idRepertorio);
}
