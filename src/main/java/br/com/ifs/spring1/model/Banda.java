package br.com.ifs.spring1.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bandas")
public class Banda {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_banda")
    private Integer idBanda;

    private String nome;

    @Column(name = "id_responsavel")
    private Integer idResponsavel;
}
