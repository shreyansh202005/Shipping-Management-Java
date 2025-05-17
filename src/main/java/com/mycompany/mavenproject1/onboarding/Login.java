/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.onboarding;

import com.mycompany.mavenproject1.IActionResponse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.mycompany.mavenproject1.database.MySqlHelper;
import com.mycompany.mavenproject1.utility.MyUtility;


/**
 * @author DELL
 */
public class Login {

    public void show() {
        JFrame fr = new JFrame("Login");
        JPanel panel = new JPanel();

        BoxLayout box = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(box);
        // label
        JLabel lbl = new JLabel("User Name");
        JTextField userName = new JTextField(15);//ctrl+p

        JLabel lblPassword = new JLabel("Password");
        JPasswordField password = new JPasswordField();

        JButton btnNo_account = new JButton("Don't have an account, Signup!!");
        JButton button = new JButton("Submit");

        JPanel panel1 = new JPanel();
        BoxLayout box1 = new BoxLayout(panel1, BoxLayout.X_AXIS);
        panel1.setLayout(box1);
        panel1.add(lbl);

        panel1.add(userName);

        JPanel panel2 = new JPanel();
        BoxLayout box2 = new BoxLayout(panel2, BoxLayout.X_AXIS);
        panel2.setLayout(box2);
        panel2.add(lblPassword);
        panel2.add(password);

        panel.add(panel1);
        panel.add(panel2);
        panel.add(button);
        // if this happen ,then destroy the login window 
        panel.add(btnNo_account);
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = userName.getText();
                String pass = new String(password.getPassword());
                validateUserInput(fr, name, pass);
            }
        };
        button.addActionListener(listener);
//       Anonymus object creation in java
        btnNo_account.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redirect2Signup();
                fr.dispose();
//                fr.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }
        });
        fr.setContentPane(panel);
        fr.setSize(300, 300);
        fr.setVisible(true);

        MySqlHelper helper = MySqlHelper.getMyConnectionHelper();
        helper.initDataBase();
    }

    private void redirect2Signup() {
        SignUp signup = new SignUp();
        signup.show();
    }

    private void validateUserInput(JFrame frame, String name, String pass) {
        boolean isNameEmpty = name.isBlank();
        boolean isNameSmall = name.length() < 3;
        boolean isPasswordEmpty = pass.isBlank();
        boolean isPasswordSmall = pass.length() < 6;

        if (isNameEmpty || isNameSmall) {
            // invalid name
            MyUtility.showErrorMessage(frame, "Please enter an valid name, must be > 3");// this will work same
//            JOptionPane.showMessageDialog(frame, "Please enter an valid name, must be > 3");
            return;
        }

        if (isPasswordEmpty || isPasswordSmall) {
            // show warning of invalid password and return from here no go ahead
            JOptionPane.showMessageDialog(frame, "Please enter a valid password, must be > 6");
            return;
        }
        // you can do io operations here
        ResultSet[] result = new ResultSet[1];
        MySqlHelper helper = new MySqlHelper();
        helper.setCommunicator(new IActionResponse() {
            @Override
            public void OnActionPerformed(Object data, Object tag) {
                String tag1 = (String) tag;
                if (tag1.equals(MySqlHelper.TAG_DATA)) {
                    // data
                    result[0] = (ResultSet) data;
                    ResultSet set = result[0];

                    try {
                        if (set.next()) {
                            String id = set.getString("id");
                            String name = set.getString("name");
                            String userType = set.getString("type");
                            userSuccessfullyLoggedIn(id, name, userType);
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "1 Please enter valid id/password");
                    }
                }
                if (tag1.equals(MySqlHelper.TAG_SUCCESS)) {
                    //success
                    JOptionPane.showMessageDialog(frame, "Welcome " + (String) data + "!!");
                } else {
                    //failure
                    JOptionPane.showMessageDialog(frame, "2 Please enter valid id/password");
                }
            }

            private void userSuccessfullyLoggedIn(String id, String name, String userType) {
                JOptionPane.showMessageDialog(frame, "Welcome " + name + "!!");
            }
        });

        helper.getPerticularUserWhere(name, pass);

//        // success message
//
//        final String assumedValidUserIDInDB = "Dhaval";
//        final String assumedValidPasswordInDB = "Aa123456@";
//
//        // checking id and password in db 
//        if (name.equals(assumedValidUserIDInDB) && pass.equals(assumedValidPasswordInDB)) {
//            JOptionPane.showMessageDialog(frame, "Welcome " + name + "!!");
//        } else {
//
//            JOptionPane.showMessageDialog(frame, "Please enter valid id/password");
//        }
    }
}
