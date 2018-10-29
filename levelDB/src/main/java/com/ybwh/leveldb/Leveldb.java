package com.ybwh.leveldb;

import java.io.File;
import java.io.IOException;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;

public class Leveldb {

	public static void main(String[] args) {
		try {
			File dbPath = new File("E:/leveldb");
			DB db = Iq80DBFactory.factory.open(new File(dbPath,"db"), new Options().createIfMissing(true));
//			db.put("Hello".getBytes("UTF-8"), "world".getBytes("UTF-8"));
			byte[] val = db.get("Hello".getBytes("UTF-8"));
			System.out.println(new String(val,"UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
