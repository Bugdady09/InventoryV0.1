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


public class CategoryFunctions {
    
    private int cid;
    private String cname;
    private Connection con = new DatabaseConnection().establishConnection();

    public CategoryFunctions(int cid, String cname) {
        this.cid = cid;
        this.cname = cname;
    }

    public CategoryFunctions() {
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
    
    public void addCategory(String name) {
        
        try {
            PreparedStatement st = con.prepareStatement("insert into category(c_name) values(?) ");
            st.setString(1, name);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void editCategory(String name, String updatedName){
        
        try {
            PreparedStatement pst = con.prepareStatement("update category set c_name = ? where c_name =? ");
            pst.setString(1, updatedName);
            pst.setString(2, name);
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CategoryFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteCategory(String name){
        
        try {
            PreparedStatement pst = con.prepareStatement("delete from category where c_name = ?");
            pst.setString(1, name);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void table_update(JTable table){
        int c;
        try {
            PreparedStatement pst = con.prepareStatement("select * from category");
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rsd = rs.getMetaData();
            c = rsd.getColumnCount();
            
            DefaultTableModel d = (DefaultTableModel) table.getModel();
            d.setRowCount(0);
            
            while (rs.next()) {                
                 
               Vector v2 = new Vector();
               
               for(int i = 1; i <= c; i++){
               
                   
                   v2.add(rs.getString("c_name"));
                  
               }
               d.addRow(v2);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CategoryFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public String toString() {
        return cname;
    }
    
    
    
}
