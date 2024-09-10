package ui.Assignment;

import controller.Events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddEquipmentToPrealerta extends JDialog {

    private JTextField inputSerialEquipment;
    private JTextField inputMACEquipment;
    private JTextField inputObservations;
    private JComboBox<String> addEquipmentTypeComboBox;

    public AddEquipmentToPrealerta(ActionListener listener) {
        setTitle("Agregar Equipo");
        setLayout(new GridBagLayout());
        setSize(300, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents(listener);
    }

    private void initComponents(ActionListener listener){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 0, 10, 0);

        JLabel textSerial = new JLabel("Ingrese el serial del equipo:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(textSerial,constraints);

        inputSerialEquipment = new JTextField(30);
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(inputSerialEquipment,constraints);

        JLabel textGuide = new JLabel("Ingrese el MAC del equipo:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(textGuide,constraints);


        inputMACEquipment = new JTextField(30);
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(inputMACEquipment,constraints);

        JLabel textObservations = new JLabel("Ingrese las observaciones del equipo:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(textObservations,constraints);

        inputObservations = new JTextField(200);
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(inputObservations,constraints);

        JLabel textAddEquipmentType = new JLabel("Seleccione el tipo de equipo:");
        constraints.gridx = 0;
        constraints.gridy = 6;
        add(textAddEquipmentType,constraints);

        addEquipmentTypeComboBox = new JComboBox<>();
        constraints.gridx = 0;
        constraints.gridy = 7;
        add(addEquipmentTypeComboBox,constraints);

        constraints.insets = new Insets(10, 0, 10, 0);

        JButton okButton = new JButton("Aceptar");
        okButton.addActionListener(listener);
        okButton.setActionCommand(Events.ADD_EQUIPMENT_TO_PREALERTA.name());
        constraints.gridx = 0;
        constraints.gridy = 8;
        add(okButton,constraints);
    }

    public JComboBox<String> getAddEquipmentTypeComboBox() {
        return addEquipmentTypeComboBox;
    }

    public JTextField getInputSerialEquipment() {
        return inputSerialEquipment;
    }

    public JTextField getInputMACEquipment() {
        return inputMACEquipment;
    }

    public JTextField getInputObservations() {
        return inputObservations;
    }
}
