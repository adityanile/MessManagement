import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// This Class will have methods for Login, Register, Change Credentails
public class Authentication {

    // This is to Verify User Login
    public boolean ValidateLogin(String username, String password){

        String correctPass = "";

        try{

            Connect db = new Connect();
            Connection conn = db.ConnectDB();

            PreparedStatement stm = conn.prepareStatement("SELECT password from Users Where user_id = ?");
            stm.setInt(1,Integer.parseInt(username));

            ResultSet result = stm.executeQuery();

            while(result.next()){
                correctPass = result.getString(1);
                break;
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        System.out.println(correctPass);
        return correctPass.equals(password);
    }
    public boolean ValidateOrgLogin(String username, String password){

        boolean status = false;
        String correctPass = "";

        try{

            Connect db = new Connect();
            Connection conn = db.ConnectDB();

            PreparedStatement stm = conn.prepareStatement("SELECT password from Organisation Where org_id = ?");
            stm.setInt(1,Integer.parseInt(username));

            ResultSet result = stm.executeQuery();

            while(result.next()){
                correctPass = result.getString(1);
                break;
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return correctPass.equals(password);
    }

    public int RegisterOragnisation(String name, String desc, String contact, String address, String password){

        int org_id = 0;

        try{
            Connect db = new Connect();
            Connection conn = db.ConnectDB();

            PreparedStatement stm;

            stm = conn.prepareStatement("INSERT INTO Organisation(org_id, org_name, contact, address, password, description) values (orgSeq.nextval,?,?,?,?,?)");
            stm.setString(1,name);
            stm.setString(5,desc);
            stm.setString(2,contact);
            stm.setString(3,address);
            stm.setString(4,password);

            stm.executeUpdate();

            stm = conn.prepareStatement("SELECT org_id FROM Organisation WHERE org_name = ? AND password = ?");
            stm.setString(1,name);
            stm.setString(2,password);

            ResultSet result = stm.executeQuery();

            while(result.next()){
                org_id = result.getInt(1);
                break;
            }

        }
        catch (SQLException e){
            System.out.println(e);
        }
        return org_id;
    }

    public int RegisterUser(String name,String password){

        int usr_id = 0;

        try{
            Connect db = new Connect();
            Connection conn = db.ConnectDB();

            PreparedStatement stm;

            stm = conn.prepareStatement("INSERT INTO Users(user_id, name, password) values(userSeq.nextval, ?,?)");
            stm.setString(1,name);
            stm.setString(2,password);
            stm.executeUpdate();

            stm = conn.prepareStatement("SELECT user_id FROM Users WHERE name = ? AND password = ?");
            stm.setString(1,name);
            stm.setString(2,password);

            ResultSet result = stm.executeQuery();

            while(result.next()){
                usr_id = result.getInt(1);
                break;
            }

        }
        catch (SQLException e){
            System.out.println(e);
        }
        return usr_id;
    }


    public boolean ValidatePassword(String pass){

        if(pass.length() >= 6){
            return true;
        }
        else{
            return false;
        }

    }

}
