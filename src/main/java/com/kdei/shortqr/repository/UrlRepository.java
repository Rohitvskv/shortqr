package com.kdei.shortqr.repository;

import com.kdei.shortqr.entity.URL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<URL, Long> {

    Optional<URL> findByShortCode(String shortCode);

    boolean existsByShortCode(String shortCode);

    @Modifying
    @Query("""
            UPDATE URL u
            SET u.clickCount = u.clickCount + 1
            WHERE u.shortCode = :shortCode
            """)
    void incrementClickCount(@Param("shortCode") String shortCode);
}