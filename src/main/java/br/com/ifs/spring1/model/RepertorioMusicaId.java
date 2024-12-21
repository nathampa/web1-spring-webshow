package br.com.ifs.spring1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepertorioMusicaId implements Serializable {
    private Integer idRepertorio;
    private Integer idMusica;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepertorioMusicaId that = (RepertorioMusicaId) o;
        return Objects.equals(idRepertorio, that.idRepertorio) &&
                Objects.equals(idMusica, that.idMusica);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRepertorio, idMusica);
    }
}
