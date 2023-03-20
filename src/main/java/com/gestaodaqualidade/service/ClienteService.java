package com.gestaodaqualidade.service;

import com.gestaodaqualidade.dto.request.ClienteRequest;
import com.gestaodaqualidade.dto.response.ClienteResponse;
import com.gestaodaqualidade.model.Cliente;
import com.gestaodaqualidade.model.Empresa;
import com.gestaodaqualidade.repository.ClienteRepository;
import com.gestaodaqualidade.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository repository;
    @Autowired
    EmpresaRepository empresaRepository;

    public ResponseEntity<ClienteResponse> criarOuAlterarCliente(ClienteRequest clienteRequest) {
        Cliente cliente = null;

        if(clienteRequest.getId() == null){
            cliente = new Cliente();

            if(clienteRequest.getEmpresaId() == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Obrigatório ter uma empresa");
            }

            Empresa empresa = this.empresaRepository.findById(clienteRequest.getEmpresaId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não localizada"));

            cliente.setEmpresa(empresa);
        }else{
            cliente = this.repository.findById(clienteRequest.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        }

        cliente.setNome(clienteRequest.getNome() != null ? clienteRequest.getNome() : cliente.getNome());
        cliente.setEmail(clienteRequest.getEmail() != null ? clienteRequest.getEmail() : cliente.getEmail());
        cliente.setObservacao(clienteRequest.getObservacao() != null ? clienteRequest.getObservacao() : cliente.getObservacao());
        cliente.setEndereco(clienteRequest.getEndereco() != null ? clienteRequest.getEndereco() : cliente.getEndereco());
        cliente.setNomeContato(clienteRequest.getNomeContato() != null ? clienteRequest.getNomeContato() : cliente.getNomeContato());


        return new ResponseEntity<>(new ClienteResponse(this.repository.save(cliente)), HttpStatus.CREATED);
    }
}
