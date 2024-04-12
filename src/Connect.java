import java.sql.DriverManager;
import java.sql.*;

public class Connect {
    Connection conn = null;
    public Connect(){
        Oracle();
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
}
