package com.api.ecommerce.application.ports.in.service;

import com.api.ecommerce.infrastructure.dto.StatisticsDTOs.TopBuyerDTO;
import com.api.ecommerce.infrastructure.dto.StatisticsDTOs.AverageTicketDTO;
import com.api.ecommerce.infrastructure.dto.StatisticsDTOs.MonthlyRevenueDTO;

import java.util.List;

public interface StatisticsUC {

    List<TopBuyerDTO> findTop5Buyers();

    List<AverageTicketDTO> findAverageTicketPerUser();

    MonthlyRevenueDTO findTotalRevenueInMonth();

}