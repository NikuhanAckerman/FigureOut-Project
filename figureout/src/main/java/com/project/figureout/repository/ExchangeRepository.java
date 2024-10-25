package com.project.figureout.repository;

import com.project.figureout.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
}
