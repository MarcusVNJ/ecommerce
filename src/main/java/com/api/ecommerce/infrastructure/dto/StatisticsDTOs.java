package com.api.ecommerce.infrastructure.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class StatisticsDTOs {

    public interface TopBuyerDTO {
        UUID getUserId();
        String getUserName();
        BigDecimal getTotalSpent();
    }

    public interface AverageTicketDTO {
        UUID getUserId();
        String getUserName();
        BigDecimal getAverageTicket();
    }

    public record MonthlyRevenueDTO(
            BigDecimal totalRevenue
    ) {}
}
