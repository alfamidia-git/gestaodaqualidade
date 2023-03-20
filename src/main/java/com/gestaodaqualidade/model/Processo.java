package com.gestaodaqualidade.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Processo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private LocalDate dataCriacao;

    private LocalDate dataLimite;

    private String observacao;

    private Status status;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Empresa empresa;


    @OneToMany(mappedBy = "processo")
    List<Etapa> etapas;

}
