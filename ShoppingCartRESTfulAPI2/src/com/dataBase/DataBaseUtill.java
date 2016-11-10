package com.dataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dataModel.BasketDS;
import com.dataModel.BasketItemDS;
import com.dataModel.ItemDS;

public class DataBaseUtill {
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String username = "c##user2016";
	private static final String password = "cw";
	private static final int initConns = 2;
	private static final int maxConns = 2;
	private static final boolean waitIfBusy = true;
	private static ConnectionPool connectionPool = null; 
	
	private static ConnectionPool getConnectionPool(){
		if(connectionPool == null) {
			connectionPool = new ConnectionPool(driver, url, username, password, initConns, maxConns, waitIfBusy);
		}
		return connectionPool;
	}
	/******************basket**************************************************/
	public static BasketDS getBaseket(String basketId) throws SQLException{
		Connection conn = getConnectionPool().getConnection();
		
		BasketDS cart = new BasketDS();
	    Statement stmt = conn.createStatement();
	    ResultSet rset = stmt.executeQuery("select * from basket where basket_id = "+ basketId);
	    while (rset.next()) {
	      cart.setBasket_id((Integer.valueOf(rset.getString(1)).intValue()));
	    }
	    stmt.close();
	    connectionPool.free(conn);
	    if(cart.getBasket_id() ==0){
	    	return null;
	    }
	    return cart;
	}
	
	public static void deleteBaseket(String basketId) throws SQLException{
		Connection conn = getConnectionPool().getConnection();
		
	    Statement stmt = conn.createStatement();
	    stmt.executeUpdate("delete basket where basket_id = "+ basketId);
	    conn.commit();
	    stmt.close();
	    connectionPool.free(conn);
	}
	
	public static void updateBaseket(String basketId, String newBasketId) throws SQLException{
		Connection conn = getConnectionPool().getConnection();
		
	    Statement stmt = conn.createStatement();
	    stmt.executeUpdate("update basket set basket_id="+newBasketId+" where basket_id = "+ basketId);
	    conn.commit();
	    stmt.close();
	    connectionPool.free(conn);
	}
	
	public static void createBaseket(String basketId) throws SQLException{
		Connection conn = getConnectionPool().getConnection();
		
	    Statement stmt = conn.createStatement();
	    String sql = "INSERT INTO basket (basket_id) VALUES ('"+basketId+"')";
	    stmt.executeUpdate(sql);
	    conn.commit();
	    stmt.close();
	    connectionPool.free(conn);
	}
	
	/******************item**************************************************/
	public static List<ItemDS> getAllItem()throws SQLException{
		Connection conn = getConnectionPool().getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("select * from item");
		List<ItemDS> listOfItem = new ArrayList<ItemDS>();
		while (rset.next()) {
			ItemDS theItem = new ItemDS();
	    	theItem.setItemId((Integer.valueOf(rset.getString(1)).intValue()));
	        theItem.setName(rset.getString(2));
	        theItem.setRate((Double.valueOf(rset.getString(3)).doubleValue()));
	        theItem.setInventory((Integer.valueOf(rset.getString(4)).intValue()));
	    	listOfItem.add(theItem);
		}
		return listOfItem;
	}
	
	public static ItemDS getItem(String itemId) throws SQLException{
		Connection conn = getConnectionPool().getConnection();
		
		ItemDS theItem = new ItemDS();
	    Statement stmt = conn.createStatement();
	    ResultSet rset = stmt.executeQuery("select * from item where item_id = "+ itemId);
	    while (rset.next()) {
	    	theItem.setItemId((Integer.valueOf(rset.getString(1)).intValue()));
	        theItem.setName(rset.getString(2));
	        theItem.setRate((Double.valueOf(rset.getString(3)).doubleValue()));
	        theItem.setInventory((Integer.valueOf(rset.getString(4)).intValue()));
	    }
	    stmt.close();
	    connectionPool.free(conn);
	    if(theItem.getItemId() ==0){
	    	return null;
	    }
	    
	    return theItem;
	}
	
	public static void deleteItem(String itemId) throws SQLException{
		Connection conn = getConnectionPool().getConnection();
		
	    Statement stmt = conn.createStatement();
	    stmt.executeUpdate("delete item where item_id = "+ itemId);
	    conn.commit();
	    stmt.close();
	    connectionPool.free(conn);
	}
	
	public static void updateItem(String itemId, String name ,String rate, String inventory) throws SQLException{
		Connection conn = getConnectionPool().getConnection();
		
	    Statement stmt = conn.createStatement();
	    stmt.executeUpdate("update item set name='"+ name +"',rate='"+ rate +"',inventory='"+ inventory +"' where item_id = "+ itemId);
	    conn.commit();
	    stmt.close();
	    connectionPool.free(conn);
	}
	
	public static void createItem(String itemId, String name, String rate, String inventory ) throws SQLException{
		Connection conn = getConnectionPool().getConnection();
		
	    Statement stmt = conn.createStatement();
	    String sql = "INSERT INTO item (item_id,name,rate,inventory) VALUES ('"+itemId+"','"+name+"','"+rate+"','"+inventory+"')";
	    stmt.executeUpdate(sql);
	    conn.commit();
	    stmt.close();    
	    connectionPool.free(conn);
	}
	
	/***************************basketItem*********************************/
	public static List<BasketItemDS> getAllBasketItem(String basketId)throws SQLException{
		Connection conn = getConnectionPool().getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("select * from basket_item where basket_id = " +basketId);
		List<BasketItemDS> listOfBasketItem = new ArrayList<BasketItemDS>();
		while (rset.next()) {
			BasketItemDS basketItem = new BasketItemDS();
	    	basketItem.setBasketId((Integer.valueOf(rset.getString(1)).intValue()));
	    	basketItem.setItemId((Integer.valueOf(rset.getString(2)).intValue()));
	    	basketItem.setName(rset.getString(3));
	    	basketItem.setPrice((Double.valueOf(rset.getString(4)).doubleValue()));
	    	basketItem.setQuantity((Integer.valueOf(rset.getString(5)).intValue()));
	    	basketItem.setScale((Integer.valueOf(rset.getString(6)).intValue()));
	    	listOfBasketItem.add(basketItem);
		}
		return listOfBasketItem;
	}
	
	public static BasketItemDS getBasketItem(String basketId,String itemId) throws SQLException{
		Connection conn = getConnectionPool().getConnection();
		BasketItemDS basketItem = new BasketItemDS();
	    Statement stmt = conn.createStatement();
	    ResultSet rset = stmt.executeQuery("select * from basket_item where basket_id = " +basketId+ "and item_id = "+ itemId);
	    while (rset.next()) {
	    	basketItem.setBasketId((Integer.valueOf(rset.getString(1)).intValue()));
	    	basketItem.setItemId((Integer.valueOf(rset.getString(2)).intValue()));
	    	basketItem.setName(rset.getString(3));
	    	basketItem.setPrice((Double.valueOf(rset.getString(4)).doubleValue()));
	    	basketItem.setQuantity((Integer.valueOf(rset.getString(5)).intValue()));
	    	basketItem.setScale((Integer.valueOf(rset.getString(6)).intValue()));
	    }
	    stmt.close();
	    connectionPool.free(conn);
	    if(basketItem.getItemId() ==0){
	    	return null;
	    }
	    
	    return basketItem;
	}
	
	
	
	public static void addBasketItem(String basketId,String itemId, String name, String price, String quantity, String scale ) throws SQLException{
		Connection conn = getConnectionPool().getConnection();
		
	    Statement stmt = conn.createStatement();
	    String sql = "INSERT INTO basket_item (basket_id,item_id,name,price,quantity,scale) VALUES ('"+basketId+"','"+itemId+"','"+name+"','"+price+"','"+quantity+"','"+scale+"')";
	    stmt.executeUpdate(sql);
	    conn.commit();
	    stmt.close();    
	    connectionPool.free(conn);
	}
	
	public static void deleteBasketItem(String basketId,String itemId) throws SQLException{
		Connection conn = getConnectionPool().getConnection();
		
	    Statement stmt = conn.createStatement();
	    String sql = "delete basket_item where basket_id = '"+basketId +"' and item_id = "+itemId;
	    stmt.executeUpdate(sql);
	    conn.commit();
	    stmt.close();    
	    connectionPool.free(conn);
	}
	
	public static void updateBasketItemQuantity(String basketId,String itemId, String quantity, String scale) throws SQLException{
		Connection conn = getConnectionPool().getConnection();
		
	    Statement stmt = conn.createStatement();
	    stmt.executeUpdate("update basket_item set quantity='"+ quantity +"', scale='"+scale+"' where basket_id ="+ basketId+ "and item_id ="+ itemId);
	    conn.commit();
	    stmt.close();
	    connectionPool.free(conn);
	}
	
	public static void updateBasketItemScale(String basketId, String itemId, String scale ) throws SQLException{
		Connection conn = getConnectionPool().getConnection();
		
	    Statement stmt = conn.createStatement();
	    stmt.executeUpdate("update basket_item set scale='"+ scale +"' where basket_id ="+ basketId+ "and item_id ="+ itemId);
	    conn.commit();
	    stmt.close();
	    connectionPool.free(conn);
	}
	
	
	//only for local test-----------------------------------------------------
	public static void main(String[] args) {
		ConnectionPool pool = getConnectionPool();
		Connection conn;
		try {
			conn = pool.getConnection();
			conn.setAutoCommit(false);
			
			String basketId = "10080";
			BasketDS cart = getBaseket(basketId);
			int id = cart.getBasket_id();
			
			createBaseket("10060");
			cart = getBaseket("10060");
			id = cart.getBasket_id();

			
			updateBaseket("10060", "10050");
			cart = getBaseket("10050");
			id = cart.getBasket_id();

			
			deleteBaseket("10050");

			
			pool.free(conn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
