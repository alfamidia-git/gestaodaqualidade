package com.gestaodaqualidade.rest;

import com.gestaodaqualidade.dto.request.AlterarPrazoRequest;
import com.gestaodaqualidade.dto.request.ProcessoRequest;
import com.gestaodaqualidade.dto.response.ProcessoCriadoResponse;
import com.gestaodaqualidade.model.Usuario;
import com.gestaodaqualidade.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/processo")
public class ProcessoRest {

    @Autowired
    EmpresaService empresaService;
    @Operation(summary = "Inicia um tipo de processo com suas etapas e dados",
            description = "Deve receber como paramêtro o cliente deste processo, o id do tipo de processo" +
            " e o prazo no formato de data yyyy-MM-dd")
    @PostMapping("/iniciarProcesso")
    public ResponseEntity<ProcessoCriadoResponse> iniciarProcesso(@RequestBody ProcessoRequest processoRequest){
        return empresaService.iniciarProcesso(processoRequest);
    }

    @Operation(summary = "Retorna um processo e seus detalhes",
            description = "Retorna o processo, suas etapas, seus dados e o status de cada um.")
    @GetMapping("/verProcesso/{idProcesso}")
    public ResponseEntity<ProcessoCriadoResponse> verProcesso(@PathVariable Long idProcesso){
        return empresaService.verDetalhesDoProcesso(idProcesso);
    }

    @Operation(summary = "Conclui uma etapa e inicia a próxima (caso houver)",
            description = "Recebe o id da etapa a ser concluída. Caso tenha dados em andamento ou não iniciados, são concluidos automaticamente. " +
                    "Também inicia um próxima etapa se houver, senão, concluí o processo")
    @PutMapping("/etapa/concluir/{idEtapa}")
    public ResponseEntity<Void> concluirEtapa(@PathVariable Long idEtapa){
        return empresaService.concluirEtapa(idEtapa);
    }

    @Operation(summary = "Altera o prazo de uma etapa", description = "recebe o novo prazo no formato yyyy-MM-dd e o id da etapa")
    @PutMapping("/etapa/alterarPrazo")
    public ResponseEntity<ProcessoCriadoResponse.EtapaResponse> alterarPrazoEtapa(@RequestBody AlterarPrazoRequest alterarPrazoRequest){
        return this.empresaService.alterarPrazoDaEtapa(alterarPrazoRequest);
    }

    @Operation(summary = "Conclui um processo",
            description = "Recebe o id do processo a ser concluído. Caso tenha etapas e dados em andamento ou não " +
                    "iniciados, são concluidos automaticamente.")
    @PutMapping("/concluir/{idProcesso}")
    public ResponseEntity<Void> concluirProcesso(@PathVariable Long idProcesso){
        return this.empresaService.concluirProcesso(idProcesso);
    }

    @Operation(summary = "Altera o prazo de um processo", description = "recebe o novo prazo no formato yyyy-MM-dd e o id do processo")
    @PutMapping("/alterarPrazo")
    public ResponseEntity<ProcessoCriadoResponse> alterarPrazoProcesso(@RequestBody AlterarPrazoRequest alterarPrazoRequest){
        return this.empresaService.alterarPrazoDoProcesso(alterarPrazoRequest);
    }

    @Operation(summary = "Conclui um dado",
            description = "Recebe o id do dado a ser concluído. Caso tenha um próximo dado, inicia automaticamente, senão, inicia a próxima etapa. " +
                    "Caso não haja próxima etapa, concluí o processo")
    @PutMapping("/dado/concluir/{idDado}")
    public ResponseEntity<Void> concluirDado(@PathVariable Long idDado){
        return this.empresaService.concluirDado(idDado);
    }

    @Operation(summary = "Altera o prazo de um dado", description = "recebe o novo prazo no formato yyyy-MM-dd e o id do dado")
    @PutMapping("/dado/alterarPrazo")
    public ResponseEntity<ProcessoCriadoResponse.DadoResponse> alterarPrazoDado(@RequestBody AlterarPrazoRequest alterarPrazoRequest){
        return this.empresaService.alterarPrazoDoDado(alterarPrazoRequest);
    }

    @Operation(summary = "Retorna todos processos atrasados levando em consideração a data atual",
    description = "Quem tem permissão MASTER poderá ver todas empresas, quem tem permissão ADM, só poderá ver da empresa que está vinculado")
    @GetMapping("atrasados")
    public ResponseEntity<List<ProcessoCriadoResponse>> processosAtrasados(@AuthenticationPrincipal Usuario usuario){
        return this.empresaService.verProcessosAtrasados(usuario);
    }
}