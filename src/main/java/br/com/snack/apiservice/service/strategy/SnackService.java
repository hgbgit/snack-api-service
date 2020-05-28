package br.com.snack.apiservice.service.strategy;

import br.com.snack.apiservice.data.dto.food.SnackResponse;
import br.com.snack.apiservice.data.entity.food.Snack;
import br.com.snack.apiservice.data.mapper.food.SnackMapper;
import br.com.snack.apiservice.data.repository.SnackRepository;
import br.com.snack.apiservice.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SnackService {

    private final SnackRepository snackRepository;
    private final SnackMapper snackMapper;

    @Autowired
    public SnackService(SnackRepository snackRepository, SnackMapper snackMapper) {
        this.snackRepository = snackRepository;
        this.snackMapper = snackMapper;
    }

    @Transactional(readOnly = true)
    public List<SnackResponse> findAll() {
        return snackRepository.findAll()
                              .stream()
                              .map(snackMapper::targetToSource)
                              .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SnackResponse findById(UUID id) {
        Snack snack = snackRepository.findById(id)
                                     .orElseThrow(() -> new EntityNotFoundException(String.format("Lanche [id= %s] nao encontrado.", id)));

        return snackMapper.targetToSource(snack);
    }
}
