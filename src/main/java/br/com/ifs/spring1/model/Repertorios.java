package br.com.ifs.spring1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "repertorios")

public class Repertorios {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_repertorio")
    private Integer idRepertorio;

    private String nome;

    @Column(name = "id_banda")
    private Integer idBanda;
}
