package com.gestaodaqualidade.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProcessoRequest {

    private String nome;
    private Long idCliente;
    private Long idTipoProcesso;
    private LocalDate prazo;
}
