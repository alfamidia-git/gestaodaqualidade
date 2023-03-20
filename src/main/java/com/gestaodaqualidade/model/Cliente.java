package com.gestaodaqualidade.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String endereco;
    private String nomeContato;
    private String observacao;

    @ManyToOne
    private Empresa empresa;

    @OneToMany(mappedBy = "cliente")
    List<Processo> processos;
}
