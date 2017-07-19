package com.ybwh.cglib.example.proxy;
public class TestMain {

    public static void main(String[] args) {

        Dao dao = DaoFactory.create();

//        dao.add(new Object());

        dao.add(1);

//        dao.add("1");

    }

}