package com.ybwh.cglib.example.proxy;
public class DaoImpl implements Dao {

    @Override

    public void add(Object o) {

        System.out.println("add(Object o)");

    }

 

    @Override

    public void add(int i) {

        System.out.println("add(int i)");

    }

 

    public final void add(String s) {

        System.out.println("add(String s)");

    }

}