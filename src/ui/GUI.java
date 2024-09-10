package ui;

import ui.Assignment.AssignmentPanel;
import ui.Assignment.DeleteEquipmentJDialog;
import ui.Collection.CollectionPanel;
import ui.EquipmentType.CreateEquipmentType;
import ui.EquipmentType.DeleteEquipmentType;
import ui.EquipmentType.PanelTipoEquipo;
import ui.Prealerta.CreatePrealertaJDialog;
import ui.Prealerta.DeletePrealertaJDialog;
import ui.Prealerta.PanelPrealerta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private PanelPrealerta prealertaPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private DeletePrealertaJDialog deleteJDialog;
    private CreatePrealertaJDialog createPrealertaJDialog;
    private PanelTipoEquipo panelTipoEquipo;
    private CreateEquipmentType createEquipmentType;
    private DeleteEquipmentType deleteEquipmentType;
    private AssignmentPanel assignmentPanel;
    private CollectionPanel collectionPanel;
    private DeleteEquipmentJDialog deleteEquipmentJDialog;

    public GUI(ActionListener listener) {
        // Configuracion del frame
        setTitle("PREALERTAS");
        setSize(1050, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        deleteJDialog = new DeletePrealertaJDialog(listener);
        deleteEquipmentType = new DeleteEquipmentType(listener);
        createPrealertaJDialog = new CreatePrealertaJDialog(listener);
        createEquipmentType = new CreateEquipmentType(listener);
        deleteEquipmentJDialog = new DeleteEquipmentJDialog(listener);


        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(1, 3));

        // Botones del menú
        JButton btnPrealerta = new JButton("Prealertas");
        ImageIcon iconPrealerta = new ImageIcon("src/resources/prealerta.png");
        btnPrealerta.setIcon(iconPrealerta);
        btnPrealerta.setFocusable(false);

        JButton btnTipos = new JButton("Tipos de Equipo");
        btnTipos.setFocusable(false);
        ImageIcon iconEquipmentType = new ImageIcon("src/resources/tiposEquipo.png");
        btnTipos.setIcon(iconEquipmentType);

        JButton btnAsignacion = new JButton("Asignacion");
        btnAsignacion.setFocusable(false);
        ImageIcon iconAsignacion = new ImageIcon("src/resources/agregacion.png");
        btnAsignacion.setIcon(iconAsignacion);

        JButton btnRecogida = new JButton("Recogida");
        btnRecogida.setFocusable(false);
        ImageIcon iconCollection = new ImageIcon("src/resources/recoger.png");
        btnRecogida.setIcon(iconCollection);

        // Agregar los botones al panel del menú
        menuPanel.add(btnPrealerta);
        menuPanel.add(btnTipos);
        menuPanel.add(btnAsignacion);
        menuPanel.add(btnRecogida);


        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // Crear y agregar el panel principal
        prealertaPanel = new PanelPrealerta(listener);
        panelTipoEquipo = new PanelTipoEquipo(listener);
        assignmentPanel = new AssignmentPanel(listener);
        collectionPanel = new CollectionPanel(listener);

        contentPanel.add(prealertaPanel, "Prealertas");
        contentPanel.add(panelTipoEquipo, "Tipos de Equipo");
        contentPanel.add(assignmentPanel, "Asignacion");
        contentPanel.add(collectionPanel, "Recogida");

        btnPrealerta.addActionListener(e -> cardLayout.show(contentPanel, "Prealertas"));
        btnTipos.addActionListener(e -> cardLayout.show(contentPanel, "Tipos de Equipo"));
        btnAsignacion.addActionListener(e -> cardLayout.show(contentPanel, "Asignacion"));
        btnRecogida.addActionListener(e -> cardLayout.show(contentPanel, "Recogida"));

        add(menuPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public PanelPrealerta getPrealertaPanel() {
        return prealertaPanel;
    }

    public String verifyDataPanelSearchName(){
        return prealertaPanel.verifyDataSearchName();
    }

    public String verifyDataPanelSearchGuide(){
        return prealertaPanel.verifyDataSearchGuide();
    }

    public String verifyDataPanelSearchStartDate(){
        return prealertaPanel.verifyDataSearchStartDate();
    }

    public String verifyDataPanelSearchEndDate(){
        return prealertaPanel.verifyDataSearchEndDate();
    }

    public void clearFieldsSearchPrealertaPanel(){
        prealertaPanel.clearFields();
    }

    public DeletePrealertaJDialog getDeleteJDialog() {
        return deleteJDialog;
    }

    public CreatePrealertaJDialog getCreatePrealertaJDialog() {
        return createPrealertaJDialog;
    }

    public PanelTipoEquipo getPanelTipoEquipo() {
        return panelTipoEquipo;
    }

    public CreateEquipmentType getCreateEquipmentType() {
        return createEquipmentType;
    }

    public DeleteEquipmentType getDeleteEquipmentType() {
        return deleteEquipmentType;
    }

    public AssignmentPanel getAssignmentPanel() {
        return assignmentPanel;
    }

    public CollectionPanel getCollectionPanel() {
        return collectionPanel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public DeleteEquipmentJDialog getDeleteEquipmentJDialog() {
        return deleteEquipmentJDialog;
    }
}