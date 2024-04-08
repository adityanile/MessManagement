import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Order {

    String address;
    boolean delivered;
    String username;
    int order_id;

    public void SetData(String a, boolean b,String d,int e){
        address = a;
        delivered = b;
        username = d;
        order_id = e;
    }

    public void OrderTheMeal(String address, int meal_id,int user_id){

        try{

            Connect conn = new Connect();
            Connection db = conn.ConnectDB();

            PreparedStatement stm = db.prepareStatement("INSERT INTO Orders(order_id,address,latestMealMeal_id, usersUser_id) values(orderSeq.nextval,?,?,?)");
            stm.setString(1,address);
            stm.setInt(2,meal_id);
            stm.setInt(3,user_id);

            stm.executeUpdate();

        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public List<Order> GetOrderDetails(int meal_id){

        List<Order> odrs = new ArrayList<>();

        try{

            Connect conn = new Connect();
            Connection db = conn.ConnectDB();

            PreparedStatement stm = db.prepareStatement("SELECT Orders.address,Orders.delivered,U.name,Orders.order_id FROM Orders INNER JOIN Users U on Orders.usersUser_id = U.user_id WHERE latestMealMeal_id = ? AND delivered is null ORDER BY order_id DESC");
            stm.setInt(1,meal_id);

            ResultSet result = stm.executeQuery();

            while(result.next()){
                Order temp = new Order();
                temp.SetData(result.getString(1), result.getBoolean(2),result.getString(3),result.getInt(4) );
                odrs.add(temp);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return odrs;
    }

    public List<Order> GetDoneOrderDetails(int meal_id){

        List<Order> odrs = new ArrayList<>();

        try{

            Connect conn = new Connect();
            Connection db = conn.ConnectDB();

            PreparedStatement stm = db.prepareStatement("SELECT Orders.address,Orders.delivered,U.name,Orders.order_id FROM Orders INNER JOIN Users U on Orders.usersUser_id = U.user_id WHERE latestMealMeal_id = ? AND delivered = 'd' ORDER BY order_id DESC");
            stm.setInt(1,meal_id);

            ResultSet result = stm.executeQuery();

            while(result.next()){
                Order temp = new Order();

                String del = result.getString(2);
                boolean status = false;

                if(del.equals('d')){
                    status = true;
                }

                temp.SetData(result.getString(1),status,result.getString(3),result.getInt(4) );
                odrs.add(temp);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        System.out.println(odrs.size());
        return odrs;
    }

    void MarkOrdersCompleted(int order_id){
        try{

            Connect conn = new Connect();
            Connection db = conn.ConnectDB();

            PreparedStatement stm = db.prepareStatement("UPDATE Orders SET delivered = 'd' WHERE order_id = ?");
            stm.setInt(1,order_id);

            stm.executeUpdate();

        }catch (SQLException e){
            System.out.println(e);
        }

    }



}
