import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Meal {


    public int ScheduleAMeal(String type, int org_Id){

        int meal_id = 0;

        try{
            Connect db = new Connect();
            Connection conn = db.ConnectDB();

            PreparedStatement stm;

            System.out.println(org_Id);

            stm = conn.prepareStatement("insert into LatestMeal(meal_id,type,organisationOrg_id) values (mealSeq.nextval,?,?)");
            stm.setString(1,type);
            stm.setInt(2,org_Id);

            stm.executeUpdate();

            stm = conn.prepareStatement("SELECT meal_id FROM LatestMeal WHERE type = ? AND organisationOrg_id = ? ORDER BY meal_id desc");
            stm.setString(1,type);
            stm.setInt(2,org_Id);

            ResultSet result = stm.executeQuery();

            while(result.next()){
                meal_id = result.getInt(1);
                break;
            }

        }
        catch (SQLException e){
            System.out.println(e);
        }
        return meal_id;
    }

    public void AddFoodItem(int meal_id, String name, String type, String desc){


        try{

            Connect conn = new Connect();
            Connection db = conn.ConnectDB();

            PreparedStatement stm;

            stm = db.prepareStatement("INSERT INTO FoodItem(item_id,name,type,latestMealMeal_id,description) values(itemSeq.nextval,?,?,?,?)");
            stm.setString(1,name);
            stm.setString(2,type);
            stm.setInt(3,meal_id);
            stm.setString(4,desc);

            stm.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e);
        }

    }

    public int SearchLatestMeal(int Org_id, String type){

        int meal_id = 0;

          try{

              Connect conn = new Connect();
              Connection db = conn.ConnectDB();

              PreparedStatement stm = db.prepareStatement("SELECT meal_id FROM LatestMeal WHERE type = ? AND organisationOrg_id = ? ORDER BY LatestMeal.meal_id desc");
              stm.setString(1, type);
              stm.setInt(2, Org_id);

              ResultSet resultSet = stm.executeQuery();

              while(resultSet.next()){
                  meal_id = resultSet.getInt(1);
                  break;
              }
          }
          catch(SQLException e){
              System.out.println(e);
          }
        return meal_id;
    }

    List<FoodItem> GetFoodItem(int meal_id){

        List<FoodItem> items = new ArrayList<>();

        try{
            Connect conn = new Connect();
            Connection db = conn.ConnectDB();

            PreparedStatement stm = db.prepareStatement("SELECT name, FoodItem.type, description from FoodItem INNER JOIN LatestMeal ON FoodItem.latestMealMeal_id = LatestMeal.meal_id WHERE meal_id = ?");
            stm.setInt(1,meal_id);

            ResultSet result = stm.executeQuery();

            while(result.next()){
                FoodItem itm = new FoodItem();
                itm.SetData(result.getString(1),result.getString(2), result.getString(3));
                items.add(itm);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return items;
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

    // This will be get us the lastest meal ids of all type of meals
    public int[] GetMealsID(int org_id){

        int[] ids = new int[4];
        int tempID = 0;

         try{

             Connect conn = new Connect();
             Connection db = conn.ConnectDB();

             PreparedStatement stm = db.prepareStatement("SELECT LatestMeal.meal_id from LatestMeal where organisationOrg_id = ? AND type = ? ORDER BY LatestMeal.meal_id desc");

             String[] type = {"Breakfast","Lunch","Snacks","Dinner"};

             for(int i=0;i < 4;i++){

                 tempID = 0;

                 stm.setInt(1,org_id);
                 stm.setString(2,type[i]);

                 ResultSet result = stm.executeQuery();

                 while (result.next()){
                     tempID = result.getInt(1);
                     break;
                 }
                 ids[i] = tempID;
             }
         }
         catch (SQLException e){
             System.out.println(e);
         }
         return ids;
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

    public void DeleteAllMeals(int org_id){

        try{

            Connect conn = new Connect();
            Connection db = conn.ConnectDB();

            PreparedStatement stm;
            stm = db.prepareStatement("DELETE FROM LatestMeal where organisationOrg_id = ?");
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
    public void DeleteSelectedMeal(int meal_id){

        try{

            Connect conn = new Connect();
            Connection db = conn.ConnectDB();

            PreparedStatement stm;

            stm = db.prepareStatement("DELETE FROM FoodItem WHERE latestMealMeal_id = ?");
            stm.setInt(1, meal_id);

            stm.executeUpdate();

            stm = db.prepareStatement("delete from Review where latestMealMeal_id = ?");
            stm.setInt(1, meal_id);

            stm.executeUpdate();

            stm = db.prepareStatement("delete from Orders where latestMealMeal_id = ?");
            stm.setInt(1, meal_id);

            stm.executeUpdate();

            stm = db.prepareStatement("DELETE FROM LatestMeal WHERE meal_id = ?");
            stm.setInt(1, meal_id);

            stm.executeUpdate();

        }
        catch(SQLException e){
            System.out.println(e);
        }


    }

    public void DeleteAllLatestMeal(int org_id){
        int[] ids = GetMealsID(org_id);
        for(int id : ids){
            DeleteSelectedMeal(id);
        }
    }

}
