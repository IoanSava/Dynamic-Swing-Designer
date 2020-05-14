package gui.panels;

import gui.MainFrame;
import lombok.Getter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@Getter
public class PropertiesPanel extends JPanel {
    private final MainFrame frame;
    private final JTable propertiesTable;

    public PropertiesPanel(MainFrame frame) {
        this.frame = frame;
        this.propertiesTable = new JTable(new DefaultTableModel(new String[]{"Type", "Name"}, 100));
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        propertiesTable.setFillsViewportHeight(true);
        JScrollPane scrollTable = new JScrollPane(propertiesTable);
        add(scrollTable, BorderLayout.CENTER);
    }
}
