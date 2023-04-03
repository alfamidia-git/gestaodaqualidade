package com.gestaodaqualidade.rest;

import com.gestaodaqualidade.dto.request.EmpresaRequest;
import com.gestaodaqualidade.dto.response.EmpresaListaResponse;
import com.gestaodaqualidade.model.Empresa;
import com.gestaodaqualidade.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class EmpresaRest {

    @Autowired
    EmpresaService empresaService;

    @Operation(summary = "Cria uma nova empresa",
            description = "Deverá receber o nome e e-mail desta empresa")
    @PostMapping("/criarNovaEmpresa")
    public ResponseEntity<Empresa> criarEmpresa(@RequestBody EmpresaRequest empresa){
        return this.empresaService.criarOuAlterarEmpresa(empresa);
    }

    @Operation(summary = "Altera uma empresa",
            description = "Deverá receber o id, nome e e-mail desta empresa")
    @PutMapping("/alterarEmpresa")
    public ResponseEntity<Empresa> alterarEmpresa(@RequestBody EmpresaRequest empresa){
        return this.empresaService.criarOuAlterarEmpresa(empresa);
    }

    @Operation(summary = "Lista todas empresas")
    @GetMapping("/listarTodasEmpresas")
    public ResponseEntity<List<EmpresaListaResponse>> buscarEmpresas(){
        return this.empresaService.buscarTodasEmpresas();
    }

    @Operation(summary = "Retorna detalhe de uma empresa por id")
    @GetMapping("detalhe/{id}")
    public ResponseEntity<Empresa> buscarDetalhe(@PathVariable Long id){
        return this.empresaService.buscarDetalhe(id);
    }

}
