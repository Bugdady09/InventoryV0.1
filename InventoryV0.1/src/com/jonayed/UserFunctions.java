package com.jonayed;

import com.jonayed.Connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UserFunctions {
    
    
    private String uname;
    private String upass;
    private Connection con =  new DatabaseConnection().establishConnection();


    public UserFunctions() {
    }

    public UserFunctions(String uname, String upass) {
        this.uname = uname;
        this.upass = upass;
    }
    
    public void addUser(String uname, String pass) {
        
        try {
            PreparedStatement st = con.prepareStatement("insert into user(u_name,u_pass) values(?,?) ");
            st.setString(1, uname);
            st.setString(2, pass);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StatusFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteUser(String name){
        
        try {
            PreparedStatement pst = con.prepareStatement("delete from user where u_name = ?");
            pst.setString(1, name);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StatusFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void table_update(JTable table){
        int c;
        try {
            PreparedStatement pst = con.prepareStatement("select * from user");
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rsd = rs.getMetaData();
            c = rsd.getColumnCount();
            
            DefaultTableModel d = (DefaultTableModel) table.getModel();
            d.setRowCount(0);
            
            while (rs.next()) {                
                 
               Vector v2 = new Vector();
               
               for(int i = 1; i <= c; i++){
               
                   
                   v2.add(rs.getString("u_name"));
                  
               }
               d.addRow(v2);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}
