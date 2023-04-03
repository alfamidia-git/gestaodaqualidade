package com.gestaodaqualidade.repository;

import com.gestaodaqualidade.model.Empresa;
import com.gestaodaqualidade.model.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    @Query("SELECT p FROM Processo p WHERE p.dataLimite < :dataAtual AND p.status != 0")
    List<Processo> buscarAtrasados(@Param("dataAtual") LocalDate dataAtual);
    @Query("SELECT p FROM Processo p WHERE p.empresa = :empresa AND p.dataLimite < :dataAtual AND p.status != 0")
    List<Processo> buscarPorEmpresaEAtrasados(@Param("empresa") Empresa empresa, @Param("dataAtual") LocalDate dataAtual);
}
