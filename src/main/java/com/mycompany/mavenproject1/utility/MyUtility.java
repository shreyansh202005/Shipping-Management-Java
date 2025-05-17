package com.mycompany.mavenproject1.utility;

import javax.swing.*;

/***
 * This class will have some methods that are useful in all parts of this project
 */
public class MyUtility {

    // in this method we will sent only string and it will show a dialog to user with that message.
    public static void showErrorMessage(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message);
        // now see the use of this method
    }

}
