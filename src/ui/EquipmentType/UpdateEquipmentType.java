package ui.EquipmentType;

import controller.Events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UpdateEquipmentType extends JDialog{

    private JTextField inputSizeSerial;
    private JTextField inputSizeMAC;
    private JComboBox<String> typeEquipmentComboBox;

    public UpdateEquipmentType(ActionListener listener) {
        setTitle("Editar Tipo de Equipo");
        setLayout(new GridBagLayout());
        setSize(450, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents(listener);
    }

    private void initComponents(ActionListener listener){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 0, 10, 0);

        JLabel textId = new JLabel("Seleccione el tipo de equipo a editar");
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(textId,constraints);

        typeEquipmentComboBox = new JComboBox<>();
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(typeEquipmentComboBox,constraints);

        JLabel textName = new JLabel("Ingrese el nuevo tamaño del serial del tipo de equipo:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(textName,constraints);

        constraints.insets = new Insets(0, 0, 10, 100);

        inputSizeSerial = new JTextField(10);
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(inputSizeSerial,constraints);

        JLabel textGuide = new JLabel("Ingrese el nuevo tamaño del MAC del tipo de equipo:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(textGuide,constraints);

        constraints.insets = new Insets(0, 0, 10, 100);

        inputSizeMAC = new JTextField(10);
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(inputSizeMAC,constraints);


        constraints.insets = new Insets(0, 0, 0, 0);

        JButton okButton = new JButton("Aceptar");
        okButton.addActionListener(listener);
        okButton.setActionCommand(Events.UPDATE_EQUIPMENT_TYPE.name());
        constraints.gridx = 0;
        constraints.gridy = 6;
        add(okButton,constraints);
    }

    public JTextField getInputSizeSerial() {
        return inputSizeSerial;
    }

    public JTextField getInputSizeMAC() {
        return inputSizeMAC;
    }

    public JComboBox<String> getTypeEquipmentComboBox() {
        return typeEquipmentComboBox;
    }
}
