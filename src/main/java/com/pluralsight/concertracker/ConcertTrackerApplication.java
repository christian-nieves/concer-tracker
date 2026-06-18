package com.pluralsight.concertracker;

import com.pluralsight.concertracker.models.Artist;
import com.pluralsight.concertracker.models.Concert;
import com.pluralsight.concertracker.models.Promoter;
import com.pluralsight.concertracker.models.Venue;
import com.pluralsight.concertracker.service.ConcertService;
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
                case "2":
                    showSearchConcerts(concertService, myScanner);
                    break;
                case "3":
                    showArtists(concertService, myScanner);
                    break;
                case "4":
                    showVenues(concertService, myScanner);
                    break;
                case "5":
                    showPromoters(concertService, myScanner);
                    break;
                case "6":
                    showReports(concertService, myScanner);
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

    // Concerts screen
    private void showConcerts(ConcertService concertService, Scanner myScanner) {

        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n===== Concerts =====");
            System.out.println("1) List all concerts");
            System.out.println("2) View concert by ID");
            System.out.println("3) Add a concert");
            System.out.println("4) Update ticket price");
            System.out.println("5) Update tickets sold");
            System.out.println("6) Delete a concert");
            System.out.println("0) Back");
            System.out.print("Choose an option: ");

            String userChoice = myScanner.nextLine().trim();

            switch (userChoice) {
                case "1":
                    listAllConcerts(concertService);
                    break;
                case "2":
                    viewConcertById(concertService, myScanner);
                    break;
                case "3":
                    addConcert(concertService, myScanner);
                    break;
                case "4":
                    updateTicketPrice(concertService, myScanner);
                    break;
                case "5":
                    updateTicketsSold(concertService, myScanner);
                    break;
                case "6":
                    deleteConcert(concertService, myScanner);
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

    // View one concert by ID
    private void viewConcertById(ConcertService concertService, Scanner myScanner) {

        System.out.print("Enter concert ID: ");
        Long concertId = Long.parseLong(myScanner.nextLine().trim());

        Concert foundConcert = concertService.getConcertById(concertId);

        if (foundConcert == null) {
            System.out.println("No concert found with ID " + concertId);
            return;
        }

        System.out.println("\n--- Concert Details ---");
        System.out.println("ID: " + foundConcert.getId());
        System.out.println("Year: " + foundConcert.getConcertYear());
        System.out.println("Artist: " + foundConcert.getArtist().getName());
        System.out.println("Venue: " + foundConcert.getVenue().getName());
        System.out.println("Promoter: " + foundConcert.getPromoter().getName());
        System.out.println("Ticket Price: $" + foundConcert.getTicketPrice());
        System.out.println("Tickets Sold: " + foundConcert.getTicketsSold());
    }

    // Add a new concert
    private void addConcert(ConcertService concertService, Scanner myScanner) {

        // Show all artists so user can pick
        System.out.println("\n--- Available Artists ---");
        for (Artist artist : concertService.getAllArtists()) {
            System.out.println("ID: " + artist.getId() + " | " + artist.getName());
        }
        System.out.print("Enter artist ID: ");
        Long artistId = Long.parseLong(myScanner.nextLine().trim());
        Artist selectedArtist = concertService.getArtistById(artistId);

        if (selectedArtist == null) {
            System.out.println("Artist not found.");
            return;
        }

        // Show all venues so user can pick
        System.out.println("\n--- Available Venues ---");
        for (Venue venue : concertService.getAllVenues()) {
            System.out.println("ID: " + venue.getId() + " | " + venue.getName() + " (Capacity: " + venue.getCapacity() + ")");
        }
        System.out.print("Enter venue ID: ");
        Long venueId = Long.parseLong(myScanner.nextLine().trim());
        Venue selectedVenue = concertService.getVenueById(venueId);

        if (selectedVenue == null) {
            System.out.println("Venue not found.");
            return;
        }

        // Show all promoters so user can pick
        System.out.println("\n--- Available Promoters ---");
        for (Promoter promoter : concertService.getAllPromoters()) {
            System.out.println("ID: " + promoter.getId() + " | " + promoter.getName());
        }
        System.out.print("Enter promoter ID: ");
        Long promoterId = Long.parseLong(myScanner.nextLine().trim());
        Promoter selectedPromoter = concertService.getPromoterById(promoterId);

        if (selectedPromoter == null) {
            System.out.println("Promoter not found.");
            return;
        }

        System.out.print("Enter year: ");
        int concertYear = Integer.parseInt(myScanner.nextLine().trim());

        System.out.print("Enter ticket price: ");
        double ticketPrice = Double.parseDouble(myScanner.nextLine().trim());

        if (ticketPrice < 0) {
            System.out.println("Ticket price cannot be negative.");
            return;
        }

        System.out.print("Enter tickets sold: ");
        int ticketsSold = Integer.parseInt(myScanner.nextLine().trim());

        if (ticketsSold < 0) {
            System.out.println("Tickets sold cannot be negative.");
            return;
        }

        if (ticketsSold > selectedVenue.getCapacity()) {
            System.out.println("Tickets sold cannot exceed venue capacity of " + selectedVenue.getCapacity());
            return;
        }

        Concert newConcert = new Concert(concertYear, ticketPrice, ticketsSold, selectedArtist, selectedVenue, selectedPromoter);
        concertService.saveConcert(newConcert);
        System.out.println("Concert added successfully.");
    }

    // Update a concert's ticket price
    private void updateTicketPrice(ConcertService concertService, Scanner myScanner) {

        listAllConcerts(concertService);
        System.out.print("Enter concert ID to update: ");
        Long concertId = Long.parseLong(myScanner.nextLine().trim());

        Concert foundConcert = concertService.getConcertById(concertId);

        if (foundConcert == null) {
            System.out.println("No concert found with ID " + concertId);
            return;
        }

        System.out.print("Enter new ticket price: ");
        double newPrice = Double.parseDouble(myScanner.nextLine().trim());

        if (newPrice < 0) {
            System.out.println("Ticket price cannot be negative.");
            return;
        }

        foundConcert.setTicketPrice(newPrice);
        concertService.saveConcert(foundConcert);
        System.out.println("Ticket price updated successfully.");
    }

    // Update a concert's tickets sold
    private void updateTicketsSold(ConcertService concertService, Scanner myScanner) {

        listAllConcerts(concertService);
        System.out.print("Enter concert ID to update: ");
        Long concertId = Long.parseLong(myScanner.nextLine().trim());

        Concert foundConcert = concertService.getConcertById(concertId);

        if (foundConcert == null) {
            System.out.println("No concert found with ID " + concertId);
            return;
        }

        System.out.print("Enter new tickets sold: ");
        int newTicketsSold = Integer.parseInt(myScanner.nextLine().trim());

        if (newTicketsSold < 0) {
            System.out.println("Tickets sold cannot be negative.");
            return;
        }

        if (newTicketsSold > foundConcert.getVenue().getCapacity()) {
            System.out.println("Tickets sold cannot exceed venue capacity of " + foundConcert.getVenue().getCapacity());
            return;
        }

        foundConcert.setTicketsSold(newTicketsSold);
        concertService.saveConcert(foundConcert);
        System.out.println("Tickets sold updated successfully.");
    }

    // Delete a concert
    private void deleteConcert(ConcertService concertService, Scanner myScanner) {

        listAllConcerts(concertService);
        System.out.print("Enter concert ID to delete: ");
        Long concertId = Long.parseLong(myScanner.nextLine().trim());

        Concert foundConcert = concertService.getConcertById(concertId);

        if (foundConcert == null) {
            System.out.println("No concert found with ID " + concertId);
            return;
        }

        concertService.deleteConcert(concertId);
        System.out.println("Concert deleted successfully.");
    }

    // Search concerts screen
    private void showSearchConcerts(ConcertService concertService, Scanner myScanner) {

        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n===== Search Concerts =====");
            System.out.println("1) By year");
            System.out.println("2) By artist");
            System.out.println("3) By venue");
            System.out.println("4) By city");
            System.out.println("5) By maximum price");
            System.out.println("6) By price range");
            System.out.println("7) Advanced (max price + earliest year)");
            System.out.println("0) Back");
            System.out.print("Choose an option: ");

            String userChoice = myScanner.nextLine().trim();

            switch (userChoice) {
                case "1":
                    searchByYear(concertService, myScanner);
                    break;
                case "2":
                    searchByArtist(concertService, myScanner);
                    break;
                case "3":
                    searchByVenue(concertService, myScanner);
                    break;
                case "4":
                    searchByCity(concertService, myScanner);
                    break;
                case "5":
                    searchByMaxPrice(concertService, myScanner);
                    break;
                case "6":
                    searchByPriceRange(concertService, myScanner);
                    break;
                case "7":
                    searchByMaxPriceAndEarliestYear(concertService, myScanner);
                    break;
                case "0":
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Helper to print a list of concerts
    private void printConcertList(List<Concert> concerts) {

        if (concerts.isEmpty()) {
            System.out.println("No concerts found matching that search.");
            return;
        }

        for (Concert concert : concerts) {
            System.out.println("ID: " + concert.getId()
                    + " | Year: " + concert.getConcertYear()
                    + " | Artist: " + concert.getArtist().getName()
                    + " | Venue: " + concert.getVenue().getName()
                    + " | Price: $" + concert.getTicketPrice()
                    + " | Tickets Sold: " + concert.getTicketsSold());
        }
    }

    // Search by year
    private void searchByYear(ConcertService concertService, Scanner myScanner) {

        System.out.print("Enter year: ");
        int year = Integer.parseInt(myScanner.nextLine().trim());

        List<Concert> results = concertService.getConcertsByYear(year);

        System.out.println("\n--- Concerts in " + year + " ---");
        printConcertList(results);
    }

    // Search by artist name
    private void searchByArtist(ConcertService concertService, Scanner myScanner) {

        System.out.print("Enter artist name: ");
        String name = myScanner.nextLine().trim();

        List<Concert> results = concertService.getConcertsByArtistName(name);

        System.out.println("\n--- Concerts by Artist: " + name + " ---");
        printConcertList(results);
    }

    // Search by venue name
    private void searchByVenue(ConcertService concertService, Scanner myScanner) {

        System.out.print("Enter venue name: ");
        String name = myScanner.nextLine().trim();

        List<Concert> results = concertService.getConcertsByVenueName(name);

        System.out.println("\n--- Concerts at Venue: " + name + " ---");
        printConcertList(results);
    }

    // Search by city
    private void searchByCity(ConcertService concertService, Scanner myScanner) {

        System.out.print("Enter city: ");
        String city = myScanner.nextLine().trim();

        List<Concert> results = concertService.getConcertsByCity(city);

        System.out.println("\n--- Concerts in City: " + city + " ---");
        printConcertList(results);
    }

    // Search by maximum price
    private void searchByMaxPrice(ConcertService concertService, Scanner myScanner) {

        System.out.print("Enter maximum price: ");
        double maxPrice = Double.parseDouble(myScanner.nextLine().trim());

        List<Concert> results = concertService.getConcertsByMaxPrice(maxPrice);

        System.out.println("\n--- Concerts at or below $" + maxPrice + " ---");
        printConcertList(results);
    }

    // Search by price range
    private void searchByPriceRange(ConcertService concertService, Scanner myScanner) {

        System.out.print("Enter minimum price: ");
        double minPrice = Double.parseDouble(myScanner.nextLine().trim());

        System.out.print("Enter maximum price: ");
        double maxPrice = Double.parseDouble(myScanner.nextLine().trim());

        List<Concert> results = concertService.getConcertsByPriceRange(minPrice, maxPrice);

        System.out.println("\n--- Concerts between $" + minPrice + " and $" + maxPrice + " ---");
        printConcertList(results);
    }

    // Search by max price and earliest year together
    private void searchByMaxPriceAndEarliestYear(ConcertService concertService, Scanner myScanner) {

        System.out.print("Enter maximum price: ");
        double maxPrice = Double.parseDouble(myScanner.nextLine().trim());

        System.out.print("Enter earliest year: ");
        int earliestYear = Integer.parseInt(myScanner.nextLine().trim());

        List<Concert> results = concertService.getConcertsByMaxPriceAndEarliestYear(maxPrice, earliestYear);

        System.out.println("\n--- Concerts at or below $" + maxPrice + " from " + earliestYear + " onwards ---");
        printConcertList(results);
    }

    // Reports screen
    private void showReports(ConcertService concertService, Scanner myScanner) {

        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n===== Reports =====");
            System.out.println("1) Revenue per venue");
            System.out.println("2) Busiest venue and artist");
            System.out.println("3) Average ticket price by year");
            System.out.println("4) Capacity report");
            System.out.println("0) Back");
            System.out.print("Choose an option: ");

            String userChoice = myScanner.nextLine().trim();

            switch (userChoice) {
                case "1":
                    reportRevenuePerVenue(concertService);
                    break;
                case "2":
                    reportBusiestVenueAndArtist(concertService);
                    break;
                case "3":
                    reportAveragePriceByYear(concertService);
                    break;
                case "4":
                    reportCapacity(concertService);
                    break;
                case "0":
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Revenue per venue report
    private void reportRevenuePerVenue(ConcertService concertService) {

        List<Object[]> results = concertService.getRevenuePerVenue();

        if (results.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        System.out.println("\n--- Revenue Per Venue ---");
        for (Object[] row : results) {
            String venueName = (String) row[0];
            double totalRevenue = ((Number) row[1]).doubleValue();
            System.out.printf("Venue: %-30s | Total Revenue: $%,.2f%n", venueName, totalRevenue);
        }
    }

    // Busiest venue and artist report
    private void reportBusiestVenueAndArtist(ConcertService concertService) {

        List<Object[]> venueResults = concertService.getConcertCountPerVenue();
        List<Object[]> artistResults = concertService.getConcertCountPerArtist();

        if (venueResults.isEmpty() || artistResults.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        // First result is the busiest since results are ordered by count descending
        Object[] busiestVenue = venueResults.get(0);
        Object[] busiestArtist = artistResults.get(0);

        System.out.println("\n--- Busiest Venue and Artist ---");
        System.out.println("Busiest Venue:  " + busiestVenue[0] + " (" + busiestVenue[1] + " concerts)");
        System.out.println("Busiest Artist: " + busiestArtist[0] + " (" + busiestArtist[1] + " concerts)");
    }

    // Average ticket price by year report
    private void reportAveragePriceByYear(ConcertService concertService) {

        List<Object[]> results = concertService.getAveragePriceByYear();

        if (results.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        System.out.println("\n--- Average Ticket Price by Year ---");
        for (Object[] row : results) {
            int year = ((Number) row[0]).intValue();
            double avgPrice = ((Number) row[1]).doubleValue();
            System.out.printf("Year: %d | Average Price: $%,.2f%n", year, avgPrice);
        }
    }

    // Capacity report
    private void reportCapacity(ConcertService concertService) {

        List<Object[]> results = concertService.getCapacityReport();

        if (results.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        System.out.println("\n--- Capacity Report ---");
        for (Object[] row : results) {
            long concertId = ((Number) row[0]).longValue();
            String artistName = (String) row[1];
            String venueName = (String) row[2];
            int ticketsSold = ((Number) row[3]).intValue();
            int capacity = ((Number) row[4]).intValue();

            // Calculate percentage full
            double percentageFull = ((double) ticketsSold / capacity) * 100;

            // Flag if sold out
            String soldOutFlag = (ticketsSold >= capacity) ? " *** SOLD OUT ***" : "";

            System.out.printf("ID: %d | Artist: %-25s | Venue: %-30s | %d/%d (%.1f%%)%s%n",
                    concertId, artistName, venueName, ticketsSold, capacity, percentageFull, soldOutFlag);
        }
    }

    // Artists screen
    private void showArtists(ConcertService concertService, Scanner myScanner) {

        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n===== Artists =====");
            System.out.println("1) List all artists");
            System.out.println("2) Add an artist");
            System.out.println("3) Find by genre");
            System.out.println("4) Find by name");
            System.out.println("5) Update genre");
            System.out.println("6) Delete an artist");
            System.out.println("0) Back");
            System.out.print("Choose an option: ");

            String userChoice = myScanner.nextLine().trim();

            switch (userChoice) {
                case "1":
                    listAllArtists(concertService);
                    break;
                case "2":
                    addArtist(concertService, myScanner);
                    break;
                case "3":
                    findArtistsByGenre(concertService, myScanner);
                    break;
                case "4":
                    findArtistsByName(concertService, myScanner);
                    break;
                case "5":
                    updateArtistGenre(concertService, myScanner);
                    break;
                case "6":
                    deleteArtist(concertService, myScanner);
                    break;
                case "0":
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // List all artists
    private void listAllArtists(ConcertService concertService) {

        List<Artist> allArtists = concertService.getAllArtists();

        if (allArtists.isEmpty()) {
            System.out.println("No artists found.");
            return;
        }

        System.out.println("\n--- All Artists ---");
        for (Artist artist : allArtists) {
            System.out.println("ID: " + artist.getId()
                    + " | Name: " + artist.getName()
                    + " | Genre: " + artist.getGenre());
        }
    }

    // Add a new artist
    private void addArtist(ConcertService concertService, Scanner myScanner) {

        System.out.print("Enter artist name: ");
        String artistName = myScanner.nextLine().trim();

        System.out.print("Enter genre: ");
        String artistGenre = myScanner.nextLine().trim();

        Artist newArtist = new Artist(artistName, artistGenre);
        concertService.saveArtist(newArtist);
        System.out.println("Artist added successfully.");
    }

    // Find artists by genre
    private void findArtistsByGenre(ConcertService concertService, Scanner myScanner) {

        System.out.print("Enter genre to search: ");
        String genre = myScanner.nextLine().trim();

        List<Artist> foundArtists = concertService.getArtistsByGenre(genre);

        if (foundArtists.isEmpty()) {
            System.out.println("No artists found with genre: " + genre);
            return;
        }

        System.out.println("\n--- Artists by Genre: " + genre + " ---");
        for (Artist artist : foundArtists) {
            System.out.println("ID: " + artist.getId()
                    + " | Name: " + artist.getName()
                    + " | Genre: " + artist.getGenre());
        }
    }

    // Find artists by name
    private void findArtistsByName(ConcertService concertService, Scanner myScanner) {

        System.out.print("Enter name to search: ");
        String name = myScanner.nextLine().trim();

        List<Artist> foundArtists = concertService.getArtistsByName(name);

        if (foundArtists.isEmpty()) {
            System.out.println("No artists found with name containing: " + name);
            return;
        }

        System.out.println("\n--- Artists by Name: " + name + " ---");
        for (Artist artist : foundArtists) {
            System.out.println("ID: " + artist.getId()
                    + " | Name: " + artist.getName()
                    + " | Genre: " + artist.getGenre());
        }
    }

    // Update an artist's genre
    private void updateArtistGenre(ConcertService concertService, Scanner myScanner) {

        listAllArtists(concertService);
        System.out.print("Enter artist ID to update: ");
        Long artistId = Long.parseLong(myScanner.nextLine().trim());

        Artist foundArtist = concertService.getArtistById(artistId);

        if (foundArtist == null) {
            System.out.println("No artist found with ID " + artistId);
            return;
        }

        System.out.print("Enter new genre: ");
        String newGenre = myScanner.nextLine().trim();

        foundArtist.setGenre(newGenre);
        concertService.saveArtist(foundArtist);
        System.out.println("Artist genre updated successfully.");
    }

    // Delete an artist
    private void deleteArtist(ConcertService concertService, Scanner myScanner) {

        listAllArtists(concertService);
        System.out.print("Enter artist ID to delete: ");
        Long artistId = Long.parseLong(myScanner.nextLine().trim());

        Artist foundArtist = concertService.getArtistById(artistId);

        if (foundArtist == null) {
            System.out.println("No artist found with ID " + artistId);
            return;
        }

        concertService.deleteArtist(artistId);
        System.out.println("Artist deleted successfully.");
    }

    // Venues screen
    private void showVenues(ConcertService concertService, Scanner myScanner) {

        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n===== Venues =====");
            System.out.println("1) List all venues");
            System.out.println("2) Add a venue");
            System.out.println("3) Find by city");
            System.out.println("4) Find by name");
            System.out.println("5) Find by minimum capacity");
            System.out.println("6) Update capacity");
            System.out.println("7) Delete a venue");
            System.out.println("0) Back");
            System.out.print("Choose an option: ");

            String userChoice = myScanner.nextLine().trim();

            switch (userChoice) {
                case "1":
                    listAllVenues(concertService);
                    break;
                case "2":
                    addVenue(concertService, myScanner);
                    break;
                case "3":
                    findVenuesByCity(concertService, myScanner);
                    break;
                case "4":
                    findVenuesByName(concertService, myScanner);
                    break;
                case "5":
                    findVenuesByMinCapacity(concertService, myScanner);
                    break;
                case "6":
                    updateVenueCapacity(concertService, myScanner);
                    break;
                case "7":
                    deleteVenue(concertService, myScanner);
                    break;
                case "0":
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // List all venues
    private void listAllVenues(ConcertService concertService) {

        List<Venue> allVenues = concertService.getAllVenues();

        if (allVenues.isEmpty()) {
            System.out.println("No venues found.");
            return;
        }

        System.out.println("\n--- All Venues ---");
        for (Venue venue : allVenues) {
            System.out.println("ID: " + venue.getId()
                    + " | Name: " + venue.getName()
                    + " | City: " + venue.getCity()
                    + " | Capacity: " + venue.getCapacity());
        }
    }

    // Add a new venue
    private void addVenue(ConcertService concertService, Scanner myScanner) {

        System.out.print("Enter venue name: ");
        String venueName = myScanner.nextLine().trim();

        System.out.print("Enter city: ");
        String venueCity = myScanner.nextLine().trim();

        System.out.print("Enter capacity: ");
        int venueCapacity = Integer.parseInt(myScanner.nextLine().trim());

        Venue newVenue = new Venue(venueName, venueCity, venueCapacity);
        concertService.saveVenue(newVenue);
        System.out.println("Venue added successfully.");
    }

    // Find venues by city
    private void findVenuesByCity(ConcertService concertService, Scanner myScanner) {

        System.out.print("Enter city to search: ");
        String city = myScanner.nextLine().trim();

        List<Venue> foundVenues = concertService.getVenuesByCity(city);

        if (foundVenues.isEmpty()) {
            System.out.println("No venues found in city: " + city);
            return;
        }

        System.out.println("\n--- Venues in " + city + " ---");
        for (Venue venue : foundVenues) {
            System.out.println("ID: " + venue.getId()
                    + " | Name: " + venue.getName()
                    + " | Capacity: " + venue.getCapacity());
        }
    }

    // Find venues by name
    private void findVenuesByName(ConcertService concertService, Scanner myScanner) {

        System.out.print("Enter name to search: ");
        String name = myScanner.nextLine().trim();

        List<Venue> foundVenues = concertService.getVenuesByName(name);

        if (foundVenues.isEmpty()) {
            System.out.println("No venues found with name containing: " + name);
            return;
        }

        System.out.println("\n--- Venues by Name: " + name + " ---");
        for (Venue venue : foundVenues) {
            System.out.println("ID: " + venue.getId()
                    + " | Name: " + venue.getName()
                    + " | City: " + venue.getCity()
                    + " | Capacity: " + venue.getCapacity());
        }
    }

    // Find venues by minimum capacity
    private void findVenuesByMinCapacity(ConcertService concertService, Scanner myScanner) {

        System.out.print("Enter minimum capacity: ");
        int minCapacity = Integer.parseInt(myScanner.nextLine().trim());

        List<Venue> foundVenues = concertService.getVenuesByMinCapacity(minCapacity);

        if (foundVenues.isEmpty()) {
            System.out.println("No venues found with capacity of at least: " + minCapacity);
            return;
        }

        System.out.println("\n--- Venues with Capacity >= " + minCapacity + " ---");
        for (Venue venue : foundVenues) {
            System.out.println("ID: " + venue.getId()
                    + " | Name: " + venue.getName()
                    + " | City: " + venue.getCity()
                    + " | Capacity: " + venue.getCapacity());
        }
    }

    // Update a venue's capacity
    private void updateVenueCapacity(ConcertService concertService, Scanner myScanner) {

        listAllVenues(concertService);
        System.out.print("Enter venue ID to update: ");
        Long venueId = Long.parseLong(myScanner.nextLine().trim());

        Venue foundVenue = concertService.getVenueById(venueId);

        if (foundVenue == null) {
            System.out.println("No venue found with ID " + venueId);
            return;
        }

        System.out.print("Enter new capacity: ");
        int newCapacity = Integer.parseInt(myScanner.nextLine().trim());

        foundVenue.setCapacity(newCapacity);
        concertService.saveVenue(foundVenue);
        System.out.println("Venue capacity updated successfully.");
    }

    // Delete a venue
    private void deleteVenue(ConcertService concertService, Scanner myScanner) {

        listAllVenues(concertService);
        System.out.print("Enter venue ID to delete: ");
        Long venueId = Long.parseLong(myScanner.nextLine().trim());

        Venue foundVenue = concertService.getVenueById(venueId);

        if (foundVenue == null) {
            System.out.println("No venue found with ID " + venueId);
            return;
        }

        concertService.deleteVenue(venueId);
        System.out.println("Venue deleted successfully.");
    }

    // Promoters screen
    private void showPromoters(ConcertService concertService, Scanner myScanner) {

        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n===== Promoters =====");
            System.out.println("1) List all promoters");
            System.out.println("2) Add a promoter");
            System.out.println("3) Find by name");
            System.out.println("4) Delete a promoter");
            System.out.println("0) Back");
            System.out.print("Choose an option: ");

            String userChoice = myScanner.nextLine().trim();

            switch (userChoice) {
                case "1":
                    listAllPromoters(concertService);
                    break;
                case "2":
                    addPromoter(concertService, myScanner);
                    break;
                case "3":
                    findPromotersByName(concertService, myScanner);
                    break;
                case "4":
                    deletePromoter(concertService, myScanner);
                    break;
                case "0":
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // List all promoters
    private void listAllPromoters(ConcertService concertService) {

        List<Promoter> allPromoters = concertService.getAllPromoters();

        if (allPromoters.isEmpty()) {
            System.out.println("No promoters found.");
            return;
        }

        System.out.println("\n--- All Promoters ---");
        for (Promoter promoter : allPromoters) {
            System.out.println("ID: " + promoter.getId()
                    + " | Name: " + promoter.getName());
        }
    }

    // Add a new promoter
    private void addPromoter(ConcertService concertService, Scanner myScanner) {

        System.out.print("Enter promoter name: ");
        String promoterName = myScanner.nextLine().trim();

        Promoter newPromoter = new Promoter(promoterName);
        concertService.savePromoter(newPromoter);
        System.out.println("Promoter added successfully.");
    }

    // Find promoters by name
    private void findPromotersByName(ConcertService concertService, Scanner myScanner) {

        System.out.print("Enter name to search: ");
        String name = myScanner.nextLine().trim();

        List<Promoter> foundPromoters = concertService.getPromotersByName(name);

        if (foundPromoters.isEmpty()) {
            System.out.println("No promoters found with name containing: " + name);
            return;
        }

        System.out.println("\n--- Promoters by Name: " + name + " ---");
        for (Promoter promoter : foundPromoters) {
            System.out.println("ID: " + promoter.getId()
                    + " | Name: " + promoter.getName());
        }
    }

    // Delete a promoter
    private void deletePromoter(ConcertService concertService, Scanner myScanner) {

        listAllPromoters(concertService);
        System.out.print("Enter promoter ID to delete: ");
        Long promoterId = Long.parseLong(myScanner.nextLine().trim());

        Promoter foundPromoter = concertService.getPromoterById(promoterId);

        if (foundPromoter == null) {
            System.out.println("No promoter found with ID " + promoterId);
            return;
        }

        concertService.deletePromoter(promoterId);
        System.out.println("Promoter deleted successfully.");
    }
}