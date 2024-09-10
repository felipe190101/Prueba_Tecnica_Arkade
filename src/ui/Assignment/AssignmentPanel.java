package ui.Assignment;

import controller.Events;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class AssignmentPanel extends JPanel{

    private JButton openFolder;
    private JTable equipmentByPrealertasTable;
    private JComboBox<String> prealertaComboBox;

    public AssignmentPanel(ActionListener listener) {
        initComponents(listener);
    }

    private void initComponents(ActionListener listener){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Panel Titulo
        JPanel panelTitle = new JPanel();
        panelTitle.setBackground(new Color(43, 123, 149));
        JLabel title = new JLabel("ASIGNACIÓN DE EQUIPOS");
        title.setForeground(new Color(255, 255, 255));
        panelTitle.add(title);
        add(panelTitle);

        // Panel de filtros
        JPanel filterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, -100, 5, 0);

        JLabel name = new JLabel("Prealerta");
        constraints.gridx = 0;
        constraints.gridy = 0;
        filterPanel.add(name, constraints);

        prealertaComboBox = new JComboBox<>();
        constraints.gridx = 0;
        constraints.gridy = 1;
        prealertaComboBox.setPreferredSize(new Dimension(300, 25));
        filterPanel.add(prealertaComboBox,constraints);

        constraints.insets = new Insets(5, 5, 5, 0);

        JButton search = new JButton();
        search.setBackground(new Color(238, 238, 238));
        ImageIcon icon = new ImageIcon("src/resources/search.png");
        search.setIcon(icon);
        search.addActionListener(listener);
        search.setActionCommand(Events.SEARCH_EQUIPMENT_PREALERTA.name());
        search.setFocusable(false);
        search.setForeground(Color.WHITE);
        constraints.gridx = 1;
        constraints.gridy = 1;
        filterPanel.add(search, constraints);

        constraints.insets = new Insets(5, 50, 5, 0);

        JLabel excel = new JLabel("Cargar información desde Excel");
        constraints.gridx = 2;
        constraints.gridy = 0;
        filterPanel.add(excel, constraints);

        constraints.insets = new Insets(5, 50, 5, 0);

        openFolder = new JButton();
        ImageIcon iconFolder = new ImageIcon("src/resources/iconFolder.png");
        openFolder.addActionListener(listener);
        openFolder.setActionCommand(Events.INSERT_DATA_EXCEL.name());
        openFolder.setIcon(iconFolder);
        openFolder.setBorderPainted(false);
        openFolder.setFocusable(false);
        openFolder.setBackground(new Color(238, 238, 238));
        constraints.gridx = 2;
        constraints.gridy = 1;
        filterPanel.add(openFolder, constraints);

        constraints.insets = new Insets(5, 50, 5, 0);

        JLabel add = new JLabel("Agregar un solo equipo");
        constraints.gridx = 3;
        constraints.gridy = 0;
        filterPanel.add(add, constraints);

        JButton addEquipment = new JButton();
        addEquipment.setBackground(new Color(238, 238, 238));
        ImageIcon iconAdd = new ImageIcon("src/resources/add.png");
        addEquipment.setIcon(iconAdd);
        addEquipment.addActionListener(listener);
        addEquipment.setActionCommand(Events.SHOW_ADD_EQUIPMENT_TO_PREALERTA.name());
        addEquipment.setBorderPainted(false);
        addEquipment.setFocusable(false);
        addEquipment.setForeground(Color.WHITE);
        constraints.gridx = 3;
        constraints.gridy = 1;
        filterPanel.add(addEquipment, constraints);

        JLabel delete = new JLabel("Eliminar equipo");
        constraints.gridx = 4;
        constraints.gridy = 0;
        filterPanel.add(delete, constraints);

        JButton deleteEquipment = new JButton();
        deleteEquipment.setBackground(new Color(238, 238, 238));
        ImageIcon iconDelete = new ImageIcon("src/resources/delete.png");
        deleteEquipment.setIcon(iconDelete);
        deleteEquipment.addActionListener(listener);
        deleteEquipment.setActionCommand(Events.DELETE_EQUIPMENT.name());
        deleteEquipment.setBorderPainted(false);
        deleteEquipment.setFocusable(false);
        deleteEquipment.setForeground(Color.WHITE);
        constraints.gridx = 4;
        constraints.gridy = 1;
        filterPanel.add(deleteEquipment, constraints);

        add(filterPanel);

        equipmentByPrealertasTable = new JTable();
        equipmentByPrealertasTable.setDefaultEditor(Object.class, null);
        equipmentByPrealertasTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(equipmentByPrealertasTable);

        add(scrollPane);
    }

    public JComboBox<String> getPrealertaComboBox() {
        return prealertaComboBox;
    }

    public void setTableModel(DefaultTableModel model){
        equipmentByPrealertasTable.setModel(model);
    }
}
