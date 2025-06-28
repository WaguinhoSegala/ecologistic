package com.projeto.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.api.entity.PontoModel;

@Repository
public interface PontoRepository extends JpaRepository<PontoModel, Long>  {
    List<PontoModel> findByUserId(Long userId);
}
