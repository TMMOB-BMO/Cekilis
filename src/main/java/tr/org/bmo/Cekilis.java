package tr.org.bmo;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Hello world!
 */
public class Cekilis extends JFrame {
    private Timer timer;
    private Random random = new Random();
    private JLabel lucky = new JLabel();
    private List<String> listOfCandidates = new ArrayList<String>();
    private int ellapsedTime = 0;
    private int endTime = 7000;
    private int iteration = 100;

    public Cekilis() throws HeadlessException {
        Container pane = getContentPane();
        JButton button = new JButton("Şanslı Katılımcıyı Seç");
        timer = new Timer(iteration, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                kurraCek();
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                ellapsedTime = 0;
                timer.start();
            }
        });

        readFile();

        pane.setBackground(Color.yellow);
        Font font = new Font(null, Font.BOLD, 72);
        lucky.setFont(font);

        pane.setLayout(new GridBagLayout());
        pane.add(button, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        pane.add(lucky, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        pane.add(new JLabel(), new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
    }

    private void kurraCek() {
        int luckyNumber = random.nextInt(listOfCandidates.size());
        String luckyCandidate = listOfCandidates.get(luckyNumber);
        lucky.setText(luckyCandidate);
        ellapsedTime += iteration;
        if (ellapsedTime >= endTime) {
            listOfCandidates.remove(luckyCandidate);
            timer.stop();
        }
    }

    private void readFile() {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;
        try {
            String textLine;
            fis = new FileInputStream("Candidates.txt");
            isr = new InputStreamReader(fis, "UTF-8");
            reader = new BufferedReader(isr);
            while ((textLine=reader.readLine())!=null) {
                if (textLine.trim().length() > 0) {
                    listOfCandidates.add(textLine);
                }
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Cekilis();
    }

}
