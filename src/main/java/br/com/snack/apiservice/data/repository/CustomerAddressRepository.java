package br.com.snack.apiservice.data.repository;

import br.com.snack.apiservice.data.entity.customer.CustomerAddress;
import br.com.snack.apiservice.data.entity.customer.CustomerAddressId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, CustomerAddressId> {

    Optional<CustomerAddress> findByIdCustomerIdAndIdAddressId(UUID customerId, UUID addressID);

}
