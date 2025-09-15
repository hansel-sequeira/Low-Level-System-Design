package org.example;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

/*
-> overview of oops (*)
-> pillars of oops
    : abstraction
    : encapsulation
    : inheritance
        -> single, multilevel
    : polymorphism
        -> static, dynamic
-> is-a vs has-a (aggregation)


OOPS: a programming paradigm which strives to emulate real world programming in a bottom up fashion (as opposed to
procedural programming). It lists out the core entities, their attributes and behavior, the relationship between these
entities and proceeds to define behavior and runtime state to implement the real world logic. It makes use of principles
like abstraction, encapsulation, inheritance and polymorphism in order to achieve modularity/reusability and extensibility.

1) Abstraction: principle in which we expose only the core functionalities without the implementation details to the service user.
This exposition serves as a contract that the client can use. We can have various concrete implementation on the service provider
end that honors the contract and provides custom implementations for each of those functionalities. The client can then, on demand,
swap in/out the concrete implementations (objects) by solely relying on the contract that they all honor. In this way, if tomorrow,
a new concrete implementation for a functionality were to be introduced, the client code has to do nothing but just utilize the
instance of the new concrete class, since it will honor the contract.

Eg: interface PaymentProcessor {
    public void processPayment();
}

class PayPalProcessor : PaymentProcessor
class CashProcessor : PaymentProcessor
class CreditCardProcessor : PaymentProcessor


class ClientCheckout {
    PaymentProcessor paymentProcessor; // all implementations of this honor a certain contract
                                       // No need to do CashProcessor cashProcessor -> tight coupling.

    ClientCheckout(PaymentProcessor paymentProcessor) { // swap-in/out on demand.
        this.paymentProcessor = paymentProcessor;
    }

    void checkout() {
        paymentProcessor.processPayment();
    }
}

-> major benefits: prevents tight coupling, thereby aids in extensibility.
-> the existence of the contract and the various implementations honoring the contract help facilitate this.
-> abstraction can simply achieved using access modifiers (encapsulation) without the need of interfaces/abstract classes.
But in practice and to follow good OOPS principles, it makes sense to use the explicit contract method.


2) Encapsulation

-> two aspects: one is bundling up of related attributes/behavior under a common unit (class, enum, etc) and providing access
and mutations to those attributes using the class construct - This unit provides controlled access and mutation of its
internal attributes through the class’s own methods (getters, setters, or business logic).
-> second is: security principle determine what methods will be exposed or hidden to the outside world.
-> at its core, it helps achieve abstraction - by hiding concrete implementation details.
-> you can achieve safe access/modifications to the state by making the state private and exposing out a getter/setter.

3) Inheritance

-> principle in which entities extend/inherit capabilities of a parent.
-> promotes code reusability and acts as a segue to polymorphism.
-> reusability is also a feature in procedural programming (reuse the same function), however, when it comes to extensibility,
 we would need to modify existing code - existing entities ideally should be open for extension but closed for modification.
 Extensibility and reusability in its truest sense can be achieved through inheritance.
-> extensibility is supported as a virtue.

4) Polymorphism

-> two flavors: a simple compile type construct where the same method name can be reused - only differing in parameters.
 The specific implementation will be tied to during compile time. This is method overloading.
-> most important flavor: the virtue of invoking a specific implementation of a contract during runtime. This promotes
 loose coupling since the client code can rely on abstractions in its implementation instead of concrete instances.
-> The specific implementation of the contract is tied during time on the basis of the object created.

Is-a : inheritance principle
Has-a: aggregation (weak binding) vs composition (strong binding)

-> aggregation: the existence of one object does not depend on the existence of the other. If the object which contains
 another object were to die, the contained object still exists. This won't be the case in composition.

class Seat {
}

class Engine {
    void start()
}

class Car {
    private Engine engine = new Engine(); // Composition (car owns engine)
    List<Seat> seats; /// aggregation
    void startCar() {
        engine.start();
    }
}
-> Instantiating an object directly inside the container class is composition.
-> Referencing an externally created object, or where the contained object can outlive the container,
   is usually aggregation or association, not composition
 */

public class Main {
    public static void main(String[] args) {

    }
}