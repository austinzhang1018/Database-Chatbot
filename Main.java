import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *  * This chatbot attempts to seem human
 * by matching conversational prompts to
 * a large database of different conversations
 * (at the time of writing this it's around
 * 300,000 lines of dialogue pulled from movies).
 * The chatbot uses an algorithm to match a sentence
 * to the closest sentence it knows and gives the
 * corresponding response to the sentence it already
 * has saved. Because this is how it works, by adding
 * additional conversations to the database, and adding
 * more data to pull from, the program gets more intelligent.
 * However there are a few significant downsides of
 * a program that pulls conversational data from a database.
 * Mismatches are very common, and if the AI doesn't have a
 * good match to what the user typed in unrelated gibberish
 * is often returned. There are also expletives in the program
 * due to the database the AI pulls from. This AI can be improved
 * upon by adding more conversational data to the database
 * of answers, and by adding machine learning so the program
 * not only starts with more answers, but can also learn more.
 */
public class Main {

    public static void main(String args[]) throws IOException {

        //Reader used for scanning in the corpus.
        BufferedReader reader = new BufferedReader(new FileReader(FileTranslator.retrieveFile("TranslatedMovieLines.txt")));

        //Data structure used to store all the data.
        ArrayList<String> responses = new ArrayList<String>();

        String currentLine = reader.readLine();

        //Add all the lines of conversation from the text file to the list
        while (currentLine != null) {
            responses.add(currentLine);
            currentLine = reader.readLine();
        }

        //This is the default response the bot gives
        String nextResponse = "Hello.";

        //Keep looping and responding to questions until the user exits
        while (true) {

            //Get the input sentence from the user and display response.
            String input = JOptionPane.showInputDialog(nextResponse, "Enter your response");
            System.out.println("What you said: " + input);

            if (input == null) {
                System.exit(0);
            }

            long startTime = System.currentTimeMillis();


            //Find the closest match to the input sentence.
            Object[] closestMatchAndIndex = FindClosestMatch.findClosestMatch(input, responses, 1000000000);

            String closestMatchResponse = responses.get((Integer) closestMatchAndIndex[1] + 1);


            //For stripping the metadata out of the response sentence.
            //Should be used when creating the raw conversational data in FileTranslator, but the program runs out of memory when used it there.
            while (closestMatchResponse.contains("<")) {
                String firstHalfOfResponse = closestMatchResponse.substring(0, closestMatchResponse.indexOf("<"));
                String secondHalfOfResponse = closestMatchResponse.substring(closestMatchResponse.indexOf(">") + 1);
                closestMatchResponse = firstHalfOfResponse + secondHalfOfResponse;
            }


            /** FOR TESTING PURPOSES- but can reenable if curious
             * to reenable just delete the two slashes at the beginning and recompile and run.
             * The first line is the closest match the algorithm could find to your input sentence.
             * The second line is how long it took to find the match.
             */
            System.out.println("What I think you said: " + closestMatchAndIndex[0]);
            System.out.println("My response: " + closestMatchResponse);
            //System.out.println(System.currentTimeMillis() - startTime);

            nextResponse = closestMatchResponse;
        }
    }
}
