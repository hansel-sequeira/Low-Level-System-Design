package org.example;

/*

Constructors:
    -> why constructors do not have a return type?
        : normal methods can have the same name as the Class.
        Student s = new Student();
        It does not make sense to have our constructor return the classType, even though it is obvious, because
        even normal methods can return a classType. Normal method with className + classType return -> ambiguity.
        Hence, constructors have the className as convention and no return type to distinguish ambiguity.
    -> why constructors cannot be final?
        : because there is no point in doing so. Constructors are an inherent property of a class. The constructor name
         is same as class name. They cannot be inherited, so there's no point in making it final.
    -> why constructors cannot be abstract?
        -> same reason as above, constructors are not inherited, and they are intrinsic to a class/abstract class.
            Besides, if the constructor were abstract and let's say the subclass were to inherit and override it,
            but you still can't instantiate the base class!
            Abstract classes cannot be instantiated (since they may have abstract methods) but they can have
            constructors which are invoked via the base class.
    -> why constructors cannot be static?
        -> the only time that a constructor is invoked is during object creation. So there is no point making it
        static since in normal operations, you will never expliclity call the constructor.
        Static means belonging to the class, but constructor is tied to the instance.
        If the constructor were static, it can only access static variables, so there's no way to initialize the
        instance variables!
    -> can we define a constructor in an interface?
           : No. Interfaces are not 'extended' by subclasses. They are 'implemented'. An interface is a contract, not
           a parent. So either ways it can't be instantiated, and on top of that, no one's there to call super() since it's super
           to no one.
     -> constructor chaining using this() and super()

 Methods:
    -> are static methods inherited? Yes.
    -> static methods cannot be overridden because overriding is a dynamic binding/runtime concept.
    Static methods are binded at compile time. The parent static method is instead 'hidden'.

-> ~n = -(n+1)
   ~4 = -5
        1....0011
        =(-2^3 + 2 + 1 = -8 + 3 = -5)

    x << n
    The leftmost bits (MSB) that go past the word size (32 for int, 64 for long) are discarded.
    The rightmost bits (LSB) are filled with zeros.
    Nothing “rolls over” → it’s not a circular shift.

    x >> n
    The rightmost bits (LSB) that fall off are discarded.
    The leftmost bits (MSB) are filled with the sign bit (0 for positive, 1 for negative).
    int z = -10;       // 1111...0110 (two’s complement)
    int w = z >> 1;    // 1111...1011 = -5
    So for negatives, the MSB stays 1, preserving the sign.

    x >>> n (unsigned right shift)
    Similar to >>, but always fills MSB with zeros, regardless of sign.

    int x = -10;        // 1111...0110
    int y = x >>> 1;    // 0111...1011 (a large positive number)

    why no unsigned left shift?
    Because the normal left shift already behaves like an unsigned left shift -> The MSB gets affected.

    ------------------------------------------------

   childInstance instanceOf ParentClass
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Parent {
    static void foo() {

    }
    final void bar() {}

}

class Child extends Parent {
    // void bar() {} -> not allowed!
}

class Child2 extends Parent {

}

public class Main {

    public int sum(int a, int ...varArgs) {
        int sum = 0;
        for(int x: varArgs)
            sum += x;
        return sum;
    }

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        Child.foo();

        int val = 1;
        String outputVal = switch(val) {
            case 1 -> "Abc";
            case 2 -> "Pqr";
            default -> "xyz";
        }; // this approach does not allow for compound statements.

        outputVal = switch(val) {
            case 1 -> {
                System.out.println("Compound statement");
                yield "Hello";
            }
            case 2 -> {
                yield("Mellow");
            }
            default -> "Default";
        };

        Parent parent = new Child();
        System.out.println(parent instanceof Child); // true
        System.out.println(parent instanceof Parent); // true
        System.out.println(parent instanceof Child2); // false
        Child child = new Child();
        System.out.println(child instanceof Parent); // true
    }
}