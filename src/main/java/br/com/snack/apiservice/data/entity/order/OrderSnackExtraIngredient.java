package br.com.snack.apiservice.data.entity.order;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table
public class OrderSnackExtraIngredient implements Serializable {

    @EmbeddedId
    private OrderSnackExtraIngredientId id;

    @Column(nullable = false)
    private Integer amount;
}
