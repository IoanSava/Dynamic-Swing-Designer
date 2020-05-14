package gui.panels;

import gui.MainFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Ioan Sava
 */
public class ControlPanel extends JPanel {
    private final MainFrame frame;
    /**
     * The ControlPanel will allow the
     * user to specify any class
     * name of a Swing component
     */
    private final JTextField componentClassTextField;

    /**
     * A default text for a component
     * (if applicable)
     */
    private final JTextField componentTextField;

    /**
     * A button for creating and
     * adding an instance of the
     * specified component to the DesignPanel
     */
    private final JButton addButton;

    private final JButton saveButton;
    private final JButton loadButton;
    private final JButton exitButton;

    private static final Logger logger = LogManager.getLogger(ControlPanel.class);

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        this.componentClassTextField = initializeTextField("Component name");
        this.componentTextField = initializeTextField("Default text");
        addButton = new JButton("Add");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        exitButton = new JButton("Exit");
        init();
    }

    private JTextField initializeTextField(String text) {
        JTextField textField = new JTextField(text, 20);

        // placeholder effect
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(text)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(text);
                }
            }
        });

        return textField;
    }

    private void init() {
        setLayout(new GridLayout(1, 6));
        addElementsToContainer();
        addListenersToButtons();
    }

    private void addElementsToContainer() {
        add(componentClassTextField);
        add(componentTextField);
        add(addButton);
        add(loadButton);
        add(saveButton);
        add(exitButton);
    }

    private void addListenersToButtons() {
        addButton.addActionListener(this::add);
        loadButton.addActionListener(this::load);
        saveButton.addActionListener(this::save);
        exitButton.addActionListener(this::exit);
    }

    /**
     * Append a specified component to DesignPanel
     */
    private void add(ActionEvent event) {
        String componentName = "javax.swing.J" + componentClassTextField.getText();

        Class<?> componentClass = null;
        Constructor<?> constructor;
        Component componentInstance;
        try {
            componentClass = Class.forName(componentName);
            Class<?>[] signature = new Class[]{String.class};
            constructor = componentClass.getConstructor(signature);
            String text = componentTextField.getText();
            componentInstance = (Component) constructor.newInstance(text);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            logger.error(exception.getClass() + ": " + exception.getMessage(), exception);
            return;
        } catch (NoSuchMethodException exception) {
            try {
                logger.info("This component has no String constructor");
                constructor = componentClass.getConstructor();
                componentInstance = (Component) constructor.newInstance();
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                logger.error(e.getClass() + ": " + e.getMessage(), e);
                return;
            }
        }

        this.frame.getDesignPanel().addComponent(componentInstance);
        this.frame.getDesignPanel().addFocusListenerToComponent(componentInstance);
    }

    /**
     * The DesignPanel container must be restored
     * in XML format, using JavaBeans serialization
     */
    private void load(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Select an xml document");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML documents", "xml");
        fileChooser.addChoosableFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile().getPath());

            try {
                XMLDecoder decoder =
                        new XMLDecoder(new BufferedInputStream(
                                new FileInputStream(file)));

                DesignPanel designPanel = (DesignPanel) decoder.readObject();
                decoder.close();
                this.frame.updateDesignPanel(designPanel);
            } catch (FileNotFoundException e) {
                logger.error(e.getClass() + ": " + e.getMessage(), e);
            }
        }
    }

    /**
     * The DesignPanel container must be saved
     * in XML format, using JavaBeans serialization
     */
    private void save(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Choose a directory to save your file");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile() + "\\panel.xml");
            try {
                XMLEncoder encoder = new XMLEncoder(
                        new BufferedOutputStream(
                                new FileOutputStream(file)));

                encoder.writeObject(this.frame.getDesignPanel());
                encoder.close();
            } catch (FileNotFoundException e) {
                logger.error(e.getClass() + ": " + e.getMessage(), e);
            }
        }
    }

    /**
     * Exit the application
     */
    private void exit(ActionEvent event) {
        System.exit(0);
    }
}
