
package com.kdei.shortqr.repository;

import com.kdei.shortqr.entity.URL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<URL, Long> {

    Optional<URL> findByShortCode(String shortCode);

    boolean existsByShortCode(String shortCode);
}