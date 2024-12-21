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
@IdClass(RepertorioMusicaId.class)
@Table(name = "repertorio_musica")
public class RepertorioMusica {
    @Id
    @Column(name = "id_repertorio")
    private Integer idRepertorio;

    @Id
    @Column(name = "id_musica")
    private Integer idMusica;

    private Boolean status;
}
