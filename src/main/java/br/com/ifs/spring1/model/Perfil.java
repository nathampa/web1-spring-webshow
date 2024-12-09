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
@Table(name = "per_perfil")
public class Perfil {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer per_nr_id;
    @Column(name = "per_tx_nome")
    private String perTxNome;
    @Column(name = "per_tx_status")
    private String perTxStatus;
}
