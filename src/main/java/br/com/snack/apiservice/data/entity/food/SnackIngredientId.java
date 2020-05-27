package br.com.snack.apiservice.data.entity.food;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class SnackIngredientId implements Serializable {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Snack snack;

}
