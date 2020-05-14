package gui;

import gui.panels.ControlPanel;
import gui.panels.DesignPanel;
import gui.panels.PropertiesPanel;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

/**
 * Frame/Window of the application
 *
 * @author Ioan Sava
 */
@Getter
public class MainFrame extends JFrame {
    private ControlPanel controlPanel;
    private DesignPanel designPanel;
    private PropertiesPanel propertiesPanel;

    public MainFrame() {
        super("Dynamic Swing Designer");
        init();
    }

    /**
     * Initialize the Mainframe Component
     * adding its children (panels)
     */
    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initPanels();
        addElementsInContainer();
        pack();
    }

    private void initPanels() {
        controlPanel = new ControlPanel(this);
        designPanel = new DesignPanel(this, 800, 600);
        propertiesPanel = new PropertiesPanel(this);
    }

    /**
     * arrange the components in the container (frame)
     * JFrame uses a BorderLayout by default
     */
    private void addElementsInContainer() {
        add(controlPanel, BorderLayout.NORTH);
        add(designPanel, BorderLayout.CENTER);
        add(propertiesPanel, BorderLayout.EAST);
    }

    public void updateDesignPanel(DesignPanel designPanel) {
        remove(this.designPanel);
        this.designPanel = designPanel;
        add(designPanel, BorderLayout.CENTER);
        pack();
    }
}
