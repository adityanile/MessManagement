import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Review {

    public int review;
    public String feedback;
    public String username;

    public void SetData(int a, String b, String c){
     review = a;
     feedback = b;
     username = c;
    }
    public void RateAMeal(int meal_id,int user_id, int rating, String feedback){

        try{
            Connect conn = new Connect();
            Connection db = conn.ConnectDB();

            PreparedStatement stm = db.prepareStatement("INSERT INTO Review(rev_id,feedback, rating, latestMealMeal_id,usersUser_id) values (reviewSeq.nextval,?,?,?,?)");
            stm.setString(1,feedback);
            stm.setInt(2,rating);
            stm.setInt(3,meal_id);
            stm.setInt(4,user_id);

            stm.executeUpdate();

        }catch (SQLException e){
            System.out.println(e);
        }
    }
    List<Review> GetMealReviews(int meal_id){

        List<Review> items = new ArrayList<>();

        try{
            Connect conn = new Connect();
            Connection db = conn.ConnectDB();

            PreparedStatement stm = db.prepareStatement("SELECT Review.rating, Review.feedback, U.name FROM Review INNER JOIN Users U on Review.usersUser_id = U.user_id where latestMealMeal_id = ? ORDER BY rev_id desc");
            stm.setInt(1,meal_id);

            ResultSet result = stm.executeQuery();

            while(result.next()){
                Review itm = new Review();
                itm.SetData(result.getInt(1),result.getString(2), result.getString(3));
                items.add(itm);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return items;
    }

}
