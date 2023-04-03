package com.gestaodaqualidade.rest;

import com.gestaodaqualidade.dto.request.SenhaRequest;
import com.gestaodaqualidade.dto.request.UsuarioRequest;
import com.gestaodaqualidade.dto.response.UsuarioResponse;
import com.gestaodaqualidade.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioRest {

    @Autowired
    UsuarioService service;

    @Operation(summary = "Cria um novo usuário no sistema", description = "Um usuário com permissão MASTER/ADM pode " +
            "criar outro usuário. Esse usuário não deve receber o id, mas é obrigatório receber a empresa e a sua permissão")
    @PostMapping("/criar")
    public ResponseEntity<UsuarioResponse> criarUsuario(@RequestBody UsuarioRequest usuarioRequest){
        return this.service.criarOuAlterarUsuario(usuarioRequest);
    }

    @Operation(summary = "Altera um usuário no sistema", description = "Um usuário com permissão MASTER/ADM pode " +
            "alterar outro usuário. Esse usuário deve receber o id para ser alterado")
    @PutMapping("/alterar")
    public ResponseEntity<UsuarioResponse> alterarUsuario(@RequestBody UsuarioRequest usuarioRequest){
        return this.service.criarOuAlterarUsuario(usuarioRequest);
    }

    @Operation(summary = "Cria a senha do usuário no sistema", description = "Ao criar um usuário, ele recebe um código por-email." +
            " Este código deve ser passado aqui, juntamente com o e-mail e a senha do usuário")
    @PostMapping("/criarSenha")
    public ResponseEntity<Void> criarSenha(@RequestBody SenhaRequest senhaRequest){
        return this.service.criarSenha(senhaRequest);
    }

    @Operation(summary = "Envia um novo código para o usuário alterar sua senha",
            description = "Após receber o código deste endpoint, deverá chamar novamente o endpoint /criarSenha com o novo código")
    @GetMapping("/resetarSenha")
    public ResponseEntity<Void> resetarSenha(@RequestParam String email){
        return this.service.resetarSenha(email);
    }
}
