/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.shipper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author DELL
 */
public class Shipper {

    public void show() {
        JFrame fr = new JFrame("Product"); //top lvl
        JPanel panel = new JPanel(); // client area  root 

        BoxLayout box = new BoxLayout(panel, BoxLayout.Y_AXIS); // layoutmanager -- arragemnt 
        panel.setLayout(box);
       
        // product search
        JLabel prd = new JLabel("Product Name ");
        JTextField Product = new JTextField(15);
        
        JLabel lblDes = new JLabel("Descreption");
        JTextField jtfDes = new JTextField(150);
        
        // For Entry of product site 
        JButton btnNo_account = new JButton("For Selling Product , Click ");
        JButton button = new JButton("Submit");

        JPanel panel1 = new JPanel();
        BoxLayout box1 = new BoxLayout(panel1, BoxLayout.X_AXIS);
        panel1.setLayout(box1);
       panel1.add(prd);
        panel1.add(Product);
        
        JPanel panel3 = new JPanel();
        BoxLayout box3 = new BoxLayout(panel3,BoxLayout.X_AXIS );
        panel3.setLayout(box3);
        panel3.add(lblDes);
        panel3.add(jtfDes);
       
        JPanel panel2 = new JPanel();
        BoxLayout box2 = new BoxLayout(panel2, BoxLayout.X_AXIS);
        panel2.setLayout(box2);
     
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(button);
     
        panel.add(btnNo_account);
        
        //   
        fr.setContentPane(panel);
        fr.setSize(500, 500);
        fr.setVisible(true);

        }
}
