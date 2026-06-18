package com.pluralsight.concertracker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

    // Find concerts by year
    @Query("SELECT c FROM Concert c WHERE c.concertYear = :year")
    List<Concert> findByYear(@Param("year") int year);

    // Find concerts by artist name containing text
    @Query("SELECT c FROM Concert c WHERE LOWER(c.artist.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Concert> findByArtistName(@Param("name") String name);

    // Find concerts by venue name
    @Query("SELECT c FROM Concert c WHERE LOWER(c.venue.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Concert> findByVenueName(@Param("name") String name);

    // Find concerts by city reaches through concert to venue to city
    @Query("SELECT c FROM Concert c WHERE LOWER(c.venue.city) = LOWER(:city)")
    List<Concert> findByCity(@Param("city") String city);

    // Find concerts at or below a maximum price
    @Query("SELECT c FROM Concert c WHERE c.ticketPrice <= :maxPrice")
    List<Concert> findByMaxPrice(@Param("maxPrice") double maxPrice);

    // Find concerts within a price range
    @Query("SELECT c FROM Concert c WHERE c.ticketPrice >= :minPrice AND c.ticketPrice <= :maxPrice")
    List<Concert> findByPriceRange(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);

    // Find concerts by max price and earliest year together
    @Query("SELECT c FROM Concert c WHERE c.ticketPrice <= :maxPrice AND c.concertYear >= :earliestYear")
    List<Concert> findByMaxPriceAndEarliestYear(@Param("maxPrice") double maxPrice, @Param("earliestYear") int earliestYear);

    // Revenue per venue total ticket price times tickets sold for each venue
    @Query("SELECT c.venue.name, SUM(c.ticketPrice * c.ticketsSold) FROM Concert c GROUP BY c.venue.name")
    List<Object[]> getRevenuePerVenue();

    // Count concerts per venue for busiest venue report
    @Query("SELECT c.venue.name, COUNT(c) FROM Concert c GROUP BY c.venue.name ORDER BY COUNT(c) DESC")
    List<Object[]> getConcertCountPerVenue();

    // Count concerts per artist for busiest artist report
    @Query("SELECT c.artist.name, COUNT(c) FROM Concert c GROUP BY c.artist.name ORDER BY COUNT(c) DESC")
    List<Object[]> getConcertCountPerArtist();

    // Average ticket price by year
    @Query("SELECT c.concertYear, AVG(c.ticketPrice) FROM Concert c GROUP BY c.concertYear ORDER BY c.concertYear")
    List<Object[]> getAveragePriceByYear();

    // Capacity report tickets sold vs venue capacity for each concert
    @Query("SELECT c.id, c.artist.name, c.venue.name, c.ticketsSold, c.venue.capacity FROM Concert c")
    List<Object[]> getCapacityReport();
}