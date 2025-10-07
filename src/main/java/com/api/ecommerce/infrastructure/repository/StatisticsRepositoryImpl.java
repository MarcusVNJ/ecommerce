package com.api.ecommerce.infrastructure.repository;

import com.api.ecommerce.application.ports.out.repository.StatisticsRepository;
import com.api.ecommerce.infrastructure.dto.StatisticsDTOs.TopBuyerDTO;
import com.api.ecommerce.infrastructure.dto.StatisticsDTOs.AverageTicketDTO;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class StatisticsRepositoryImpl implements StatisticsRepository {

    final SpringStatisticsRepository repository;

    public StatisticsRepositoryImpl(SpringStatisticsRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<TopBuyerDTO> findTop5Buyers() {
        return repository.findTop5Buyers();
    }

    @Override
    public List<AverageTicketDTO> findAverageTicketPerUser() {
        return repository.findAverageTicketPerUser();
    }

    @Override
    public BigDecimal findTotalRevenueInMonth() {
        return repository.findCurrentMonthRevenue();
    }
}
