package br.com.ifs.spring1.repository;

import br.com.ifs.spring1.model.Banda;
import br.com.ifs.spring1.model.BandaUsuario;
import br.com.ifs.spring1.model.BandaUsuarioId;
import br.com.ifs.spring1.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BandaUsuarioRepository extends JpaRepository<BandaUsuario, BandaUsuarioId> {
    boolean existsByIdBandaAndIdUsuario(Integer idBanda, Integer idUsuario);
}
