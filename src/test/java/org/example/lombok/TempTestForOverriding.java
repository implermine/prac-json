package org.example.lombok;


import org.junit.jupiter.api.Test;

public class TempTestForOverriding {

    @Test
    public void test(){
        Base base = new Derived(); // 업캐스팅
        System.out.println(base.name);
        // Base
        System.out.println(base.say());
        // method from Derived

        Object obj = new Base();
        obj.equals(new Object());
        // hey!

    }

    private static class Base{
        private String name = "Base";
        public String say(){
            return "method from Base";
        }

        @Override
        public boolean equals(Object obj) {
            System.out.println("hey!");
            return true;
        }
    }

    private static class Derived extends Base{
        private String name = "Derived";
        public String say(){
            return "method from Derived";
        }
    }


}
