package com.gestaodaqualidade.rest;

import com.gestaodaqualidade.dto.request.TipoDadoRequest;
import com.gestaodaqualidade.dto.request.TipoEtapaRequest;
import com.gestaodaqualidade.dto.request.TipoProcessoRequest;
import com.gestaodaqualidade.service.EmpresaService;
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

    @PostMapping("/criarTipoProcesso")
    public void criarTipoProcesso(@RequestBody TipoProcessoRequest tipoProcessoRequest){
        this.empresaService.criarTipoProcesso(tipoProcessoRequest);
    }

    @PostMapping("/criarTipoEtapa")
    public void criarTipoEtapa(@RequestBody TipoEtapaRequest tipoEtapaRequest){
        this.empresaService.criarTipoEtapa(tipoEtapaRequest);
    }

    @PostMapping("/criarTipoDado")
    public void criatTipoDado(@RequestBody TipoDadoRequest tipoDadoRequest){
        this.empresaService.criarTipoDado(tipoDadoRequest);
    }
}
