package com.gestaodaqualidade.repository;

import com.gestaodaqualidade.model.TipoDado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDadoRepository extends JpaRepository<TipoDado, Long> {
}
