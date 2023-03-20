package com.gestaodaqualidade.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Etapa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataCriacao;

    private LocalDate dataLimite;

    private String nome;

    private String observacao;

    private Status status;

    @ManyToOne
    private Processo processo;

    @ManyToOne
    private Empresa empresa;

    @OneToMany(mappedBy = "etapa")
    List<Dado> dados;
}
