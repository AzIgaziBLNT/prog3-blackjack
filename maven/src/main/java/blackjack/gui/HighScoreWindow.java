package blackjack.gui;

import blackjack.backend.PlayerProfile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class HighScoreWindow extends JDialog {

    public HighScoreWindow(JFrame jf, ArrayList<PlayerProfile> profiles) {
        super(jf, "Dicsőségfal", true);
        setSize(800, 600);
        setLocationRelativeTo(jf);

        // oszlopok
        String[] columns = {"Név", "Győzelem", "Vereség", "Net Worth (JMF)"};

        // táblázat modelje

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }

            @Override
            public Class<?> getColumnClass(int colIdx) {
                if (colIdx == 0) return String.class;
                else if (colIdx != 3) return Integer.class;

                return Long.class;
            }
        };

        if (profiles != null) {
            for (PlayerProfile p : profiles) {
                model.addRow(new Object[]{p.getName(), p.getWinCount(), p.getLoseCount(), p.getNetWorth()});
            }
        }

        // táblázat
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
    }
}
