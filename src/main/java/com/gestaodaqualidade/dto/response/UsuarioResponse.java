package com.gestaodaqualidade.dto.response;

import com.gestaodaqualidade.model.Empresa;
import com.gestaodaqualidade.model.Usuario;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioResponse {

    private Long id;

    private String email;

    private String nome;

    private boolean inativo;

    private LocalDate dataCriacao;

    private String empresa;

    private Usuario.Permissao permissao;

    public UsuarioResponse(Usuario usuario){
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.inativo = usuario.isInativo();
        this.empresa = usuario.getEmpresa().getNome();
        this.nome = usuario.getNome();
        this.dataCriacao = usuario.getDataCriacao();
        this.permissao = usuario.getPermissao();

    }
}
