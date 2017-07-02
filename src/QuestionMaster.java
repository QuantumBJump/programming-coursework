import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Stack;

/**
 * Created by Quinn Stevens on 31/05/2017.
 */
public class QuestionMaster implements ActionListener {
    JFrame frame;
    int questionNum= 0;
    int score = 0;
    String currentAnswer;

    String[] questions;
    String[][] options;
    String[] answers;

    boolean skipAllowed;
    Stack skipped = new Stack();
    int incorrectAnswers = 0;

    String report = "";

    public QuestionMaster (String[] questions, String[][] options, String[] answers) {
        this.questions = questions;
        this.options = options;
        this.answers = answers;
        this.questionNum = 0;
        skipAllowed = true;
        askQuestion(questions[questionNum], options[questionNum], answers[questionNum]);
    }

    private void askQuestion(String question, String[] options, String answer) {
        frame = new JFrame("Qui[2{n}|z]");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(600, 300);
        frame.setLayout(new BorderLayout());

        // Panel for question
        JLabel qLabel = new JLabel("Question " + (questionNum+1) + ": " + question);

        // Panel for answer options
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton[] answerButtons = new JRadioButton[options.length];
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i] = new JRadioButton(options[i]);
            answerButtons[i].addActionListener(this);
            buttonGroup.add(answerButtons[i]);
            buttonPanel.add(answerButtons[i]);
        }

        // Panel for controls
        JPanel controlPanel = new JPanel(new FlowLayout());
        // Create quit button
        JButton quitButton = new JButton("Quit");
        quitButton.setActionCommand("quit");
        quitButton.addActionListener(this);
        controlPanel.add(quitButton);

        if (skipAllowed) {
            // Create skip button
            JButton skipButton = new JButton("Skip");
            skipButton.setActionCommand("skip");
            skipButton.addActionListener(this);
            controlPanel.add(skipButton);
        }

        // Create submit button
        JButton answerButton = new JButton("Answer");
        answerButton.setActionCommand("answer");
        answerButton.addActionListener(this);
        controlPanel.add(answerButton);

        frame.add(qLabel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    private void nextQuestion() {
        frame.dispose();
        questionNum++;
        if (questionNum < questions.length) {
            askQuestion(questions[questionNum], options[questionNum], answers[questionNum]);
        } else if (!skipped.empty()) {
            Object[] skipOptions = {"Yes", "No"};
            int retry = JOptionPane.showOptionDialog(
                    null,
                    "You skipped " + skipped.size() + " questions. Would you like to retry them?",
                    "Retry skipped questions?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    skipOptions,
                    skipOptions[1]);
            if (retry == 0) {
                skipAllowed = false;
                retrySkipped();
            } else {
                endQuiz();
            }
        } else {
            endQuiz();
        }
    }

    private void updateReport() {
        incorrectAnswers += 1;
        report += "\n\nQ: " + questions[questionNum] + "\nCorrect answer: " + answers[questionNum] + "\n";
    }

    private void endQuiz() {
        if (incorrectAnswers != 0) {
            report = "\n\nYou got " + incorrectAnswers + " wrong:" + report;
        }

        JOptionPane.showMessageDialog(null, "You have scored " + score + " out of 10.\n" +
                "This is equivalent to " + Math.round(score/10.0*100) + "%\nWell done!" + report);
    }

    private void checkAnswer() {
        if (Objects.equals(currentAnswer, answers[questionNum])) {
            score ++;
        } else {
            updateReport();
        }
    }

    private void resetQuiz() {
        frame.dispose();

        Quiz.runQuiz();
    }

    private void retrySkipped() {
        int stackSize = skipped.size();
        String[] skippedQs = new String[stackSize];
        String[][] skippedOpts = new String[stackSize][4];
        String[] skippedAnsw = new String[stackSize];

        for (int i = stackSize-1; i >= 0; i--) {
            int qNum = (int) skipped.pop();
            skippedQs[i] = questions[qNum];
            skippedOpts[i] = options[qNum];
            skippedAnsw[i] = answers[qNum];
        }

        questions = skippedQs;
        options = skippedOpts;
        answers = skippedAnsw;
        questionNum = -1;
        nextQuestion();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command == "answer" && currentAnswer != null) {
            checkAnswer();
            nextQuestion();
        } else if (command == "answer" && currentAnswer == null) {
            JOptionPane.showMessageDialog(null, "You haven't selected an answer!");
        } else if (command == "quit") {
            resetQuiz();
        } else if (command == "skip") {
            skipped.push(questionNum);
            nextQuestion();
        } else {
            currentAnswer = command;
        }
    }
}