package com.pluralsight.concertracker.data;

import com.pluralsight.concertracker.models.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Long> {

    List<Venue> findByCity(String city);

    List<Venue> findByNameContainingIgnoreCase(String name);

    List<Venue> findByCapacityGreaterThanEqual(int minCapacity);
}