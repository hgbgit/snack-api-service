package br.com.snack.apiservice.data.repository;

import br.com.snack.apiservice.data.entity.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    List<Customer> findByFirstNameContainingIgnoreCase(String firstName);

}
