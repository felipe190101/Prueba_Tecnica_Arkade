package ui.Assignment;

import controller.Events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DeleteEquipmentJDialog  extends JDialog {

    private JTextField inputField;

    public DeleteEquipmentJDialog(ActionListener listener) {
        setTitle("Eliminar");
        setLayout(new GridBagLayout());
        setSize(300, 150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents(listener);
    }

    private void initComponents(ActionListener listener){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 0, 10, 0);
        JLabel text = new JLabel("Ingrese el id del equipo que desea eliminar:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(text,constraints);

        constraints.insets = new Insets(0, 0, 10, 100);

        inputField = new JTextField(10);
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(inputField,constraints);

        constraints.insets = new Insets(0, 0, 0, 0);

        JButton okButton = new JButton("Aceptar");
        okButton.addActionListener(listener);
        okButton.setActionCommand(Events.ACCEPT_DELETE_EQUIPMENT.name());
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(okButton,constraints);
    }

    public JTextField getInputField() {
        return inputField;
    }
}
