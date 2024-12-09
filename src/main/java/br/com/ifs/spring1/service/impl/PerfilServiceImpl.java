package br.com.ifs.spring1.service.impl;

import br.com.ifs.spring1.model.Perfil;
import br.com.ifs.spring1.repository.PerfilRepository;
import br.com.ifs.spring1.service.IPerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerfilServiceImpl implements IPerfilService {
    private final PerfilRepository perfilRepository;

    public List<Perfil> getAll() {
        return perfilRepository.findAll();
    }

    @Override
    public List<Perfil> getByNomeLike(String nome) {
        return perfilRepository.findByPerTxNomeLikeIgnoreCase("%" + nome + "%");
    }

    public Perfil cadastrar(Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    @Override
    public void excluir(Integer perNrId) {
        perfilRepository.deleteById(perNrId);
    }
}
