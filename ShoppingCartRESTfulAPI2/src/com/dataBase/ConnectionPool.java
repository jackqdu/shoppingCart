package com.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
public class ConnectionPool implements Runnable {
	private String driver, url, username, password;
	private int maxConns;
	private boolean waitIfBusy;
	private ArrayList idleConns, busyConns;
	private boolean connPending = false;

	//for local test 
	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String username = "c##user2016";
		String password = "cw";
		int initConns = 2;
		int maxConns = 5;
		boolean waitIfBusy = true;
		ConnectionPool pool = new ConnectionPool(driver, url, username, password, initConns, maxConns, waitIfBusy);
		Thread t = new Thread(pool);
		t.start();
		
		int number = pool.totalConns();
		try {
			Connection oneConnection = pool.getConnection();
			String a = "";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pool.closeAllConns();
	}
	
	public ConnectionPool(String driver, String url, String username, 
			String password, int initConns, int maxConns, boolean waitIfBusy){
		this.driver= driver;
		this.url = url;
		
		this.username = username;
		this.password = password;
		this.maxConns = maxConns;
		this.waitIfBusy = waitIfBusy;
		
		this.idleConns = new ArrayList(initConns);
		this.busyConns = new ArrayList();
		for(int i=0; i<initConns; i++) idleConns.add(makeNewConn());
	}

	private Connection makeNewConn(){
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, username, password);
			conn.setAutoCommit(false);
			return conn;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public synchronized Connection getConnection() throws SQLException {
		if (!idleConns.isEmpty()) {
			int lastIndex=idleConns.size()-1;
			Connection existingConn=(Connection) idleConns.get(lastIndex);
			idleConns.remove(lastIndex);
			if (existingConn.isClosed()) { notify(); return(getConnection()); }
			else { busyConns.add(existingConn); return(existingConn); }
		} else {
			if (totalConns()<maxConns && !connPending) makeBackgroundConn();
			else if (!waitIfBusy) {
				throw new SQLException("Connection limit reached");
			}
			try { 
				wait();
			} catch (InterruptedException ie) {}
			return(getConnection());
		}
	}

	private void makeBackgroundConn() {
		connPending = true;
		try {
			Thread connThread = new Thread(this);
			connThread.start();
		} catch(OutOfMemoryError e) { /*code*/ }
	}


	public void run() {
		try {
			Connection conn = makeNewConn();
			synchronized(this) {
				idleConns.add(conn);
				connPending = false;
				notifyAll();
			}
		} catch (Exception e) {
			/* code */
		}
	}

	public synchronized void free(Connection conn) {
		busyConns.remove(conn);
		idleConns.add(conn);
		notifyAll();
	}
	public synchronized int totalConns() {
		return(idleConns.size()+busyConns.size()); 
	}

	public synchronized void closeAllConns() {
		closeConns(idleConns); idleConns = new ArrayList();
		closeConns(busyConns); busyConns = new ArrayList();
	}

	private void closeConns(ArrayList conns) {
		try {
			for (int i=0; i<conns.size(); i++) {
				Connection conn = (Connection) conns.get(i);
				if(!conn.isClosed()) conn.close();
			}
		} catch (SQLException se) { /* code */ }
	}
}

