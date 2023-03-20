package com.gestaodaqualidade.repository;

import com.gestaodaqualidade.model.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {
}
