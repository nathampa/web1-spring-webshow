package br.com.ifs.spring1.repository;

import br.com.ifs.spring1.model.Banda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BandaRepository extends JpaRepository<Banda, Integer> {

}
