import java.sql.*;
import java.util.Scanner;

public class Oracle_Setup {

    public static void main(String[] arg){

            Scanner input = new Scanner(System.in);
            String username = input.nextLine();

            try {

                Connect db = new Connect();
                Connection conn = db.ConnectDB();
                PreparedStatement stm = conn.prepareStatement("Select id from Temp where name = ?");
                stm.setString(1, username);

                ResultSet result = stm.executeQuery();

                Login login = new Login();
                login.Start();

                while (result.next()) {
                    System.out.println(result.getInt(1));
                }

            }
            catch (SQLException e){
                System.out.println(e);
            }


    }

}
