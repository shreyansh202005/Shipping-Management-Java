/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.shipper;

import java.awt.Component;
import java.awt.Panel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author DELL
 */
public class Orders {

//create table Orders(
//order_id int auto_increment,
//psrid int ,
//user_id int ,
//delivery_D date default null,
//status enum ('ready' ,'pending','Cancelled','Delivered') default 'pending',
//primary key(order_id));
    public void show() {
        JFrame fr = new JFrame("Order");
        JPanel panel = new JPanel();
        BoxLayout box1 = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(box1);

        JButton btnChangeStatus = new JButton("ChangeStatus : ");

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product");
        model.addColumn("Address");
        model.addColumn("DeliveryDate");
        model.addColumn("Status");
        model.addColumn("ChangeStatus");

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        TableColumn column = table.getColumn("ChangeStatus");
        column.setCellRenderer(new ButtonRenderer());

        putDataInTable(model);
        panel.add(btnChangeStatus);
//        JPanel panel1 = new JPanel(); //mere call aa gya tha
//        String[] data = {"ready", "pending", "Cancelled", "Delivered"};
//        JList<String> list = new JList<String>(data);
//        panel1.add(list);
//        panel.add(panel1);
        fr.setContentPane(panel);
        fr.setSize(300, 300);
        fr.setVisible(true);

    }

    private JPanel createAPanel(JPanel rootPanel, Component com1, Component com2) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(com1);
        panel.add(com1);
        rootPanel.add(panel);

        return panel;

    }

    private void putDataInTable(DefaultTableModel table) {
//          model.addColumn("Product");
//        model.addColumn("Address");
//        model.addColumn("DeliveryDate");
//        model.addColumn("Status");
//        model.addColumn("ChangeStatus");

        JPanel panel1 = new JPanel(); //mere call aa gya tha
        String[] data = {"ready", "pending", "Cancelled", "Delivered"};
        JList<String> list = new JList<String>(data);

        Object[] row1 = new Object[]{
            "Chair",
            "Kasera Bazar",
            "25/11/2025",
            "pending",
            "aaa"
        };
        table.addRow(row1);

    }

  public static  class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Click" : value.toString());
            return this;
        }
    }

}
