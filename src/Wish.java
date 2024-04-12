import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Wish {

    String name;
    String description;
    String type;
    String username;

    void SetData(String a, String b, String c, String d){
        name = a;
        description = b;
        type = c;
        username = d;
    }
    public List<Wish> GetWishlist(int maxlimit){

        List<Wish> wish = new ArrayList<>();

        try{

            Connect conn = new Connect();
            Connection db = conn.ConnectDB();

            PreparedStatement stm = db.prepareStatement("SELECT Wish.itemName,Wish.description,Wish.type,U.name FROM Wish INNER JOIN Users U on Wish.usersUser_id = U.user_id ORDER BY wish_id desc FETCH FIRST ? ROWS ONLY\n");
            stm.setInt(1,maxlimit);

            ResultSet result = stm.executeQuery();

            while(result.next()){
                Wish temp = new Wish();
                temp.SetData(result.getString(1), result.getString(2),result.getString(3),result.getString(4) );
                wish.add(temp);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return wish;
    }

    public void AddAwish(String name,String description, String type,int user_id){

        try{
            Connect conn = new Connect();
            Connection db = conn.ConnectDB();

            PreparedStatement stm = db.prepareStatement("INSERT into Wish(wish_id,itemName, description, type, usersUser_id) values(wishSeq.nextval,?,?,?,?)");
            stm.setString(1,name);
            stm.setString(2,description);
            stm.setString(3,type);
            stm.setInt(4,user_id);

            stm.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }
}
