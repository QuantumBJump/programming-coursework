import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Quinn Stevens on 31/05/2017.
 */
public class FileReader {
    public String[][] getAnswerArray(String filename) {

        String[] inputArray = getArray(filename);
        String[][] outputArray = new String[inputArray.length][4];
        String[] splitText;
        for (int i = 0; i < inputArray.length; i++) {
            splitText = inputArray[i].split(", ");

            for (int j = 0; j < splitText.length; j++) {
                outputArray[i][j] = splitText[j];
            }
        }

        return outputArray;
    }

    public String[] getArray(String filename) {
        String[] text = null;
        try {
            text = readFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }

    public static String[] readFile(String filename) throws IOException {
        String fileLine;
        Stack textStack = new Stack();

        File questionFile = new File(filename);
        Scanner fileScan = new Scanner(questionFile);

        while (fileScan.hasNext()) {
            fileLine = fileScan.nextLine();
            textStack.push(fileLine);
        }

        String[] textArray = new String[textStack.size()];

        for (int i = textArray.length -1; i >=0; i--) {
            textArray[i] = (String) textStack.pop();
        }

        return textArray;
    }
}

