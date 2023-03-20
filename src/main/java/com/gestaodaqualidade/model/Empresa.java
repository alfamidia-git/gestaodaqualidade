package com.gestaodaqualidade.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nome;

    private boolean inativo;

    private LocalDate dataCriacao;

    @OneToMany(mappedBy = "empresa")
    List<Cliente> clientes;

    @OneToMany(mappedBy = "empresa")
    List<Dado> dados;

    @OneToMany(mappedBy = "empresa")
    List<Etapa> etapas;

    @OneToMany(mappedBy = "empresa")
    List<Processo> processos;

    @OneToMany(mappedBy = "empresa")
    List<TipoEtapa> tipoEtapas;

    @OneToMany(mappedBy = "empresa")
    List<TipoProcesso> tipoProcessos;

    @OneToMany(mappedBy = "empresa")
    List<TipoDado> tipoDados;

    @OneToMany(mappedBy = "empresa")
    List<Usuario> usuarios;

}
