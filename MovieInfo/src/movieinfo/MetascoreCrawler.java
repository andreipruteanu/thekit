/*
 * Apache v2 license: https://github.com/andreipruteanu/thekit/blob/master/LICENSE
 */
package movieinfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author andrei
 */
public class MetascoreCrawler implements Crawler {
    // if a final variable holds a reference to an object (array is an object), 
    // then the components of the array may be changed by operations on the array, 
    // but the variable will always refer to the same object (array)
    private final List movieList;
    
    // create Regexp pattern in the reponse JSON string
    private final String pattern = "(\"Metascore\":\")(\\d+)\"";
                
    // Create a Pattern object
    private final Pattern r = Pattern.compile(pattern);
    
    // class Constructor
    MetascoreCrawler(List _movieList) {
        this.movieList = _movieList;
    }
    
    @Override
    public List getScores() {
        // method needs to be implemented since class implements the interface
        List scores = new ArrayList();
        for (Object movieTitle : this.movieList) {
            float movieScore = getMovieScore(movieTitle);
            scores.add(movieScore);
            try {
                // "sleep" for 1 second in-between requests to avoid making too 
                // many requests to the server
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ImdbCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return scores;
    }
    
    public float getMovieScore(Object _movieTitle) {
        float movieScore = -1;
        try {
            // replace space with "+"
            String movieTitle = ((String) _movieTitle).replaceAll(" ", "+");
            // Create a URL for the desired movie query
            URL url = new URL("http://www.omdbapi.com/?t=" + movieTitle
                    + "&y=&plot=short&r=json");

            try ( // Read all the text returned by the server
                    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String line;
                String resp = "";
                while ((line = in.readLine()) != null) {
                    resp += line;
                }

                // Now create matcher object.
                Matcher m = this.r.matcher(resp);
                // check if we found a match in the respons string
                if (m.find()) {
                    movieScore = Float.parseFloat(m.group(2));
                } else {
                    System.out.println("No Meta score found for movie " 
                            + movieTitle);
                }
                System.out.println("\"" + _movieTitle + "\" Metascore: " 
                        + movieScore);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(CsvWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CsvWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return movieScore;
    }
}
