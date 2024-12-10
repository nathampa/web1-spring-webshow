package br.com.ifs.spring1.service.impl;

import br.com.ifs.spring1.model.Usuario;
import br.com.ifs.spring1.repository.UsuarioRepository;
import br.com.ifs.spring1.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {
    private final UsuarioRepository usuarioRepository;

    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    /*@Override
    public List<Usuario> getByNomeLike(String nome) {
        return usuarioRepository.findByPerTxNomeLikeIgnoreCase("%" + nome + "%");
    }*/

    public Usuario cadastrar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void excluir(Integer idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }
}
