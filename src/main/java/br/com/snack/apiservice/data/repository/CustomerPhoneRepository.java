package br.com.snack.apiservice.data.repository;

import br.com.snack.apiservice.data.entity.customer.CustomerAddressId;
import br.com.snack.apiservice.data.entity.customer.CustomerPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPhoneRepository extends JpaRepository<CustomerPhone, CustomerAddressId> {
}
