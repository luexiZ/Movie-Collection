import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MovieCollectionRunner
{


  public static void main(String[] args)
  {
    MovieCollection collection = new MovieCollection("src\\movies_data.csv");
//    ArrayList<Movie> MovieList = collection.getMovies();
//    for (Movie movies : MovieList)
//    {
//      System.out.println(movies);
//    }
    collection.menu();

  }
}