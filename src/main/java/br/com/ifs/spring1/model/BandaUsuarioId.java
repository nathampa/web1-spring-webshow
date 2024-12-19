package br.com.ifs.spring1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

// Classe representando a chave composta para BandaUsuario
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BandaUsuarioId implements Serializable {

    private Integer idBanda;
    private Integer idUsuario;


    // Sobrescrevendo equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BandaUsuarioId that = (BandaUsuarioId) o;
        return Objects.equals(idBanda, that.idBanda) &&
                Objects.equals(idUsuario, that.idUsuario);
    }

    // Sobrescrevendo hashCode
    @Override
    public int hashCode() {
        return Objects.hash(idBanda, idUsuario);
    }
}

