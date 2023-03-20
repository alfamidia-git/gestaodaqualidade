package com.gestaodaqualidade.repository;

import com.gestaodaqualidade.model.TipoEtapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoEtapaRepository extends JpaRepository<TipoEtapa, Long> {
}
