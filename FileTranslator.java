import java.io.*;
import java.util.LinkedList;

/**
 * Created by austinzhang on 12/6/16.
 * Source for dialogue: https://www.cs.cornell.edu/~cristian/Cornell_Movie-Dialogs_Corpus.html
 *
 * This class was created to remove the metadata from the Cornell Movie Dialogs Corpus
 * for the purpose of extracting the raw conversational logs.
 * It also flips the order of the conversations- the previous conversations were upside down
 * (the question would be below the response) and didn't really make sense.
 */
public class FileTranslator {
    public static void main(String args[]) throws IOException {

        //Make a reader and writer
        //The reader is used for scanning the cornell text file one line at a time.
        //The writer is for writing the new text file that doesn't have the metadata included.
        BufferedReader reader = new BufferedReader(new FileReader(retrieveFile("movie_lines.txt")));
        PrintWriter writer = new PrintWriter(new FileWriter("TranslatedMovieLines.txt"), true);

        String currentLine = reader.readLine();

        //Create a new list of all the lines of dialogue
        LinkedList<String> listOfQuotes = new LinkedList<String>();


        int counter = 0;

        //Go through each line until the end of the file and strip out metadata.
        //Then add the line to the list of dialogue.
        while (currentLine != null) {
            counter++;

            //Strip out the useless metadata
            String modifiedLine = currentLine.substring(currentLine.lastIndexOf("+ ") + 1, currentLine.length());

            //Strips out any markup notation, removes <> and anything between them.

            /* Does not currently work, has a memory issue.
            while (modifiedLine.contains("<")) {
                String firstHalfOfResponse = modifiedLine.substring(0, modifiedLine.indexOf("<"));
                String secondHalfOfResponse = modifiedLine.substring(modifiedLine.indexOf(">") + 1);
                modifiedLine = firstHalfOfResponse + secondHalfOfResponse;
            }
            */

            listOfQuotes.add(0, modifiedLine);

            currentLine = reader.readLine();
        }

        //Take the list of dialogue built and write to a new file in opposite order.
        while (listOfQuotes.size() != 0)
        {
            writer.println(listOfQuotes.remove(0));
        }

        //This is the number of lines of dialogue currently being used in the database.
        System.out.println(counter);


    }


    //This method is for file management, it's how the program finds the files it wants to read and write to.
    public static File retrieveFile(String directoryName) {
        String currentDirectoryName = new File(".").getAbsolutePath().substring(0, new File(".").getAbsolutePath().lastIndexOf("."));
        File[] startingDirectory = new File(currentDirectoryName).listFiles();

        for (File file : startingDirectory) {
            if (file.getName().equals(directoryName)) {
                return file;
            }
        }
        return null;
    }

    //This method is also for file management.
    //Returns a subfile in retrieveFileDirectory
    public static File retrieveFile(File file, String fileName) {
        File[] childrenFiles = file.listFiles();
        for (File child : childrenFiles) {
            if (child.getName().equals(fileName)) {
                return child;
            }
        }

        return null;
    }


}
