package com.gestaodaqualidade.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class TipoProcesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    private Empresa empresa;

    @OneToMany(mappedBy = "tipoProcesso")
    List<TipoEtapa> tipoEtapas;

    @OneToMany(mappedBy = "tipoProcesso")
    List<TipoDado> tipoDados;

}
