package org.example;


/*
Nested classes -> static nested class,
                -> inner class (local inner class, member inner class, anonymous inner class)

inner classes can be private/protected/public/default

 */

class OuterClass {
    int instanceVariable = 10;
    static int classLevelVariable = 20;

    static class StaticNestedClass {
        int x = 5; // instance variable in a static nested class!
        public static void print() {
            System.out.println(classLevelVariable);
        }
        public void printA() {
            System.out.println(classLevelVariable);
            // static classes cannot access non-static fields of the outer class.
        }
    }

    class InnerClass {
        int instanceVariable;
        public void printB() {
            System.out.println(this.instanceVariable); // prints instance variable of Inner class
            System.out.println(OuterClass.this.instanceVariable); // instance variable of Outer class
         }
    }

    void demoLocalInnerClass() { // local inner classes are created in a block (if/while/for/method block)
         class LocalInnerClass {
            static void foo() {}
        }
        LocalInnerClass.foo();
    }
}

class InheritedInnerClass extends OuterClass.InnerClass{

    // The inner class is tied to an instance of the outer class - the inner class can access all static
    // and non static fields of the outer class. So it makes sense to pass in the outerClass instance in the
    // inheritedInnerClass constructor.

    public InheritedInnerClass() {
        new OuterClass().super();
    }

    public InheritedInnerClass(OuterClass outerClass) {
        outerClass.super(); // think of this line as OuterClass.InnerClass inner = outerInstance.new InnerClass() -> you're instantiating the innerInstance using outer instance
        // outerClass.super() DOESN'T mean superclass of outer! It means call super(), i.e the constructor in InnerClass, but also pass in the outerInstance to that constructor.
        // outer.super() → call my direct superclass constructor that is a non-static inner class, and bind it to the specific outer instance outer.
    }
}

interface AnonymousInnerClassDemo {
    void bar();
}

public class Main {
    public static void main(String[] args) {
        Object o = new Object(); // wait(), notify(), hashCode(), equals(), clone(), getClass()
        OuterClass.StaticNestedClass.print();
        OuterClass.StaticNestedClass inner = new OuterClass.StaticNestedClass(); // you can create multiple instances of a static nested class.
        inner.printA();

        OuterClass outerClass = new OuterClass();
        OuterClass.InnerClass innerClass = outerClass.new InnerClass(); // inner class is tied to instance of outer class. Similarly, when extending an inner class, you need
        // to provide instance of outer class.
        innerClass.printB();

        OuterClass outer = new OuterClass();
        InheritedInnerClass inherited = new InheritedInnerClass(outer);

        AnonymousInnerClassDemo anonymousInnerClassDemo = new AnonymousInnerClassDemo() {
            @Override
            public void bar() {
            }
        };
    }
}

/******************************************************************************************/

/*
enums -> collection of constants (static final)
      -> They internally extend the Enum class, hence enums cant extend any class. They can implement interface.
      -> Cannot be instantitated, constructor is private by default. Cannot override/add non-private constructors.
      -> It can have variables, methods, private constructors;
      -> It can have abstract methods, but then all the constants need to implement that method
 */

    enum DaysOfWeek {
        SUNDAY,
    MONDAY;

    final int x = 5;
}

enum CustomEnum implements AnonymousInnerClassDemo{
        SUNDAY,
    MONDAY
    ; // add in all your constants before ';'. Even if you have no constants to add, the semicolon is mandatory.

    int x = 5;
    @Override
    public void bar() {
        System.out.println("My name is: " + this.name() + " having ordinal: " + this.ordinal());
    }
}

enum CustomValueEnum {

        MONDAY(101, "This is monday"),

        TUESDAY(102, "This is tuesday") {
            @Override
            void commonMethod() {
                System.out.println("Overriding the common method");
            }
        };

        private int val;
        private String comment;

        CustomValueEnum(int val, String comment) {
           this.val = val;
           this.comment = comment;
       }

       int getVal() {return this.val;}
       String getComment() {return this.comment;}

        void commonMethod() {
            System.out.println("This is a common method");
        }
}


enum AbstractEnum {
        MONDAY {
            @Override void foo() {}
        },
    TUESDAY {
      @Override void foo() {}
    };
        abstract void foo();
}

class EnumDemo {
    public static void main(String[] args) {
        for(DaysOfWeek days: DaysOfWeek.values())
            System.out.println(days.ordinal()); // prints 0 and 1
        for(DaysOfWeek day: DaysOfWeek.values())
            System.out.println(day.name()); // prints SUNDAY and MONDAY
        System.out.println(DaysOfWeek.SUNDAY); // prints SUNDAY

        for(CustomValueEnum customValueEnum : CustomValueEnum.values()) {
            System.out.println(customValueEnum.getVal() + customValueEnum.getComment());
        }

        CustomValueEnum customValueEnum = CustomValueEnum.MONDAY; // think of each enum constant as an instance
        // you can now access the instance methods
        customValueEnum.getComment();
        CustomValueEnum.TUESDAY.getComment();
    }
}


// why are enums better than constant classes?

enum WeekEnum {
        SUNDAY, MONDAY, TUESDAY
}

class WeekConstant {
        public static final int SUNDAY = 0;
        public static final int MONDAY = 1;
        public static final int TUESDAY = 2;
}

class EnumVSConstantClassDemo {
    public static boolean isSunday(WeekEnum weekEnum) {
        return weekEnum == WeekEnum.SUNDAY;
    }

    public static boolean isSundayJankyMethod(int day) {
        if(day == 0) return true;
        // but day can be any number;
        return false;
    }
}
