package br.com.snack.apiservice.data.entity.order;

import br.com.snack.apiservice.data.entity.food.Ingredient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderSnackExtraIngredientId that = (OrderSnackExtraIngredientId) o;
        return Objects.equals(orderSnack.getId(), that.orderSnack.getId()) &&
               Objects.equals(ingredient.getId(), that.ingredient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderSnack.getId(), ingredient.getId());
    }

}
