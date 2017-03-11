/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.thogakade.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import lk.ijse.thogakade.db.DBConnection;
import lk.ijse.thogakade.models.Customer;
import lk.ijse.thogakade.models.Item;

/**
 *
 * @author student
 */
public class ItemController {

    public static Item searchItem(String itemCode) {

        Item item = null;
        try {
            String sql = "SELECT * FROM Item WHERE code = '" + itemCode + "' ";
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rset = stm.executeQuery(sql);
            if (rset.next()) {
                item = new Item(rset.getString(1),
                        rset.getString(2),
                        rset.getDouble(3),
                        rset.getInt(4));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return item;
    }

    public static ArrayList<Item> getAllItems() {
        ArrayList<Item> alItems = new ArrayList<>();

        try {

            String sql = "select * from Item";
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rset = stm.executeQuery(sql);

            while (rset.next()) {
                String code = rset.getString("code");
                String description = rset.getString("description");
                int qtyOnHand = rset.getInt("qtyOnHand");
                double unitPrice = rset.getDouble("unitPrice");

                Item item = new Item(code, description, unitPrice, qtyOnHand);
                alItems.add(item);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return alItems;
    }

}
