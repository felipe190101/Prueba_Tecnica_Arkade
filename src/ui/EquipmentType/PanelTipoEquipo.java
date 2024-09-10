package ui.EquipmentType;

import controller.Events;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelTipoEquipo extends JPanel {

    private JTable equipmentTypeTable;

    public PanelTipoEquipo(ActionListener listener) {
        initComponents(listener);
    }

    private void initComponents(ActionListener listener) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Panel Titulo
        JPanel panelTitle = new JPanel();
        panelTitle.setBackground(new Color(43, 123, 149));
        JLabel title = new JLabel("TIPOS DE EQUIPOS");
        title.setForeground(new Color(255, 255, 255));
        panelTitle.add(title);
        add(panelTitle);

        equipmentTypeTable = new JTable();
        equipmentTypeTable.setDefaultEditor(Object.class, null);
        equipmentTypeTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(equipmentTypeTable);

        add(scrollPane);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Agregar");
        addButton.setBackground(new Color(95, 213, 213, 255));
        addButton.addActionListener(listener);
        addButton.setActionCommand(Events.SHOW_CREATE_EQUIPMENT_TYPE.name());
        addButton.setBorderPainted(false);
        addButton.setFocusable(false);
        addButton.setForeground(Color.WHITE);

        JButton editButton = new JButton("Actualizar");
        editButton.setBackground(new Color(95, 213, 213, 255));
        editButton.addActionListener(listener);
        editButton.setActionCommand(Events.SHOW_UPDATE_EQUIPMENT_TYPE.name());
        editButton.setBorderPainted(false);
        editButton.setFocusable(false);
        editButton.setForeground(Color.WHITE);

        JButton deleteButton = new JButton("Eliminar");
        deleteButton.setBackground(new Color(95, 213, 213, 255));
        deleteButton.addActionListener(listener);
        deleteButton.setActionCommand(Events.SHOW_DELETE_EQUIPMENT_TYPE.name());
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusable(false);
        deleteButton.setForeground(Color.WHITE);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel);
    }

    public void setTableModel(DefaultTableModel model) {
        equipmentTypeTable.setModel(model);
    }

    public JTable getEquipmentTypeTable() {
        return equipmentTypeTable;
    }
}
