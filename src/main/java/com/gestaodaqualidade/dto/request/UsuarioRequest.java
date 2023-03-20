package com.gestaodaqualidade.dto.request;

import com.gestaodaqualidade.model.Empresa;
import com.gestaodaqualidade.model.Usuario;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioRequest {

    private Long id;

    private String email;

    private String nome;

    private Long empresaId;

    private Usuario.Permissao permissao;
}
