package com.mycompany.mavenproject1.database;

// responsibility division
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mycompany.mavenproject1.IActionResponse;



public class MySqlHelper {

    public static final String TAG_SUCCESS = "SUCCESS";
    public static final String TAG_FAILURE = "FAILURE";
    public static final String TAG_DATA = "TAG_DATA";

    //1) connection create krna 
    //2) connection destroy krna 
    private static MySqlHelper helper;
    private static Connection myConnection = null;
    private IActionResponse communicator;

    public static MySqlHelper getMyConnectionHelper() {
        if (myConnection == null) {
            makeConnectionWithDatabase();
        }
        helper = new MySqlHelper();
        return helper;
    }

    public static void makeConnectionWithDatabase() {
        //1) connection create krna 
        try {
            if (myConnection != null && !myConnection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
            myConnection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306",
                    "root",
                    "root"
            );
            System.out.println("Connected to MySQL database.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return myConnection;
    }

    public void initDataBase() {
        final String createDB = "Create Database if not exists Shipping";
        final String useDB = "use Shipping";
        final String createUserTable = "Create table if not exists User("
                + "id int auto_increment primary key,"
                + "name varchar(50) unique,"
                + "email varchar(150),"
                + "password varchar(150),"
                + "type enum('shipper', 'admin','user')"
                + ")";
        try {
            Statement state = getConnection().createStatement();
            state.execute(createDB);
            state.execute(useDB);
            state.execute(createUserTable);
            state.close();
            System.out.println("tables Created");
        } catch (SQLException ex) {
            Logger.getLogger(MySqlHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCommunicator(IActionResponse r) {
        this.communicator = r;
    }

   public void createANewUser(String userName, String email, String password, String userType) { // id auto increment he to use nahi likhenge kr entry
        String query = "insert into User("
                + "name,email,password,type"
                + ") values"
                + "(?, ?, ?,?)"
                + ""; //good? understood? so gya kya? koi ni me porri likh raha kal dekhte he 
        try {
            PreparedStatement sta = getConnection().prepareStatement(query); // new thing prepareStatement vs createSTatement()ok
            sta.setString(1, userName);
            sta.setString(2, email);
            sta.setString(3, password);
            sta.setString(4, userType);
            sta.executeUpdate();

            communicator.OnActionPerformed("Welcome, " + userName, TAG_SUCCESS);
            System.out.println("new user added successfully :: " + userName); // aaj ke liye itna hi baki picture kal 
        } catch (SQLException e) {
            communicator.OnActionPerformed("Ohh, No!! " + e.getMessage(), TAG_FAILURE);
            e.printStackTrace();
        }
    }

    // userLogin
    public void getPerticularUserWhere(String userName, String password) {
        final String loginQuery
                = "select * from user "
                + "where name=" + userName + " and "
                + "password=" + password + " "
                + "limit 1";
        final String loginQuery2
                = "select * from user "
                + "where name=? and "
                + "password=?";
        try {
            PreparedStatement sta = getConnection().prepareStatement(loginQuery2);
            sta.setString(1, userName);
            sta.setString(2, password);

            ResultSet result = sta.executeQuery();
            communicator.OnActionPerformed(result, TAG_DATA);
        } catch (SQLException e) {
            communicator.OnActionPerformed("Ohh, No!! " + e.getMessage(), TAG_FAILURE);
            e.printStackTrace();
        }
    }

}
