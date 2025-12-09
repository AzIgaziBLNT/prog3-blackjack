package blackjack.gui;

import blackjack.backend.Card;
import blackjack.backend.DataSerializer;
import blackjack.backend.PlayerProfile;
import blackjack.logic.GameStateHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class GameFrame extends JFrame{

    private GameStateHandler gameLogic = new GameStateHandler();
    private PlayerProfile currentPlayer;

    // mentéshez és megjelenítéshez
    private ArrayList<PlayerProfile> profiles;
    private DataSerializer dataSerializer;

    // panelek a kártyák megjelenítéséhez
    private JPanel dealerCardPanel;
    private JPanel playerCardPanel;
    private JFrame frame;

    // side panel
    private JLabel balanceLabel;
    private JLabel betLabel;
    private JTextField betInputField;
    private JButton placeBetButton;
    private JButton hitButton;
    private JButton standButton;

    public GameFrame() {
        profiles = new ArrayList<>();
        dataSerializer = new DataSerializer(profiles);

        dataSerializer.loadFile();
        profiles = dataSerializer.getProfiles();

        createGUI();
        login();
    }

    private void login() {
        JComboBox<Object> profileSelecter = new JComboBox<>();

        String newProfile = "Új profil létrehozása";
        profileSelecter.addItem(newProfile);

        if (profiles != null) {
            for (PlayerProfile p : profiles)
                profileSelecter.addItem(p);
        }

        Object[] msg = {"Válassz játékosprofilt:", profileSelecter};
        int option = JOptionPane.showConfirmDialog(frame, msg, "Profil választása", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            Object selected = profileSelecter.getSelectedItem();

            if (selected instanceof String) { // új profil létrehozása opció a listából
                createNewProfile();
            } else if (selected instanceof PlayerProfile) {
                currentPlayer = (PlayerProfile) selected;
            }

            if (gameLogic != null) {
                gameLogic.resetGame(); //profilváltásnál reset

                // UI reset
                hitButton.setEnabled(false);
                standButton.setEnabled(false);
                placeBetButton.setEnabled(true);
                betInputField.setEnabled(true);
                betLabel.setText("Jelenlegi tét: 0");

                updateGUI();
            }

        } else { // default profil
            currentPlayer = new PlayerProfile("Guest");
        }
        updateTitle();
    }

    private void updateTitle() {
        if (frame != null && currentPlayer != null) {
            frame.setTitle("Blackjack - Prog 3 NHF 2025 Edition by Habibi - Játékos: " + currentPlayer.getName());
        }
    }

    private void createNewProfile() {
        String name = JOptionPane.showInputDialog(frame, "Játékos neve:", "Új profil",
                JOptionPane.PLAIN_MESSAGE);

        if (name == null || name.trim().isEmpty()) {
            name = "Játékos " + (profiles.size() + 1);
        }

        // ha már van ilyen név
        for (PlayerProfile p : profiles) {
            if (p.getName().equalsIgnoreCase(name)) {
                JOptionPane.showMessageDialog(frame, "Ilyen nevű profil már létezik!");
                currentPlayer = p;
                return;
            }
        }

        // új profil
        currentPlayer = new PlayerProfile(name);
        profiles.add(currentPlayer);

        dataSerializer.saveFile();
    }
    private void createGUI() {
        frame = new JFrame("Blackjack - Prog 3 NHF 2025 Edition by Habibi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1440, 720);

        java.net.URL url = getClass().getResource("/cards/icon.png");
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            frame.setIconImage(icon.getImage());
        } else {
            System.err.println("Alkalmazás ikonja nem található!");
        }
        // menüsor hozzáadása
        frame.setJMenuBar(createMenuBar());

        // game panel hozzáadása
        frame.getContentPane().add(createGamePanel());

        frame.setLocationRelativeTo(null); // képernyő közepére igazítás
        frame.setVisible(true);
    }

    private JPanel createGamePanel() {
        // main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(230, 230, 230));

        // kártyák panele (osztóé fent, játékosé lent)
        JPanel cardPanel = new JPanel(new GridLayout(2,1));

        // játékos panele
        JPanel playerPanel = new JPanel(new BorderLayout());
        playerPanel.setBorder(BorderFactory.createTitledBorder("A te lapjaid"));
        playerCardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playerCardPanel.setBackground(new Color(53, 101, 77));
        playerPanel.add(playerCardPanel, BorderLayout.CENTER);
        playerPanel.setBackground(new Color(230, 230, 230));


        // osztó panele
        JPanel dealerPanel = new JPanel(new BorderLayout());
        dealerPanel.setBorder(BorderFactory.createTitledBorder("Osztó lapjai"));
        dealerCardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dealerCardPanel.setBackground(new Color(53, 101, 77));
        dealerPanel.add(dealerCardPanel, BorderLayout.CENTER);
        playerPanel.setBackground(new Color(230, 230, 230));

        cardPanel.add(dealerPanel);
        cardPanel.add(playerPanel);

        //Gombok panele
        JPanel controlPanel = new JPanel(new FlowLayout());
        hitButton = new JButton("Hit");
        hitButton.setEnabled(false);
        standButton = new JButton("Stand");
        standButton.setEnabled(false);

        hitButton.addActionListener(e -> handleHit());
        standButton.addActionListener(e -> handleStand());

        controlPanel.add(hitButton);
        controlPanel.add(standButton);

        // side panel
        JPanel sidePanel = createSidePanel();

        mainPanel.add(cardPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        mainPanel.add(sidePanel, BorderLayout.EAST);

        return mainPanel;
    }

    private JPanel createSidePanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setPreferredSize(new Dimension(250, 0));
        panel.setBackground(new Color(230, 230, 230));

        // egyenleg
        JLabel balTitle = new JLabel("Egyenleg:");
        balTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        balanceLabel = new JLabel("JMF");
        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        balanceLabel.setForeground(new Color(0, 100, 0));
        balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // tét
        JLabel betTitle = new JLabel("Tét megadása:");
        betTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        betInputField = new JTextField("20");
        betInputField.setMaximumSize(new Dimension(150, 30));
        betInputField.setHorizontalAlignment(JTextField.CENTER);

        placeBetButton = new JButton("LET'S GO GAMBLING!");
        placeBetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        placeBetButton.setBackground(new Color(50, 50, 200));
        placeBetButton.setForeground(Color.WHITE);
        placeBetButton.addActionListener(e -> betUI());

        betLabel = new JLabel("Jelenlegi tét: 0");
        betLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(balTitle);
        panel.add(balanceLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(new JSeparator());
        panel.add(Box.createVerticalStrut(30));
        panel.add(betTitle);
        panel.add(betInputField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(placeBetButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(betLabel);

        return panel;
    }

    private void betUI() {
        try {
            long bet = Long.parseLong(betInputField.getText());

            // ell.
            if (bet <= 0) {
                JOptionPane.showMessageDialog(frame, "A tétnek pozitívnak kell lennie!");
                return;
            }
            if (bet > currentPlayer.getNetWorth()) {
                JOptionPane.showMessageDialog(frame, "Túl nagy a tét, nincs elég della!");
                return;
            }
            gameLogic.setBet(bet);
            gameLogic.newRound();

            // UI
            hitButton.setEnabled(true);
            standButton.setEnabled(true);
            placeBetButton.setEnabled(false); // kör közben nincs bet
            betInputField.setEnabled(false);

            updateGUI();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Érvényes számot adj meg!");
        }
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // file menü
        JMenu fileMenu = new JMenu("File");
        JMenuItem save = new JMenuItem("Mentés");
        JMenuItem load = new JMenuItem("Betöltés");

        save.addActionListener(e -> dataSerializer.saveFile());
        load.addActionListener(e -> {
            dataSerializer.loadFile();
            profiles = dataSerializer.getProfiles();
        });

        fileMenu.add(save);
        fileMenu.add(load);

        // játék menü
        JMenu gameMenu = new JMenu("Játék");
        JMenuItem newGame = new JMenuItem("Új játék");
        JMenuItem changeProfile = new JMenuItem("Játékosprofil váltása");

        newGame.addActionListener(e -> {
            gameLogic.newRound();
            updateGUI();
        });
        gameMenu.add(newGame);

        changeProfile.addActionListener(e -> login());
        gameMenu.add(changeProfile);

        // dicsőségfal menü
        JMenu highScoresMenu = new JMenu("Dicsőségfal");
        JMenuItem highScores = new JMenuItem("Dicsőségfal");

        highScores.addActionListener(e -> showHighScores());
        highScoresMenu.add(highScores);

        menuBar.add(fileMenu);
        menuBar.add(gameMenu);
        menuBar.add(highScoresMenu);

        return menuBar;
    }

    private void handleHit() {
        gameLogic.hit();
        updateGUI();
    }

    private void handleStand() {
        gameLogic.stand();
        updateGUI();

    }

    private void updateGUI() {
        dealerCardPanel.removeAll();
        playerCardPanel.removeAll();

        // játékos lapjai
        for (Card c : gameLogic.getPlayerHand().getHand()) {
            ImageIcon icon = getCardImage(c);
            if (icon != null) {
                Image img = icon.getImage().getScaledInstance(180, 250, Image.SCALE_SMOOTH);
                playerCardPanel.add(new JLabel(new ImageIcon(img)));
            }
        }

        // osztó lapjai
        for (Card c : gameLogic.getDealerHand().getHand()) {
            ImageIcon icon = getCardImage(c);
            if (icon != null) {
                Image img = icon.getImage().getScaledInstance(180, 250, Image.SCALE_SMOOTH);
                dealerCardPanel.add(new JLabel(new ImageIcon(img)));
            }
        }

        // frissites a képernyőn
        dealerCardPanel.revalidate();
        dealerCardPanel.repaint();
        playerCardPanel.revalidate();
        playerCardPanel.repaint();

        if (currentPlayer != null) {
            balanceLabel.setText(currentPlayer.getNetWorth() + " JMF");
        }
        betLabel.setText("Jelenlegi tét: " + gameLogic.getBet());


        if (gameLogic.getCurrentState() == GameStateHandler.GameState.ROUND_END) {
            String message = gameLogic.payout(currentPlayer);
            balanceLabel.setText(currentPlayer.getNetWorth() + " JMF");

            // játék vége msg
            JOptionPane.showMessageDialog(frame, message);

            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            placeBetButton.setEnabled(true);
            betInputField.setEnabled(true);

            dataSerializer.saveFile();
        }
    }

    private void showHighScores() {
        if (profiles == null)
            profiles = new ArrayList<>();

        HighScoreWindow hsWindow = new HighScoreWindow(frame, profiles);
        hsWindow.setVisible(true);

    }

    private ImageIcon getCardImage(Card card) {
        String filename = "";

        if (card.getIsHidden()) {
            filename = "cover.png";
        } else {
            filename = card.getSuit() + card.getCardValue() + ".png";
        }

        java.net.URL imgUrl = getClass().getResource("/cards/" + filename);
        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        } else {
            System.err.println("Nem található: " + filename);
            return null;
        }
    }


}
