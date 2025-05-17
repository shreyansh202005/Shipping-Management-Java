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

    // product related qureies below
    public void createProductTable() {
        String query = "create table if not exists product(" + // ok ok one more thing what if product table already exists so
                "prod_id int auto_increment," +
                "name varchar(100)," + //good
                "description text," +
                "primary key(prod_id)" +
                ")";

        try {
            // iska matalab abhi try block me esa koi bhi code nahi he jo is exception ko throw kre
            Statement statement = getConnection().createStatement();
            // ab aa gya throw krne vala code
            statement.execute(query);
            statement.close();
        } catch (SQLException e) {
            // if exception thrown means something worng
            // we are not handling that right now but if project gets bigger will handle it
        }
    }

    /***
     * this method will insert data into product
     * name and description will be given to the method and this method will insert them to database
     */
    public void insertIntoProduct(String productName, String productDescription) {
        String query = "insert into product("
                + "name , description)value("
                + "?,?)"
                + "";

        try {
            PreparedStatement state = getConnection().prepareStatement(query);
            // this prepare statement method is special coz it adds the params to query
            state.setString(1, productName);
            state.setString(2, productDescription);

            // now our statement is complete so execute qurey
            state.execute();
            state.close();
        } catch (SQLException e) {
            // will handle it later if requried
        }
    }

    /***
     * Since seller is adding a produdct so the relation of product should be with seller
     */
    public void createProductSellerRelationshipTable() {
        String qurey =
                "create table if not exists product_seller_relation(" +
                        "id int auto_increment," +
                        "price int," +
                        "seller_id int , " +
                        "product_id int," +
                        "meta text, " +
                        "shipping_charge int ," +
                        "primary key(id)" +
                        ")";
        try {
            Statement state = getConnection().createStatement();
            state.execute(qurey);
            state.close();
        } catch (SQLException e) {
            // will handle later
        }
    }

    /***
     * This method will receive following param and insert them in productsellerrealation table
     * @param price
     * @param sellerID
     * @param productID
     * @param metaJsonIfAny put empty here if no data
     * @param shippingCharge
     */
    public void insertIntoProductSellerRelationTable(
            int price,
            int sellerID,
            int productID,
            String metaJsonIfAny,
            int shippingCharge
    ) {
        String query = "insert into product_seller_relation(" +
                "price, seller_id,product_id,meta,shipping_charge) values(" +
                "?,?,?,?,?)" +
                "";
        try {
            // now see the difference between the function above and this one
            // above i ve used the createstatement() and here prepare statement()
            // both have specific usage here i have some dynamic values to put in query so i used preparestatement .
            // in above i dont have any dynamic value to put in query so i used createstatement()
            //ok first let me complete it
            PreparedStatement state = getConnection().prepareStatement(query);
            // now put dynamic data here
            state.setInt(1,price);
            state.setInt(2,sellerID);
            state.setInt(3,productID);
            state.setString(4,metaJsonIfAny);
            state.setInt(5,shippingCharge);

            // now my sattement ready to executed
            state.execute();
            state.close();
        } catch (SQLException e) {
            // no errors handled right now
        }
    }





}
