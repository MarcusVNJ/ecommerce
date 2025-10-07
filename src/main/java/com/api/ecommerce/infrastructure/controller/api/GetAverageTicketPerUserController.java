package com.api.ecommerce.infrastructure.controller.api;

import com.api.ecommerce.application.ports.in.service.StatisticsUC;
import com.api.ecommerce.infrastructure.controller.resource.StatisticsResource;
import com.api.ecommerce.infrastructure.dto.StatisticsDTOs.AverageTicketDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class GetAverageTicketPerUserController implements StatisticsResource {

    final StatisticsUC statisticsUseCase;

    public GetAverageTicketPerUserController(StatisticsUC statisticsUseCase) {
        this.statisticsUseCase = statisticsUseCase;
    }

    @GetMapping("/average-ticket")
    public ResponseEntity<List<AverageTicketDTO>> execute() {
        return ResponseEntity.ok(statisticsUseCase.findAverageTicketPerUser());
    }

}
