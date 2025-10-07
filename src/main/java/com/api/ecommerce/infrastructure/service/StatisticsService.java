package com.api.ecommerce.infrastructure.service;

import com.api.ecommerce.application.ports.in.service.StatisticsUC;
import com.api.ecommerce.application.ports.out.repository.StatisticsRepository;
import com.api.ecommerce.infrastructure.dto.StatisticsDTOs.AverageTicketDTO;
import com.api.ecommerce.infrastructure.dto.StatisticsDTOs.MonthlyRevenueDTO;
import com.api.ecommerce.infrastructure.dto.StatisticsDTOs.TopBuyerDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class StatisticsService implements StatisticsUC {

    private final StatisticsRepository statisticsRepository;

    public StatisticsService(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    @Override
    public List<TopBuyerDTO> findTop5Buyers() {
        return statisticsRepository.findTop5Buyers();
    }

    @Override
    public List<AverageTicketDTO> findAverageTicketPerUser() {
        return statisticsRepository.findAverageTicketPerUser();
    }

    @Override
    public MonthlyRevenueDTO findTotalRevenueInMonth() {
        BigDecimal total = statisticsRepository.findTotalRevenueInMonth();
        return new MonthlyRevenueDTO(total != null ? total : BigDecimal.ZERO);
    }

}
