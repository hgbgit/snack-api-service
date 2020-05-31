package br.com.snack.apiservice.api.food;

import br.com.snack.apiservice.data.dto.food.IngredientResponse;
import br.com.snack.apiservice.service.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/ingredients", produces = MediaType.APPLICATION_JSON_VALUE)
public class IngredientController {

    private static final Logger logger = LoggerFactory.getLogger(IngredientController.class);

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public List<IngredientResponse> findAll() {
        logger.info("Received request for listing all ingredients.");
        return ingredientService.findAll();
    }

    @GetMapping("/{id}")
    public IngredientResponse bindById(@PathVariable UUID id) {
        logger.info("Received request for finding ingredient by id: {}", id);
        return ingredientService.findById(id);
    }
}
