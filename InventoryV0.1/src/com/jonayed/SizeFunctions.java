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

public class SizeFunctions {
    private int sid;
    private String sname;
    private Connection con =  new DatabaseConnection().establishConnection();

    public SizeFunctions() {
    }

    public SizeFunctions(int sid, String sname) {
        this.sid = sid;
        this.sname = sname;
    }

    public int getSid() {
        return sid;
    }

    public String getSname() {
        return sname;
    }
    
    public void addSize(String name) {
        
        try {
            PreparedStatement st = con.prepareStatement("insert into size(s_name) values(?) ");
            st.setString(1, name);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SizeFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void editSize(String name, String updatedName){
        
        try {
            PreparedStatement pst = con.prepareStatement("update size set s_name = ? where s_name =? ");
            pst.setString(1, updatedName);
            pst.setString(2, name);
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(SizeFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteSize(String name){
        
        try {
            PreparedStatement pst = con.prepareStatement("delete from size where s_name = ?");
            pst.setString(1, name);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SizeFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void table_update(JTable table){
        int c;
        try {
            PreparedStatement pst = con.prepareStatement("select * from size");
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rsd = rs.getMetaData();
            c = rsd.getColumnCount();
            
            DefaultTableModel d = (DefaultTableModel) table.getModel();
            d.setRowCount(0);
            
            while (rs.next()) {                
                 
               Vector v2 = new Vector();
               
               for(int i = 1; i <= c; i++){
               
                   
                   v2.add(rs.getString("s_name"));
                  
               }
               d.addRow(v2);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SizeFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public String toString() {
        return sname;
    }
    
    
    
}
