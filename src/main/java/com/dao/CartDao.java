package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bean.Cart;
import com.util.ProjectUtil;

public class CartDao {

	public static void addToCart(Cart c) {
			try {
				Connection conn = ProjectUtil.createConnection();
				String sql = "INSERT INTO Cart(uid, pid, product_price, product_qty, total_price) VALUES(?, ?, ?, ?, ?)";
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setInt(1, c.getUid());
				pst.setInt(2, c.getPid());
				pst.setInt(3, c.getProduct_price());
				pst.setInt(4, c.getProduct_qty());
				pst.setInt(5, c.getTotal_price());
				pst.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public static List<Cart> getCartByUser(int uid) {
		boolean payment_status = false;
		List<Cart> list = new ArrayList<Cart>();
		try {
			Connection conn = ProjectUtil.createConnection();
			String sql = "SELECT * FROM cart WHERE uid=? and payment_status=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, uid);
			pst.setBoolean(2, payment_status);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Cart c = new Cart();
				c.setPid(rs.getInt("pid"));
				c.setUid(rs.getInt("uid"));
				c.setCid(rs.getInt("cid"));
				c.setProduct_price(rs.getInt("product_price"));
				c.setProduct_qty(rs.getInt("product_qty"));
				c.setTotal_price(rs.getInt("total_price"));
				c.setPayment_status(rs.getBoolean("payment_status"));
				
				list.add(c);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Cart> getOrdersByUser(int uid) {
		boolean payment_status = true;
		List<Cart> list = new ArrayList<Cart>();
		try {
			Connection conn = ProjectUtil.createConnection();
			String sql = "SELECT * FROM cart WHERE uid=? AND payment_status=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, uid);
			pst.setBoolean(2, payment_status);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Cart c = new Cart();
				c.setPid(rs.getInt("pid"));
				c.setUid(rs.getInt("uid"));
				c.setCid(rs.getInt("cid"));
				c.setProduct_price(rs.getInt("product_price"));
				c.setProduct_qty(rs.getInt("product_qty"));
				c.setTotal_price(rs.getInt("total_price"));
				c.setPayment_status(rs.getBoolean("payment_status"));
				
				list.add(c);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Cart> getOrdersByAllUser() {
		boolean payment_status = true;
		List<Cart> list = new ArrayList<Cart>();
		try {
			Connection conn = ProjectUtil.createConnection();
			String sql = "SELECT * FROM cart WHERE payment_status=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			
			pst.setBoolean(1, payment_status);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Cart c = new Cart();
				c.setPid(rs.getInt("pid"));
				c.setUid(rs.getInt("uid"));
				c.setCid(rs.getInt("cid"));
				c.setProduct_price(rs.getInt("product_price"));
				c.setProduct_qty(rs.getInt("product_qty"));
				c.setTotal_price(rs.getInt("total_price"));
				c.setPayment_status(rs.getBoolean("payment_status"));
				
				list.add(c);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static boolean checkCart(int uid, int pid) {
		boolean flag = false;
		boolean payment_status = false;
		try {
			Connection conn = ProjectUtil.createConnection();
			String sql = "SELECT * FROM cart WHERE uid=? AND pid=? AND payment_status=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, uid);
			pst.setInt(2, pid);
			pst.setBoolean(3, payment_status);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public static boolean removeFromCart(Cart c) {
		boolean flag = false;
		try {
			Connection conn = ProjectUtil.createConnection();
			String sql = "DELETE FROM cart WHERE uid=? AND pid=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, c.getUid());
			pst.setInt(2, c.getPid());
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public static Cart getCart(int cid) {
		
		Cart c = null;
		try {
			Connection conn = ProjectUtil.createConnection();
			String sql = "SELECT * FROM cart WHERE cid=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, cid);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				c = new Cart();
				c.setPid(rs.getInt("pid"));
				c.setUid(rs.getInt("uid"));
				c.setCid(rs.getInt("cid"));
				c.setProduct_price(rs.getInt("product_price"));
				c.setProduct_qty(rs.getInt("product_qty"));
				c.setTotal_price(rs.getInt("total_price"));
				c.setPayment_status(rs.getBoolean("payment_status"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public static void updateCart(Cart c) {
		
		try {
			Connection conn = ProjectUtil.createConnection();
			String sql = "UPDATE cart SET product_qty=?, total_price=? WHERE cid=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, c.getProduct_qty());
			pst.setInt(2, c.getTotal_price());
			pst.setInt(3, c.getCid());
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
public static void updatePayment(int uid) {
		
		try {
			boolean payment_status = true;
			Connection conn = ProjectUtil.createConnection();
			String sql = "UPDATE cart SET payment_status=? WHERE uid=?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setBoolean(1, payment_status);
			pst.setInt(2, uid);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
