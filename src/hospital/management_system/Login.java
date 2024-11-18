package hospital.management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {
    JTextField textField;
    JPasswordField jPasswordField;
    JButton b1,b2;
    Login(){

        JLabel nameLabel = new JLabel("Username");
        nameLabel.setBounds(40,20,100,30);
        nameLabel.setFont(new Font("Tahoma",Font.BOLD,16));
        nameLabel.setForeground(Color.BLACK);
        add(nameLabel);


        JLabel password = new JLabel("Password");
        password.setBounds(40,70,100,30);
        password.setFont(new Font("Tahoma",Font.BOLD,16));
        password.setForeground(Color.BLACK);
        add(password);

        textField = new JTextField();
        textField.setBounds(150,20,150,30);
        textField.setFont(new Font("Tahoma",Font.PLAIN,15));
        textField.setBackground(new Color(255,179,0));
        add(textField);

        jPasswordField = new JPasswordField();
        jPasswordField.setBounds(150,70,150,30);
        jPasswordField.setFont(new Font("Tahoma",Font.PLAIN,15));
        jPasswordField.setBackground(new Color(255,179,0));
        add(jPasswordField);

        b1 = new JButton("Login");
        b1.setBounds(40,140,120,30);
        b1.setFont(new Font("serif",Font.BOLD,15));
        b1.setForeground(Color.WHITE);
        b1.setBackground(Color.BLACK);
        b1.addActionListener(this);
        add(b1);

        b2 = new JButton("Cancel");
        b2.setBounds(180,140,120,30);
        b2.setFont(new Font("serif",Font.BOLD,15));
        b2.setForeground(Color.WHITE);
        b2.setBackground(Color.BLACK);
        add(b2);


        ImageIcon imageIcon=new ImageIcon(ClassLoader.getSystemResource("icon/login.png"));
        Image i1=imageIcon.getImage().getScaledInstance(500,500,Image.SCALE_DEFAULT);
        ImageIcon imageIcon1=new ImageIcon(i1);
        JLabel label = new JLabel(imageIcon1);
        label.setBounds(320,-30,400,300);
        add(label);





        getContentPane().setBackground(new Color(109,164,170));
        setSize(700,300);
        setLocation(400,280);
        setLayout(null);
        setVisible(true);

    }
    public static void main(String[] args) {
        new Login();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            try {
                conn c = new conn(); // Assuming this is a class that manages your DB connection
                String user = textField.getText();
                String pass = jPasswordField.getText();

                String query = "SELECT * FROM login WHERE ID = ? AND PW = ?";
                PreparedStatement pstmt = c.connection.prepareStatement(query);
                pstmt.setString(1, user);
                pstmt.setString(2, pass);

                ResultSet resultSet = pstmt.executeQuery();

                if (resultSet.next()) {
                    new Reception(); // Assuming this opens the next window
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.exit(10);
        }
    }

}
