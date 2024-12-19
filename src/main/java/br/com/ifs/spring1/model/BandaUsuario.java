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
@IdClass(BandaUsuarioId.class) // Usando @IdClass para definir a chave composta
@Table(name = "banda_usuario")
public class BandaUsuario {
    @Id
    @Column(name = "id_banda")
    private Integer idBanda;

    @Id
    @Column(name = "id_usuario")
    private Integer idUsuario;
}
