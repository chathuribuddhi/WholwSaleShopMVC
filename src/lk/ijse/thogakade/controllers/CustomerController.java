/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.thogakade.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import lk.ijse.thogakade.db.DBConnection;
import lk.ijse.thogakade.models.Customer;

/**
 *
 * @author Dilshan
 */
public class CustomerController {

    public static boolean addCustomer(Customer customer) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement("INSERT INTO Customer VALUES(?,?,?,?)");
            pst.setObject(1, customer.getId());
            pst.setObject(2, customer.getName());
            pst.setObject(3, customer.getAddress());
            pst.setObject(4, customer.getSalary());
            int result = pst.executeUpdate();
            return (result > 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static Customer searchCustomer(String id) {

        Customer customer = null;
        try {
            String sql = "SELECT * FROM Customer WHERE id = '" + id + "' ";
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rset = stm.executeQuery(sql);
            if (rset.next()) {
                customer = new Customer(
                        rset.getString(1),
                        rset.getString(2),
                        rset.getString(3),
                        rset.getDouble(4));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return customer;
    }

    public static boolean updateCustomer(Customer customer) {

        try {
            String sql = "UPDATE customer SET name = ?, address = ?, salary = ? WHERE id = ? ";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setObject(4, customer.getId());
            pst.setObject(1, customer.getName());
            pst.setObject(2, customer.getAddress());
            pst.setObject(3, customer.getSalary());
            int result = pst.executeUpdate();
            return (result > 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean deleteCustomer(Customer customer) {
        try {

            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM customer WHERE id = ? ";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setObject(1, customer.getId());
            int result = pst.executeUpdate();
            return (result > 0);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static ArrayList<Customer> getAllCustomers() {
        
        ArrayList<Customer> alCustomers = new ArrayList<>();
        
        try {

            String sql = "select * from Customer";
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rset = stm.executeQuery(sql);

            while (rset.next()) {
                String id = rset.getString("id");
                String name = rset.getString("name");
                String address = rset.getString("address");
                double salary = rset.getDouble("salary");

                Customer customer = new Customer(id, name, address, salary);
                alCustomers.add(customer);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return alCustomers;
    }

}
