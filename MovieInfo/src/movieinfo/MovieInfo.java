/*
 * Apache v2 license: https://github.com/andreipruteanu/thekit/blob/master/LICENSE
 */
package movieinfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author andrei
 */
public class MovieInfo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Crawling started.");
        List movieList = new ArrayList();
        movieList.add("Arrival");
        movieList.add("Star Wars");
        movieList.add("Schindler's List");
        movieList.add("Star Trek");
        movieList.add("Indiana Jones");
        movieList.add("Pulp Fiction");
        movieList.add("Interstellar");
        movieList.add("Inception");
        movieList.add("The Matrix");
        movieList.add("City of God");
        ImdbCrawler imdbcrawler = new ImdbCrawler(movieList);
        List imdbScores = imdbcrawler.getScores();
        MetascoreCrawler metascorecrawler = new MetascoreCrawler(movieList);
        List metaScores = metascorecrawler.getScores();
        System.out.println("Crawling done");
        CsvWriter cw = new CsvWriter("movie_data.csv");
        cw.writeData(movieList, imdbScores, metaScores);
        System.out.println("Saving to csv done");
    }
    
}
