package org.example;

/*
-> contract that prevents tight coupling to a specific concrete implementation.
-> only public/default interfaces allowed. protected/final make no sense in an interface.
    all methods are public abstract only. (From Java8, it can have default, private, static methods)
    variables are constants (public static final)
 -> default methods are meant to be overridden (they're only there to offer some default behaviour, rather than
    have duplicate code).
 -> interfaces can only be private/protected, when they're data members of another class.
-> Overriding method cannot be more restrictive. (public -> private not allowed).
        Animal a = new Tiger();
        a.move();

        compile time: move() is public, but runtime it is private().
 */

// nested interface:



interface Bird {
    void fly();
    interface NonFlyingBird {
        void run();
    }
    private void hi() {

    }
    default void hello() {
    }
    public static void behaviour() {
        System.out.println("I am a bird");
    }

    String toString();
}

// A class implementing from multiple interfaces all having the same default method signature is forced to override it.
// A child interface can extend from a parent interface that has a default method and override it to make it abstract!
// Then, any class implementing the child interface has to override the abstract method (which was previously default)

interface LivingThing {
    default void live() {
    }
    static void breathe() {

    }
}

interface LivingBeing extends LivingThing {
    @Override
    default void live() {
        LivingThing.super.live();
        // some other implementation.
        LivingThing.breathe();
    }
}

// in a functional interface, only 1 abstract method, but we can have static, private, default AND OBJECT methods.


// class Eagle implements Bird which has a toString() method. But it's not forced to implement it.
// This is because Eagle extends Object which already has a default implementation for this method.
// Eagle can simply call it's super.toString();
class Eagle implements Bird {
    @Override
    public void fly() {

    }
}

class KomodoDragon implements Bird.NonFlyingBird {
    @Override
    public void run() {

    }
}


// nested interface inside a class

class Mammal {
    public interface NormalMammal {
        void move();
    }

    void foo() {
        NormalMammal normalMammal = () -> {
            System.out.println("Move");
        };
        normalMammal.move(); // concrete implementation of a functional interface.
    }

}

class NormalMammalFI implements Mammal.NormalMammal{
    @Override
    public void move() {
        System.out.println("Move..");
    }
}

public class Main {
    public static void main(String[] args) {
        Eagle eagle = new Eagle();
        eagle.hello();
        Bird.behaviour(); // static behaviour() is not present in Eagle!

        Mammal.NormalMammal normalMammal = () -> {
            System.out.println("Move..");
        };
        Mammal.NormalMammal normalMammal2 = new Mammal.NormalMammal() {
            @Override
            public void move() {

            }
        };
        Mammal.NormalMammal normalMammal1 = new NormalMammalFI();

        Bird.NonFlyingBird nonFlyingBird = new KomodoDragon();
    }
}