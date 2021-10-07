/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appPack;

/**
 *
 * @author HP
 */
public class Utils {

    public static void refreshCustomers(javax.swing.JTable tbl_customers) {
        DB db = new DB();
        tbl_customers.setModel(db.allCustomers());
        db.close();
    }
    public static void refreshOrders(javax.swing.JTable tbl_orders){
        DB db = new DB();
        tbl_orders.setModel(db.allOrders());
        db.close();
    }
}
