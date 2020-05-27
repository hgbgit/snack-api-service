package br.com.snack.apiservice.data.entity.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table
public class Phone {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String countryCode;

    @Column(nullable = false)
    private Integer areaCode;

    @Column(nullable = false)
    private Long number;

}
