package com.api.ecommerce.infrastructure.repository;

import com.api.ecommerce.infrastructure.dto.StatisticsDTOs.TopBuyerDTO;
import com.api.ecommerce.infrastructure.dto.StatisticsDTOs.AverageTicketDTO;
import com.api.ecommerce.infrastructure.entity.OrderEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface SpringStatisticsRepository extends JpaRepository<OrderEntity, UUID> {

    @Query("""
                    SELECT u.id AS userId, u.name AS userName, SUM(o.totalAmount) AS totalSpent
                    FROM OrderEntity o JOIN UserEntity u ON o.userId = u.id
                    WHERE o.status = 'PAID'
                    GROUP BY u.id, u.name
                    ORDER BY totalSpent DESC
                    LIMIT 5
            """)
    List<TopBuyerDTO> findTop5Buyers();


    @Query("""
                    SELECT u.id AS userId, u.name AS userName, AVG(o.totalAmount) AS averageTicket
                    FROM OrderEntity o JOIN UserEntity u ON o.userId = u.id
                    WHERE o.status = 'PAID'
                    GROUP BY u.id, u.name
                    ORDER BY averageTicket DESC
            """)
    List<AverageTicketDTO> findAverageTicketPerUser();


    @Query("""
            SELECT COALESCE(SUM(o.totalAmount), 0)
            FROM OrderEntity o
            WHERE o.status = 'PAID'
            AND YEAR(o.createdAt) = YEAR(CURRENT_DATE)
            AND MONTH(o.createdAt) = MONTH(CURRENT_DATE)
            """)
    BigDecimal findCurrentMonthRevenue();

}
