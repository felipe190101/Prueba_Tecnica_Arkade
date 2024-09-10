package ui.Prealerta;

import controller.Events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CreatePrealertaJDialog extends JDialog {

    private JTextField inputNamePrealerta;
    private JTextField inputGuidePrealerta;

    public CreatePrealertaJDialog(ActionListener listener) {
        setTitle("Crear Prealerta");
        setLayout(new GridBagLayout());
        setSize(350, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents(listener);
    }

    private void initComponents(ActionListener listener){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 0, 10, 0);
        
        JLabel textName = new JLabel("Ingrese el nombre de la prealerta:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(textName,constraints);

        constraints.insets = new Insets(0, 0, 10, 100);

        inputNamePrealerta = new JTextField(10);
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(inputNamePrealerta,constraints);

        JLabel textGuide = new JLabel("Ingrese la guia de la prealerta:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(textGuide,constraints);

        constraints.insets = new Insets(0, 0, 10, 100);

        inputGuidePrealerta = new JTextField(10);
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(inputGuidePrealerta,constraints);


        constraints.insets = new Insets(0, 0, 0, 0);

        JButton okButton = new JButton("Aceptar");
        okButton.addActionListener(listener);
        okButton.setActionCommand(Events.ACCEPT_CREATE_PREALERTA.name());
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(okButton,constraints);
    }

    public JTextField getInputNamePrealerta() {
        return inputNamePrealerta;
    }

    public JTextField getInputGuidePrealerta() {
        return inputGuidePrealerta;
    }
}
