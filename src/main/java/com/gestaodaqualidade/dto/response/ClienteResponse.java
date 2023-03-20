package com.gestaodaqualidade.dto.response;

import com.gestaodaqualidade.model.Cliente;
import lombok.Data;

@Data
public class ClienteResponse {

    private Long id;

    private String nome;
    private String email;
    private String endereco;
    private String nomeContato;
    private String observacao;
    private String empresa;

    public ClienteResponse(Cliente cliente){
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.endereco = cliente.getEndereco();
        this.nomeContato = cliente.getNomeContato();
        this.observacao = cliente.getObservacao();

        this.empresa = cliente.getEmpresa().getNome();
    }
}
