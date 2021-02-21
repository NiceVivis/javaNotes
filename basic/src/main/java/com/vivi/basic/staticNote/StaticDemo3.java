package com.vivi.basic.staticNote;

public class StaticDemo3 {
    static int i =1;
    int j=1;

    public static void  A(){
        i++;
        System.out.println(i);
    }
    public static void B(){
        i=i+2;
        System.out.println(i);
    }
    public void C(){
        j++;
        System.out.println(j);
    }
    public void D(){
        j = j+2;
        System.out.println(j);
    }

    public static void main(String[] args) {
        StaticDemo3.A();
        StaticDemo3.B();
        new StaticDemo3().C();
        new StaticDemo3().D();
        System.out.println(i);
        System.out.println(new StaticDemo3().j);
    }
}
