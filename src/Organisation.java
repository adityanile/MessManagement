import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class Organisation {

    private int org_id;
    private String password;

    public void SetDetails(String Org_id, String password){

        this.org_id = Integer.parseInt(Org_id);
        this.password = password;

    }
    public int GetOrgId(){
        return org_id;
    }

    public void DeleteAccount(int org_id){

        try{

            Connect conn = new Connect();
            Connection db = conn.ConnectDB();

            PreparedStatement stm;
            stm = db.prepareStatement("DELETE FROM LatestMeal where organisationOrg_id = ?");
            stm.setInt(1, org_id);

            stm.executeUpdate();

            stm = db.prepareStatement("DELETE FROM Organisation WHERE org_id = ?");
            stm.setInt(1, org_id);

            stm.executeUpdate();

            stm = db.prepareStatement("DELETE FROM FoodItem WHERE latestMealMeal_id is null");
            stm.executeUpdate();

            stm = db.prepareStatement("DELETE FROM Review WHERE latestMealMeal_id is null");
            stm.executeUpdate();

        }
        catch(SQLException e){
            System.out.println(e);
        }
    }

    public void ChangePassword(int org_id, String newPass){

        try{

            Connect conn = new Connect();
            Connection db = conn.ConnectDB();

            PreparedStatement stm;
            stm = db.prepareStatement("UPDATE Organisation SET password = ? WHERE org_id = ?");
            stm.setString(1,newPass);
            stm.setInt(2, org_id);

            stm.executeUpdate();
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
}
