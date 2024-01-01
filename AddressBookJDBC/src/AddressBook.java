import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class AddressBook {
	    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	    private static final String DB_URL = "jdbc:mysql://localhost/address_book";
	    private static final String USER = "root";
	    private static final String PASS = "akhilmysql";

	    private Connection conn = null;
	    
	    public AddressBook(){
	        try {
	            Class.forName(JDBC_DRIVER);
	            conn = DriverManager.getConnection(DB_URL, USER, PASS);
	        } catch (ClassNotFoundException | SQLException e) {
	            showError("Database Connection Error", "Failed to connect to the database.");
	            System.exit(1);
	        }

	        JFrame frame = new JFrame("Address Book");
	        frame.setSize(500, 300);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        JPanel panel = new JPanel();

	        JLabel nameLabel = new JLabel("Name:");
	        JTextField nameField = new JTextField(20);

	        JLabel phoneLabel = new JLabel("Phone:");
	        JTextField phoneField = new JTextField(20);

	        JLabel emailLabel = new JLabel("Email:");
	        JTextField emailField = new JTextField(20);

	        JRadioButton mobileByEmail = new JRadioButton("Update Mobile by Email");
	        JRadioButton emailByPhone = new JRadioButton("Update Email by Phone");

	        ButtonGroup group = new ButtonGroup();
	        group.add(mobileByEmail);
	        group.add(emailByPhone);
	        
	        
	        JButton addButton = new JButton("Add Contact");
	        addButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                insertContact(nameField.getText(), phoneField.getText(), emailField.getText());
	            }

	        });

	        JButton updateButton = new JButton("Update Contact");
	        updateButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (mobileByEmail.isSelected()) {
	                    updateMobileByEmail(nameField.getText(), emailField.getText(), phoneField.getText());
	                } else if (emailByPhone.isSelected()) {
	                    updateEmailByPhone(nameField.getText(), phoneField.getText(), emailField.getText());
	                } else {
	                    JOptionPane.showMessageDialog(null, "Please select an update option!");
	                }
	            }
	        });
	        
	        JButton deleteButton = new JButton("Delete Contact");
	        deleteButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                deleteContact(nameField.getText());
	            }
	        });

	        panel.add(nameLabel);
	        panel.add(nameField);
	        panel.add(phoneLabel);
	        panel.add(phoneField);
	        panel.add(emailLabel);
	        panel.add(emailField);
	        panel.add(addButton);
	        panel.add(mobileByEmail);
	        panel.add(emailByPhone);
	        panel.add(updateButton);
	        
	        panel.add(deleteButton);
	        
	        

	        frame.add(panel);
	        frame.setVisible(true);
	    }
	    
	    private void insertContact(String name, String phone, String email) {
	        String sql = "INSERT INTO contacts (name, phone, email) VALUES (?, ?, ?)";
	        
	        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, name);
	            pstmt.setString(2, phone);
	            pstmt.setString(3, email);
	            
	            int rowsInserted = pstmt.executeUpdate();
	            if (rowsInserted > 0) {
	                JOptionPane.showMessageDialog(null, "Contact added successfully!");
	            } else {
	                JOptionPane.showMessageDialog(null, "Failed to add contact.");
	            }
	        } catch (SQLException e) {
	            showError("Database Error", "Failed to insert contact into the database.");
	        }
	    }
	  


	
}
