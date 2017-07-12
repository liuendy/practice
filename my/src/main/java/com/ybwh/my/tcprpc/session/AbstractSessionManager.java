package com.ybwh.my.tcprpc.session;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象的会话管理器
 * 
 * @author fanbeibei
 *
 */
public class AbstractSessionManager implements SessionManager{
	
	private ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

	public boolean registerSession(String vin, Session session) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean releaseSession(String vin) {
		// TODO Auto-generated method stub
		return false;
	}

	public Session getSession(String vin) {
		// TODO Auto-generated method stub
		return null;
	}

}
