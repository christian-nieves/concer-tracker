package com.pluralsight.concertracker;

import jakarta.persistence.*;

@Entity
@Table(name = "concert")
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int concertYear;
    private double ticketPrice;
    private int ticketsSold;

    // Each concert must have exactly one artist, one venue, and one promoter
    @ManyToOne(optional = false)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToOne(optional = false)
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @ManyToOne(optional = false)
    @JoinColumn(name = "promoter_id")
    private Promoter promoter;

    public Concert() {}

    public Concert(int concertYear, double ticketPrice, int ticketsSold, Artist artist, Venue venue, Promoter promoter) {
        this.concertYear = concertYear;
        this.ticketPrice = ticketPrice;
        this.ticketsSold = ticketsSold;
        this.artist = artist;
        this.venue = venue;
        this.promoter = promoter;
    }

    public Long getId() {
        return id;
    }

    public int getConcertYear() {
        return concertYear;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public Artist getArtist() {
        return artist;
    }

    public Venue getVenue() {
        return venue;
    }

    public Promoter getPromoter() {
        return promoter;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setConcertYear(int concertYear) {
        this.concertYear = concertYear;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public void setPromoter(Promoter promoter) {
        this.promoter = promoter;
    }
}