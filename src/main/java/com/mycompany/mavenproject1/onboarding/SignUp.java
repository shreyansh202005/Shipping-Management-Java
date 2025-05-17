/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.onboarding;

import com.mycompany.mavenproject1.IActionResponse;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import com.mycompany.mavenproject1.database.MySqlHelper;
/**
 *
 * @author DELL
 */
public class SignUp {

    public void show() {

        JFrame fr = new JFrame("Sign Up");
        JPanel panel = new JPanel();

        BoxLayout box = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(box);
        // label
        JLabel lblUserName = new JLabel("UserName:");
        JTextField tfUserName = new JTextField();

        JLabel lbl = new JLabel("Email Address:");
        JTextField Email = new JTextField();

        String[] types = new String[]{"user", "admin", "shipper"};
        JComboBox typeCombo = new JComboBox(types);
        JLabel labelType = new JLabel("Select Type");

        JLabel lblPassword = new JLabel("Set Password:");
        JPasswordField setpassword = new JPasswordField();

        JLabel lblConfirmPassword = new JLabel("Confirm Password:");
        JPasswordField setConfirmpassword = new JPasswordField();

        JButton btnAlreadyHasAccount = new JButton("Already have an account, Login!!");
        JButton button = new JButton("Submit");

        JPanel panel0 = new JPanel();
        BoxLayout box0 = new BoxLayout(panel0, BoxLayout.X_AXIS);
        panel0.add(lblUserName);
        panel0.add(tfUserName);
        panel0.setLayout(box0);

        JPanel panel1 = new JPanel();
        BoxLayout box1 = new BoxLayout(panel1, BoxLayout.X_AXIS);
        panel1.add(lbl);
        panel1.add(Email);
        panel1.setLayout(box1);

        JPanel panel2 = new JPanel();
        BoxLayout box2 = new BoxLayout(panel2, BoxLayout.X_AXIS);
        panel2.add(lblPassword);
        panel2.add(setpassword);
        panel2.setLayout(box2);

        JPanel panel3 = new JPanel();
        BoxLayout box3 = new BoxLayout(panel3, BoxLayout.X_AXIS);
        panel3.add(lblConfirmPassword);
        panel3.add(setConfirmpassword);
        panel3.setLayout(box3);

        JPanel panel4 = new JPanel();
        BoxLayout box4 = new BoxLayout(panel4, BoxLayout.X_AXIS);
        panel4.setLayout(box4);
        panel4.add(labelType);
        panel4.add(typeCombo);

        panel.add(panel0);
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(panel4);
        panel.add(button);
        panel.add(btnAlreadyHasAccount);

        fr.setContentPane(panel);
        fr.setSize(300, 300);
        fr.setVisible(true);

        // actions below
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = tfUserName.getText();
                String email = Email.getText();
                String pwd = new String(setpassword.getPassword());
                String confrmPwd = new String(setConfirmpassword.getPassword());
                String userType = (String) typeCombo.getSelectedItem();
                validateInputAndProcess(fr, name, email, pwd, confrmPwd, userType);
            }

        });
        btnAlreadyHasAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redirect2Login();
                fr.dispose();
            }
        });

    }

    private void validateInputAndProcess(JFrame fr, String userName, String email, String password, String confirmPwd, String userType) {
//        yaha validate kruga fir sql query laga kr store kra dunga 

        boolean isNameEmpty = userName.isBlank();
        boolean isNameSmall = userName.length() < 3;
        boolean isPasswordEmpty = password.isBlank();
        boolean isPasswordSmall = password.length() < 6;
        boolean isPasswordMatches = password.equals(confirmPwd);

        if (isNameEmpty || isNameSmall) {
            JOptionPane.showMessageDialog(fr, "Please enter an valid name, must be > 3");
            return;
        }

        if (isPasswordEmpty || isPasswordSmall) {
            JOptionPane.showMessageDialog(fr, "Please enter a valid password, must be > 5");
            return;
        }

        if (!isPasswordMatches) {
            JOptionPane.showMessageDialog(fr, "Password and confirm password do not match.");
            return;
        }

        // now we are good to go 
        MySqlHelper helper = MySqlHelper.getMyConnectionHelper();
        helper.setCommunicator(new IActionResponse() {
            @Override
            public void OnActionPerformed(Object data, Object tag) {
                String message = (String) data;
                String tag1 = (String) tag;
                if (tag1.equals(MySqlHelper.TAG_SUCCESS)) {
                   //success
                    JOptionPane.showMessageDialog(fr, message);
                } else {
                    //failure
                    JOptionPane.showMessageDialog(fr, message);
                }

            }
        });
        helper.initDataBase();
        helper.createANewUser(userName, email, password, userType);

    }

    private void redirect2Login() {
        Login login = new Login();
        login.show();
    }
    // private void ValidUserInput(JFrame frame, String Email ,String setpassword, String )

}
