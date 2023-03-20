package com.gestaodaqualidade.dto.request;

import lombok.Data;

@Data
public class TipoEtapaRequest {

    private String nome;

    private Long idEmpresa;

    private Long idTipoProcesso;

    private Integer prazo;
}
