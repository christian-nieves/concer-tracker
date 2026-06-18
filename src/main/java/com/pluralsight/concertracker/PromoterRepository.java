package com.pluralsight.concertracker;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PromoterRepository extends JpaRepository<Promoter, Long> {

    List<Promoter> findByNameContainingIgnoreCase(String name);
}
