package com.ybwh.netty4;

import java.util.Properties;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
            Properties prop = new Properties();
//            prop.load(new FileInputStream(new File(ClassLoader.getSystemResource("").getFile()+File.separator+"kafka.properties")));
            prop.load(Test.class.getResourceAsStream("/kafka.properties"));
            System.out.println(prop.getProperty("kafka.zkConnect"));
//            System.out.println(Test.class.getResourceAsStream("/kafka.properties"));

        }catch (Exception e){
        	e.printStackTrace();
        }
	}

}
