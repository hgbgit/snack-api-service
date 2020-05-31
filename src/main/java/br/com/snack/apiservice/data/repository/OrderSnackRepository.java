package br.com.snack.apiservice.data.repository;

import br.com.snack.apiservice.data.entity.order.OrderSnack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderSnackRepository extends JpaRepository<OrderSnack, UUID> {
}
