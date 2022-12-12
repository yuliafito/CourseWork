package org.gifts.database;

import org.gifts.entity.item.*;

import java.sql.Connection;
import java.sql.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;

public class DBhandler {
    static Connection dbConnection;
    static PresentBox presentBox;
    public static Connection getConnection() throws SQLException, IOException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("src/main/java/org/gifts/database/Properties"))) {
            props.load(in);
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        return DriverManager.getConnection(url, username, password);
    }

    public static void addPresentBox(int id, String color, PresentShape shape) {
        String shapeId = "SELECT  id FROM presentshape where name LIKE \'" + shape.name()+"\'";
        String insert = "INSERT INTO `candy_store`.`presentbox` (`id`, `color`, `presentShape_id`) VALUES (?,?,?);";

        try {
            Statement statement = getConnection().createStatement();
            ResultSet shId = statement.executeQuery(shapeId);
            PreparedStatement preparestatement = getConnection().prepareStatement(insert);
            shId.next();
            preparestatement.setInt(1, id);
            preparestatement.setString(2, color);
            preparestatement.setInt(3, shId.getInt("id"));
            preparestatement.executeUpdate();
            presentBox = PresentBox.getPresentBox();
        } catch (Exception ex) {
            System.out.println("add present box error");
            System.out.println(ex);
        }
    }

    public static void addSweet(String title, float weight, int presentBoxId) {
        String sweetId = "SELECT  id FROM sweet where title LIKE \"" + title + "\"";
        String insert = "INSERT INTO presentbox_has_sweet (presentBox_id, sweet_id, weight) Values (?, ?, ?)\n";
        try {
            Statement statement = getConnection().createStatement();
            ResultSet id = statement.executeQuery(sweetId);
            id.next();
            PreparedStatement preparestatement = getConnection().prepareStatement(insert);
            preparestatement.setInt(1, presentBoxId);
            preparestatement.setInt(2, id.getInt("id"));
            preparestatement.setFloat(3, weight);
            preparestatement.executeUpdate();
            presentBox.setComponents(getSweets(presentBox.getId()));
        } catch (Exception ex) {
            System.out.println("dbhandler add sweet error");
            System.out.println(ex);
        }
    }


    public static void updateSweet(String title, float weight, int presentBoxId) {
        String sweetId = "SELECT  id FROM sweet where title LIKE \"" + title + "\"";
        try {
            Statement statement = getConnection().createStatement();
            ResultSet id = statement.executeQuery(sweetId);
            id.next();
            String update = "UPDATE presentbox_has_sweet SET weight = " + weight + " WHERE presentBox_id = " + presentBoxId + " AND sweet_id = " + id.getInt("id");
            statement.executeUpdate(update);
            presentBox.setComponents(getSweets(presentBox.getId()));
        } catch (Exception ex) {
            System.out.println("dbhandler update sweet error");
            System.out.println(ex);
        }
    }

    public static void deleteSweet(String title, int presentBoxId) {
        String sweetId = "SELECT  id FROM sweet where title LIKE \"" + title+ "\"";
        try {
            Statement statement = getConnection().createStatement();
            ResultSet id = statement.executeQuery(sweetId);
            id.next();
            String delete = "DELETE FROM presentbox_has_sweet WHERE presentBox_id = " + presentBoxId + " AND sweet_id = " + id.getInt("id");

            statement.executeUpdate(delete);
            presentBox.setComponents(getSweets(presentBox.getId()));
        } catch (Exception ex) {
            System.out.println("dbhandler delete sweet error");
            System.out.println(ex);
        }
    }

    public static List<Sweet> getSweets(int presentBoxId) {
        String select = "SELECT sweet.id, sweet.title, sweet.price, sweet.description, sweet.isAvailable, sweet.sugarLevel, sweet.calorieContent,"+
                " sweet.category_id, sweet.candy_id, presentbox_has_sweet.weight FROM presentbox_has_sweet"+
                " JOIN sweet ON presentbox_has_sweet.sweet_id = sweet.id WHERE  presentBox_id = " + presentBoxId +";";
        return template(select);
    }

    public static List<Sweet> filterSweets(int presentBoxId, String cond, int min, int max) {
        String select = "SELECT sweet.id, sweet.title, sweet.price, sweet.description, sweet.isAvailable, sweet.sugarLevel, sweet.calorieContent,"+
                " sweet.category_id, sweet.candy_id, presentbox_has_sweet.weight FROM presentbox_has_sweet"+
                " JOIN sweet ON presentbox_has_sweet.sweet_id = sweet.id WHERE  presentBox_id = " + presentBoxId +
                 " AND "+cond+" * presentbox_has_sweet.weight * 10" +" < "+max +" AND "+ cond+ " * presentbox_has_sweet.weight *10" + " > "+min+";";
        return template(select);
    }


    public static List<Sweet> template(String select) {
        List<Sweet> sweets = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery(select);
            while (result.next()) {
                int sweetId = result.getInt("id");
                float weight = result.getFloat("weight");
                Statement st = getConnection().createStatement();

                int categoryId = result.getInt("category_id");
                String title = result.getString("title");
                float price = result.getFloat("price");
                String description = result.getString("description");
                boolean isAvailable = result.getInt("isAvailable") == 1 ? true : false;

                float sugarLevel = result.getFloat("sugarLevel");
                int calorieContent = result.getInt("calorieContent");
                int candyId = result.getInt("candy_id");
                if (categoryId == 1) {
                    String selectCandy = "SELECT cocoaCount, waffle, nuts, chocolatecandyfillers.name as filler  FROM chocolatecandy" +
                            " JOIN chocolatecandyfillers ON chocolatecandyfillers.Id = chocolatecandy.chocolateCandyFillers_id WHERE chocolatecandy.id = " + candyId;

                    ResultSet candy = st.executeQuery(selectCandy);
                    candy.next();
                    float cocoaCount = candy.getFloat("cocoaCount");
                    boolean waffle = candy.getInt("waffle") == 1 ? true : false;
                    boolean nuts = candy.getInt("nuts") == 1 ? true : false;

                    ChocolateCandyFillers filler = ChocolateCandyFillers.valueOf(candy.getString("filler"));
                    sweets.add(new ChocolateCandy(sweetId, categoryId, title, price, description, isAvailable, sugarLevel,
                            weight, calorieContent, cocoaCount, waffle, nuts, filler));
                } else if (categoryId == 2) {
                    String selectCandy = "SELECT sweetsflavors.name as flavor, lollipopfiller.name as filler FROM candy_store.lollipop" +
                            " JOIN lollipopfiller ON lollipop.lollipopFiller_id = lollipopfiller.id" +
                            " JOIN sweetsflavors ON lollipop.sweetsFlavors_id = sweetsflavors.id" +
                            " WHERE lollipop.id = " + candyId;

                    ResultSet candy = st.executeQuery(selectCandy);
                    candy.next();
                    LollipopFiller filler = LollipopFiller.valueOf(candy.getString("filler"));
                    SweetsFlavors flavor = SweetsFlavors.valueOf(candy.getString("flavor"));
                    sweets.add(new Lollipop(sweetId, categoryId, title, price, description, isAvailable, sugarLevel,
                            weight, calorieContent, flavor, filler));
                } else if (categoryId == 3) {
                    String selectCandy = "SELECT sweetsflavors.name as flavor FROM candy_store.marshmallow" +
                            " JOIN sweetsflavors ON marshmallow.sweetsFlavors_id = sweetsflavors.id" +
                            " WHERE marshmallow.id = " + candyId;

                    ResultSet candy = st.executeQuery(selectCandy);
                    candy.next();
                    SweetsFlavors flavor = SweetsFlavors.valueOf(candy.getString("flavor"));
                    sweets.add(new Marshmallow(sweetId, categoryId, title, price, description, isAvailable, sugarLevel,
                            weight, calorieContent, flavor));
                }

            }
        } catch (Exception ex) {
            System.out.println("dbhandler template error");
            System.out.println(ex);
        }
        return sweets;
    }

    public static List<Sweet> getAllSweets() {
        String select = "SELECT * FROM sweet";
        List<Sweet> sweets = new ArrayList<>();
        try {
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery(select);
            while (result.next()) {
                int sweetId = result.getInt("id");
                Statement st = getConnection().createStatement();

                int categoryId = result.getInt("category_id");
                String title = result.getString("title");
                float price = result.getFloat("price");
                String description = result.getString("description");
                boolean isAvailable = result.getInt("isAvailable") == 1 ? true : false;

                float sugarLevel = result.getFloat("sugarLevel");
                int calorieContent = result.getInt("calorieContent");
                int candyId = result.getInt("candy_id");
                if (categoryId == 1) {
                    String selectCandy = "SELECT cocoaCount, waffle, nuts, chocolatecandyfillers.name as filler  FROM chocolatecandy" +
                            " JOIN chocolatecandyfillers ON chocolatecandyfillers.Id = chocolatecandy.chocolateCandyFillers_id WHERE chocolatecandy.id = " + candyId;

                    ResultSet candy = st.executeQuery(selectCandy);
                    candy.next();
                    float cocoaCount = candy.getFloat("cocoaCount");
                    boolean waffle = candy.getInt("waffle") == 1 ? true : false;
                    boolean nuts = candy.getInt("nuts") == 1 ? true : false;

                    ChocolateCandyFillers filler = ChocolateCandyFillers.valueOf(candy.getString("filler"));
                    sweets.add(new ChocolateCandy(sweetId, categoryId, title, price, description, isAvailable, sugarLevel,
                            0.0F, calorieContent, cocoaCount, waffle, nuts, filler));
                } else if (categoryId == 2) {
                    String selectCandy = "SELECT sweetsflavors.name as flavor, lollipopfiller.name as filler FROM candy_store.lollipop" +
                            " JOIN lollipopfiller ON lollipop.lollipopFiller_id = lollipopfiller.id" +
                            " JOIN sweetsflavors ON lollipop.sweetsFlavors_id = sweetsflavors.id" +
                            " WHERE lollipop.id = " + candyId;

                    ResultSet candy = st.executeQuery(selectCandy);
                    candy.next();
                    LollipopFiller filler = LollipopFiller.valueOf(candy.getString("filler"));
                    SweetsFlavors flavor = SweetsFlavors.valueOf(candy.getString("flavor"));
                    sweets.add(new Lollipop(sweetId, categoryId, title, price, description, isAvailable, sugarLevel,
                            0.0F, calorieContent, flavor, filler));
                } else if (categoryId == 3) {
                    String selectCandy = "SELECT sweetsflavors.name as flavor FROM candy_store.marshmallow" +
                            " JOIN sweetsflavors ON marshmallow.sweetsFlavors_id = sweetsflavors.id" +
                            " WHERE marshmallow.id = " + candyId;

                    ResultSet candy = st.executeQuery(selectCandy);
                    candy.next();
                    SweetsFlavors flavor = SweetsFlavors.valueOf(candy.getString("flavor"));
                    sweets.add(new Marshmallow(sweetId, categoryId, title, price, description, isAvailable, sugarLevel,
                            0.0F, calorieContent, flavor));
                }

            }
        } catch (Exception ex) {
            System.out.println("dbhandler get all sweet error");
            System.out.println(ex);
        }
        return sweets;
    }
}


