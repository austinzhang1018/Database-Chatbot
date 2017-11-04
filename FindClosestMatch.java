import java.util.ArrayList;

/**
 * Created by austinzhang on 12/7/16.
 * This class is used to find the sentence that most matches the input sentence
 * and to provide information used to find the next sentence.
 */
public class FindClosestMatch {

    //Searches through entire database or until the timeout is reached and gets the
    //lowest lev. distance from the responses searched.
    public static Object[] findClosestMatch(String input, ArrayList<String> responses, long timeToRespond) {
        long startTime = System.currentTimeMillis();

        int indexOfClosestMatch = 0;

        int currentLowestLevDistance = Integer.MAX_VALUE;

        ArrayList<Integer> lowestLevDistanceIndexes = new ArrayList<Integer>();

        //Find lev. distance for each response in the database and pick the lowest one
        for (int i = 0; i < responses.size(); i++) {
            if (startTime + timeToRespond < System.currentTimeMillis()) {
                Object[] stringAndIndexPair = new Object[2];
                stringAndIndexPair[0] = responses.get(indexOfClosestMatch);
                stringAndIndexPair[1] = indexOfClosestMatch;
                return stringAndIndexPair;
            }

            int currentLevDistance = LevenshteinDistance.levenshteinDistance(input, responses.get(i));

            if (currentLevDistance == currentLowestLevDistance) {
                lowestLevDistanceIndexes.add(i);
            }

            if (currentLowestLevDistance > currentLevDistance) {
                indexOfClosestMatch = i;
                currentLowestLevDistance = currentLevDistance;
                lowestLevDistanceIndexes = new ArrayList<Integer>();
                lowestLevDistanceIndexes.add(i);
            }
        }

        /** For debugging - if curious delete the // and program will print out the value of the
         * lowest lev. distance and the index of the closest match.
         */
        //System.out.println(currentLowestLevDistance);
        //System.out.println(indexOfClosestMatch);

        //return the sentence as well as the index for the sentence that can be used
        //to determine the next sentence in the conversations

        Object[] stringAndIndexPair = new Object[2];

        int index = (int) (lowestLevDistanceIndexes.size() * Math.random());

        stringAndIndexPair[0] = responses.get(lowestLevDistanceIndexes.get(index));
        stringAndIndexPair[1] = lowestLevDistanceIndexes.get(index);
        return stringAndIndexPair;
    }

}
