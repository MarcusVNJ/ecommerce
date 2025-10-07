package com.api.ecommerce.infrastructure.config;

import com.api.ecommerce.domain.enums.OrderStatus;
import com.api.ecommerce.domain.enums.UserRole;
import com.api.ecommerce.infrastructure.entity.OrderEntity;
import com.api.ecommerce.infrastructure.entity.OrderItemEntity;
import com.api.ecommerce.infrastructure.entity.ProductEntity;
import com.api.ecommerce.infrastructure.entity.UserEntity;
import com.api.ecommerce.infrastructure.repository.SpringOrderRepository;
import com.api.ecommerce.infrastructure.repository.SpringProductRepository;
import com.api.ecommerce.infrastructure.repository.SpringUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DatabaseSeeder {

    @Bean
    @Transactional
    public CommandLineRunner initDatabase(
            SpringUserRepository userRepository,
            SpringProductRepository productRepository,
            SpringOrderRepository orderRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("admin@email.com").isPresent()) {
                System.out.println("Banco de dados já populado. Seeding ignorado.");
                return;
            }

            // 1. Criar Usuários
            String encodedPassword = passwordEncoder.encode("123456");

            // Deixe o JPA gerar os IDs
            UserEntity admin = new UserEntity(null, "Admin User", "admin@email.com", encodedPassword, UserRole.ADMIN);
            UserEntity user1 = new UserEntity(null, "Ana Silva", "ana.silva@email.com", encodedPassword, UserRole.USER);
            UserEntity user2 = new UserEntity(null, "Bruno Costa", "bruno.costa@email.com", encodedPassword, UserRole.USER);
            UserEntity user3 = new UserEntity(null, "Carla Dias", "carla.dias@email.com", encodedPassword, UserRole.USER);
            UserEntity user4 = new UserEntity(null, "Daniel Faria", "daniel.faria@email.com", encodedPassword, UserRole.USER);
            UserEntity user5 = new UserEntity(null, "Elisa Gomes", "elisa.gomes@email.com", encodedPassword, UserRole.USER);
            List<UserEntity> users = userRepository.saveAll(List.of(admin, user1, user2, user3, user4, user5));

            // Recuperar usuários salvos para usar suas referências
            UserEntity savedUser1 = users.stream().filter(u -> u.getEmail().equals("ana.silva@email.com")).findFirst().get();
            UserEntity savedUser2 = users.stream().filter(u -> u.getEmail().equals("bruno.costa@email.com")).findFirst().get();
            UserEntity savedUser3 = users.stream().filter(u -> u.getEmail().equals("carla.dias@email.com")).findFirst().get();
            UserEntity savedUser4 = users.stream().filter(u -> u.getEmail().equals("daniel.faria@email.com")).findFirst().get();

            // 2. Criar Produtos
            ProductEntity p1 = new ProductEntity(null, "Notebook Pro M3", "Notebook de alta performance com 16GB RAM.", new BigDecimal("4500.00"), "Eletrônicos", 10, null, null);
            ProductEntity p2 = new ProductEntity(null, "Mouse Gamer RGB", "Mouse com 16000 DPI e iluminação RGB.", new BigDecimal("250.50"), "Periféricos", 50, null, null);
            ProductEntity p3 = new ProductEntity(null, "Teclado Mecânico", "Teclado com switches blue.", new BigDecimal("350.00"), "Periféricos", 30, null, null);
            ProductEntity p4 = new ProductEntity(null, "Monitor 4K 27 polegadas", "Monitor com resolução Ultra HD.", new BigDecimal("2200.00"), "Monitores", 15, null, null);
            ProductEntity p5 = new ProductEntity(null, "Cadeira Gamer Confort", "Cadeira ergonômica para longas sessões.", new BigDecimal("800.00"), "Móveis", 2, null, null);
            List<ProductEntity> products = productRepository.saveAll(List.of(p1, p2, p3, p4, p5));

            // Recuperar produtos salvos para usar suas referências
            ProductEntity savedP1 = products.stream().filter(p -> p.getName().equals("Notebook Pro M3")).findFirst().get();
            ProductEntity savedP2 = products.stream().filter(p -> p.getName().equals("Mouse Gamer RGB")).findFirst().get();
            ProductEntity savedP3 = products.stream().filter(p -> p.getName().equals("Teclado Mecânico")).findFirst().get();
            ProductEntity savedP4 = products.stream().filter(p -> p.getName().equals("Monitor 4K 27 polegadas")).findFirst().get();
            ProductEntity savedP5 = products.stream().filter(p -> p.getName().equals("Cadeira Gamer Confort")).findFirst().get();

            // 3. Criar Pedidos e Itens
            // Pedido 1
            List<OrderItemEntity> itemsOrder1 = List.of(
                new OrderItemEntity(null, null, savedP1.getId(), 1, new BigDecimal("4500.00")),
                new OrderItemEntity(null, null, savedP5.getId(), 1, new BigDecimal("800.50"))
            );
            BigDecimal totalOrder1 = itemsOrder1.stream().map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
            OrderEntity order1 = new OrderEntity(null, savedUser1.getId(), new ArrayList<>(), totalOrder1, OrderStatus.PAID, LocalDateTime.of(2025, 9, 10, 10, 0), LocalDateTime.of(2025, 9, 10, 10, 5));
            itemsOrder1.forEach(item -> { item.setOrder(order1); order1.addItem(item); });
            orderRepository.save(order1);

            // Pedido 2
            List<OrderItemEntity> itemsOrder2 = List.of(
                new OrderItemEntity(null, null, savedP4.getId(), 1, new BigDecimal("2200.00")),
                new OrderItemEntity(null, null, savedP2.getId(), 1, new BigDecimal("250.50"))
            );
            BigDecimal totalOrder2 = itemsOrder2.stream().map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
            OrderEntity order2 = new OrderEntity(null, savedUser2.getId(), new ArrayList<>(), totalOrder2, OrderStatus.PAID, LocalDateTime.of(2025, 9, 15, 14, 30), LocalDateTime.of(2025, 9, 15, 14, 32));
            itemsOrder2.forEach(item -> { item.setOrder(order2); order2.addItem(item); });
            orderRepository.save(order2);

            // Pedido 3
            List<OrderItemEntity> itemsOrder3 = List.of(new OrderItemEntity(null, null, savedP5.getId(), 1, new BigDecimal("800.00")));
            BigDecimal totalOrder3 = itemsOrder3.stream().map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
            OrderEntity order3 = new OrderEntity(null, savedUser1.getId(), new ArrayList<>(), totalOrder3, OrderStatus.PAID, LocalDateTime.now(), LocalDateTime.now());
            itemsOrder3.forEach(item -> { item.setOrder(order3); order3.addItem(item); });
            orderRepository.save(order3);

            // Pedido 4
            List<OrderItemEntity> itemsOrder4 = List.of(new OrderItemEntity(null, null, savedP3.getId(), 1, new BigDecimal("350.00")));
            BigDecimal totalOrder4 = itemsOrder4.stream().map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
            OrderEntity order4 = new OrderEntity(null, savedUser3.getId(), new ArrayList<>(), totalOrder4, OrderStatus.PAID, LocalDateTime.now(), LocalDateTime.now());
            itemsOrder4.forEach(item -> { item.setOrder(order4); order4.addItem(item); });
            orderRepository.save(order4);

            // Pedido 5
            List<OrderItemEntity> itemsOrder5 = List.of(new OrderItemEntity(null, null, savedP2.getId(), 1, new BigDecimal("250.50")));
            BigDecimal totalOrder5 = itemsOrder5.stream().map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
            OrderEntity order5 = new OrderEntity(null, savedUser4.getId(), new ArrayList<>(), totalOrder5, OrderStatus.PENDING, LocalDateTime.now(), LocalDateTime.now());
            itemsOrder5.forEach(item -> { item.setOrder(order5); order5.addItem(item); });
            orderRepository.save(order5);

            System.out.println("Seeding completo do banco de dados concluído com sucesso.");
        };
    }
}