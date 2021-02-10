package com.Focus.Reddit.repository;

import com.Focus.Reddit.model.Country;
import com.Focus.Reddit.model.Customer;
import com.Focus.Reddit.model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ImageRepository extends JpaRepository<ImageModel, Long> {
    Optional<ImageModel> findByName(String name);
}
