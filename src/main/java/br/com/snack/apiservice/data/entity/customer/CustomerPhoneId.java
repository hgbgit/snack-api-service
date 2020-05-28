package br.com.snack.apiservice.data.entity.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerPhoneId implements Serializable {

    @ManyToOne
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(nullable = false, updatable = false)
    private Customer customer;

    @ManyToOne
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @JoinColumn(nullable = false, updatable = false)
    private Phone phone;

}
