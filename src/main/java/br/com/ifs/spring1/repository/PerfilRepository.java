package br.com.ifs.spring1.repository;

import br.com.ifs.spring1.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {


    @Query(
            nativeQuery = true,
            value = """
                    select * from per_perfil  
                    where upper(per_tx_nome) like upper(?1)
                    order by per_tx_nome 
                    """
    )
    List<Perfil> getByLikeNome(String nome);

    @Query("""
            select per from Perfil per where per.perTxNome like ?1 order by per.perTxNome
            """
    )
    List<Perfil> getByLikeNome2(String nome);

    List<Perfil> findByPerTxNomeLikeIgnoreCase(String nome);

    List<Perfil> findByPerTxNomeLikeIgnoreCaseAndPerTxStatus(String nome, String status);
}
