import java.sql.DriverManager;
import java.sql.*;

public class Connect {
    Connection conn = null;
    public Connect(){
        Oracle();
        // MySQL();
    }
    public Connection ConnectDB(){
        return conn;
    }

    void Oracle(){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE","system", "admin");
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    void MySQL(){
        try{
            String dbUrl = "jdbc:mysql://avnadmin:AVNS_Ek3JJ8PZtbVZ-e2847I@mysql-c581f0d-mr-d4a5.a.aivencloud.com:12716/masterDB?ssl-mode=REQUIRED";
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

}
