package com.gestaodaqualidade.dto.request;

import lombok.Data;

@Data
public class SenhaRequest {

    private String email;
    private String codigoVerificacao;
    private String senha;
}
