package org.example;


import java.util.ArrayList;
import java.util.List;

// this Print class in a way works with a generic 'Object' type.
// but how do you enforce compile time checks with this? You risk having ClassCastExceptions on runs..
// Besides, you'll have to perform manual typecasts everywhere.
class Print {
    private Object value;
    public Object getPrintValue() {
        return value;
    }
    public void setPrintValue(Object value) {
        this.value = value;
    }
}

class GenericPrint<T> {
    T name;
    void setName(T input){}
}

class NonGenericChildStringPrint extends GenericPrint<String> {
}

class GenericChildPrint<T> extends GenericPrint<T> {
    public T getGenericName() {
        return name;
    }
}

class Vehicle {
}
class Car extends Vehicle{
}

// bounded generics:
class Bike<T extends Vehicle> {}

public class Main {

    public static void acceptVehicleList(List<Vehicle> vehicleList) {
    }

    // why need a wildcard when we have generics? we can do:
    public <P extends Number, Q extends String> void computeList(List<P> source, List<Q> destination) {
    }

    // if we want a restriction that both the source and dest, though they're children should be of the same type,
    // we stick to generics:
    public <T extends String> void computeList1(List<T> source, List<T> destination) {
    }

    // Generics does not support lower bounding - super!

    // three kinds of wildcard: upperbounded <? extends UB>, lowerbounded <? super LB> and unbounded: <?>
    // you need to use a wildcard here:
    public static <T> void acceptCovariantVehicleList(List<? extends Vehicle> vehicleList) {
    }


    /* generic method:
    public <K,V> void printValue(Pair<K,V> p1, Pair<K,V> p2) {
      if(p1.getKey().equals(p2.getKey())
        doSomething
    }
    */

    public static void main(String[] args) {
        Print p = new Print();
        p.setPrintValue("123");
        Object str = (String)p.getPrintValue();
        Object int1 = (Integer)p.getPrintValue();
        p.setPrintValue(123);
        // in order to work with this, typecasting becomes crucial.
        if(str instanceof String) {
            // do something
        }
        /********/
        GenericChildPrint<String> genericChildPrint = new GenericChildPrint<String>();

        GenericPrint<String> pr1 = new GenericPrint(); // raw type on RHS
        //pr1.setName(123); // does not compile

        List<Vehicle> vehicleList = new ArrayList<>();
        List<Car> cars = new ArrayList<>();
        acceptVehicleList(vehicleList); // works fine
        //acceptVehicleList(cars); // won't work. Collections are invariant.
        acceptCovariantVehicleList(cars);
    }
}