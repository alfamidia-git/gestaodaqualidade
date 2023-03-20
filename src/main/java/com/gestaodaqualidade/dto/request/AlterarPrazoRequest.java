package com.gestaodaqualidade.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AlterarPrazoRequest {

    private Long id;

    private LocalDate novoPrazo;
}
