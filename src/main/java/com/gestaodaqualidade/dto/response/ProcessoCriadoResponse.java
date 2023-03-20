package com.gestaodaqualidade.dto.response;

import com.gestaodaqualidade.model.Dado;
import com.gestaodaqualidade.model.Etapa;
import com.gestaodaqualidade.model.Processo;
import com.gestaodaqualidade.model.Status;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProcessoCriadoResponse {

    private Long id;

    private String nome;
    private LocalDate dataLimite;
    private List<EtapaResponse> etapas;

    private Status status;

    public ProcessoCriadoResponse(Processo processo){
        this.id = processo.getId();
        this.nome = processo.getNome();
        this.dataLimite = processo.getDataLimite();
        this.status = processo.getStatus();
        this.etapas = processo.getEtapas().stream().map(etapa -> new EtapaResponse(etapa)).collect(Collectors.toList());
    }


    @Data
    public static class EtapaResponse{
        private Long id;
        private String nome;
        private LocalDate dataLimite;
        private Status status;

        List<DadoResponse> dados;

        public EtapaResponse(Etapa etapa){
            this.id = etapa.getId();
            this.nome = etapa.getNome();
            this.dataLimite = etapa.getDataLimite();
            this.status = etapa.getStatus();

            this.dados = etapa.getDados().stream().map(dado -> new DadoResponse(dado)).collect(Collectors.toList());
        }
    }

    @Data
    public static class DadoResponse{
        private Long id;
        private String nome;
        private Status status;
        private LocalDate dataLimite;

        public DadoResponse(Dado dado){
            this.id = dado.getId();
            this.nome = dado.getNome();
            this.status = dado.getStatus();
            this.dataLimite = dado.getDataLimite();
        }
    }
}
