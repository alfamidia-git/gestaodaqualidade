package com.gestaodaqualidade.rest;

import com.gestaodaqualidade.dto.request.ClienteRequest;
import com.gestaodaqualidade.dto.response.ClienteResponse;
import com.gestaodaqualidade.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteRest {

    @Autowired
    ClienteService service;

    @Operation(summary = "Cria um novo cliente. Aqui não pode passar o id")
    @PostMapping("/criar")
    public ResponseEntity<ClienteResponse> criarCliente(@RequestBody ClienteRequest clienteRequest){
        return this.service.criarOuAlterarCliente(clienteRequest);
    }

    @Operation(summary = "Altera um cliente. Aqui deve passar o id")
    @PutMapping("/alterar")
    public ResponseEntity<ClienteResponse> alterarCliente(@RequestBody ClienteRequest clienteRequest){
        return this.service.criarOuAlterarCliente(clienteRequest);
    }

}
