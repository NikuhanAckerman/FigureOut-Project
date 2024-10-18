package com.project.figureout.repository;

import com.project.figureout.model.ActiveProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveProductsRepository extends JpaRepository<ActiveProducts, Long> {
}
