package com.pluralsight.concertracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ConcertTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConcertTrackerApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(ConcertService concertService) {
        return args -> {

            // Seed the database only if it is empty
            if (concertService.isDatabaseEmpty()) {
                seedData(concertService);
            }

            // Start the main menu
            runMainMenu(concertService);
        };
    }

    // Seeds starter data into the database
    private void seedData(ConcertService concertService) {

        // Create venues
        Venue venue1 = concertService.saveVenue(new Venue("Madison Square Garden", "New York", 20000));
        Venue venue2 = concertService.saveVenue(new Venue("The Forum", "Los Angeles", 17500));
        Venue venue3 = concertService.saveVenue(new Venue("United Center", "Chicago", 23500));
        Venue venue4 = concertService.saveVenue(new Venue("Red Rocks Amphitheatre", "Morrison", 9525));

        // Create artists
        Artist artist1 = concertService.saveArtist(new Artist("Taylor Swift", "pop"));
        Artist artist2 = concertService.saveArtist(new Artist("Kendrick Lamar", "hip-hop"));
        Artist artist3 = concertService.saveArtist(new Artist("The Beatles Revival", "rock"));
        Artist artist4 = concertService.saveArtist(new Artist("Miles Davis Tribute", "jazz"));

        // Create promoters
        Promoter promoter1 = concertService.savePromoter(new Promoter("Live Nation"));
        Promoter promoter2 = concertService.savePromoter(new Promoter("AEG Presents"));
        Promoter promoter3 = concertService.savePromoter(new Promoter("Goldenvoice"));

        // Create concerts
        concertService.saveConcert(new Concert(2023, 150.00, 19500, artist1, venue1, promoter1));
        concertService.saveConcert(new Concert(2023, 120.00, 17000, artist2, venue2, promoter2));
        concertService.saveConcert(new Concert(2024, 95.00, 9000, artist3, venue4, promoter3));
        concertService.saveConcert(new Concert(2024, 200.00, 22000, artist1, venue3, promoter1));
        concertService.saveConcert(new Concert(2024, 75.00, 8000, artist4, venue4, promoter2));
        concertService.saveConcert(new Concert(2022, 110.00, 16000, artist2, venue1, promoter1));
        concertService.saveConcert(new Concert(2022, 85.00, 9200, artist3, venue4, promoter3));
        concertService.saveConcert(new Concert(2023, 175.00, 20000, artist1, venue2, promoter2));
        concertService.saveConcert(new Concert(2025, 130.00, 12000, artist2, venue3, promoter1));
        concertService.saveConcert(new Concert(2025, 90.00, 9525, artist4, venue4, promoter3));

        System.out.println("Starter data loaded successfully.");
    }

    // Main menu loop
    private void runMainMenu(ConcertService concertService) {

        Scanner myScanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n===== Concert Tracker =====");
            System.out.println("1) Concerts");
            System.out.println("2) Search Concerts");
            System.out.println("3) Artists");
            System.out.println("4) Venues");
            System.out.println("5) Promoters");
            System.out.println("6) Reports");
            System.out.println("0) Quit");
            System.out.print("Choose an option: ");

            String userChoice = myScanner.nextLine().trim();

            switch (userChoice) {
                case "1":
                    showConcerts(concertService, myScanner);
                    break;
                case "0":
                    keepRunning = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        myScanner.close();
    }

    // Concerts screen — for now just lists all concerts
    private void showConcerts(ConcertService concertService, Scanner myScanner) {

        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n===== Concerts =====");
            System.out.println("1) List all concerts");
            System.out.println("0) Back");
            System.out.print("Choose an option: ");

            String userChoice = myScanner.nextLine().trim();

            switch (userChoice) {
                case "1":
                    listAllConcerts(concertService);
                    break;
                case "0":
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Lists all concerts with artist name and venue name
    private void listAllConcerts(ConcertService concertService) {

        List<Concert> allConcerts = concertService.getAllConcerts();

        if (allConcerts.isEmpty()) {
            System.out.println("No concerts found.");
            return;
        }

        System.out.println("\n--- All Concerts ---");
        for (Concert concert : allConcerts) {
            System.out.println("ID: " + concert.getId()
                    + " | Year: " + concert.getConcertYear()
                    + " | Artist: " + concert.getArtist().getName()
                    + " | Venue: " + concert.getVenue().getName()
                    + " | Price: $" + concert.getTicketPrice()
                    + " | Tickets Sold: " + concert.getTicketsSold());
        }
    }
}
