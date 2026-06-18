package com.pluralsight.concertracker.data;

import com.pluralsight.concertracker.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    List<Artist> findByGenre(String genre);

    List<Artist> findByNameContainingIgnoreCase(String name);
}