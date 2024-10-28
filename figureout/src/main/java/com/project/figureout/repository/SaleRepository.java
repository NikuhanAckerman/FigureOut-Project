package com.project.figureout.repository;

import com.project.figureout.dto.SaleDTO;
import com.project.figureout.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    // NÃO FAÇO IDEIA DE COMO QUE TÁ NO BANCO, TEM QUE VER DEPOIS COM CALMA. MAS A IDEIA É ESSA.
//    @Query("SELECT new com.project.figureout.dto.SaleDTO(p.name, FUNCTION('DATE_FORMAT', s.date, '%Y-%m'), SUM(s.quantity)) " +
//            "FROM Sale s JOIN s.product p " +
//            "GROUP BY p.id, FUNCTION('DATE_FORMAT', s.date, '%Y-%m')")
   // List<SaleDTO> findSalesByProductByMonth();
}
