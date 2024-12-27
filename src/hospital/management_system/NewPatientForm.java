package hospital.management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;

// Клас для представлення пацієнта
class Patient {
    private String idType;
    private String idNumber;
    private String name;
    private String gender;
    private String disease;
    private String roomNumber;
    private String admissionDate;
    private double deposit;

    public Patient(String idType, String idNumber, String name, String gender, String disease, String roomNumber, String admissionDate, double deposit) {
        this.idType = idType;
        this.idNumber = idNumber;
        this.name = name;
        this.gender = gender;
        this.disease = disease;
        this.roomNumber = roomNumber;
        this.admissionDate = admissionDate;
        this.deposit = deposit;
    }

    public String getInsertQuery() {
        return String.format(
                "INSERT INTO Patient_Info VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', %.2f)",
                idType, idNumber, name, gender, disease, roomNumber, admissionDate, deposit
        );
    }

    public String getUpdateRoomQuery() {
        return String.format("UPDATE Room SET Availability = 'Occupied' WHERE room_no = '%s'", roomNumber);
    }
}

// Клас для роботи з базою даних
class DatabaseConnection {
    private conn connection;

    public DatabaseConnection() {
        connection = new conn();
    }

    public ResultSet executeQuery(String query) {
        try {
            return connection.statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean executeUpdate(String query) {
        try {
            connection.statement.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

// Основний клас інтерфейсу
public class NewPatientForm extends JFrame implements ActionListener {
    private JComboBox<String> comboBoxID;
    private JTextField textFieldNumber, textName, textFieldDisease, textFieldDeposit;
    private JRadioButton r1, r2;
    private Choice roomChoice;
    private JLabel dateLabel;
    private JButton addButton, backButton;
    private DatabaseConnection dbConnection;

    public NewPatientForm() {
        dbConnection = new DatabaseConnection();
        initializeUI();
    }

    private void initializeUI() {
        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 840, 540);
        panel.setBackground(new Color(90, 156, 163));
        panel.setLayout(null);
        add(panel);

        JLabel titleLabel = new JLabel("NEW PATIENT FORM");
        titleLabel.setBounds(118, 11, 260, 53);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(titleLabel);

        addLabeledField(panel, "ID:", 35, 76, comboBoxID = new JComboBox<>(new String[]{"Aadhar card", "Voter ID", "Driving License"}));
        addLabeledField(panel, "Number:", 35, 111, textFieldNumber = new JTextField());
        addLabeledField(panel, "Name:", 35, 151, textName = new JTextField());
        addLabeledField(panel, "Gender:", 35, 191, r1 = new JRadioButton("Male"), r2 = new JRadioButton("Female"));
        addLabeledField(panel, "Disease:", 35, 231, textFieldDisease = new JTextField());
        addLabeledField(panel, "Room:", 35, 274, roomChoice = new Choice());
        addLabeledField(panel, "Time:", 35, 316, dateLabel = new JLabel(new Date().toString()));
        addLabeledField(panel, "Deposit:", 35, 359, textFieldDeposit = new JTextField());

        addButton = createButton(panel, "ADD", 100, 430);
        backButton = createButton(panel, "BACK", 260, 430);

        populateRoomChoices();

        setSize(850, 550);
        setLayout(null);
        setLocation(300, 250);
        setVisible(true);
    }

    private void addLabeledField(JPanel panel, String labelText, int x, int y, Component... components) {
        JLabel label = new JLabel(labelText);
        label.setBounds(x, y, 200, 14);
        label.setFont(new Font("Tahoma", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        panel.add(label);

        int offsetX = 271;
        for (Component component : components) {
            component.setBounds(offsetX, y, 150, 20);
            panel.add(component);
            offsetX += 160;
        }
    }

    private JButton createButton(JPanel panel, String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 120, 30);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.addActionListener(this);
        panel.add(button);
        return button;
    }

    private void populateRoomChoices() {
        try {
            ResultSet rs = dbConnection.executeQuery("SELECT room_no FROM Room WHERE Availability = 'Available'");
            while (rs != null && rs.next()) {
                roomChoice.add(rs.getString("room_no"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String gender = r1.isSelected() ? "Male" : (r2.isSelected() ? "Female" : "");
            Patient patient = new Patient(
                    (String) comboBoxID.getSelectedItem(),
                    textFieldNumber.getText(),
                    textName.getText(),
                    gender,
                    textFieldDisease.getText(),
                    roomChoice.getSelectedItem(),
                    dateLabel.getText(),
                    Double.parseDouble(textFieldDeposit.getText())
            );

            if (dbConnection.executeUpdate(patient.getInsertQuery()) &&
                    dbConnection.executeUpdate(patient.getUpdateRoomQuery())) {
                JOptionPane.showMessageDialog(null, "Added Successfully");
            }
        } else if (e.getSource() == backButton) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new NewPatientForm();
    }
}
