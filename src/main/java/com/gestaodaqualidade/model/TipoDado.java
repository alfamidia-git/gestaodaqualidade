package com.gestaodaqualidade.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TipoDado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Long prazo;

    @ManyToOne
    private TipoEtapa tipoEtapa;

    @ManyToOne
    private TipoProcesso tipoProcesso;

    @ManyToOne
    private Empresa empresa;
}
