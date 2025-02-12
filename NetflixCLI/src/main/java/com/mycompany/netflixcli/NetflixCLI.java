/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.netflixcli;

import java.util.*;

class Movie {
    private String title;
    private String genre;
    private int duration; // in minutes

    public Movie(String title, String genre, int duration) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return title + " (" + genre + ", " + duration + " mins)";
    }
}

class User {
    private String name;
    private List<Movie> viewingHistory;

    public User(String name) {
        this.name = name;
        this.viewingHistory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void watchMovie(Movie movie) {
        viewingHistory.add(movie);
        System.out.println("You watched: " + movie);
    }

    public List<Movie> getViewingHistory() {
        return viewingHistory;
    }

    public String getFavoriteGenre() {
        if (viewingHistory.isEmpty()) {
            return "Unknown";
        }

        Map<String, Integer> genreCount = new HashMap<>();
        for (Movie movie : viewingHistory) {
            genreCount.put(movie.getGenre(), genreCount.getOrDefault(movie.getGenre(), 0) + 1);
        }

        return Collections.max(genreCount.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}

public class NetflixCLI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Movie> movieCatalog = new ArrayList<>();
    private static User currentUser;

    public static void main(String[] args) {
        initializeCatalog();

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        currentUser = new User(name);

        boolean exit = false;
        while (!exit) {
            displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    displayMovieCatalog();
                    break;
                case 2:
                    watchMovie();
                    break;
                case 3:
                    displayViewingHistory();
                    break;
                case 4:
                    recommendMovie();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Thank you for using Netflix CLI!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void initializeCatalog() {
        movieCatalog.add(new Movie("Inception", "Sci-Fi", 148));
        movieCatalog.add(new Movie("The Dark Knight", "Action", 152));
        movieCatalog.add(new Movie("Interstellar", "Sci-Fi", 169));
        movieCatalog.add(new Movie("The Notebook", "Romance", 123));
        movieCatalog.add(new Movie("Parasite", "Thriller", 132));
    }

    private static void displayMenu() {
        System.out.println("\nNetflix CLI Menu:");
        System.out.println("1. View Movie Catalog");
        System.out.println("2. Watch a Movie");
        System.out.println("3. View Viewing History");
        System.out.println("4. Get a Movie Recommendation");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void displayMovieCatalog() {
        System.out.println("\nMovie Catalog:");
        for (int i = 0; i < movieCatalog.size(); i++) {
            Movie movie = movieCatalog.get(i);
            System.out.println((i + 1) + ". " + movie);
            System.out.println("   Description: A brief description of " + movie.getTitle());
        }
    }

    private static void watchMovie() {
        displayMovieCatalog();
        System.out.print("Enter the number of the movie you want to watch: ");
        int movieIndex = getUserChoice() - 1;

        if (movieIndex >= 0 && movieIndex < movieCatalog.size()) {
            System.out.println("Preparing to watch: " + movieCatalog.get(movieIndex).getTitle());
            currentUser.watchMovie(movieCatalog.get(movieIndex));
        } else {
            System.out.println("Invalid movie selection. Please try again.");
        }
    }

    private static void displayViewingHistory() {
        List<Movie> history = currentUser.getViewingHistory();

        if (history.isEmpty()) {
            System.out.println("You have not watched any movies yet.");
        } else {
            System.out.println("\nYour Viewing History (" + history.size() + " movies watched):");
            for (Movie movie : history) {
                System.out.println(movie);
            }
        }
    }

    private static void recommendMovie() {
        String favoriteGenre = currentUser.getFavoriteGenre();
        System.out.println("Your favorite genre: " + favoriteGenre);

        boolean recommendationFound = false;
        for (Movie movie : movieCatalog) {
            if (movie.getGenre().equals(favoriteGenre) && !currentUser.getViewingHistory().contains(movie)) {
                System.out.println("We recommend you watch: " + movie);
                System.out.println("   Reason: It's a popular choice in your favorite genre!");
                recommendationFound = true;
                break;
            }
        }

        if (!recommendationFound) {
            System.out.println("No recommendations available based on your viewing history.");
        }
    }
}