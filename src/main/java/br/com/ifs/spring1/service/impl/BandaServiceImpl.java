package br.com.ifs.spring1.service.impl;

import br.com.ifs.spring1.exception.UnauthorizedAccessException;
import br.com.ifs.spring1.model.Banda;
import br.com.ifs.spring1.model.Usuario;
import br.com.ifs.spring1.repository.BandaRepository;
import br.com.ifs.spring1.service.IBandaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BandaServiceImpl implements IBandaService {
    private final BandaRepository bandaRepository;

    @Override
    public List<Banda> getAll() {
        return bandaRepository.findAll();
    }

    @Override
    public Banda cadastrar(Banda banda, Usuario usuario) {
        banda.setIdResponsavel(usuario.getIdUsuario());
        return bandaRepository.save(banda);
    }

    @Override
    public void excluir(Integer idBanda, Integer idUsuario) {
        Banda banda = bandaRepository.findById(idBanda)
        .orElseThrow(() -> new EntityNotFoundException("Banda não encontrada"));

        if(!banda.getIdResponsavel().equals(idUsuario)){
            throw new UnauthorizedAccessException("Usuário não autorizado");
        }

        bandaRepository.deleteById(idBanda);
    }
}
