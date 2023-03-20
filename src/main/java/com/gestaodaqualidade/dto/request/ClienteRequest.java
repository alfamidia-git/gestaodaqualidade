package com.gestaodaqualidade.dto.request;

import lombok.Data;

@Data
public class ClienteRequest {
    private Long id;

    private String nome;
    private String email;
    private String endereco;
    private String nomeContato;
    private String observacao;

    private Long empresaId;
}
