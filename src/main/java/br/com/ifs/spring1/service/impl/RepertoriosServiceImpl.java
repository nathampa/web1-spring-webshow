package br.com.ifs.spring1.service.impl;

import br.com.ifs.spring1.model.Repertorios;
import br.com.ifs.spring1.repository.RepertoriosRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class RepertoriosServiceImpl {

    private RepertoriosRepository repertorioRepository;

    public Repertorios cadastrar(Repertorios repertorio) {
        return repertorioRepository.save(repertorio);
    }

    public void excluir(Integer id) {
        Optional<Repertorios> repertorio = repertorioRepository.findById(id);
        if (repertorio.isPresent()) {
            repertorioRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Repertório com ID " + id + " não encontrado.");
        }
    }
}
