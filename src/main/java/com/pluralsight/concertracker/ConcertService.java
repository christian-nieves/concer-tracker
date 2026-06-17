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
}
