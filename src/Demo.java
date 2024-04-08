import java.sql.*;

public class Demo {

    public static void main(String[] args){

        try{
            Connect conn = new Connect();
            Connection db = conn.ConnectDB();

//            PreparedStatement stm = db.prepareStatement("select name from Users");
//            ResultSet result = stm.executeQuery();

            Statement stm = db.createStatement();
            ResultSet result = stm.executeQuery("select * from Users");

            while(result.next()){
                System.out.println(result.getString(3));
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

}
