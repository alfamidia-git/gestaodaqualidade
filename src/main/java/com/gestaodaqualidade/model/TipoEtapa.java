package com.gestaodaqualidade.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class TipoEtapa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    private TipoProcesso tipoProcesso;

    private Integer prazo;

    @ManyToOne
    private Empresa empresa;

    @OneToMany(mappedBy = "tipoEtapa")
    List<TipoDado> tipoDados;

}
