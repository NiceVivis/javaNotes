package com.vivi.basic.staticNote;

class A {
    private static A a =new A();

    static {
        System.out.print("static");
    }
    {
        System.out.print("A");
    }

}

/** private static A a =new A();
 * 输出结果AstaticAB
 *
 */
public class StaticDemo extends A{
    public StaticDemo(){
        System.out.print("B");
    }

    public static void main(String[] args) {
        StaticDemo staticDemo = new StaticDemo();
    }
}
