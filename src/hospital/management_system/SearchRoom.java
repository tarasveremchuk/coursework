package hospital.management_system;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.xml.transform.Result;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class SearchRoom extends JFrame {
    SearchRoom(){
        JPanel panel = new JPanel();
        panel.setBounds(5,5,690,490);
        panel.setBackground(new Color(90,156,163));
        panel.setLayout(null);
        add(panel);

        JLabel search = new JLabel("Search Room");
        search.setBounds(250,11,186,31);
        search.setForeground(Color.WHITE);
        search.setFont(new Font("Tahoma",Font.BOLD,20));
        panel.add(search);

        JLabel status = new JLabel("Status");
        status.setBounds(50,73,120,20);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("Tahoma",Font.BOLD,14));
        panel.add(status);

        Choice choice = new Choice();
        choice.setBounds(170,70,120,320);
        choice.add("Available");
        choice.add("Occupied");
        panel.add(choice);

        JTable table = new JTable();
        table.setBounds(0,187,700,210);
        table.setBackground(new Color(90,156,163));
        table.setForeground(Color.WHITE);
        panel.add(table);


        try{
            conn c = new conn();
            String q="select * from room";
            ResultSet resultSet=c.statement.executeQuery(q);
            table.setModel((DbUtils.resultSetToTableModel(resultSet)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        JLabel roomNum = new JLabel("Room Number");
        roomNum.setBounds(23,162,150,20);
        roomNum.setForeground(Color.WHITE);
        roomNum.setFont(new Font("Tahoma",Font.BOLD,14));
        panel.add(roomNum);

        JLabel availability = new JLabel("Availability");
        availability.setBounds(175,162,150,20);
        availability.setForeground(Color.WHITE);
        availability.setFont(new Font("Tahoma",Font.BOLD,14));
        panel.add(availability);

        JLabel price = new JLabel("Price");
        price.setBounds(458,162,150,20);
        price.setForeground(Color.WHITE);
        price.setFont(new Font("Tahoma",Font.BOLD,14));
        panel.add(price);

        JLabel bedType = new JLabel("Bed Type");
        bedType.setBounds(580,162,150,20);
        bedType.setForeground(Color.WHITE);
        bedType.setFont(new Font("Tahoma",Font.BOLD,14));
        panel.add(bedType);

        JButton searchBtn=new JButton("Search");
        searchBtn.setBounds(200,420,120,20);
        searchBtn.setBackground(Color.BLACK);
        searchBtn.setForeground(Color.WHITE);
        panel.add(searchBtn);
        searchBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String q ="select * from Room where Availability= '"+choice.getSelectedItem()+"'";
                try{
                    conn c = new conn();
                    ResultSet resultSet=c.statement.executeQuery(q);
                    table.setModel((DbUtils.resultSetToTableModel(resultSet)));

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });


        JButton backBtn=new JButton("Back");
        backBtn.setBounds(340,420,120,20);
        backBtn.setBackground(Color.BLACK);
        backBtn.setForeground(Color.WHITE);
        panel.add(backBtn);
        backBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });


        setUndecorated(true);
        setSize(700,500);
        setLayout(null);
        setLocation(450,250);
        setVisible(true);
    }
    public static void main(String[] args) {
        new SearchRoom();
    }

}
