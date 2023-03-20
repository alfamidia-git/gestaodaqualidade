package com.gestaodaqualidade.rest;

import com.gestaodaqualidade.dto.request.AlterarPrazoRequest;
import com.gestaodaqualidade.dto.request.ProcessoRequest;
import com.gestaodaqualidade.dto.response.ProcessoCriadoResponse;
import com.gestaodaqualidade.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/processo")
public class ProcessoRest {

    @Autowired
    EmpresaService empresaService;

    @PostMapping("/iniciarProcesso")
    public ResponseEntity<ProcessoCriadoResponse> iniciarProcesso(@RequestBody ProcessoRequest processoRequest){
        return empresaService.iniciarProcesso(processoRequest);
    }

    @GetMapping("/verProcesso/{idProcesso}")
    public ResponseEntity<ProcessoCriadoResponse> verProcesso(@PathVariable Long idProcesso){
        return empresaService.verDetalhesDoProcesso(idProcesso);
    }

    @PutMapping("/etapa/concluir/{idEtapa}")
    public ResponseEntity<Void> concluirEtapa(@PathVariable Long idEtapa){
        return empresaService.concluirEtapa(idEtapa);
    }

    @PutMapping("/etapa/alterarPrazo")
    public ResponseEntity<ProcessoCriadoResponse.EtapaResponse> alterarPrazoEtapa(@RequestBody AlterarPrazoRequest alterarPrazoRequest){
        return this.empresaService.alterarPrazoDaEtapa(alterarPrazoRequest);
    }

    @PutMapping("/concluir/{idProcesso}")
    public ResponseEntity<Void> concluirProcesso(@PathVariable Long idProcesso){
        return this.empresaService.concluirProcesso(idProcesso);
    }

    @PutMapping("/alterarPrazo")
    public ResponseEntity<ProcessoCriadoResponse> alterarPrazoProcesso(@RequestBody AlterarPrazoRequest alterarPrazoRequest){
        return this.empresaService.alterarPrazoDoProcesso(alterarPrazoRequest);
    }

    @PutMapping("/dado/concluir/{idDado}")
    public ResponseEntity<Void> concluirDado(@PathVariable Long idDado){
        return this.empresaService.concluirDado(idDado);
    }

    @PutMapping("/dado/alterarPrazo")
    public ResponseEntity<ProcessoCriadoResponse.DadoResponse> alterarPrazoDado(@RequestBody AlterarPrazoRequest alterarPrazoRequest){
        return this.empresaService.alterarPrazoDoDado(alterarPrazoRequest);
    }
}