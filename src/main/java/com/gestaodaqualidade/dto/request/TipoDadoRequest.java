package com.gestaodaqualidade.dto.request;

import lombok.Data;

@Data
public class TipoDadoRequest {

    private String nome;
    private Long idTipoProcesso;
    private Long idTipoEtapa;
    private Long idEmpresa;
}
