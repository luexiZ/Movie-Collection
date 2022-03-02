import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MovieCollection
{
  private ArrayList<Movie> movies;
  private Scanner scanner;
  private String fileNames;

  public MovieCollection(String fileName)
  {
    this.fileNames = fileName;
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies()
  {
    return movies;
  }
  
  public void menu()
  {
    String menuOption = "";
    
    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");
    
    while (!menuOption.equals("q"))
    {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();
      
      if (!menuOption.equals("q"))
      {
        processOption(menuOption);
      }
    }
  }
  
  private void processOption(String option)
  {
    if (option.equals("t"))
    {
      searchTitles();
    }
    else if (option.equals("c"))
    {
      searchCast();
    }
    else if (option.equals("k"))
    {
      searchKeywords();
    }
    else if (option.equals("g"))
    {
      listGenres();
    }
    else if (option.equals("r"))
    {
      listHighestRated();
    }
    else if (option.equals("h"))
    {
      listHighestRevenue();
    }
    else
    {
      System.out.println("Invalid choice!");
    }
  }

  private void searchTitles()
  {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void sortResults(ArrayList<Movie> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }
  
  private void displayMovieInfo(Movie movie)
  {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }
  
  private void searchCast()
  {
    System.out.print("Enter a person to search for(first or last name): ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();
    for (int i = 0; i < movies.size(); i++)
    {
      String cast = movies.get(i).getCast();
      cast = cast.toLowerCase();

      if (cast.indexOf(searchTerm) != -1)
      {
        //add the Movie object to the results list
        results.add(movies.get(i));
      }
    }
    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    ArrayList<String> listsOfNames = new ArrayList<String>();
    for (int i = 0; i < results.size(); i++)
    {
      String cast = results.get(i).getCast();
      String[] castNames = cast.split("\\|");
      for(int j = 0; j < castNames.length; j++)
      {
        if(castNames[j].toLowerCase().indexOf(searchTerm) != -1)
        {
          listsOfNames.add(castNames[j]);
        }
      }
    }


    for(int i = 0; i < listsOfNames.size(); i++)
    {
      for(int j = i + 1; j < listsOfNames.size(); j++)
      {
        if(listsOfNames.get(i).equals(listsOfNames.get(j)))
        {
          listsOfNames.remove(j);
          j--;
        }
      }
    }
    Collections.sort(listsOfNames);
    int choiceNum = 1;
    for(String names : listsOfNames)
    {
      System.out.println("" + choiceNum + ". " + names);
      choiceNum++;
    }

    System.out.println("Which would you like to see all movies for?");
    System.out.print("Enter number: ");

    choiceNum = scanner.nextInt();
    scanner.nextLine();

    ArrayList<Movie> listOfMovies = new ArrayList<Movie>();
    for(int i = 0; i < movies.size(); i++)
    {
      if(movies.get(i).getCast().contains(listsOfNames.get(choiceNum - 1)))
      {
        listOfMovies.add(movies.get(i));
      }
    }

    sortResults(listOfMovies);
    for (int i = 0; i < listOfMovies.size(); i++)
    {
      String title = listOfMovies.get(i).getTitle();
      // this will print index 0 as choice 1 in the results list; better for user!
      int num = i + 1;
      System.out.println("" + num + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = listOfMovies.get(choice - 1);
    displayMovieInfo(selectedMovie);
    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void searchKeywords()
  {
    System.out.print("Enter a KeyWord search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String keyword = movies.get(i).getKeywords();
      keyword = keyword.toLowerCase();

      if (keyword.indexOf(searchTerm) != -1)
      {
        //add the Movie object to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();

  }
  
  private void listGenres()
  {
     ArrayList<String> genresList = new ArrayList<String>();
     for(int i = 0; i < movies.size(); i++)
     {
       genresList.add(movies.get(i).getGenres());
     }

    ArrayList<String> genres = new ArrayList<String>();
     // hold up
    for(int i = 0; i < genresList.size(); i++)
    {
      String genre = genresList.get(i);
      String[] separatedGenres = genre.split("\\|");

      for(int j = 0; j < separatedGenres.length; j++)
      {
        genres.add(separatedGenres[j]);
      }
    }


     for(int i = 0; i < genres.size(); i++) // remove duplicate
     {
       for(int j = i + 1; j < genres.size(); j++)
       {
         if(genres.get(i).equals(genres.get(j)))
         {
           genres.remove(j);
           j--;
         }
       }
     }

     Collections.sort(genres);
     for(int i = 0; i < genres.size(); i++)
     {
       int choiceNum = i + 1;
       System.out.println(choiceNum + ". " + genres.get(i));
     }

    System.out.println("Which would you like to see all movies for ?");
    System.out.print("Enter number: ");
    int choice = scanner.nextInt();
    scanner.nextLine();


    ArrayList<Movie> listOfMovies = new ArrayList<Movie>();
    for(int i = 0; i < movies.size(); i++)
    {
      if(movies.get(i).getGenres().contains(genres.get(choice - 1)))
      {
        listOfMovies.add(movies.get(i));
      }
    }

    sortResults(listOfMovies);
    for (int i = 0; i < listOfMovies.size(); i++)
    {
      String title = listOfMovies.get(i).getTitle();
      // this will print index 0 as choice 1 in the results list; better for user!
      int num = i + 1;
      System.out.println("" + num + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = listOfMovies.get(choice - 1);
    displayMovieInfo(selectedMovie);
    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();

  }
  
  private void listHighestRated()
  {
    ArrayList<Movie> top50Ratings = new ArrayList<Movie>();
    ArrayList<Movie> copyOfMovies = movies;
    for(int i = 0; i < 50; i++)
    {
      double max = 0; //  default value
      int index = 0;
      for(int j = 0 ; j < copyOfMovies.size(); j++)
      {
        if(copyOfMovies.get(j).getUserRating() > max)
        {
          max = copyOfMovies.get(j).getUserRating();
          index = j;
        }
      }
      top50Ratings.add(copyOfMovies.remove(index));
    }

//    ArrayList<Movie> top50RatingsSorted = new ArrayList<Movie>();
//    ArrayList<Movie> sortedTitles = new ArrayList<Movie>();  // takes in the movies from the same rating
//    for(int i = 0; i < top50Ratings.size(); i++)
//    {
//      sortedTitles.clear();
//      for(int j = 0; j < top50Ratings.size(); j++)
//      {
//        sortedTitles.add(top50Ratings.get(i));
//        if(top50Ratings.get(i).getUserRating() == top50Ratings.get(j).getUserRating())
//        {
//          sortedTitles.add(top50Ratings.get(j));
//        }
//      }
//      if(!sortedTitles.isEmpty())
//      {
//        i += sortedTitles.size(); // note
//        sortResults(sortedTitles);
//        for(int k = 0; k < sortedTitles.size(); k++)
//        {
//          top50RatingsSorted.add(sortedTitles.get(k));
//        }
//      }
//    }
    ArrayList<Movie> top50RatingsSorted = new ArrayList<Movie>();
    ArrayList<Movie> sortedTitles = new ArrayList<Movie>();  // takes in the movies from the same rating
    for(int i = 0; i < top50Ratings.size(); i++)
    {
      sortedTitles.add(top50Ratings.get(i));
      for(int j = i + 1; j < top50Ratings.size(); j++)
      {
        if(top50Ratings.get(i).getUserRating() == top50Ratings.get(j).getUserRating())
        {
          sortedTitles.add(top50Ratings.remove(j));
          j--;
        }
      }
      top50Ratings.remove(i);
      i--;

      sortResults(sortedTitles);

      for(int k = 0; k < sortedTitles.size(); k++)
      {
        top50RatingsSorted.add(sortedTitles.get(k));
      }
      sortedTitles.clear();
    }

    for (int i = 0; i < top50RatingsSorted.size(); i++)
    {
      String title = top50RatingsSorted.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title + "  (" + top50RatingsSorted.get(i).getUserRating() + ")");
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = top50RatingsSorted.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
//    for(Movie rate : top50RatingsSorted)
//    {
//      System.out.println("" + rate.getTitle() + " : " + rate.getUserRating());
//    }
  }
  
  private void listHighestRevenue()
  {
    ArrayList<Movie> top50Ratings = new ArrayList<Movie>();
    ArrayList<Movie> copyOfMovies = movies;
    for(int i = 0; i < 50; i++)
    {
      double max = 0; //  default value
      int index = 0;
      for(int j = 0 ; j < copyOfMovies.size(); j++)
      {
        if(copyOfMovies.get(j).getRevenue() > max)
        {
          max = copyOfMovies.get(j).getRevenue();
          index = j;
        }
      }
      top50Ratings.add(copyOfMovies.remove(index));
    }

//    ArrayList<Movie> top50RatingsSorted = new ArrayList<Movie>();
//    ArrayList<Movie> sortedTitles = new ArrayList<Movie>();  // takes in the movies from the same rating
//    for(int i = 0; i < top50Ratings.size(); i++)
//    {
//      sortedTitles.clear();
//      for(int j = 0; j < top50Ratings.size(); j++)
//      {
//        sortedTitles.add(top50Ratings.get(i));
//        if(top50Ratings.get(i).getUserRating() == top50Ratings.get(j).getUserRating())
//        {
//          sortedTitles.add(top50Ratings.get(j));
//        }
//      }
//      if(!sortedTitles.isEmpty())
//      {
//        i += sortedTitles.size(); // note
//        sortResults(sortedTitles);
//        for(int k = 0; k < sortedTitles.size(); k++)
//        {
//          top50RatingsSorted.add(sortedTitles.get(k));
//        }
//      }
//    }
    ArrayList<Movie> top50RatingsSorted = new ArrayList<Movie>();
    ArrayList<Movie> sortedTitles = new ArrayList<Movie>();  // takes in the movies from the same rating
    for(int i = 0; i < top50Ratings.size(); i++)
    {
      sortedTitles.add(top50Ratings.get(i));
      for(int j = i + 1; j < top50Ratings.size(); j++)
      {
        if(top50Ratings.get(i).getRevenue() == top50Ratings.get(j).getRevenue())
        {
          sortedTitles.add(top50Ratings.remove(j));
          j--;
        }
      }
      top50Ratings.remove(i);
      i--;

      sortResults(sortedTitles);

      for(int k = 0; k < sortedTitles.size(); k++)
      {
        top50RatingsSorted.add(sortedTitles.get(k));
      }
      sortedTitles.clear();
    }

    for (int i = 0; i < top50RatingsSorted.size(); i++)
    {
      String title = top50RatingsSorted.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title + "  (" + top50RatingsSorted.get(i).getRevenue() + ")");
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = top50RatingsSorted.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();

  }

  private void importMovieList(String fileName)
  {
    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      movies = new ArrayList<Movie>();

      while ((line = bufferedReader.readLine()) != null)
      {
        // import all cells for a single row as an array of Strings,
        // then convert to ints as needed
        String[] moviesFromCSV = line.split(",");

        // pull out the data for this movie
        String title = moviesFromCSV[0];
        String cast = moviesFromCSV[1];
        String director = moviesFromCSV[2];
        String tagline = moviesFromCSV[3];
        String keywords = moviesFromCSV[4];
        String overview = moviesFromCSV[5];
        int runtime = Integer.parseInt(moviesFromCSV[6]);
        String genres = moviesFromCSV[7];
        double userRating = Double.parseDouble(moviesFromCSV[8]);
        int year = Integer.parseInt(moviesFromCSV[9]);
        int revenue = Integer.parseInt(moviesFromCSV[10]);

        // create Movie object to store values
        Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);

        // adding Movie object to the arraylist
        movies.add(nextMovie);
      }
      bufferedReader.close();
    }
    catch(IOException exception)
    {
      // Print out the exception that occurred
      System.out.println("Unable to access " + exception.getMessage());
    }

  }
  
  // ADD ANY ADDITIONAL PRIVATE HELPER METHODS you deem necessary

}