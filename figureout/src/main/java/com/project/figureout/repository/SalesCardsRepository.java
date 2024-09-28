package com.project.figureout.repository;

import com.project.figureout.model.SalesCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesCardsRepository extends JpaRepository<SalesCards, Long> {
}
