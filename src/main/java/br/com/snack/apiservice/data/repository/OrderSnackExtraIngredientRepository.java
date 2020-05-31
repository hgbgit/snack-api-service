package br.com.snack.apiservice.data.repository;

import br.com.snack.apiservice.data.entity.order.OrderSnack;
import br.com.snack.apiservice.data.entity.order.OrderSnackExtraIngredientId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderSnackExtraIngredientRepository extends JpaRepository<OrderSnack, OrderSnackExtraIngredientId> {
}
