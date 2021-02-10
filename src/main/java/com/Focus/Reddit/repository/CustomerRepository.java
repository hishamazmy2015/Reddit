package com.Focus.Reddit.repository;

import com.Focus.Reddit.model.Country;
import com.Focus.Reddit.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByFirstName(String firstName);

//    List<Customer> findByCountry(KeplerWorx.KeplerWorx.model.Customer byId);
    Customer findByCountry(Country name);


}
