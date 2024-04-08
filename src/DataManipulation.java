import com.google.gson.Gson;
import org.json.simple.JSONArray;

import java.util.Scanner;
import java.sql.*;

/*
      Fetching Data From Online Database using jdbc MySql
      
      Then Making the Raw Data to a Proper JSON
      
      Done Both ways By JSONObject and Temp Class's Object

      Also parsing the JSON to objects For Further Manipulation
 */

class Temp {

    public int Id;
    public String Name;

    public void put(int val1,String val2){
        this.Id = val1;
        this.Name = val2;
    }

    public void get(){
        System.out.println(this.Id + ":" + this.Name);
    }
}

public class DataManipulation {

    public static void main(String[] args) {

        String dbUrl = "jdbc:mysql://avnadmin:AVNS_Ek3JJ8PZtbVZ-e2847I@mysql-c581f0d-mr-d4a5.a.aivencloud.com:12716/masterDB?ssl-mode=REQUIRED";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbUrl);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Temp;");

            JSONArray array = new JSONArray();

            while(resultSet.next()){
                Temp jsonObject = new Temp();
                Gson tJson = new Gson();

                jsonObject.put(resultSet.getInt(1), resultSet.getString(2));

                //jsonObject.put("Id", resultSet.getInt(1));
                // jsonObject.put("Name", resultSet.getString(2));

                array.add(tJson.toJson(jsonObject));
            }

            System.out.println(array.toString());

            Temp[] users = new Temp[array.size()];

            for(int i=0; i < array.size(); i++){
                Gson fJson = new Gson();
                users[i] = fJson.fromJson(array.get(i).toString(),Temp.class);
            }

            for(int i=0; i < users.length; i++){
                users[i].get();
            }

//            // For Parsing the Json
//            JSONParser obj = new JSONParser();
//            JSONObject fnl = (JSONObject) obj.parse(array.get(0).toString());
//            System.out.println(fnl.get("Id") + ":" + fnl.get("Name"));

            resultSet.close();
            statement.close();
            connection.close();

        }
        catch(Exception ex){
            System.out.println(ex);
        }

        Scanner input = new Scanner(System.in);

    }
}