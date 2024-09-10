package ui.EquipmentType;

import controller.Events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CreateEquipmentType  extends JDialog {

    private JTextField inputNameEquipmentType;
    private JTextField inputSerialEquipmentType;
    private JTextField inputMACEquipmentType;

    public CreateEquipmentType(ActionListener listener) {
        setTitle("Crear Tipo de Equipo");
        setLayout(new GridBagLayout());
        setSize(400, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents(listener);
    }

    private void initComponents(ActionListener listener){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 0, 10, 0);

        JLabel textName = new JLabel("Ingrese el nombre del tipo de equipo");
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(textName,constraints);

        constraints.insets = new Insets(0, 0, 10, 100);

        inputNameEquipmentType = new JTextField(10);
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(inputNameEquipmentType,constraints);

        JLabel textSerial = new JLabel("Ingrese el largo del serial de este tipo de equipo");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(textSerial,constraints);

        constraints.insets = new Insets(0, 0, 10, 100);

        inputSerialEquipmentType = new JTextField(10);
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(inputSerialEquipmentType,constraints);

        JLabel textMAC = new JLabel("Ingrese el largo del MAC de este tipo de equipo");
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(textMAC,constraints);

        constraints.insets = new Insets(0, 0, 10, 100);

        inputMACEquipmentType = new JTextField(10);
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(inputMACEquipmentType,constraints);


        constraints.insets = new Insets(0, 0, 0, 0);

        JButton okButton = new JButton("Aceptar");
        okButton.addActionListener(listener);
        okButton.setActionCommand(Events.CREATE_EQUIPMENT_TYPE.name());
        constraints.gridx = 0;
        constraints.gridy = 6;
        add(okButton,constraints);
    }

    public JTextField getInputNameEquipmentType() {
        return inputNameEquipmentType;
    }

    public JTextField getInputSerialEquipmentType() {
        return inputSerialEquipmentType;
    }

    public JTextField getInputMACEquipmentType() {
        return inputMACEquipmentType;
    }
}
