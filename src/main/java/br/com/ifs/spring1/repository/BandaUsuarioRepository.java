package br.com.ifs.spring1.repository;

import br.com.ifs.spring1.model.Banda;
import br.com.ifs.spring1.model.BandaUsuario;
import br.com.ifs.spring1.model.BandaUsuarioId;
import br.com.ifs.spring1.model.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BandaUsuarioRepository extends JpaRepository<BandaUsuario, BandaUsuarioId> {
    boolean existsByIdBandaAndIdUsuario(Integer idBanda, Integer idUsuario);
    @Modifying
    @Transactional
    void deleteByIdBandaAndIdUsuario(Integer idBanda, Integer idUsuario);

    /*@Query(value = "SELECT b FROM bandas b WHERE b.id_banda IN (SELECT bu.id_banda FROM banda_usuario bu WHERE bu.id_usuario = :usuarioId) ORDER BY b.nome ASC",
            nativeQuery = true)
    List<Banda> findBandasByUsuarioIdOrderByNome(@Param("usuarioId") Integer usuarioId);*/

    @Query(value = "SELECT b FROM Banda b WHERE b.idBanda IN (SELECT bu.idBanda FROM BandaUsuario bu WHERE bu.idUsuario = :usuarioId) ORDER BY b.nome ASC")
    List<Banda> findBandasByUsuarioIdOrderByNome(@Param("usuarioId") Integer usuarioId);
}
