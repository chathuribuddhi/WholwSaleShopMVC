/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.thogakade.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lk.ijse.thogakade.db.DBConnection;
import lk.ijse.thogakade.models.Orderdetail;
import lk.ijse.thogakade.models.Orders;

/**
 *
 * @author student
 */
public class PlaceOrderController {

    public static boolean saveOrder(Orders order, ArrayList<Orderdetail> alOrderDetails) {

        try {

            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO Orders VALUES(?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setObject(1, order.getId());
            pstm.setObject(2, order.getDate());
            pstm.setObject(3, order.getCustomerId());

            int result = pstm.executeUpdate();

            if (result > 0) {

                sql = "INSERT INTO OrderDetail VALUES(?,?,?,?)";
                pstm = connection.prepareStatement(sql);

                String sql4Qty = "UPDATE Item SET qtyOnHand=? WHERE code=?";
                PreparedStatement pstm4Qty = connection.prepareStatement(sql4Qty);

                for (Orderdetail orderdetail : alOrderDetails) {
                    pstm.setObject(1, order.getId());
                    pstm.setObject(2, orderdetail.getItemCode());
                    pstm.setObject(3, orderdetail.getQty());
                    pstm.setObject(4, orderdetail.getUnitPrice());

                    result = pstm.executeUpdate();
                    if (result < 0) {
                        return false;
                    } else {

                        Statement stm = connection.createStatement();
                        ResultSet rst = stm.executeQuery("SELECT qtyOnHand FROM Item WHERE code='" + orderdetail.getItemCode() + "'");
                        rst.next();

                        int Qty = rst.getInt(1);

                        pstm4Qty.setObject(1, Qty - orderdetail.getQty());
                        pstm4Qty.setObject(2, orderdetail.getItemCode());

                        result = pstm4Qty.executeUpdate();
                        
                    }

                }

            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PlaceOrderController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

}
