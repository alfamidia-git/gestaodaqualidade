package com.gestaodaqualidade.dto.response;

import com.gestaodaqualidade.model.Empresa;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class EmpresaListaResponse {

    private Long id;

    private String email;

    private String nome;

    private String dataCriacao;


    public EmpresaListaResponse(Empresa empresa){
        this.id = empresa.getId();
        this.email = empresa.getEmail();
        this.nome = empresa.getNome();
        this.dataCriacao = empresa.getDataCriacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    }
}
