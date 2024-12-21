package br.com.ifs.spring1.service.impl;

import br.com.ifs.spring1.model.Banda;
import br.com.ifs.spring1.model.BandaUsuario;
import br.com.ifs.spring1.model.Repertorios;
import br.com.ifs.spring1.model.Usuario;
import br.com.ifs.spring1.repository.BandaRepository;
import br.com.ifs.spring1.repository.BandaUsuarioRepository;
import br.com.ifs.spring1.repository.RepertoriosRepository;
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
    private final BandaUsuarioRepository bandaUsuarioRepository;

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
    public void excluir(Integer idRepertorio, Usuario usuario) {
        Optional<Repertorios> repertorio = repertorioRepository.findById(idRepertorio);
        if (repertorio.isPresent()) {
            repertorioRepository.deleteById(idRepertorio);
        } else {
            throw new IllegalArgumentException("Repertório com ID " + idRepertorio + " não encontrado.");
        }
    }
}
