package com.api.ecommerce.infrastructure.controller.api;

import com.api.ecommerce.application.ports.in.service.StatisticsUC;
import com.api.ecommerce.infrastructure.controller.resource.StatisticsResource;
import com.api.ecommerce.infrastructure.dto.StatisticsDTOs.MonthlyRevenueDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@PreAuthorize("hasRole('ADMIN')")
public class GetMonthlyRevenueController implements StatisticsResource {

    final StatisticsUC statisticsUseCase;

    public GetMonthlyRevenueController(StatisticsUC statisticsUseCase) {
        this.statisticsUseCase = statisticsUseCase;
    }

    @GetMapping("/monthly-revenue")
    public ResponseEntity<MonthlyRevenueDTO> execute() {
        return ResponseEntity.ok(statisticsUseCase.findTotalRevenueInMonth());
    }

}
