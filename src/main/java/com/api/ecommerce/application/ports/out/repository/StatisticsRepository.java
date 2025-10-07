package com.api.ecommerce.application.ports.out.repository;

import com.api.ecommerce.infrastructure.dto.StatisticsDTOs.TopBuyerDTO;
import com.api.ecommerce.infrastructure.dto.StatisticsDTOs.AverageTicketDTO;

import java.math.BigDecimal;
import java.util.List;


public interface StatisticsRepository {

    List<TopBuyerDTO> findTop5Buyers();

    List<AverageTicketDTO> findAverageTicketPerUser();

    BigDecimal findTotalRevenueInMonth();

}
