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
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AddProductFunctions {
    
    private String barcode;
    private int categoryId;
    private int brandId;
    private double costPrice;
    private double sellPrice;
    private int statusId;
    private int sizeId;
    private String sql = "insert into product(barcode, category_id, brand_id, cost_price, sell_price, status_id, size_id) value(?,?,?,?,?,?,?)";
    private String sql2 = "UPDATE product SET category_id = ?, brand_id = ?, cost_price = ?, sell_price = ?, status_id = ?, size_id = ? WHERE barcode = ?";
    private String sql3 = "delete from product where barcode =?";
    private Connection con =  new DatabaseConnection().establishConnection();


    public AddProductFunctions() {
    }

    public AddProductFunctions(String barcode, int categoryId, int brandId, double costPrice, double sellPrice, int statusId, int sizeId) {
        this.barcode = barcode;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.costPrice = costPrice;
        this.sellPrice = sellPrice;
        this.statusId = statusId;
        this.sizeId = sizeId;
    }
    
    public int  addProduct(){
        int i = 1;
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1,barcode);
            st.setInt(2,categoryId);
            st.setInt(3,brandId);
            st.setDouble(4,costPrice);
            st.setDouble(5,sellPrice);
            st.setInt(6,statusId);
            st.setInt(7,sizeId);
            st.executeUpdate();
            return i;
        } catch (SQLException ex) {
            Logger.getLogger(StatusFunctions.class.getName()).log(Level.SEVERE, null, ex);
            i = 0;
            return i;
        }
    }
    public void  updateProduct(){
        
        try {
            PreparedStatement st = con.prepareStatement(sql2);
            
            st.setInt(1,categoryId);
            st.setInt(2,brandId);
            st.setDouble(3,costPrice);
            st.setDouble(4,sellPrice);
            st.setInt(5,statusId);
            st.setInt(6,sizeId);
            st.setString(7,barcode);
            st.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(StatusFunctions.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
    public void deleteProduct(String b){
        try {
            PreparedStatement st = con.prepareStatement(sql3);
            st.setString(1, b);
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AddProductFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void categoryLoadCombobox(JComboBox cCombo){
        
        try {
            PreparedStatement pst = con.prepareStatement("select * from category");
            ResultSet rs = pst.executeQuery();
            cCombo.removeAllItems();
            
            while (rs.next()) {                
                cCombo.addItem(new CategoryFunctions(rs.getInt(1), rs.getString(2)));
        
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AddProductFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void brandLoadCombobox(JComboBox cCombo){
        
        try {
            PreparedStatement pst = con.prepareStatement("select * from brand");
            ResultSet rs = pst.executeQuery();
            cCombo.removeAllItems();
            
            while (rs.next()) {                
                cCombo.addItem(new BrandFunctions(rs.getInt(1), rs.getString(2)));
        
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AddProductFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void statusLoadCombobox(JComboBox cCombo){
        
        try {
            PreparedStatement pst = con.prepareStatement("select * from status");
            ResultSet rs = pst.executeQuery();
            cCombo.removeAllItems();
            
            while (rs.next()) {                
                cCombo.addItem(new StatusFunctions(rs.getInt(1), rs.getString(2)));
        
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AddProductFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sizeLoadCombobox(JComboBox cCombo){
        
        try {
            PreparedStatement pst = con.prepareStatement("select * from size");
            ResultSet rs = pst.executeQuery();
            cCombo.removeAllItems();
            
            while (rs.next()) {                
                cCombo.addItem(new SizeFunctions(rs.getInt(1), rs.getString(2)));
        
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AddProductFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void table_update(JTable table){
        int c;
        String sql = "select p.barcode, c.c_name, b.b_name, p.cost_price, p.sell_price, s.s_name, st.s_name from product p, category c, brand b, size s , status st where p.category_id = c.c_id and \n" +
"p.brand_id = b.b_id and p.size_id = s.s_id and p.status_id = st.s_id";
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rsd = rs.getMetaData();
            c = rsd.getColumnCount();
            
            DefaultTableModel d = (DefaultTableModel) table.getModel();
            d.setRowCount(0);
            
            while (rs.next()) {                
                 
               Vector v2 = new Vector();
               
               for(int i = 1; i <= c; i++) {
                   
                   v2.add(rs.getString(1));
                   v2.add(rs.getString(2));
                   v2.add(rs.getString(3));
                   v2.add(rs.getString(4));
                   v2.add(rs.getString(5));
                   v2.add(rs.getString(6));
                   v2.add(rs.getString(7));
                   
               }
               d.addRow(v2);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public String toString() {
        return "AddProductFunctions{" + "barcode=" + barcode + ", categoryId=" + categoryId + ", brandId=" + brandId + ", costPrice=" + costPrice + ", sellPrice=" + sellPrice + ", statusId=" + statusId + ", sizeId=" + sizeId + '}';
    }
    
    
}
