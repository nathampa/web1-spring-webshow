package br.com.ifs.spring1.service.impl;

import br.com.ifs.spring1.model.*;
import br.com.ifs.spring1.repository.*;
import br.com.ifs.spring1.service.IRepertoriosService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepertoriosServiceImpl implements IRepertoriosService {

    private final RepertoriosRepository repertorioRepository;
    private final BandaRepository bandaRepository;
    private final RepertorioMusicaRepository repertorioMusicaRepository;
    private final MusicasRepository musicaRepository;

    @Override
    public List<Repertorios> getAll() {
        return repertorioRepository.findAll();
    }
    @Override
    public Repertorios cadastrar(Repertorios repertorio, Usuario usuario) {
        Banda banda = bandaRepository.findById(repertorio.getIdBanda())
        .orElseThrow(() -> new EntityNotFoundException("Banda não encontrada"));

        if(!banda.getIdResponsavel().equals(usuario.getIdUsuario())){
            throw new IllegalStateException("Apenas o responsável pela banda pode criar repertórios.");
        }

        return repertorioRepository.save(repertorio);
    }

    @Override
    public RepertorioMusica adicionarMusica(RepertorioMusica repertorioMusica, Usuario usuario) {
        //Checa se o repertorio existe
        Repertorios repertorio = repertorioRepository.findById(repertorioMusica.getIdRepertorio())
                .orElseThrow(() -> new EntityNotFoundException("Repertório não encontrado"));

        //Checa se a banda existe
        Banda banda = bandaRepository.findById(repertorio.getIdBanda())
                .orElseThrow(() -> new EntityNotFoundException("Banda não encontrada"));

        //Checa se o usuário é o responsável pela banda
        if(!banda.getIdResponsavel().equals(usuario.getIdUsuario())){
            throw new IllegalStateException("Apenas o responsável pela banda pode adicionar músicas ao repertório.");
        }

        //Checa se a música existe
        musicaRepository.findById(repertorioMusica.getIdMusica())
                .orElseThrow(() -> new IllegalArgumentException("Música não encontrada."));

        //Checa se a música já está no repertório
        if (repertorioMusicaRepository.existsByIdMusicaAndIdRepertorio(repertorioMusica.getIdMusica(), repertorioMusica.getIdRepertorio())) {
            throw new IllegalStateException("Música já está associada a este repertório.");
        }

        return repertorioMusicaRepository.save(repertorioMusica);
    }

    @Override
    public void excluir(Integer idRepertorio, Usuario usuario) {
        Optional<Repertorios> repertorio = repertorioRepository.findById(idRepertorio);
        if (repertorio.isPresent()) {
            repertorioRepository.deleteById(idRepertorio);
        } else {
            throw new IllegalArgumentException("Repertório com ID " + idRepertorio + " não encontrado.");
        }
    }
}
