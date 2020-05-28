package br.com.snack.apiservice.api.food;

import br.com.snack.apiservice.data.dto.food.SnackResponse;
import br.com.snack.apiservice.service.strategy.SnackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/snacks", produces = MediaType.APPLICATION_JSON_VALUE)
public class SnackController {

    private final static Logger logger = LoggerFactory.getLogger(SnackController.class);

    private final SnackService snackService;

    @Autowired
    public SnackController(SnackService snackService) {
        this.snackService = snackService;
    }

    @GetMapping
    public List<SnackResponse> findAll() {
        logger.info("Received request for list all snacks.");
        return snackService.findAll();
    }

    @GetMapping(value = "/{id}")
    public SnackResponse findAll(@PathVariable UUID id) {
        logger.info("Received request for find snack: {}", id);
        return snackService.findById(id);
    }

}
