/*
 * Apache v2 license: https://github.com/andreipruteanu/thekit/blob/master/LICENSE
 */
package movieinfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author andrei
 */
public class CsvWriter {
    private final String fileName;
    CsvWriter(String _fileName) {
        this.fileName = _fileName;
    }
    
    public void writeData(List movieList, List imdbScores, List metaScores) {
        try {
            PrintWriter pw = new PrintWriter(new File(fileName));
            StringBuilder sb = new StringBuilder();
            sb.append("idx");
            sb.append(',');
            sb.append("Movie Title");
            sb.append(',');
            sb.append("Imdb Score");
            sb.append(",");
            sb.append("Meta Score");
            sb.append("\n");
            // check (assert) all three lists have the same size
            assert metaScores.size() == movieList.size() && 
                    metaScores.size() == imdbScores.size() &&
                    movieList.size() == imdbScores.size();
            // iterate over all three lists using one index since they are of 
            // equal size
            for(int i=0; i<movieList.size(); ++i)
            {
               Object movieTitle = movieList.get(i);
               Object imdbScore = imdbScores.get(i);
               Object metaScore = metaScores.get(i);
               // append to the String
               sb.append(i);
               sb.append(",");
               sb.append(movieTitle);
               sb.append(",");
               sb.append(imdbScore);
               sb.append(",");
               sb.append(metaScore);
               sb.append('\n');
            }
            // write to CSV file
            pw.write(sb.toString());
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CsvWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
