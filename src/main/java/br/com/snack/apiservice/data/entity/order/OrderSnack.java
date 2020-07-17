package br.com.snack.apiservice.data.entity.order;


import br.com.snack.apiservice.data.entity.food.Snack;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "order")
@EqualsAndHashCode(exclude = "order")
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table
public class OrderSnack implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Snack snack;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private BigDecimal value;

    @OneToMany(mappedBy = "id.orderSnack", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private Set<OrderSnackExtraIngredient> orderSnackExtraIngredients;

}
