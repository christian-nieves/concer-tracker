package com.pluralsight.concertracker.data;

import com.pluralsight.concertracker.models.Promoter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PromoterRepository extends JpaRepository<Promoter, Long> {

    List<Promoter> findByNameContainingIgnoreCase(String name);
}
