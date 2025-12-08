package blackjack.gameGUI;

import blackjack.gameLogic.GameStateHandler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GameFrame {

    private GameStateHandler gameLogic = new GameStateHandler();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GameFrame().createGUI();
        })
    }

    private void createGUI() {
        JFrame frame = new JFrame("Blackjack - Prog 3 NHF 2025 Edition by Habibi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // menüsor hozzáadása
        frame.setJMenuBar(createMenuBar());

        // game panel hozzáadása
        frame.getContentPane().add(createGamePanel());

        frame.pack();
        frame.setVisible(true);
    }

    private JPanel createGamePanel() {
        // main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // kártyák panele
        JPanel cardPanel = new JPanel(new GridLayout(2,1));
        cardPanel.setBorder(BorderFactory.createTitledBorder("Gameplay"));

        //wip: display panelek

        //Gombok panele
        JPanel controlPanel = new JPanel(new FlowLayout());
        JButton hitButton = new JButton("Hit");
        JButton standButton = new JButton("Stand");

        hitButton.addActionListener(e -> handleHit());
        standButton.addActionListener(e -> handleStand());

        controlPanel.add(hitButton);
        controlPanel.add(standButton);

        mainPanel.add(cardPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // file menü
        JMenu fileMenu = new JMenu("File");
        JMenuItem save = new JMenuItem("Mentés");
        JMenuItem load = new JMenuItem("Betöltés");

        save.addActionListener(e -> saveFile());
        load.addActionListener(e -> loadFile());

        fileMenu.add(save);
        fileMenu.add(load);

        // játék menü
        JMenu gameMenu = new JMenu("Játék");
        JMenuItem newGame = new JMenuItem("Új játék");

        newGame.addActionListener(e -> gameLogic.newRound());
        gameMenu.add(newGame);

        // dicsőségfal menü
        JMenu highScoresMenu = new JMenu("Dicsőségfal");
        //JMenuItem highScores = new JMenuItem("Dicsőségfal");

        //highScores.addActionListener(e -> showHighScores());
        //highScoresMenu.add(highScores);
        highScoresMenu.addActionListener(e -> showHighScores());

        menuBar.add(fileMenu);
        menuBar.add(gameMenu);
        menuBar.add(highScoresMenu);

        return menuBar;
    }

    private void handleHit() {
        gameLogic.hit();
        updateGUI();

        if (gameLogic.getCurrentState() == GameStateHandler.GameState.ROUND_END) {
            JOptionPane.showMessageDialog(null, "kör vége");
        }
    }

    private void handleStand() {

    }

    private void updateGUI() {

    }

    private void saveFile(){}
    private void loadFile(){}

    private void showHighScores() {
        if (profiles == null)
            profiles = new ArrayList<>();

        HighScoreWindow hsWindow = new HighScoreWindow(this, profiles);
        hsWindow.setVisible(true);

    }
}
