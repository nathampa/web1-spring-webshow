package br.com.ifs.spring1.service.impl;

import br.com.ifs.spring1.controller.dto.UsuarioDTO;
import br.com.ifs.spring1.exception.UnauthorizedAccessException;
import br.com.ifs.spring1.model.Banda;
import br.com.ifs.spring1.model.BandaUsuario;
import br.com.ifs.spring1.model.BandaUsuarioId;
import br.com.ifs.spring1.model.Usuario;
import br.com.ifs.spring1.repository.BandaRepository;
import br.com.ifs.spring1.repository.BandaUsuarioRepository;
import br.com.ifs.spring1.repository.UsuarioRepository;
import br.com.ifs.spring1.service.IBandaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BandaServiceImpl implements IBandaService {
    private final BandaRepository bandaRepository;
    private final UsuarioRepository usuarioRepository;
    private final BandaUsuarioRepository bandaUsuarioRepository;
    @Override
    public List<Banda> getAll() {
        return bandaRepository.findAll();
    }
    @Override
    public List<BandaUsuario> getAllMusicos() {
        return bandaUsuarioRepository.findAll();
    }

    @Override
    public List<UsuarioDTO> getMusicosByBanda(Integer idBanda) {
        //Checa se a banda existe
        bandaRepository.findById(idBanda)
                .orElseThrow(() -> new EntityNotFoundException("Banda não encontrada"));

        List<Integer> usuarioIds = bandaUsuarioRepository.findByIdBanda(idBanda)
                .stream()
                .map(BandaUsuario::getIdUsuario)
                .collect(Collectors.toList());

        return usuarioRepository.findAllById(usuarioIds)
                .stream()
                .map(usuario -> new UsuarioDTO(usuario.getIdUsuario(), usuario.getNome(), usuario.getLogin()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Banda> findBandasByUsuarioIdOrderByNome(Integer idUsuario) {
        return bandaUsuarioRepository.findBandasByUsuarioIdOrderByNome(idUsuario);
    }

    @Override
    public Banda cadastrar(Banda banda, Usuario usuario) {
        banda.setIdResponsavel(usuario.getIdUsuario());
        Banda bandaSalva = bandaRepository.save(banda);

        //Adicionando o próprio responsável na banda
        BandaUsuario bandaUsuario = BandaUsuario.builder()
                .idBanda(bandaSalva.getIdBanda())
                .idUsuario(usuario.getIdUsuario())
                .build();
        bandaUsuarioRepository.save(bandaUsuario);

        return bandaSalva;
    }

    @Override
    public BandaUsuario adicionarUsuario(BandaUsuario bandaUsuario, Usuario usuario) {
        //Checa se a banda existe
        Banda banda = bandaRepository.findById(bandaUsuario.getIdBanda())
                .orElseThrow(() -> new EntityNotFoundException("Banda não encontrada"));

        if(!banda.getIdResponsavel().equals(usuario.getIdUsuario())){
            throw new UnauthorizedAccessException("Usuário não autorizado");
        }

        usuarioRepository.findById(bandaUsuario.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        if (bandaUsuarioRepository.existsByIdBandaAndIdUsuario(bandaUsuario.getIdBanda(), bandaUsuario.getIdUsuario())) {
            throw new IllegalStateException("Usuário já está associado a esta banda.");
        }

        return bandaUsuarioRepository.save(bandaUsuario);
    }

    @Override
    public void removerUsuario(BandaUsuario bandaUsuario, Usuario usuario) {
        //Checa se a banda existe
        Banda banda = bandaRepository.findById(bandaUsuario.getIdBanda())
                .orElseThrow(() -> new EntityNotFoundException("Banda não encontrada"));

        if(!banda.getIdResponsavel().equals(usuario.getIdUsuario())){
            throw new UnauthorizedAccessException("Usuário não autorizado");
        }

        usuarioRepository.findById(bandaUsuario.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        if (!bandaUsuarioRepository.existsByIdBandaAndIdUsuario(bandaUsuario.getIdBanda(), bandaUsuario.getIdUsuario())) {
            throw new IllegalStateException("Usuário não se encontra na banda.");
        }

        bandaUsuarioRepository.deleteByIdBandaAndIdUsuario(bandaUsuario.getIdBanda(), bandaUsuario.getIdUsuario());
    }

    @Override
    public void excluir(Integer idBanda, Usuario usuario) {
        Banda banda = bandaRepository.findById(idBanda)
        .orElseThrow(() -> new EntityNotFoundException("Banda não encontrada"));

        if(!banda.getIdResponsavel().equals(usuario.getIdUsuario())){
            throw new UnauthorizedAccessException("Usuário não autorizado");
        }

        bandaRepository.deleteById(idBanda);
    }
}
