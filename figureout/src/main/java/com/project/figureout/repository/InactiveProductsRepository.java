package com.project.figureout.repository;

import com.project.figureout.model.InactiveProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InactiveProductsRepository extends JpaRepository<InactiveProducts, Long> {
}
