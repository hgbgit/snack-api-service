package br.com.snack.apiservice.data.entity.order;

import br.com.snack.apiservice.data.entity.food.Ingredient;
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
public class OrderSnackExtraIngredientId implements Serializable {

    @ManyToOne
    @JoinColumn(nullable = false)
    private OrderSnack orderSnack;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Ingredient ingredient;

}
