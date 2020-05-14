package gui.panels;

import gui.MainFrame;
import lombok.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;

/**
 * @author Ioan Sava
 */
@NoArgsConstructor
public class DesignPanel extends JPanel implements Serializable {
    private MainFrame frame;
    @Getter
    private int width;
    @Getter
    private int height;

    public DesignPanel(MainFrame frame, int width, int height) {
        this.frame = frame;
        this.width = width;
        this.height = height;
        init();
    }

    public void setWidth(int width) {
        this.width = width;
        init();
    }

    public void setHeight(int height) {
        this.height = height;
        init();
    }

    private void init() {
        setPreferredSize(new Dimension(this.width, this.height));
    }

    public void addComponent(Component component) {
        add(component);
        revalidate();
        repaint();
    }

    /**
     * Whenever the user sets the focus on an added component,
     * its properties should be displayed in a JTable component
     */
    public void addFocusListenerToComponent(Component component) {
        component.addFocusListener(new FocusListener() {
            @SneakyThrows
            @Override
            public void focusGained(FocusEvent e) {
                Class<?> componentClass = component.getClass();
                BeanInfo info = Introspector.getBeanInfo(componentClass);
                int i = 0;
                DefaultTableModel model = (DefaultTableModel) frame.getPropertiesPanel().getPropertiesTable().getModel();
                for (PropertyDescriptor propertyDescriptor : info.getPropertyDescriptors()) {
                    model.setValueAt(String.valueOf(propertyDescriptor.getPropertyType()), i, 0);
                    model.setValueAt(String.valueOf(propertyDescriptor.getName()), i, 1);
                    ++i;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // nothing
            }
        });
    }
}
