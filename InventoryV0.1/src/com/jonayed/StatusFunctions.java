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


public class StatusFunctions {
    private int sid;
    private String sname;
    private Connection con =  new DatabaseConnection().establishConnection();


    public StatusFunctions() {
    }

    public StatusFunctions(int sid, String sname) {
        this.sid = sid;
        this.sname = sname;
    }

    public int getSid() {
        return sid;
    }

    public String getSname() {
        return sname;
    }
    
    public void addStatus(String name) {
        
        try {
            PreparedStatement st = con.prepareStatement("insert into status(s_name) values(?) ");
            st.setString(1, name);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StatusFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void editStatus(String name, String updatedName){
        
        try {
            PreparedStatement pst = con.prepareStatement("update status set s_name = ? where s_name =? ");
            pst.setString(1, updatedName);
            pst.setString(2, name);
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(StatusFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteStatus(String name){
        
        try {
            PreparedStatement pst = con.prepareStatement("delete from status where s_name = ?");
            pst.setString(1, name);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StatusFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void table_update(JTable table){
        int c;
        try {
            PreparedStatement pst = con.prepareStatement("select * from status");
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
            Logger.getLogger(StatusFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public String toString() {
        return sname;
    }
    
    
}
