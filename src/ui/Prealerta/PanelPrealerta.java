package ui.Prealerta;

import controller.Events;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelPrealerta extends JPanel {

    private JButton openCalendarStart;
    private JButton openCalendarEnd;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTable prealertasTable;
    private JTextField nameField;
    private JTextField guideField;

    public PanelPrealerta(ActionListener listener) {
        initComponents(listener);
    }

    private void initComponents(ActionListener listener){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Panel Titulo
        JPanel panelTitle = new JPanel();
        panelTitle.setBackground(new Color(43, 123, 149));
        JLabel title = new JLabel("PREALERTAS");
        title.setForeground(new Color(255, 255, 255));
        panelTitle.add(title);
        add(panelTitle);

        // Panel de filtros
        JPanel filterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(2, 0, 5, 50);

        JLabel name = new JLabel("Nombre");
        constraints.gridx = 0;
        constraints.gridy = 0;
        filterPanel.add(name, constraints);

        nameField = new JTextField(10);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridheight = 4;
        filterPanel.add(nameField, constraints);

        JLabel guide = new JLabel("Guia");
        constraints.gridx = 2;
        constraints.gridy = 0;
        filterPanel.add(guide, constraints);

        guideField = new JTextField(10);
        constraints.gridx = 2;
        constraints.gridy = 1;
        filterPanel.add(guideField, constraints);

        JLabel startDate = new JLabel("Fecha Inicio");
        constraints.gridx = 4;
        constraints.gridy = 0;
        filterPanel.add(startDate, constraints);

        startDateField = new JTextField(8);
        constraints.gridx = 4;
        constraints.gridy = 1;
        filterPanel.add(startDateField, constraints);

        constraints.insets = new Insets(2, -50, 5, 50);

        openCalendarStart = new JButton();
        ImageIcon icon = new ImageIcon("src/resources/calendarIcon.png");
        openCalendarStart.setIcon(icon);
        openCalendarStart.setBorderPainted(false);
        openCalendarStart.setFocusable(false);
        openCalendarStart.setBackground(new Color(238, 238, 238));
        openCalendarStart.addActionListener(listener);
        openCalendarStart.setActionCommand(Events.ACTIVAR_CALENDARIO.name());
        constraints.gridx = 5;
        constraints.gridy = 1;
        filterPanel.add(openCalendarStart, constraints);

        constraints.insets = new Insets(2, 0, 5, 50);

        JLabel endDate = new JLabel("Fecha Fin");
        constraints.gridx = 6;
        constraints.gridy = 0;
        filterPanel.add(endDate, constraints);

        endDateField = new JTextField(8);
        constraints.gridx = 6;
        constraints.gridy = 1;
        filterPanel.add(endDateField, constraints);

        constraints.insets = new Insets(2, -50, 5, 50);

        openCalendarEnd = new JButton();
        openCalendarEnd.addActionListener(listener);
        openCalendarEnd.setActionCommand(Events.ACTIVAR_CALENDARIO_END.name());
        openCalendarEnd.setIcon(icon);
        openCalendarEnd.setBorderPainted(false);
        openCalendarEnd.setFocusable(false);
        openCalendarEnd.setBackground(new Color(238, 238, 238));
        constraints.gridx = 7;
        constraints.gridy = 1;
        filterPanel.add(openCalendarEnd, constraints);

        constraints.insets = new Insets(2, 0, 5, 50);

        JButton search = new JButton("Buscar");
        search.setBackground(new Color(95, 213, 213, 255));
        search.addActionListener(listener);
        search.setActionCommand(Events.SEARCH_PREALERTA.name());
        search.setBorderPainted(false);
        search.setFocusable(false);
        search.setForeground(Color.WHITE);
        constraints.gridx = 8;
        constraints.gridy = 1;
        filterPanel.add(search, constraints);

        JButton showAll = new JButton("Mostrar Todo");
        showAll.setBackground(new Color(95, 213, 213, 255));
        showAll.addActionListener(listener);
        showAll.setActionCommand(Events.SHOW_ALL_PREALERTAS.name());
        showAll.setBorderPainted(false);
        showAll.setFocusable(false);
        showAll.setForeground(Color.WHITE);
        constraints.gridx = 9;
        constraints.gridy = 1;
        filterPanel.add(showAll, constraints);

        add(filterPanel);

        prealertasTable = new JTable();
        prealertasTable.getTableHeader().setReorderingAllowed(false);
        prealertasTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(prealertasTable);

        add(scrollPane);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Agregar");
        addButton.setBackground(new Color(95, 213, 213, 255));
        addButton.addActionListener(listener);
        addButton.setActionCommand(Events.SHOW_CREATE_PREALERTA.name());
        addButton.setBorderPainted(false);
        addButton.setFocusable(false);
        addButton.setForeground(Color.WHITE);

        JButton editButton = new JButton("Actualizar");
        editButton.setBackground(new Color(95, 213, 213, 255));
        editButton.addActionListener(listener);
        editButton.setActionCommand(Events.SHOW_UPDATE_PREALERTA.name());
        editButton.setBorderPainted(false);
        editButton.setFocusable(false);
        editButton.setForeground(Color.WHITE);

        JButton deleteButton = new JButton("Eliminar");
        deleteButton.setBackground(new Color(95, 213, 213, 255));
        deleteButton.addActionListener(listener);
        deleteButton.setActionCommand(Events.SHOW_DELETE_PREALERTA.name());
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusable(false);
        deleteButton.setForeground(Color.WHITE);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel);
    }

    public void clearFields(){
        nameField.setText("");
        guideField.setText("");
        startDateField.setText("");
        endDateField.setText("");
    }

    public String verifyDataSearchName(){
        return nameField.getText().trim();
    }

    public String verifyDataSearchGuide(){
        return guideField.getText().trim();
    }

    public String verifyDataSearchStartDate(){
        return startDateField.getText().trim();
    }

    public String verifyDataSearchEndDate(){
        return endDateField.getText().trim();
    }

    public void setDateFieldStart(String date){
        startDateField.setText(date);
        repaint();
    }

    public void setTableModel(DefaultTableModel model){
        prealertasTable.setModel(model);
    }

    public void setDateFieldEnd(String date){
        endDateField.setText(date);
        repaint();
    }

    public JTable getPrealertasTable() {
        return prealertasTable;
    }
}
