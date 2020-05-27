package br.com.snack.apiservice.data.entity.order;


import br.com.snack.apiservice.data.entity.customer.Address;
import br.com.snack.apiservice.data.entity.customer.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table
public class Order {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Address address;

    @Column(nullable = false)
    private BigDecimal totalPaid;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime cretedAt;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
