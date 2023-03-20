package com.gestaodaqualidade.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Dado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate dataLimite;

    private String nome;

    private String observacao;

    private Status status;

    @ManyToOne
    private Etapa etapa;

    @ManyToOne
    private Empresa empresa;


}
