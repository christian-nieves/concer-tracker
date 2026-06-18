package com.pluralsight.concertracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {

    private final VenueRepository venueRepository;
    private final ArtistRepository artistRepository;
    private final PromoterRepository promoterRepository;
    private final ConcertRepository concertRepository;

    @Autowired
    public ConcertService(VenueRepository venueRepository,
                          ArtistRepository artistRepository,
                          PromoterRepository promoterRepository,
                          ConcertRepository concertRepository) {
        this.venueRepository = venueRepository;
        this.artistRepository = artistRepository;
        this.promoterRepository = promoterRepository;
        this.concertRepository = concertRepository;
    }

    // Returns all venues
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    // Returns all artists
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    // Returns all promoters
    public List<Promoter> getAllPromoters() {
        return promoterRepository.findAll();
    }

    // Returns all concerts
    public List<Concert> getAllConcerts() {
        return concertRepository.findAll();
    }

    // Saves a venue
    public Venue saveVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    // Saves an artist
    public Artist saveArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    // Saves a promoter
    public Promoter savePromoter(Promoter promoter) {
        return promoterRepository.save(promoter);
    }

    // Saves a concert
    public Concert saveConcert(Concert concert) {
        return concertRepository.save(concert);
    }

    // Checks if the database is empty (used before seeding)
    public boolean isDatabaseEmpty() {
        return venueRepository.count() == 0;
    }

    // Find a venue by its id
    public Venue getVenueById(Long id) {
        return venueRepository.findById(id).orElse(null);
    }

    // Find an artist by its id
    public Artist getArtistById(Long id) {
        return artistRepository.findById(id).orElse(null);
    }

    // Find a promoter by its id
    public Promoter getPromoterById(Long id) {
        return promoterRepository.findById(id).orElse(null);
    }

    // Find a concert by its id
    public Concert getConcertById(Long id) {
        return concertRepository.findById(id).orElse(null);
    }

    // Delete a venue by its id
    public void deleteVenue(Long id) {
        venueRepository.deleteById(id);
    }

    // Delete an artist by its id
    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }

    // Delete a promoter by its id
    public void deletePromoter(Long id) {
        promoterRepository.deleteById(id);
    }

    // Delete a concert by its id
    public void deleteConcert(Long id) {
        concertRepository.deleteById(id);
    }

    // Find venues by city
    public List<Venue> getVenuesByCity(String city) {
        return venueRepository.findByCity(city);
    }

    // Find venues by name containing text
    public List<Venue> getVenuesByName(String name) {
        return venueRepository.findByNameContainingIgnoreCase(name);
    }

    // Find venues by minimum capacity
    public List<Venue> getVenuesByMinCapacity(int minCapacity) {
        return venueRepository.findByCapacityGreaterThanEqual(minCapacity);
    }

    // Find artists by genre
    public List<Artist> getArtistsByGenre(String genre) {
        return artistRepository.findByGenre(genre);
    }

    // Find artists by name containing text
    public List<Artist> getArtistsByName(String name) {
        return artistRepository.findByNameContainingIgnoreCase(name);
    }

    // Find promoters by name containing text
    public List<Promoter> getPromotersByName(String name) {
        return promoterRepository.findByNameContainingIgnoreCase(name);
    }

    // Find concerts by year
    public List<Concert> getConcertsByYear(int year) {
        return concertRepository.findByYear(year);
    }

    // Find concerts by artist name
    public List<Concert> getConcertsByArtistName(String name) {
        return concertRepository.findByArtistName(name);
    }

    // Find concerts by venue name
    public List<Concert> getConcertsByVenueName(String name) {
        return concertRepository.findByVenueName(name);
    }

    // Find concerts by city
    public List<Concert> getConcertsByCity(String city) {
        return concertRepository.findByCity(city);
    }

    // Find concerts by maximum price
    public List<Concert> getConcertsByMaxPrice(double maxPrice) {
        return concertRepository.findByMaxPrice(maxPrice);
    }

    // Find concerts by price range
    public List<Concert> getConcertsByPriceRange(double minPrice, double maxPrice) {
        return concertRepository.findByPriceRange(minPrice, maxPrice);
    }

    // Find concerts by max price and earliest year
    public List<Concert> getConcertsByMaxPriceAndEarliestYear(double maxPrice, int earliestYear) {
        return concertRepository.findByMaxPriceAndEarliestYear(maxPrice, earliestYear);
    }

    // Get revenue per venue
    public List<Object[]> getRevenuePerVenue() {
        return concertRepository.getRevenuePerVenue();
    }

    // Get concert count per venue for busiest venue report
    public List<Object[]> getConcertCountPerVenue() {
        return concertRepository.getConcertCountPerVenue();
    }

    // Get concert count per artist for busiest artist report
    public List<Object[]> getConcertCountPerArtist() {
        return concertRepository.getConcertCountPerArtist();
    }

    // Get average ticket price by year
    public List<Object[]> getAveragePriceByYear() {
        return concertRepository.getAveragePriceByYear();
    }

    // Get capacity report for each concert
    public List<Object[]> getCapacityReport() {
        return concertRepository.getCapacityReport();
    }
}
