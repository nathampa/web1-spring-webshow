package br.com.ifs.spring1.service;

import br.com.ifs.spring1.model.Banda;
import br.com.ifs.spring1.model.BandaUsuario;
import br.com.ifs.spring1.model.BandaUsuarioId;
import br.com.ifs.spring1.model.Usuario;

import java.util.List;

public interface IBandaService {
    List<Banda> getAll();
    List<BandaUsuario> getAllMusicos();
    //List<Banda> getByNomeLike(String nome);
    Banda cadastrar(Banda banda, Usuario usuario);
    BandaUsuario adicionarUsuario(BandaUsuario bandaUsuario, Usuario usuario);
    void removerUsuario(BandaUsuario bandaUsuario, Usuario usuario);
    void excluir(Integer idBanda, Usuario usuario);
}
