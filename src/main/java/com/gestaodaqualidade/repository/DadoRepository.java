package com.gestaodaqualidade.repository;

import com.gestaodaqualidade.model.Dado;
import com.gestaodaqualidade.model.Etapa;
import com.gestaodaqualidade.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DadoRepository extends JpaRepository<Dado, Long> {
    List<Dado> findByEtapa(Etapa etapa);

    List<Dado> findByEtapaAndStatus(Etapa etapa, Status status);
}
