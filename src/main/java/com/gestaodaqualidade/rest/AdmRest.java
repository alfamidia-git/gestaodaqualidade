package com.gestaodaqualidade.rest;

import com.gestaodaqualidade.dto.request.TipoDadoRequest;
import com.gestaodaqualidade.dto.request.TipoEtapaRequest;
import com.gestaodaqualidade.dto.request.TipoProcessoRequest;
import com.gestaodaqualidade.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adm")
public class AdmRest {

    @Autowired
    EmpresaService empresaService;

    @Operation(summary = "Cria um novo tipo de processo")
    @PostMapping("/criarTipoProcesso")
    public void criarTipoProcesso(@RequestBody TipoProcessoRequest tipoProcessoRequest){
        this.empresaService.criarTipoProcesso(tipoProcessoRequest);
    }

    @Operation(summary = "Cria um novo tipo de etapa")
    @PostMapping("/criarTipoEtapa")
    public void criarTipoEtapa(@RequestBody TipoEtapaRequest tipoEtapaRequest){
        this.empresaService.criarTipoEtapa(tipoEtapaRequest);
    }

    @Operation(summary = "Cria um novo tipo de dado")
    @PostMapping("/criarTipoDado")
    public void criatTipoDado(@RequestBody TipoDadoRequest tipoDadoRequest){
        this.empresaService.criarTipoDado(tipoDadoRequest);
    }
}
