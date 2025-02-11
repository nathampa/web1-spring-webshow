package br.com.ifs.spring1.repository;

import br.com.ifs.spring1.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /*@Query(
            nativeQuery = true,
            value = """
                    select * from per_perfil
                    where upper(per_tx_nome) like upper(?1)
                    order by per_tx_nome
                    """
    )
    List<Usuario> getByLikeNome(String nome);

    /*@Query("""
            select per from Perfil per where per.perTxNome like ?1 order by per.perTxNome
            """
    )
    List<Usuario> getByLikeNome2(String nome);

    List<Usuario> findByPerTxNomeLikeIgnoreCase(String nome);

    List<Usuario> findByPerTxNomeLikeIgnoreCaseAndPerTxStatus(String nome, String status);

     */


    Usuario findByLogin(String login);
    boolean existsByLogin(String login);

}
