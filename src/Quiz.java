import javax.swing.*;

/**
 * Created by t.stevens on 31/05/2017.
 */
public class Quiz {
    static int score = 0;

    public static void main (String[] args) {
        runQuiz();
    }

    public static void runQuiz() {
        String[] questions;
        String[][] options;
        String[] correctAnswers;

        Object[] difficulties = {"Easy", "Hard", "Quit"};

        int difficulty = JOptionPane.showOptionDialog(null, "Start quiz?", "Start quiz", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, difficulties, difficulties[0]);

        FileReader fr = new FileReader();
        if (difficulty == 0) {
            questions = fr.getArray("resources/easy/questions.txt");
            options = fr.getAnswerArray("resources/easy/options.txt");
            correctAnswers = fr.getArray("resources/easy/answers.txt");
            QuestionMaster qm = new QuestionMaster(questions, options, correctAnswers);
        } else if (difficulty == 1){
            questions = fr.getArray("resources/hard/questions.txt");
            options = fr.getAnswerArray("resources/hard/options.txt");
            correctAnswers = fr.getArray("resources/hard/answers.txt");
            QuestionMaster qm = new QuestionMaster(questions, options, correctAnswers);
        } else {
            System.exit(0);
        }
    }
}

