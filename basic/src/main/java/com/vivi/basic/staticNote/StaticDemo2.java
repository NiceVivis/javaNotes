package com.vivi.basic.staticNote;

class B{
    public B(){
        System.out.print("A gouzaokuai");
    }
    private static B b =new B();
    static {
        System.out.print(" static");
    }
    {
        System.out.print(" A1");
    }
}
public class StaticDemo2 extends B{
    public StaticDemo2(){
        System.out.print(" B");
    }

    public static void main(String[] args) {
        System.out.print(" 0000");
        StaticDemo2 b = new StaticDemo2();
    }
}
