package hospital.management_system;

import javax.swing.*;
import java.awt.*;

public class Employee_info extends JFrame {
    Employee_info(){
        JPanel panel = new JPanel();
        panel.setBounds(5,5,990,590);
        panel.setBackground(new Color(109,64,170));
        panel.setLayout(null);
        add(panel);

        JTable table = new JTable();
        table.setBounds(10,34,980,450);
        table.setBackground(new Color(109,64,170));
        panel.add(table);




        setSize(1000,600);
        setLocation(350,230);
        setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Employee_info();
    }
}
