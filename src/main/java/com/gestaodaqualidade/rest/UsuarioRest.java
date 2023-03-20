package com.gestaodaqualidade.rest;

import com.gestaodaqualidade.dto.request.SenhaRequest;
import com.gestaodaqualidade.dto.request.UsuarioRequest;
import com.gestaodaqualidade.dto.response.UsuarioResponse;
import com.gestaodaqualidade.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioRest {

    @Autowired
    UsuarioService service;

    @PostMapping("/criar")
    public ResponseEntity<UsuarioResponse> criarUsuario(@RequestBody UsuarioRequest usuarioRequest){
        return this.service.criarOuAlterarUsuario(usuarioRequest);
    }

    @PutMapping("/alterar")
    public ResponseEntity<UsuarioResponse> alterarUsuario(@RequestBody UsuarioRequest usuarioRequest){
        return this.service.criarOuAlterarUsuario(usuarioRequest);
    }

    @PostMapping("/criarSenha")
    public ResponseEntity<Void> criarSenha(@RequestBody SenhaRequest senhaRequest){
        return this.service.criarSenha(senhaRequest);
    }
}
