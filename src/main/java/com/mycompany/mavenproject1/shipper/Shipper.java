/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.shipper;

import com.mycompany.mavenproject1.IActionResponse;
import com.mycompany.mavenproject1.database.MySqlHelper;
import com.mycompany.mavenproject1.utility.MyUtility;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
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
        JTextField product = new JTextField(15);

        JLabel lblDes = new JLabel("Descreption");
        JTextField jtfDes = new JTextField(150);

        // For Entry of product site 
        JButton btnNo_account = new JButton("For Selling Product , Click ");
        JButton button = new JButton("Submit");

        JPanel panel1 = new JPanel();
        BoxLayout box1 = new BoxLayout(panel1, BoxLayout.X_AXIS);
        panel1.setLayout(box1);
        panel1.add(prd);
        panel1.add(product);

        JPanel panel3 = new JPanel();
        BoxLayout box3 = new BoxLayout(panel3, BoxLayout.X_AXIS);
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
        setUpActions(product,
                jtfDes,
                button);
        //   
        fr.setContentPane(panel);
        fr.setSize(500, 500);
        fr.setVisible(true);
    }

    /**
     * In this method I am setting up actions on these components
     *
     * @param product
     * @param productDescription
     * @param button
     */
    private void setUpActions(JFrame frame, JTextField product, JTextField productDescription, JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // this anonymous class object will llisten to the button actions.
                String productName = product.getText().toString().trim();
                String description = productDescription.getText().toString().trim();
                //trim function removes extra space from last edge if user entered "papita " instead of "papita" it will make it same
                boolean isProductNameOK = !productName.isBlank() && productName.length() > 3;
                boolean isProductDescriptionOk = !description.isBlank() && description.length() > 10; // let us assume a sentence is atleast 10 characters long.

                if(!isProductNameOK){
                    // invalid product name show dialog here
                    MyUtility.showErrorMessage(frame, "Please enter a valid product name.");
                    return;
                }

                if(!isProductDescriptionOk){
                    // invalid description just let user know
                    MyUtility.showErrorMessage(frame, "Please enter a valid product description for product.");
                    return;
                }

                // if code comes here mean s every entry is ok
                // now we just have to check that if entry already exists
                // in db
                // now let us take db class and checck the product
                MySqlHelper helper = MySqlHelper.getMyConnectionHelper(); // this static method will provide us the database helper
                helper.setCommunicator(new IActionResponse() {
                    @Override
                    public void OnActionPerformed(Object data, Object tag) {
                        // this annonymus object will bring all the responses happened in the database
                    }
                });







            }
        });


    }
}
