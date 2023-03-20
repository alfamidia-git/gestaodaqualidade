package com.gestaodaqualidade.repository;

import com.gestaodaqualidade.model.Etapa;
import com.gestaodaqualidade.model.Processo;
import com.gestaodaqualidade.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtapaRepository extends JpaRepository<Etapa, Long> {

    List<Etapa> findByProcesso(Processo processo);

    List<Etapa> findByProcessoAndStatus(Processo processo, Status status);
}
