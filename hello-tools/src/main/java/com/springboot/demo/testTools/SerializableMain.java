package com.springboot.demo.testTools;

import java.io.*;

public class SerializableMain {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //  如果将序列化对象改成父类，则会抛出异常，没有标记为Serializable接口
//        Father father = new Father();
        Father father = new Son();
        father.f = 5;

        //  序列化
        FileOutputStream fileOutputStream  = new FileOutputStream("temp.txt");
        ObjectOutput objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(father);

        //  反序列化
        FileInputStream fileInputStream = new FileInputStream("temp.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object object = objectInputStream.readObject();
        Father f = (Father) object;
        //  由于子类没有f这个变量，是调用的父类的f变量
        System.out.println(f.f);
    }
}
