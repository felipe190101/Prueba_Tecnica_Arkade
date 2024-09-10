package ui.utilities;

import com.toedter.calendar.JCalendar;
import controller.Events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;

public class CalendarDialog extends JDialog {

    private JCalendar calendar;
    private JButton accept;

    public CalendarDialog(ActionListener listener) {
        setTitle("Calendario");
        setLayout(new BorderLayout());
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents(listener);
    }

    public void initComponents(ActionListener listener){
        calendar = new JCalendar();
        this.add(calendar,BorderLayout.CENTER);

        accept = new JButton("Aceptar");
        accept.addActionListener(listener);

        this.add(accept, BorderLayout.SOUTH);
    }

    public void setOption(boolean option){
        if (option) {
            accept.setActionCommand(Events.OK_OPTION.name());
        } else {
            accept.setActionCommand(Events.OK_OPTION_END.name());
        }
    }

    public Date extractDate(){
        return calendar.getDate();
    }
}
