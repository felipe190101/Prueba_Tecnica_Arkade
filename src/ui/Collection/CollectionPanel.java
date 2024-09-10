package ui.Collection;

import controller.Events;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class CollectionPanel extends JPanel{

    private JTable equipmentByPrealertasTable;
    private JComboBox<String> prealertaComboBox;
    private JTextField inputSerial;
    private JTextField inputMAC;
    private JLabel quantityCollection;
    private JLabel totalEquipment;

    public CollectionPanel(ActionListener listener) {
        initComponents(listener);
    }

    private void initComponents(ActionListener listener){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Panel Titulo
        JPanel panelTitle = new JPanel();
        panelTitle.setBackground(new Color(43, 123, 149));
        JLabel title = new JLabel("RECOGIDA DE EQUIPOS");
        title.setForeground(new Color(255, 255, 255));
        panelTitle.add(title);
        add(panelTitle);

        // Panel de filtros
        JPanel filterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 14, 5, 0);

        JLabel name = new JLabel("Prealerta");
        constraints.gridx = 0;
        constraints.gridy = 0;
        filterPanel.add(name, constraints);

        prealertaComboBox = new JComboBox<>();
        constraints.gridx = 0;
        constraints.gridy = 1;
        prealertaComboBox.setPreferredSize(new Dimension(300, 25));
        filterPanel.add(prealertaComboBox,constraints);

        JButton search = new JButton();
        search.setBackground(new Color(238, 238, 238));
        ImageIcon icon = new ImageIcon("src/resources/search.png");
        search.setIcon(icon);
        search.addActionListener(listener);
        search.setActionCommand(Events.SEARCH_EQUIPMENT_PREALERTA_COLLECTION.name());
        search.setFocusable(false);
        search.setForeground(Color.WHITE);
        constraints.gridx = 1;
        constraints.gridy = 1;
        filterPanel.add(search, constraints);

        JLabel excel = new JLabel("Serial");
        constraints.gridx = 2;
        constraints.gridy = 0;
        filterPanel.add(excel, constraints);

        inputSerial = new JTextField(15);
        constraints.gridx = 2;
        constraints.gridy = 1;
        filterPanel.add(inputSerial, constraints);

        JLabel add = new JLabel("MAC");
        constraints.gridx = 3;
        constraints.gridy = 0;
        filterPanel.add(add, constraints);

        inputMAC = new JTextField(15);
        constraints.gridx = 3;
        constraints.gridy = 1;
        filterPanel.add(inputMAC, constraints);

        JButton collectionEquipment = new JButton("Recoger Equipo");
        collectionEquipment.setBackground(new Color(95, 213, 213, 255));
        collectionEquipment.addActionListener(listener);
        collectionEquipment.setActionCommand(Events.COLLECTION_EQUIPMENT.name());
        collectionEquipment.setFocusable(false);
        collectionEquipment.setBorderPainted(false);
        collectionEquipment.setForeground(Color.WHITE);
        constraints.gridx = 4;
        constraints.gridy = 1;
        filterPanel.add(collectionEquipment, constraints);

        JLabel textCollection = new JLabel("Has recogido:");
        constraints.gridx = 4;
        constraints.gridy = 0;
        filterPanel.add(textCollection, constraints);

        quantityCollection = new JLabel("0");
        constraints.gridx = 5;
        constraints.gridy = 0;
        filterPanel.add(quantityCollection, constraints);

        JLabel textCollectionSecond = new JLabel(" equipo(s) de ");
        constraints.gridx = 6;
        constraints.gridy = 0;
        filterPanel.add(textCollectionSecond, constraints);

        totalEquipment = new JLabel("0");
        constraints.gridx = 7;
        constraints.gridy = 0;
        filterPanel.add(totalEquipment, constraints);

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

    public JLabel getQuantityCollection() {
        return quantityCollection;
    }

    public JLabel getTotalEquipment() {
        return totalEquipment;
    }

    public JTextField getInputSerial() {
        return inputSerial;
    }

    public JTextField getInputMAC() {
        return inputMAC;
    }
}
