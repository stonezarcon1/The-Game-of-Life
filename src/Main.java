import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    private static int z = 0;
    private static int y = 0;
    private static int[][] loadedBoard;

    public static int[][] boardGen(int r, int c) {
        Random random = new Random();
        int[][] myBoard = new int[r][c];

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                double randNum = random.nextDouble();
                if (randNum < 0.5) {
                    myBoard[i][j] = 1;
                } else {
                    myBoard[i][j] = 0;
                }
            }
        }
        return myBoard;
    }

    public static char[][] render(int[][] randomBoard) {
        char[][] myBoard = new char[randomBoard.length][randomBoard[0].length];
        for (int i = 0; i < randomBoard.length; i++) {
            for (int j = 0; j < randomBoard[0].length; j++) {
                if (randomBoard[i][j] == 1) {
                    myBoard[i][j] = 'O';
                } else {
                    myBoard[i][j] = ' ';
                }
            }
        }

        return myBoard;
    }

    public static int[][] nextState(int[][] randomBoard) {
        int[][] nextBoard = new int[randomBoard.length][randomBoard[0].length];
        for (int i = 0; i < randomBoard.length; i++) {
            for (int j = 0; j < randomBoard[0].length; j++) {
                nextBoard[i][j] = randomBoard[i][j];
            }
        }
        for (int i = 0; i < randomBoard.length; i++) {
            for (int j = 0; j < randomBoard[0].length; j++) {
                int value = 0;
                if (j > 0) {
                    value = value + randomBoard[i][j - 1];
                }
                if (j < randomBoard[0].length - 1) {
                    value = value + randomBoard[i][j + 1];
                }
                if (i > 0) {
                    value = value + randomBoard[i - 1][j];
                }
                if (i < randomBoard.length - 1) {
                    value = value + randomBoard[i + 1][j];
                }
                if (i < randomBoard.length - 1 && j < randomBoard[0].length - 1) {
                    value = value + randomBoard[i + 1][j + 1];
                }
                if (i < randomBoard.length - 1 && j > 0) {
                    value = value + randomBoard[i + 1][j - 1];
                }
                if (i > 0 && j < randomBoard[0].length - 1) {
                    value = value + randomBoard[i - 1][j + 1];
                }
                if (i > 0 && j > 0) {
                    value = value + randomBoard[i - 1][j - 1];
                }
                //now we assign values
                if (value < 2) {
                    nextBoard[i][j] = 0;
                }
                if (randomBoard[i][j] == 1 && value == 2 || value == 3) {
                    nextBoard[i][j] = 1;
                }
                if (value > 3) {
                    nextBoard[i][j] = 0;
                }
                if (randomBoard[i][j] == 0 && value == 3) {
                    nextBoard[i][j] = 1;
                }
            }
        }
        return nextBoard;
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame(); //creating GUI
        frame.pack();
        frame.setLayout(null);
        frame.setSize(500, 500);

        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(); //panel that JObjects will be added to
        panel.setBounds(0, 0, 600, 500);
        panel.setLayout(null); //allows us to set bounds for our objects

        JLabel label1 = new JLabel("Enter your rows :");
        label1.setBounds(100, 10, 110, 20);

        JLabel label2 = new JLabel("Enter your columns :");
        label2.setBounds(100, 30, 130, 20);

        JTextArea rowArea = new JTextArea();
        rowArea.setBounds(215, 10, 70, 20);

        JTextArea columnArea = new JTextArea();
        columnArea.setBounds(240, 30, 70, 20);

        JButton rowButton = new JButton("Enter");
        rowButton.setBounds(290, 10, 70, 20);

        JButton columnButton = new JButton("Enter");
        columnButton.setBounds(315, 30, 70, 20);

        String info = "Click here to import your own board.";
        JLabel openText = new JLabel("<html>" + info + "</html>");
        openText.setBounds(100, 355, 120, 40);

        JButton openFile = new JButton("Open");
        openFile.setBounds(225, 355, 100, 20);

        JLabel fileLabel = new JLabel("No file selected.");
        fileLabel.setBounds(225, 375, 200, 40);

        JTextArea myField = new JTextArea(20, 30);
        Font font = myField.getFont();
        myField.setFont(font.deriveFont(Font.BOLD));
        myField.setLineWrap(true);
        myField.setEditable(false);
        myField.setBounds(100, 50, 300, 300);
        myField.setMaximumSize(new Dimension(300, 300));

        Border border = BorderFactory.createLineBorder(Color.BLACK);
        myField.setBorder(border);
        label1.setBorder(border);
        label2.setBorder(border);

        panel.add(myField);
        panel.add(label1);
        panel.add(label2);
        panel.add(rowArea);
        panel.add(columnArea);
        panel.add(rowButton);
        panel.add(columnButton);
        panel.add(openFile);
        panel.add(openText);
        panel.add(fileLabel);
        frame.add(panel);
        frame.setVisible(true);

        rowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = rowArea.getText();
                z = Integer.parseInt(s);
                rowArea.setText("");
            }
        });
        columnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = columnArea.getText();
                y = Integer.parseInt(s);
                columnArea.setText("");
            }
        });
        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] myBoard;
                final JFileChooser fc = new JFileChooser();

                int r = fc.showOpenDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) {
                    fileLabel.setText("<html>" + fc.getSelectedFile().getAbsolutePath() + "<html/>");

                    try {
                        File file = new File(String.valueOf(fc.getSelectedFile()));
                        Scanner reader = new Scanner(file);
                        ArrayList<ArrayList<Integer>> arrays = new ArrayList<>();
                        while (reader.hasNextLine()) {
                            String line = reader.nextLine();
                            ArrayList<Integer> array = new ArrayList<>();
                            for (Character ch : line.toCharArray()) {
                                array.add(Integer.parseInt(ch.toString()));
                            }
                            arrays.add(array);
                        }
                        reader.close();
                        myBoard = new int[arrays.size()][];
                        for (int i = 0; i < arrays.size(); i++) {
                            ArrayList<Integer> save = arrays.get(i);

                            int[] copy = new int[save.size()];
                            for (int j = 0; j < save.size(); j++) {
                                copy[j] = save.get(j);
                            }
                            myBoard[i] = copy;
                            loadedBoard = myBoard;
                        }
                    } catch (FileNotFoundException q) {
                        q.printStackTrace();
                    }
                } else {
                    fileLabel.setText("Operation cancelled by user");
                }
            }
        });
        while (z == 0 || y == 0) {
            TimeUnit.MILLISECONDS.sleep(1);
            if (loadedBoard != null) {
                rowButton.setEnabled(false);
                columnButton.setEnabled(false);
                rowArea.setEditable(false);
                columnArea.setEditable(false);
                myField.setText("Loading...");
                TimeUnit.SECONDS.sleep(2);
                myField.setText("");

                int[][] randomBoard = loadedBoard;
                char[][] prettyBoard;
                while (true) {
                    randomBoard = nextState(randomBoard);
                    prettyBoard = render(randomBoard);
                    for (char[] row : prettyBoard) {
                        for (char el : row) {
                            myField.append(el + " ");
                        }
                        myField.append("\n");
                    }
                    TimeUnit.MILLISECONDS.sleep(500);
                    myField.setText("");
                }
            }
        }
         if (z != 0 && y != 0) {
            openFile.setEnabled(false);
            myField.setText("Loading...");
            TimeUnit.SECONDS.sleep(2);
            myField.setText("");

            int[][] randomBoard = boardGen(z, y);
            char[][] prettyBoard;
            while (true) {
                randomBoard = nextState(randomBoard);
                prettyBoard = render(randomBoard);
                for (char[] row : prettyBoard) {
                    for (char el : row) {
                        myField.append(el + " ");
                    }
                    myField.append("\n");
                }
                TimeUnit.MILLISECONDS.sleep(500);
                myField.setText("");
            }
        }
    }
}