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
            questions = fr.getArray("questions-easy.txt");
            options = fr.getAnswerArray("options-easy.txt");
            correctAnswers = fr.getArray("answers-easy.txt");
            QuestionMaster qm = new QuestionMaster(questions, options, correctAnswers);
        } else if (difficulty == 1){
            questions = fr.getArray("questions-hard.txt");
            options = fr.getAnswerArray("options-hard.txt");
            correctAnswers = fr.getArray("answers-hard.txt");
            QuestionMaster qm = new QuestionMaster(questions, options, correctAnswers);
        } else {
            System.exit(0);
        }
    }
}

