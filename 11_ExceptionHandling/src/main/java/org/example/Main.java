package org.example;

/*

Exception: Any anomalous event that deviates from the norm and is part of the execution flow.
It is characterized by the creation of an Exception object (exception type, stack trace)

Exception hierarchy:

       Object class
        -> Throwable class (every class is an instance of the Object class)
                -> Error class
                        -> OutOfMemoryError class -> if heap space is exhausted.
                        -> StackOverflowError class
                        -> VirtualMachineError class
                    In Java, Error is meant to represent serious, irrecoverable conditions that you
                     realistically cannot handle in normal application code. It is not meant to be thrown
                     manually. JVM throws it under irrecoverable circumstances.

                -> Exception clas
                        -> RuntimeException: not detected at compile time. Directly flagged during runtime execution.
                            -> IndexOutOfBoundException
                                -> ArrayIndexOutOfBoundException, StringIndexOutOfBoundException
                            -> NullPointerException
                            -> ArithmeticException
                            -> IllegalArgumentException
                                -> NumberFormatException (Eg: Integer.parseInt("ABC"));
                            -> ClassCastException
                                                    Object x = 5;
                                                    String y = (String)x;


                        -> CheckedException -> we don't have such a class. Any class that extends Exception
                         but not RuntimeException is a checked exception.
                         It is detected by the compiler at compile time. Needs to be handled or
                         method needs to declare that it throws an exception in its signature.
                            -> ClassNotFoundException
                            -> IOException
                                -> FileNotFoundException
                            -> SQLException
                            -> InterruptedException
 */


import java.io.FileNotFoundException;
import java.io.IOException;

class Parent {}
class Child extends Parent {}



//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static void foo() throws FileNotFoundException, ClassNotFoundException,
            ArrayIndexOutOfBoundsException, StringIndexOutOfBoundsException{
        /* catch block should catch in order of most specific to least specific.
            catch(FileNotFoundException)
            catch(IOException)
            catch(Exception)
        */
    }

    public static void main(String[] args) {

        try {
            foo();
        } catch(IOException | ClassNotFoundException f) { // types must be disjoint. I can't have (parent | child)

        } catch(ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException s) {

        } catch (Exception e) {

        }


        Parent parentInstance = new Parent();
        Child c = (Child) parentInstance; // classCastException here. The actual instance is Parent which can't be
        // determined at runtime.

        // when does class casting make sense?
        // 1. downcasting/upcasting in case of primitives.
        double ddd = 1.231231;
        int x = (int)ddd;

        // object hierarchy
        Object o = "hello";
        String s = (String) o;  // downcast (safe at runtime here)

        // parent-child hierarchy
        parentInstance = new Child();
        c = (Child)parentInstance; // can now work with extra methods that Child has.


        Throwable throwable = new Throwable();
        System.out.println(throwable instanceof Object); // true

        // Integer[] arr = new Integer[10000000*999999999*999999999*999999999]; -> OutOfMemoryError

    }
}