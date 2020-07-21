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


public class BrandFunctions {

    private int id;
    private String name;
    private Connection con =  new DatabaseConnection().establishConnection();
    public BrandFunctions() {
        
    }
    public BrandFunctions(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void addBrand(String name) {
        
        try {
            PreparedStatement st = con.prepareStatement("insert into brand(b_name) values(?) ");
            st.setString(1, name);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BrandFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void editBrand(String name, String updatedName){
        
        try {
            PreparedStatement pst = con.prepareStatement("update brand set b_name = ? where b_name =? ");
            pst.setString(1, updatedName);
            pst.setString(2, name);
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(BrandFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deletBrand(String name){
        
        try {
            PreparedStatement pst = con.prepareStatement("delete from brand where b_name = ?");
            pst.setString(1, name);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BrandFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void table_update(JTable table){
        int c;
        try {
            PreparedStatement pst = con.prepareStatement("select * from brand");
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rsd = rs.getMetaData();
            c = rsd.getColumnCount();
            
            DefaultTableModel d = (DefaultTableModel) table.getModel()
                    ;
            d.setRowCount(0);
            
            while (rs.next()) {                
                 
               Vector v2 = new Vector();
               
               for(int i = 1; i <= c; i++){
               
                   
                   v2.add(rs.getString("b_name"));
                  
               }
               d.addRow(v2);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BrandFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public String toString() {
        return  name;
    }
    
    
    
}
