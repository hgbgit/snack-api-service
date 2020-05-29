package br.com.snack.apiservice.data.repository;

import br.com.snack.apiservice.data.entity.customer.CustomerAddress;
import br.com.snack.apiservice.data.entity.customer.CustomerAddressId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, CustomerAddressId> {
}
