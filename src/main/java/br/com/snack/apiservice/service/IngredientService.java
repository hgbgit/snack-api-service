package br.com.snack.apiservice.service;

import br.com.snack.apiservice.data.dto.food.IngredientResponse;
import br.com.snack.apiservice.data.entity.food.Ingredient;
import br.com.snack.apiservice.data.mapper.food.IngredientMapper;
import br.com.snack.apiservice.data.repository.IngredientRepository;
import br.com.snack.apiservice.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Autowired
    public IngredientService(IngredientRepository IngredientRepository, IngredientMapper ingredientMapper) {
        this.ingredientRepository = IngredientRepository;
        this.ingredientMapper = ingredientMapper;
    }

    @Transactional(readOnly = true)
    public List<IngredientResponse> findAll() {
        return ingredientRepository.findAll()
                                   .stream()
                                   .map(ingredientMapper::sourceToTarget)
                                   .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public IngredientResponse findById(UUID id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                                                    .orElseThrow(() -> new EntityNotFoundException(String.format("Ingrediente/Adicional [id= %s] nao encontrado.", id)));

        return ingredientMapper.sourceToTarget(ingredient);
    }
}
