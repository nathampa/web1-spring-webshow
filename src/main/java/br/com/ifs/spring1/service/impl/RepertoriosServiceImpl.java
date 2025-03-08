package br.com.ifs.spring1.service.impl;

import br.com.ifs.spring1.controller.dto.UsuarioDTO;
import br.com.ifs.spring1.exception.UnauthorizedAccessException;
import br.com.ifs.spring1.model.*;
import br.com.ifs.spring1.repository.*;
import br.com.ifs.spring1.service.IRepertoriosService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<RepertorioMusica> getAllMusicas() {
        return repertorioMusicaRepository.findAll();
    }

    @Override
    public List<Repertorios> getRepertoriosByBanda(Integer idBanda) {
        //Checa se a banda existe
        bandaRepository.findById(idBanda)
                .orElseThrow(() -> new EntityNotFoundException("Banda não encontrada"));

        return repertorioRepository.findByIdBanda(idBanda);
    }

    @Override
    public List<Musicas> getMusicasByRepertorio(Integer idRepertorio) {
        //Checa se o repertorio existe
        Repertorios repertorio = repertorioRepository.findById(idRepertorio)
                .orElseThrow(() -> new EntityNotFoundException("Repertório não encontrado"));

        //Checa se a banda existe
        Banda banda = bandaRepository.findById(repertorio.getIdBanda())
                .orElseThrow(() -> new EntityNotFoundException("Banda não encontrada"));

        List<Integer> musicasIds = repertorioMusicaRepository.findByIdRepertorio(idRepertorio)
                .stream()
                .map(RepertorioMusica::getIdMusica)
                .collect(Collectors.toList());


        return musicaRepository.findAllById(musicasIds);
    }

    @Override
    public Repertorios cadastrar(Repertorios repertorio, Usuario usuario) {
        Banda banda = bandaRepository.findById(repertorio.getIdBanda())
        .orElseThrow(() -> new EntityNotFoundException("Banda não encontrada"));

        if(!banda.getIdResponsavel().equals(usuario.getIdUsuario())){
            throw new UnauthorizedAccessException("Usuário não autorizado");
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
            throw new UnauthorizedAccessException("Usuário não autorizado");
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
    public void excluirMusica(RepertorioMusica repertorioMusica, Usuario usuario) {
        Repertorios repertorio = repertorioRepository.findById(repertorioMusica.getIdRepertorio())
                .orElseThrow(() -> new EntityNotFoundException("Repertório não encontrado"));

        Banda banda = bandaRepository.findById(repertorio.getIdBanda())
                .orElseThrow(() -> new EntityNotFoundException("Banda não encontrada"));

        if(!banda.getIdResponsavel().equals(usuario.getIdUsuario())){
            throw new UnauthorizedAccessException("Usuário não autorizado");
        }

        int registrosAtualizados = repertorioMusicaRepository.excluirLogicamente(repertorioMusica.getIdMusica(), repertorioMusica.getIdRepertorio());

        if (registrosAtualizados == 0) {
            throw new EntityNotFoundException("Música ou Repertório não encontrados.");
        }
    }

    @Override
    public void ativarMusica(RepertorioMusica repertorioMusica, Usuario usuario) {
        Repertorios repertorio = repertorioRepository.findById(repertorioMusica.getIdRepertorio())
                .orElseThrow(() -> new EntityNotFoundException("Repertório não encontrado"));

        Banda banda = bandaRepository.findById(repertorio.getIdBanda())
                .orElseThrow(() -> new EntityNotFoundException("Banda não encontrada"));

        if(!banda.getIdResponsavel().equals(usuario.getIdUsuario())){
            throw new UnauthorizedAccessException("Usuário não autorizado");
        }

        int registrosAtualizados = repertorioMusicaRepository.ativarMusica(repertorioMusica.getIdMusica(), repertorioMusica.getIdRepertorio());

        if (registrosAtualizados == 0) {
            throw new EntityNotFoundException("Música ou Repertório não encontrados.");
        }
    }

    @Override
    public void excluir(Integer idRepertorio, Usuario usuario) {
        Repertorios repertorio = repertorioRepository.findById(idRepertorio)
                .orElseThrow(() -> new EntityNotFoundException("Repertório não encontrado"));

        Banda banda = bandaRepository.findById(repertorio.getIdBanda())
                .orElseThrow(() -> new EntityNotFoundException("Banda não encontrada"));

        if(!banda.getIdResponsavel().equals(usuario.getIdUsuario())){
            throw new UnauthorizedAccessException("Usuário não autorizado");
        }

        repertorioRepository.deleteById(idRepertorio);
    }
}
