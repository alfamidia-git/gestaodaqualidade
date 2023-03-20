package com.gestaodaqualidade.service;

import com.gestaodaqualidade.dto.request.*;
import com.gestaodaqualidade.dto.response.EmpresaListaResponse;
import com.gestaodaqualidade.dto.response.ProcessoCriadoResponse;
import com.gestaodaqualidade.dto.response.ProcessoCriadoResponse.EtapaResponse;
import com.gestaodaqualidade.model.*;
import com.gestaodaqualidade.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    @Autowired
    EmpresaRepository repository;

    @Autowired
    TipoProcessoRepository tipoProcessoRepository;

    @Autowired
    TipoEtapaRepository tipoEtapaRepository;

    @Autowired
    TipoDadoRepository tipoDadoRepository;

    @Autowired
    ProcessoRepository processoRepository;

    @Autowired EtapaRepository etapaRepository;

    @Autowired DadoRepository dadoRepository;

    @Autowired ClienteRepository clienteRepository;
    public ResponseEntity<Empresa> criarOuAlterarEmpresa(EmpresaRequest empresaRequest) {

        Empresa empresaModel = null;

        if(empresaRequest.getId() == null){
            empresaModel = new Empresa();
            empresaModel.setDataCriacao(LocalDate.now());
        }else{
            empresaModel = this.repository.findById(empresaRequest.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não localizada"));
        }

        empresaModel.setNome(empresaRequest.getNome() != null ? empresaRequest.getNome() : empresaModel.getNome());
        empresaModel.setEmail(empresaRequest.getEmail() != null ? empresaRequest.getEmail() : empresaModel.getEmail());

        return new ResponseEntity<>(this.repository.save(empresaModel), HttpStatus.CREATED);
    }

    public ResponseEntity<List<EmpresaListaResponse>> buscarTodasEmpresas() {
        List<EmpresaListaResponse> response = this.repository.findAll().stream()
                                            .map(e -> new EmpresaListaResponse(e))
                                            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Empresa> buscarDetalhe(Long id) {
        return ResponseEntity.ok(this.repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não localizada")));
    }

    public void criarTipoProcesso(TipoProcessoRequest tipoProcessoRequest) {

        TipoProcesso tipoProcesso = new TipoProcesso();
        tipoProcesso.setNome(tipoProcessoRequest.getNome());
        Empresa empresa = this.repository.findById(tipoProcessoRequest.getIdEmpresa())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não localizada"));

        tipoProcesso.setEmpresa(empresa);

        this.tipoProcessoRepository.save(tipoProcesso);
    }

    public void criarTipoEtapa(TipoEtapaRequest tipoEtapaRequest) {
        TipoEtapa tipoEtapa = new TipoEtapa();

        tipoEtapa.setNome(tipoEtapaRequest.getNome());

        if(tipoEtapaRequest.getIdTipoProcesso() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Obrigatório o tipo processo");
        }

        if(tipoEtapaRequest.getIdEmpresa() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Obrigatório a empresa");
        }


        TipoProcesso tipoProcesso = this.tipoProcessoRepository.findById(tipoEtapaRequest.getIdTipoProcesso())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo processo não localizado"));

        tipoEtapa.setTipoProcesso(tipoProcesso);

        tipoEtapa.setPrazo(tipoEtapaRequest.getPrazo());

        Empresa empresa = this.repository.findById(tipoEtapaRequest.getIdEmpresa())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não localizada!"));


        tipoEtapa.setEmpresa(empresa);

        this.tipoEtapaRepository.save(tipoEtapa);

    }

    public void criarTipoDado(TipoDadoRequest tipoDadoRequest) {
        TipoDado tipoDado = new TipoDado();

        tipoDado.setNome(tipoDadoRequest.getNome());

        if(tipoDadoRequest.getIdTipoProcesso() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Obrigatório o tipo processo");
        }

        if(tipoDadoRequest.getIdEmpresa() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Obrigatório a empresa");
        }

        if(tipoDadoRequest.getIdTipoEtapa() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Obrigatório o tipo etapa");
        }

        TipoProcesso tipoProcesso = this.tipoProcessoRepository.findById(tipoDadoRequest.getIdTipoProcesso())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo Processo não localizado!"));

        TipoEtapa tipoEtapa = this.tipoEtapaRepository.findById(tipoDadoRequest.getIdTipoEtapa())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo Etapa não localizado!"));

        Empresa empresa = this.repository.findById(tipoDadoRequest.getIdEmpresa())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não localizada!"));


        tipoDado.setEmpresa(empresa);
        tipoDado.setTipoProcesso(tipoProcesso);
        tipoDado.setTipoEtapa(tipoEtapa);

        this.tipoDadoRepository.save(tipoDado);
    }

    public ResponseEntity<ProcessoCriadoResponse> iniciarProcesso(ProcessoRequest processoRequest) {
        TipoProcesso tipoProcesso = this.tipoProcessoRepository.findById(processoRequest.getIdTipoProcesso())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "tipo Procesos não localizado"));

        if(processoRequest.getIdCliente() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Processo sem cliente.");
        }
        Processo processo = new Processo();
        processo.setNome(processoRequest.getNome() != null ? processoRequest.getNome() : tipoProcesso.getNome());
        processo.setEmpresa(tipoProcesso.getEmpresa());
        processo.setDataCriacao(LocalDate.now());
        processo.setStatus(Status.EM_ANDAMENTO);
        processo.setDataLimite(processoRequest.getPrazo());

        Cliente cliente = this.clienteRepository.findById(processoRequest.getIdCliente())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não localizado!"));

        processo.setCliente(cliente);

        processo = this.processoRepository.save(processo);

       List<Etapa> etapas = this.criarEtapasDoProcesso(tipoProcesso.getTipoEtapas(), processo);


        processo = this.processoRepository.findById(processo.getId()).get();

        processo.setEtapas(etapas);

        return ResponseEntity.ok(new ProcessoCriadoResponse(processo));
    }

    private List<Etapa> criarEtapasDoProcesso(List<TipoEtapa> tipoEtapas, Processo processo){
        int i = 0;
        List<Etapa> etapas = new ArrayList<>();
        for(TipoEtapa tipoEtapa : tipoEtapas){
            Etapa etapa = new Etapa();
            etapa.setEmpresa(tipoEtapa.getEmpresa());
            etapa.setProcesso(processo);
            etapa.setNome(tipoEtapa.getNome());
            etapa.setDataCriacao(LocalDate.now());
            etapa.setDataLimite(LocalDate.now().plusDays(tipoEtapa.getPrazo()));
            etapa.setStatus(i == 0 ? Status.EM_ANDAMENTO : Status.NAO_INICIADA);
            i++;

            etapa = this.etapaRepository.save(etapa);

           List<Dado> dados = this.criarDadosDaEtapa(tipoEtapa.getTipoDados(), etapa);
           etapa.setDados(dados);
           etapas.add(etapa);

        }

        return etapas;
    }

    private List<Dado> criarDadosDaEtapa(List<TipoDado> tipoDados, Etapa etapa){
        List<Dado> dados = new ArrayList<>();
        int i = 0;
        for(TipoDado tipoDado : tipoDados){
            Dado dado = new Dado();

            dado.setEmpresa(tipoDado.getEmpresa());
            dado.setNome(tipoDado.getNome());
            dado.setDataLimite(LocalDate.now().plusDays(tipoDado.getPrazo()));
            dado.setEtapa(etapa);
            dado.setStatus(i == 0 && etapa.getStatus() == Status.EM_ANDAMENTO ? Status.EM_ANDAMENTO : Status.NAO_INICIADA);

            dado = this.dadoRepository.save(dado);
            dados.add(dado);
            i++;
        }

        return dados;
    }

    public ResponseEntity<ProcessoCriadoResponse> verDetalhesDoProcesso(Long idProcesso) {
        if(idProcesso == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sem código do processo!");
        }

        Processo processo = processoRepository.findById(idProcesso)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Processo não localizado"));

        List<Etapa> etapas = this.etapaRepository.findByProcesso(processo);

        etapas.forEach(etapa -> {
            List<Dado> dados = this.dadoRepository.findByEtapa(etapa);
            etapa.setDados(dados);
        });

        processo.setEtapas(etapas);

        return ResponseEntity.ok(new ProcessoCriadoResponse(processo));
    }

    public ResponseEntity<Void> concluirEtapa(Long idEtapa) {
        if(idEtapa == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sem código da etapa!");
        }

        Etapa etapa = this.etapaRepository.findById(idEtapa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Etapa não localizada"));

        List<Dado> dados = this.dadoRepository.findByEtapa(etapa);

        dados.forEach(dado -> dado.setStatus(Status.CONCLUIDO));

        this.dadoRepository.saveAll(dados);

        etapa.setStatus(Status.CONCLUIDO);

        this.etapaRepository.save(etapa);

        this.verificarSeHaProximaEtapa(etapa.getProcesso());

        return ResponseEntity.ok(null);
    }

    private void verificarSeHaProximaEtapa(Processo processo){
        List<Etapa> etapas = this.etapaRepository.findByProcessoAndStatus(processo, Status.NAO_INICIADA);

        if(etapas != null && etapas.size() > 0){
            Etapa etapa = etapas.stream().findFirst().orElse(null);

            if(etapa == null){
                return;
            }

            etapa.setStatus(Status.EM_ANDAMENTO);

            List<Dado> dados = this.dadoRepository.findByEtapa(etapa);

            if(dados != null && dados.size() > 0){
                Dado dado = dados.stream().findFirst().orElse(null);

                dado.setStatus(Status.EM_ANDAMENTO);

                this.dadoRepository.save(dado);
            }

            this.etapaRepository.save(etapa);
        }else{
            this.concluirProcesso(processo.getId());
        }
    }

    public ResponseEntity<EtapaResponse> alterarPrazoDaEtapa(AlterarPrazoRequest alterarPrazoRequest) {

        if(alterarPrazoRequest.getNovoPrazo() == null || alterarPrazoRequest.getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Faltando dados.");
        }

        Etapa etapa = this.etapaRepository.findById(alterarPrazoRequest.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Etapa não localizada"));

        etapa.setDataLimite(alterarPrazoRequest.getNovoPrazo());

        this.etapaRepository.save(etapa);

        return ResponseEntity.ok(new EtapaResponse(etapa));
    }

    public ResponseEntity<Void> concluirProcesso(Long idProcesso) {
        if(idProcesso == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sem código da etapa!");
        }

        Processo processo = this.processoRepository.findById(idProcesso)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Processo não localizado"));

        List<Etapa> etapas = this.etapaRepository.findByProcesso(processo);

        etapas.forEach(etapa -> {
            List<Dado> dados = this.dadoRepository.findByEtapa(etapa);
            dados.forEach(dado -> dado.setStatus(Status.CONCLUIDO));
            this.dadoRepository.saveAll(dados);

            etapa.setStatus(Status.CONCLUIDO);
        });


        this.etapaRepository.saveAll(etapas);

        processo.setStatus(Status.CONCLUIDO);

        processo = this.processoRepository.save(processo);


        return ResponseEntity.ok(null);
    }

    public ResponseEntity<ProcessoCriadoResponse> alterarPrazoDoProcesso(AlterarPrazoRequest alterarPrazoRequest) {

        if(alterarPrazoRequest.getNovoPrazo() == null || alterarPrazoRequest.getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Faltando dados.");
        }

        Processo processo = this.processoRepository.findById(alterarPrazoRequest.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Processo não localizado"));

        processo.setDataLimite(alterarPrazoRequest.getNovoPrazo());

        processo = this.processoRepository.save(processo);

        List<Etapa> etapas = this.etapaRepository.findByProcesso(processo);

        etapas.forEach(etapa -> {
            List<Dado> dados = this.dadoRepository.findByEtapa(etapa);
            etapa.setDados(dados);
        });

        processo.setEtapas(etapas);

        return ResponseEntity.ok(new ProcessoCriadoResponse(processo));
    }

    public ResponseEntity<Void> concluirDado(Long idDado) {
        if(idDado == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sem código do dado!");
        }

        Dado dado = this.dadoRepository.findById(idDado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dado não localizado"));

        dado.setStatus(Status.CONCLUIDO);

        dadoRepository.save(dado);

        this.verificarSeHaProximoDado(dado.getEtapa());
        return ResponseEntity.ok(null);
    }

    private void verificarSeHaProximoDado(Etapa etapa){
        List<Dado> dados = this.dadoRepository.findByEtapaAndStatus(etapa, Status.NAO_INICIADA);

        if(dados != null && dados.size() > 0){
            Dado dado = dados.stream().findFirst().orElse(null);

            if(dado == null){
                return ;
            }

            dado.setStatus(Status.EM_ANDAMENTO);

            this.dadoRepository.save(dado);
        }else{
            this.concluirEtapa(etapa.getId());
        }
    }

    public ResponseEntity<ProcessoCriadoResponse.DadoResponse> alterarPrazoDoDado(AlterarPrazoRequest alterarPrazoRequest) {
        if(alterarPrazoRequest.getNovoPrazo() == null || alterarPrazoRequest.getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Faltando dados.");
        }

        Dado dado = this.dadoRepository.findById(alterarPrazoRequest.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dado não localizado"));

        dado.setDataLimite(alterarPrazoRequest.getNovoPrazo());

        this.dadoRepository.save(dado);

        return ResponseEntity.ok(new ProcessoCriadoResponse.DadoResponse(dado));
    }



}
