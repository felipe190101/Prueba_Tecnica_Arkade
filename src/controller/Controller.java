package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.*;
import models.Equipo;
import models.Prealerta;
import models.TipoEquipo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ui.*;
import ui.Assignment.AddEquipmentToPrealerta;
import ui.EquipmentType.UpdateEquipmentType;
import ui.Prealerta.UpdatePrealerta;
import ui.utilities.CalendarDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller implements ActionListener{

    private UtilitiesDAO utilitiesDAO;
    private ConexionBD conexionBD;
    private GUI gui;
    private CalendarDialog calendarDialog;
    private PrealertaDAO prealertaDAO;
    private String selectedValueComboBoxUpdatePrealerta;
    private String selectedValueComboBoxUpdateEquipmentType;
    private int getIdUpdatePrealerta;
    private int getIdUpdateEquipmentType;
    private UpdatePrealerta updatePrealerta;
    private TipoEquipoDAO tipoEquipoDAO;
    private UpdateEquipmentType updateEquipmentType;
    private String selectedValueComboBoxEquipmentPrealerta;
    private EquipoDAO equipoDAO;
    private AddEquipmentToPrealerta addEquipmentToPrealerta;
    private String selectedValueComboBoxAddEquipmentToPrealerta;
    private int idEquipmentTypeToAddEquipment;
    private int idPrealertaSelectedAssignment;
    private String selectedValueComboBoxEquipmentPrealertaCollection;
    private int idPrealertaSelectedCollection;

    public Controller() throws SQLException {
        conexionBD = new ConexionBD();
        prealertaDAO = new PrealertaDAO(conexionBD);
        tipoEquipoDAO = new TipoEquipoDAO(conexionBD);
        equipoDAO = new EquipoDAO(conexionBD);
        this.gui = new GUI(this);
        createTablePrealerta("Prealerta");
        createTableEquipmentType("TipoEquipo", gui.getPanelTipoEquipo().getEquipmentTypeTable(),tipoEquipoDAO.obtenerTiposEquipos());
        fillComboBoxAssignment();
        fillComboBoxCollection();
    }

    public void createTablePrealerta(String nameTable) throws SQLException {
        utilitiesDAO = new UtilitiesDAO(conexionBD = new ConexionBD());
        List<Prealerta> prealertas = prealertaDAO.obtenerPrealertas();

        String[] headers = utilitiesDAO.obtenerNombresDeColumnas(nameTable).toArray(new String[0]);

        Object [][] dataTable = new Object[prealertas.size()][headers.length];
        for (int i = 0; i < prealertas.size(); i++) {
            Prealerta prealerta = prealertas.get(i);
            dataTable[i][0] = prealerta.getId();
            dataTable[i][1] = prealerta.getNombre();
            dataTable[i][2] = prealerta.getGuia();
            dataTable[i][3] = prealerta.getFechaCreacion();
            if (prealerta.getStatus() == 0){
                dataTable[i][4] = "Pendiente de recogida";
            }else{
                dataTable[i][4] = "Finalizado";
            }
        }

        DefaultTableModel model = new DefaultTableModel(dataTable,headers);
        gui.getPrealertaPanel().setTableModel(model);
    }

    public void createTableEquipmentType(String nameTable, JTable table, List<TipoEquipo> data) throws SQLException {

        utilitiesDAO = new UtilitiesDAO(conexionBD = new ConexionBD());
        List<TipoEquipo> equipmentTypeList = tipoEquipoDAO.obtenerTiposEquipos();

        String[] headers = utilitiesDAO.obtenerNombresDeColumnas(nameTable).toArray(new String[0]);

        Object [][] dataTable = new Object[equipmentTypeList.size()][headers.length];
        for (int i = 0; i < equipmentTypeList.size(); i++) {
            TipoEquipo tipoEquipo = equipmentTypeList.get(i);
            dataTable[i][0] = tipoEquipo.getId();
            dataTable[i][1] = tipoEquipo.getNombre();
            dataTable[i][2] = tipoEquipo.getLargoSerial();
            dataTable[i][3] = tipoEquipo.getLargoMac();
        }

        DefaultTableModel model = new DefaultTableModel(dataTable,headers);
        gui.getPanelTipoEquipo().setTableModel(model);
    }

    private void updateTablePrealerta(List<Prealerta> listPrealertas) {
        DefaultTableModel model = (DefaultTableModel) gui.getPrealertaPanel().getPrealertasTable().getModel();
        model.setRowCount(0);

        for (Prealerta prealerta : listPrealertas) {
            String statusText;
            if (prealerta.getStatus() == 0) {
                statusText = "Pendiente de recogida";
            } else {
                statusText = "Finalizado";
            }
            model.addRow(new Object[]{
                    prealerta.getId(),
                    prealerta.getNombre(),
                    prealerta.getGuia(),
                    prealerta.getFechaCreacion(),
                    statusText
            });
        }
    }

    private void activateCalendar(boolean option){
        this.calendarDialog = new CalendarDialog(this);
        calendarDialog.setOption(option);
        calendarDialog.setVisible(true);
    }

    private void extractDateStart(){
        gui.getPrealertaPanel().setDateFieldStart(extractDateField(calendarDialog.extractDate()));
        convertToLocalDateTime(calendarDialog.extractDate()); // Para guardar en base de datos
        calendarDialog.dispose();
    }

    private void extractDateEnd(){
        gui.getPrealertaPanel().setDateFieldEnd(extractDateField(calendarDialog.extractDate()));
        convertToLocalDateTime(calendarDialog.extractDate()); // Para guardar en base de datos
        calendarDialog.dispose();
    }

    public static String extractDateField(Date dateToConvert) {
        // Convertir Date a LocalDate
        LocalDate localDate = dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        // Devolver la fecha en formato yyyy-MM-dd
        return localDate.toString();
    }

    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public boolean isValidDateFormat(String dateStr) {
        String regex = "\\d{4}-\\d{2}-\\d{2}";
        return dateStr.matches(regex);
    }

    private void searchPrealertaByDate(String startDate, String endDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDateSearch;
        Date endDateSearch;

        try {
            if (isValidDateFormat(startDate) && isValidDateFormat(endDate)){
                startDateSearch = dateFormat.parse(startDate);
                endDateSearch = dateFormat.parse(endDate);
                List<Prealerta> listPrealertasByDate = prealertaDAO.buscarPorRangoDeFechas(startDateSearch, endDateSearch);
                if (listPrealertasByDate.isEmpty()){
                    JOptionPane.showMessageDialog(null,"No existen prealertas en este rango de fechas", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    listPrealertasByDate.forEach(System.out::println);
                    updateTablePrealerta(listPrealertasByDate);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Ingrese formatos de fecha validos (yyyy-mm-dd)", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private  void searchPrealertaByName(String name) {
        try {
            List<Prealerta> prealertas = new ArrayList<>();
            Prealerta prealerta = prealertaDAO.obtenerPrealertaPorNombre(name);
            if (prealerta != null){
                prealertas.add(prealerta);
                prealertas.forEach(System.out::println);
                updateTablePrealerta(prealertas);
            }else{
                JOptionPane.showMessageDialog(null,"La prealerta ingresada no existe", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void searchPrealertaByGuide(String guide) {
        try {
            List<Prealerta> prealertas = new ArrayList<>();
            Prealerta prealerta = prealertaDAO.obtenerPrealertaPorGuia(guide);
            if (prealerta != null){
                prealertas.add(prealerta);
                prealertas.forEach(System.out::println);
                updateTablePrealerta(prealertas);
            }else{
                JOptionPane.showMessageDialog(null,"La prealerta ingresada no existe", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void searchPrealertaByNameAndGuide(String name,String guide) {
        try {
            List<Prealerta> prealertas = new ArrayList<>();
            Prealerta prealerta = prealertaDAO.getPrealertaByNameAndGuide(name,guide);
            if (prealerta != null){
                prealertas.add(prealerta);
                prealertas.forEach(System.out::println);
                updateTablePrealerta(prealertas);
            }else{
                JOptionPane.showMessageDialog(null,"La prealerta ingresada no existe", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void searchPrealertaByNameAndDate(String name,String startDate, String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDateSearch;
        Date endDateSearch;
        try {
            startDateSearch = dateFormat.parse(startDate);
            endDateSearch = dateFormat.parse(endDate);
            List<Prealerta> prealertas = new ArrayList<>();
            Prealerta prealerta = prealertaDAO.searchByNameAndDate(name,startDateSearch,endDateSearch);
            if (prealerta != null){
                prealertas.add(prealerta);
                prealertas.forEach(System.out::println);
                updateTablePrealerta(prealertas);
            }else{
                JOptionPane.showMessageDialog(null,"La prealerta ingresada no existe", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void searchPrealertaByGuideAndDate(String guide,String startDate, String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDateSearch;
        Date endDateSearch;
        try {
            startDateSearch = dateFormat.parse(startDate);
            endDateSearch = dateFormat.parse(endDate);
            List<Prealerta> prealertas = new ArrayList<>();
            Prealerta prealerta = prealertaDAO.searchByGuideAndDate(guide,startDateSearch,endDateSearch);
            if (prealerta != null){
                prealertas.add(prealerta);
                prealertas.forEach(System.out::println);
                updateTablePrealerta(prealertas);
            }else{
                JOptionPane.showMessageDialog(null,"La prealerta ingresada no existe", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void searchPrealerta(){
        String startDate = gui.verifyDataPanelSearchStartDate();
        String endDate = gui.verifyDataPanelSearchEndDate();
        String name = gui.verifyDataPanelSearchName();
        String guide = gui.verifyDataPanelSearchGuide();

        boolean isStartDateEmpty = startDate.isEmpty();
        boolean isEndDateEmpty = endDate.isEmpty();
        boolean isNameEmpty = name.isEmpty();
        boolean isGuideEmpty = guide.isEmpty();

        if ((isStartDateEmpty && !isEndDateEmpty) || (!isStartDateEmpty && isEndDateEmpty)) {
            JOptionPane.showMessageDialog(null, "Debe ingresar ambas fechas para realizar la búsqueda por rango de fechas.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (isNameEmpty && isGuideEmpty && !isStartDateEmpty) {
            searchPrealertaByDate(startDate,endDate);
        } else if (!isNameEmpty && isGuideEmpty && !isStartDateEmpty) {
            searchPrealertaByNameAndDate(name,startDate,endDate);
        } else if (isNameEmpty && !isGuideEmpty && !isStartDateEmpty) {
            searchPrealertaByGuideAndDate(guide,startDate,endDate);
        } else if (!isNameEmpty && isGuideEmpty) {
            searchPrealertaByName(name);
        } else if (isNameEmpty && !isGuideEmpty) {
            searchPrealertaByGuide(guide);
        } else if (!isNameEmpty) {
            searchPrealertaByNameAndGuide(name,guide);
        }
    }

    private void clearFieldsPrealerta(){
        gui.clearFieldsSearchPrealertaPanel();
        gui.getDeleteJDialog().getInputField().setText("");
        gui.getCreatePrealertaJDialog().getInputNamePrealerta().setText("");
        gui.getCreatePrealertaJDialog().getInputGuidePrealerta().setText("");
    }

    private void showDeletePrealerta(){
        gui.getDeleteJDialog().setVisible(true);
    }

    private void showDeleteEquipmentType(){
        gui.getDeleteEquipmentType().setVisible(true);
    }

    private void showDeleteEquipment(){
        gui.getDeleteEquipmentJDialog().setVisible(true);
    }

    private void showCreatePrealerta(){
        gui.getCreatePrealertaJDialog().setVisible(true);
    }

    private void showUpdatePrealerta() {
        updatePrealerta = new UpdatePrealerta(this);
        updatePrealerta.setVisible(true);
        clearComboBoxUpdatePrealerta();
        updatePrealerta.getPrealertaComboBox().addItem("Selecciona una prealerta");
        try {
            for (Prealerta prealerta : prealertaDAO.obtenerPrealertas()) {
                updatePrealerta.getPrealertaComboBox().addItem(prealerta.getNombre());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        updatePrealerta.getPrealertaComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedValueComboBoxUpdatePrealerta = (String) updatePrealerta.getPrealertaComboBox().getSelectedItem();
                try {
                    if (!selectedValueComboBoxUpdatePrealerta.equalsIgnoreCase("Selecciona una prealerta")){
                        getIdUpdatePrealerta = prealertaDAO.obtenerPrealertaPorNombre(selectedValueComboBoxUpdatePrealerta).getId();
                        String namePrealerta = prealertaDAO.obtenerPrealertaPorId(getIdUpdatePrealerta).getNombre();
                        String guidePrealerta = prealertaDAO.obtenerPrealertaPorId(getIdUpdatePrealerta).getGuia();
                        updatePrealerta.getInputNamePrealerta().setText(namePrealerta);
                        updatePrealerta.getInputGuidePrealerta().setText(guidePrealerta);
                    }else{
                        updatePrealerta.getInputNamePrealerta().setText("");
                        updatePrealerta.getInputGuidePrealerta().setText("");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void fillComboBoxAssignment(){
        gui.getAssignmentPanel().getPrealertaComboBox().removeAllItems();
        try {
            for (Prealerta prealerta : prealertaDAO.obtenerPrealertas()) {
                gui.getAssignmentPanel().getPrealertaComboBox().addItem(prealerta.getNombre());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    private void fillComboBoxCollection(){
        gui.getCollectionPanel().getPrealertaComboBox().removeAllItems();
        try {
            for (Prealerta prealerta : prealertaDAO.obtenerPrealertas()){
                if(equipoDAO.obtenerTodosLosEquiposToPrealerta(prealerta.getId()).isEmpty() || prealerta.getStatus() == 1){
                    continue;
                }else{
                    gui.getCollectionPanel().getPrealertaComboBox().addItem(prealerta.getNombre());
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void showUpdateEquipmentType() {
        updateEquipmentType = new UpdateEquipmentType(this);
        updateEquipmentType.setVisible(true);
        clearComboBoxUpdateEquipmentType();
        updateEquipmentType.getTypeEquipmentComboBox().addItem("Selecciona un tipo de equipo");
        try {
            for (TipoEquipo tipoEquipo : tipoEquipoDAO.obtenerTiposEquipos()) {
                updateEquipmentType.getTypeEquipmentComboBox().addItem(tipoEquipo.getNombre());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        updateEquipmentType.getTypeEquipmentComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedValueComboBoxUpdateEquipmentType = (String) updateEquipmentType.getTypeEquipmentComboBox().getSelectedItem();
                try {
                    if (!selectedValueComboBoxUpdateEquipmentType.equalsIgnoreCase("Selecciona un tipo de equipo")){
                        getIdUpdateEquipmentType = tipoEquipoDAO.obtenerEquipmentTypeByName(selectedValueComboBoxUpdateEquipmentType).getId();
                        int sizeSerial = tipoEquipoDAO.obtenerTipoEquipoPorId(getIdUpdateEquipmentType).getLargoSerial();
                        int sizeMAC = tipoEquipoDAO.obtenerTipoEquipoPorId(getIdUpdateEquipmentType).getLargoSerial();
                        updateEquipmentType.getInputSizeSerial().setText(String.valueOf(sizeSerial));
                        updateEquipmentType.getInputSizeMAC().setText(String.valueOf(sizeMAC));
                    }else{
                        updateEquipmentType.getInputSizeSerial().setText("");
                        updateEquipmentType.getInputSizeMAC().setText("");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private String getIdDeletePrealerta(){
        return gui.getDeleteJDialog().getInputField().getText();
    }

    private String getIdDeleteEquipmentType(){
        return gui.getDeleteEquipmentType().getInputField().getText();
    }

    public boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void deletePrealerta(){
        try {
            if(getIdDeletePrealerta().equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(null,"Ingrese un id", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else if (!isNumber(getIdDeletePrealerta())){
                JOptionPane.showMessageDialog(null,"Debe ingresar un valor numerico", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else if(prealertaDAO.obtenerPrealertaPorId(Integer.parseInt(getIdDeletePrealerta())) == null){
                JOptionPane.showMessageDialog(null,"El id ingresado no existe", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else if(!equipoDAO.obtenerTodosLosEquiposToPrealerta(Integer.parseInt(getIdDeletePrealerta())).isEmpty()){
                JOptionPane.showMessageDialog(null,"Existen equipos asociados a esta prealerta. Debe recogerlos primero", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else{
                prealertaDAO.eliminarPrealerta(Integer.parseInt(getIdDeletePrealerta()));
                JOptionPane.showMessageDialog(null,"Prealerta borrada", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                gui.getDeleteJDialog().dispose();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteEquipment(){
        try {
            if (idPrealertaSelectedAssignment == 0) {
                JOptionPane.showMessageDialog(null, "Primero debe buscar una Prealerta", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                gui.getDeleteEquipmentJDialog().dispose();
            }else if (gui.getDeleteEquipmentJDialog().getInputField().getText().equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(null,"Ingrese un id", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else if(!isNumber(gui.getDeleteEquipmentJDialog().getInputField().getText())){
                JOptionPane.showMessageDialog(null,"Debe ingresar un valor numerico", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else if(equipoDAO.obtenerEquipoPorId(Integer.parseInt(gui.getDeleteEquipmentJDialog().getInputField().getText())) == null){
                JOptionPane.showMessageDialog(null,"El id ingresado no existe", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else{
                equipoDAO.eliminarEquipo(Integer.parseInt(gui.getDeleteEquipmentJDialog().getInputField().getText()));
                JOptionPane.showMessageDialog(null,"Equipo borrado", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                gui.getDeleteEquipmentJDialog().dispose();
                showAllEquipmentByPrealerta();
                gui.getDeleteEquipmentJDialog().getInputField().setText("");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void deleteEquipmentType(){
        try {
            if(getIdDeleteEquipmentType().equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(null,"Ingrese un id", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else if (!isNumber(getIdDeleteEquipmentType())) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un valor numerico", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else if (tipoEquipoDAO.obtenerTipoEquipoPorId(Integer.parseInt(getIdDeleteEquipmentType())) == null){
                JOptionPane.showMessageDialog(null,"El id ingresado no existe", "Error", JOptionPane.ERROR_MESSAGE);
            }else if(!equipoDAO.obtenerTodosLosEquiposByEquipmentType(Integer.parseInt(getIdDeleteEquipmentType())).isEmpty()){
                JOptionPane.showMessageDialog(null,"Existen equipos asociados a este tipo de equipo", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else{
                tipoEquipoDAO.eliminarTipoEquipo(Integer.parseInt(getIdDeleteEquipmentType()));
                gui.getDeleteEquipmentType().dispose();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createPrealerta(){
        String name = gui.getCreatePrealertaJDialog().getInputNamePrealerta().getText();
        String guide = gui.getCreatePrealertaJDialog().getInputGuidePrealerta().getText();
        Date currentDate = new Date();
        try {
            if (name.equalsIgnoreCase("")|| guide.equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(null,"Complete todos los campos para realizar el registro", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else if (prealertaDAO.obtenerPrealertaPorNombre(name) != null || prealertaDAO.obtenerPrealertaPorGuia(guide) != null){
                JOptionPane.showMessageDialog(null,"El nombre o la guia de la prealerta ya estan registrados", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                prealertaDAO.crearPrealerta(name,guide,currentDate, 0);
                gui.getCreatePrealertaJDialog().dispose();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updatePrealerta(){
        String name = updatePrealerta.getInputNamePrealerta().getText();
        String guide = updatePrealerta.getInputGuidePrealerta().getText();

        try {
            if (name.equalsIgnoreCase("") || guide.equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(null,"Complete todos los campos para realizar la actualizacion", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else if (prealertaDAO.obtenerPrealertaPorNombre(name) != null && prealertaDAO.obtenerPrealertaPorGuia(guide) != null){
                JOptionPane.showMessageDialog(null,"El nombre o la guia de la prealerta ya estan registrados", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                prealertaDAO.actualizarPrealerta(name,guide,getIdUpdatePrealerta);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        updatePrealerta.dispose();
    }

    private void updateEquipmentType(){
        String sizeSerialText = updateEquipmentType.getInputSizeSerial().getText();
        String sizeMACText = updateEquipmentType.getInputSizeMAC().getText();

        try {
            if (sizeSerialText.equalsIgnoreCase("") || sizeMACText.equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(null,"Complete todos los campos para realizar la actualizacion", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else if(!isNumber(sizeSerialText) || !isNumber(sizeMACText)){
                JOptionPane.showMessageDialog(null,"El largo del serial y el MAC deben ser valores numericos", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else if(getIdUpdateEquipmentType == 0){
                JOptionPane.showMessageDialog(null,"Debe seleccionar un tipo de equipo", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else if(!equipoDAO.obtenerTodosLosEquiposByEquipmentType(getIdUpdateEquipmentType).isEmpty()){
                JOptionPane.showMessageDialog(null,"Existen equipos asociados a este tipo de equipo. No debe haber equipos asociados para realizar el cambio de tamaño del serial y el MAC", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else{
                tipoEquipoDAO.actualizarTipoEquipo(Integer.parseInt(sizeSerialText),Integer.parseInt(sizeMACText),getIdUpdateEquipmentType);
                updateEquipmentType.dispose();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAllPrealertas(){
        try {
            createTablePrealerta("Prealerta");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void clearComboBoxUpdatePrealerta(){
        updatePrealerta.getPrealertaComboBox().removeAllItems();
    }

    private void clearComboBoxUpdateEquipmentType(){
        updateEquipmentType.getTypeEquipmentComboBox().removeAllItems();
    }

    private void showCreateEquipmentType(){
        gui.getCreateEquipmentType().setVisible(true);
    }

    private void createEquipmentType(){
        String name = gui.getCreateEquipmentType().getInputNameEquipmentType().getText();
        String sizeSerialText = gui.getCreateEquipmentType().getInputSerialEquipmentType().getText();
        String sizeMACText = gui.getCreateEquipmentType().getInputMACEquipmentType().getText();

        try {
            if(name.equalsIgnoreCase("")|| sizeSerialText.equalsIgnoreCase("") || sizeMACText.equalsIgnoreCase("")){
                JOptionPane.showMessageDialog(null,"Complete todos los campos para realizar el registro", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (isNumber(sizeSerialText) && isNumber(sizeMACText)){
                int sizeSerial = Integer.parseInt(sizeSerialText);
                int sizeMAC = Integer.parseInt(sizeMACText);
                if (tipoEquipoDAO.obtenerEquipmentTypeByName(name) != null){
                    JOptionPane.showMessageDialog(null,"El tipo de equipo ya esta registrado", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    tipoEquipoDAO.crearTipoEquipo(name,sizeSerial,sizeMAC);
                    gui.getCreateEquipmentType().dispose();
                }
            }else{
                JOptionPane.showMessageDialog(null,"El tamaño del serial y el MAC deben ser valores numericos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAllEquipmentType(){
        try {
            createTableEquipmentType("TipoEquipo", gui.getPanelTipoEquipo().getEquipmentTypeTable(),tipoEquipoDAO.obtenerTiposEquipos());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void clearFieldsEquipmentType(){
        gui.getCreateEquipmentType().getInputNameEquipmentType().setText("");
        gui.getCreateEquipmentType().getInputSerialEquipmentType().setText("");
        gui.getCreateEquipmentType().getInputMACEquipmentType().setText("");
        gui.getDeleteEquipmentType().getInputField().setText("");
    }

    private void getEquipmentByPrealerta(){
        selectedValueComboBoxEquipmentPrealerta = (String) gui.getAssignmentPanel().getPrealertaComboBox().getSelectedItem();
        if (selectedValueComboBoxEquipmentPrealerta == null){
            JOptionPane.showMessageDialog(null,"No existen prealertas para asignación", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            try {
                idPrealertaSelectedAssignment = prealertaDAO.obtenerPrealertaPorNombre(selectedValueComboBoxEquipmentPrealerta).getId();
                List<Equipo> listEquipmentByPrealerta = equipoDAO.obtenerTodosLosEquiposToPrealerta(idPrealertaSelectedAssignment);
                createTableEquipmentByPrealerta("Equipo",listEquipmentByPrealerta);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void createTableEquipmentByPrealerta(String nameTable, List<Equipo> data) throws SQLException {

        utilitiesDAO = new UtilitiesDAO(conexionBD = new ConexionBD());

        String[] headers = utilitiesDAO.obtenerNombresDeColumnas(nameTable).toArray(new String[0]);

        Object [][] dataTable = new Object[data.size()][headers.length];
        for (int i = 0; i < data.size(); i++) {
            Equipo equipo = data.get(i);
            dataTable[i][0] = equipo.getId();
            dataTable[i][1] = equipo.getSerial();
            dataTable[i][2] = equipo.getMac();
            dataTable[i][3] = equipo.getObservaciones();
            if (equipo.getStatus() == 0){
                dataTable[i][4] = "Sin recoger";
            }else{
                dataTable[i][4] = "Recogido";
            }
            dataTable[i][5] = equipo.getTipoEquipo().getNombre();
            dataTable[i][6] = equipo.getPrealerta().getNombre();
        }

        DefaultTableModel model = new DefaultTableModel(dataTable,headers);
        gui.getAssignmentPanel().setTableModel(model);
    }

    private void createTableEquipmentByPrealertaCollection(String nameTable, List<Equipo> data) throws SQLException {

        utilitiesDAO = new UtilitiesDAO(conexionBD = new ConexionBD());

        String[] headers = utilitiesDAO.obtenerNombresDeColumnas(nameTable).toArray(new String[0]);

        Object [][] dataTable = new Object[data.size()][headers.length];
        for (int i = 0; i < data.size(); i++) {
            Equipo equipo = data.get(i);
            dataTable[i][0] = equipo.getId();
            dataTable[i][1] = equipo.getSerial();
            dataTable[i][2] = equipo.getMac();
            dataTable[i][3] = equipo.getObservaciones();
            if (equipo.getStatus() == 0){
                dataTable[i][4] = "Sin recoger";
            }else{
                dataTable[i][4] = "Recogido";
            }
            dataTable[i][5] = equipo.getTipoEquipo().getNombre();
            dataTable[i][6] = equipo.getPrealerta().getNombre();
        }

        DefaultTableModel model = new DefaultTableModel(dataTable,headers);
        gui.getCollectionPanel().setTableModel(model);
    }

    private void clearComboBoxSelectedEquipmentTypeToEquipment(){
        addEquipmentToPrealerta.getAddEquipmentTypeComboBox().removeAllItems();
    }

    private void showAddEquipmentToPrealerta() {
        try {
            if (idPrealertaSelectedAssignment == 0){
                JOptionPane.showMessageDialog(null,"Primero debe buscar una Prealerta", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
//                addEquipmentToPrealerta.dispose();
            }else{
                if (prealertaDAO.obtenerPrealertaPorId(idPrealertaSelectedAssignment).getStatus() == 1){
                    JOptionPane.showMessageDialog(null,"Esta prealerta ya finalizo. No se pueden asignar mas equipos", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    addEquipmentToPrealerta = new AddEquipmentToPrealerta(this);
                    addEquipmentToPrealerta.setVisible(true);
                    clearComboBoxSelectedEquipmentTypeToEquipment();
                    addEquipmentToPrealerta.getAddEquipmentTypeComboBox().addItem("Selecciona un tipo de equipo");

                    for (TipoEquipo tipoEquipo : tipoEquipoDAO.obtenerTiposEquipos()) {
                        addEquipmentToPrealerta.getAddEquipmentTypeComboBox().addItem(tipoEquipo.getNombre());
                    }

                    addEquipmentToPrealerta.getAddEquipmentTypeComboBox().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            selectedValueComboBoxAddEquipmentToPrealerta = (String) addEquipmentToPrealerta.getAddEquipmentTypeComboBox().getSelectedItem();
                            if (selectedValueComboBoxAddEquipmentToPrealerta == null || selectedValueComboBoxAddEquipmentToPrealerta.equalsIgnoreCase("Selecciona un tipo de equipo")){
                                JOptionPane.showMessageDialog(null,"Debe seleccionar un tipo de equipo", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                            }else{
                                try {
                                    idEquipmentTypeToAddEquipment = tipoEquipoDAO.obtenerEquipmentTypeByName(selectedValueComboBoxAddEquipmentToPrealerta).getId();
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                    });
                }
            }
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    private void addEquipmentToPrealerta(){
        String serial = addEquipmentToPrealerta.getInputSerialEquipment().getText();
        String MAC = addEquipmentToPrealerta.getInputMACEquipment().getText();
        String observations = addEquipmentToPrealerta.getInputObservations().getText();


        try {
            if (idPrealertaSelectedAssignment == 0){
                JOptionPane.showMessageDialog(null,"Primero debe buscar una Prealerta", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                addEquipmentToPrealerta.dispose();
            }else if(serial.equalsIgnoreCase("") || MAC.equalsIgnoreCase("") || idEquipmentTypeToAddEquipment == 0){
                JOptionPane.showMessageDialog(null,"Debe completar todos los campos", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else if(serial.length() != tipoEquipoDAO.obtenerTipoEquipoPorId(idEquipmentTypeToAddEquipment).getLargoSerial()){
                JOptionPane.showMessageDialog(null,"El tamaño del serial no es igual al definido en el tipo de equipo", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else if(MAC.length() != tipoEquipoDAO.obtenerTipoEquipoPorId(idEquipmentTypeToAddEquipment).getLargoMac()){
                JOptionPane.showMessageDialog(null,"El tamaño del MAC no es igual al definido en el tipo de equipo", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else if(equipoDAO.obtenerEquipoPorSerial(serial) != null || equipoDAO.obtenerEquipoPorMAC(MAC) != null){
                JOptionPane.showMessageDialog(null,"Ya existe un equipo registrado con ese serial y/o MAC", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
            }else{
                equipoDAO.crearEquipo(serial,MAC,observations,idEquipmentTypeToAddEquipment,idPrealertaSelectedAssignment,0);
                JOptionPane.showMessageDialog(null,"Creado Correctamente");
                addEquipmentToPrealerta.dispose();
                showAllEquipmentByPrealerta();
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void showAllEquipmentByPrealerta(){
        try {
            createTableEquipmentByPrealerta("Equipo",equipoDAO.obtenerTodosLosEquiposToPrealerta(idPrealertaSelectedAssignment));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void showAllEquipmentByPrealertaCollection(){
        try {
            createTableEquipmentByPrealertaCollection("Equipo",equipoDAO.obtenerTodosLosEquiposToPrealerta(idPrealertaSelectedCollection));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Equipo> readEquiposFromExcel(String filePath) throws IOException {
        List<Equipo> equipos = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Leer la primera hoja

            // Iterar sobre las filas del archivo
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);

                // Leer datos de las celdas
                String serial = getCellValue(row.getCell(0));
                String mac = getCellValue(row.getCell(1));
                String observaciones = getCellValue(row.getCell(2));
                double idEquipmentTypeValue = Double.parseDouble(getCellValue(row.getCell(3)));
                int idEquipmentType = (int) idEquipmentTypeValue;
                if (tipoEquipoDAO.obtenerTipoEquipoPorId(idEquipmentType).getLargoSerial() != serial.length()){
                    JOptionPane.showMessageDialog(null,"El tamaño de uno de los seriales no es igual al definido en el tipo de equipo", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                }else if (tipoEquipoDAO.obtenerTipoEquipoPorId(idEquipmentType).getLargoMac() != mac.length()){
                    JOptionPane.showMessageDialog(null,"El tamaño de uno de los MAC no es igual al definido en el tipo de equipo", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    double idPrealertaValue = Double.parseDouble(getCellValue(row.getCell(4)));
                    int idPrealerta = (int) idPrealertaValue;
                    double statusValue = Double.parseDouble(getCellValue(row.getCell(5)));
                    int status = (int) statusValue;
                    try {
                        Equipo equipo = new Equipo(0,serial,mac,observaciones,status,tipoEquipoDAO.obtenerTipoEquipoPorId(idEquipmentType),prealertaDAO.obtenerPrealertaPorId(idPrealerta));
                        equipos.add(equipo);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return equipos;
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                double numericValue = cell.getNumericCellValue();
                if (numericValue == Math.floor(numericValue)) {
                    return String.valueOf((int) numericValue);
                } else {
                    return String.valueOf(numericValue);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    private void addDataExcel(){
        if (idPrealertaSelectedAssignment == 0) {
            JOptionPane.showMessageDialog(null, "Primero debe buscar una Prealerta", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    // Leer datos del archivo Excel
                    List<Equipo> equipos = readEquiposFromExcel(selectedFile.getAbsolutePath());
                    List<Equipo> equiposExistentes = equipoDAO.obtenerTodosLosEquipos();
                    for (Equipo equipoIngresado : equipos) {
                        boolean equipoDuplicado = false;

                        for (Equipo equipoRegistrado : equiposExistentes) {
                            if (equipoIngresado.getSerial().equalsIgnoreCase(equipoRegistrado.getSerial())) {
                                JOptionPane.showMessageDialog(null, "El serial " + equipoIngresado.getSerial() + " ya se encuentra registrado", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                                equipoDuplicado = true;
                                break; // Salir del bucle si se encuentra un serial duplicado
                            } else if (equipoIngresado.getMac().equalsIgnoreCase(equipoRegistrado.getMac())) {
                                JOptionPane.showMessageDialog(null, "El MAC " + equipoIngresado.getMac() + " ya se encuentra registrado", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                                equipoDuplicado = true;
                                break; // Salir del bucle si se encuentra un MAC duplicado
                            }
                        }
                        if (!equipoDuplicado) {
                            equipoDAO.crearEquipo(
                                    equipoIngresado.getSerial(),
                                    equipoIngresado.getMac(),
                                    equipoIngresado.getObservaciones(),
                                    equipoIngresado.getTipoEquipo().getId(),
                                    equipoIngresado.getPrealerta().getId(),
                                    equipoIngresado.getStatus()
                            );
                        }
                    }
                    JOptionPane.showMessageDialog(null,"Creado Correctamente");
                    showAllEquipmentByPrealerta();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error al leer el archivo Excel", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void getEquipmentByPrealertaCollection(){
        selectedValueComboBoxEquipmentPrealertaCollection = (String) gui.getCollectionPanel().getPrealertaComboBox().getSelectedItem();
        if (selectedValueComboBoxEquipmentPrealertaCollection == null){
            JOptionPane.showMessageDialog(null, "No existen prealertas para recoleccion", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            try {
                idPrealertaSelectedCollection = prealertaDAO.obtenerPrealertaPorNombre(selectedValueComboBoxEquipmentPrealertaCollection).getId();
                gui.getCollectionPanel().getQuantityCollection().setText(String.valueOf(equipoDAO.contarEquiposConEstadoUno(idPrealertaSelectedCollection)));
                List<Equipo> listEquipmentByPrealerta = equipoDAO.obtenerTodosLosEquiposToPrealerta(idPrealertaSelectedCollection);
                createTableEquipmentByPrealertaCollection("Equipo",listEquipmentByPrealerta);
                gui.getCollectionPanel().getTotalEquipment().setText(String.valueOf(equipoDAO.obtenerTodosLosEquiposToPrealerta(idPrealertaSelectedCollection).size()));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void clearTableCollection(){
        DefaultTableModel model = new DefaultTableModel();
        model.setRowCount(0);
        gui.getCollectionPanel().setTableModel(model);
    }

    private void clearTableAssignament(){
        DefaultTableModel model = new DefaultTableModel();
        model.setRowCount(0);
        gui.getAssignmentPanel().setTableModel(model);
    }

    private void collectionEquipment(){
        if (selectedValueComboBoxEquipmentPrealertaCollection == null){
            JOptionPane.showMessageDialog(null, "Debe seleccionar primero una prealerta", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        }else{
            String serialCollection = gui.getCollectionPanel().getInputSerial().getText();
            String macCollection = gui.getCollectionPanel().getInputMAC().getText();
            try {
                if (equipoDAO.obtenerEquipoPorSerial(serialCollection) == null || equipoDAO.obtenerEquipoPorMAC(macCollection) == null){
                    JOptionPane.showMessageDialog(null, "No existe un equipo con el serial o el MAC ingresado", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                }else if((equipoDAO.obtenerEquipoPorSerial(serialCollection) != null || equipoDAO.obtenerEquipoPorMAC(macCollection) != null ) && (equipoDAO.obtenerEquipoPorSerial(serialCollection).getStatus() == 1 || equipoDAO.obtenerEquipoPorMAC(macCollection).getStatus() == 1)){
                    JOptionPane.showMessageDialog(null, "El equipo ya ha sido recogido", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                    gui.getCollectionPanel().getInputSerial().setText("");
                    gui.getCollectionPanel().getInputMAC().setText("");
                }else{
                    equipoDAO.actualizarEquipo(serialCollection,macCollection);
                    JOptionPane.showMessageDialog(null, "Equipo recogido", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                    showAllEquipmentByPrealertaCollection();
                    gui.getCollectionPanel().getQuantityCollection().setText(String.valueOf(equipoDAO.contarEquiposConEstadoUno(idPrealertaSelectedCollection)));
                    if(equipoDAO.contarEquiposConEstadoUno(idPrealertaSelectedCollection) == Integer.parseInt(gui.getCollectionPanel().getTotalEquipment().getText())){
                        JOptionPane.showMessageDialog(null, "Prealerta finalizada", "Advertencia", JOptionPane.INFORMATION_MESSAGE);
                        prealertaDAO.actualizarEstadoPrealerta(idPrealertaSelectedCollection);
                        gui.getCollectionPanel().getInputSerial().setText("");
                        gui.getCollectionPanel().getInputMAC().setText("");
                        gui.getCollectionPanel().getQuantityCollection().setText("0");
                        gui.getCollectionPanel().getTotalEquipment().setText("0");
                        clearTableCollection();
                        clearTableAssignament();
                        gui.getCardLayout().show(gui.getContentPanel(),"Prealertas");
                        fillComboBoxCollection();
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (Events.valueOf(e.getActionCommand())){
            case ACTIVAR_CALENDARIO:
                activateCalendar(true);
                break;
            case ACTIVAR_CALENDARIO_END:
                activateCalendar(false);
                break;
            case OK_OPTION:
                extractDateStart();
                break;
            case OK_OPTION_END:
                extractDateEnd();
                break;
            case SEARCH_PREALERTA:
                searchPrealerta();
                clearFieldsPrealerta();
                break;
            case SHOW_ALL_PREALERTAS:
                showAllPrealertas();
                break;
            case SHOW_UPDATE_PREALERTA:
                showUpdatePrealerta();
                break;
            case UPDATE_PREALERTA:
                updatePrealerta();
                showAllPrealertas();
                clearFieldsPrealerta();
                fillComboBoxAssignment();
                fillComboBoxCollection();
                break;
            case SHOW_CREATE_PREALERTA:
                showCreatePrealerta();
                break;
            case ACCEPT_CREATE_PREALERTA:
                createPrealerta();
                showAllPrealertas();
                clearFieldsPrealerta();
                fillComboBoxAssignment();
                fillComboBoxCollection();
                break;
            case SHOW_DELETE_PREALERTA:
                showDeletePrealerta();
                break;
            case ACCEPT_DELETE:
                deletePrealerta();
                showAllPrealertas();
                clearFieldsPrealerta();
                fillComboBoxAssignment();
                fillComboBoxCollection();
                break;
            case SHOW_CREATE_EQUIPMENT_TYPE:
                showCreateEquipmentType();
                break;
            case CREATE_EQUIPMENT_TYPE:
                createEquipmentType();
                showAllEquipmentType();
                clearFieldsEquipmentType();
                break;
            case SHOW_UPDATE_EQUIPMENT_TYPE:
                showUpdateEquipmentType();
                break;
            case UPDATE_EQUIPMENT_TYPE:
                updateEquipmentType();
                showAllEquipmentType();
                clearFieldsEquipmentType();
                break;
            case SHOW_DELETE_EQUIPMENT_TYPE:
                showDeleteEquipmentType();
                break;
            case DELETE_EQUIPMENT_TYPE:
                deleteEquipmentType();
                showAllEquipmentType();
                clearFieldsEquipmentType();
                break;
            case SEARCH_EQUIPMENT_PREALERTA:
                getEquipmentByPrealerta();
                break;
            case SHOW_ADD_EQUIPMENT_TO_PREALERTA:
                showAddEquipmentToPrealerta();
                break;
            case ADD_EQUIPMENT_TO_PREALERTA:
                addEquipmentToPrealerta();
                fillComboBoxCollection();
                break;
            case INSERT_DATA_EXCEL:
                addDataExcel();
                break;
            case SEARCH_EQUIPMENT_PREALERTA_COLLECTION:
                getEquipmentByPrealertaCollection();
                break;
            case COLLECTION_EQUIPMENT:
                collectionEquipment();
                try {
                    createTablePrealerta("Prealerta");
                    fillComboBoxAssignment();
                    fillComboBoxCollection();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case DELETE_EQUIPMENT:
                showDeleteEquipment();
                break;
            case ACCEPT_DELETE_EQUIPMENT:
                deleteEquipment();
                break;
        }
    }

    public static void main(String[] args) throws SQLException {
        Controller controller = new Controller();
    }
}
