package br.com.snack.apiservice.data.repository;

import br.com.snack.apiservice.data.entity.food.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {

    Optional<Ingredient> findTop1ByNameContainingIgnoreCase(String name);
}
