package br.com.ifs.spring1.service.impl;

import br.com.ifs.spring1.model.Banda;
import br.com.ifs.spring1.model.BandaUsuario;
import br.com.ifs.spring1.model.Musicas;
import br.com.ifs.spring1.model.Usuario;
import br.com.ifs.spring1.repository.MusicasRepository;
import br.com.ifs.spring1.service.IMusicasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicasServiceImpl implements IMusicasService {
    private final MusicasRepository musicasRepository;

    public List<Musicas> getAll(){
        return musicasRepository.findAll();
    }

    @Override
    public Musicas cadastrar(Musicas musica, Usuario usuario) {
        musica.setIdUsuario(usuario.getIdUsuario());
        return musicasRepository.save(musica);
    }

    @Override
    public void excluir(Integer idMusica) {
        musicasRepository.deleteById(idMusica);
    }

    @Override
    public List<Musicas> getMusicasDoUsuario(Integer idUsuario) {
        return musicasRepository.findByIdUsuario(idUsuario);
    }

    @Override
    public Musicas findByIdMusica(Integer idMusica) {
        return musicasRepository.findByIdMusica(idMusica);
    }
}
