package org.example;

/*

The classes will be loaded by JVM when:
Any method/field/static member is invoked.
The class is referenced in some other way (reflection, etc.)

Classes are loaded only on demand - when they're actually referenced.
The first thing that's done when loading a class is to take care of the static state
    => the static members are initialized
    => the static block is executed
    => and only then is the constructor invoked.
 */

import java.util.ArrayList;
import java.util.List;

/**

class BillPughSolution {
    private BillPughSolution() {
        System.out.println("BillPughSolution constructor called");
    }

    // Static field in outer class
    static int x = initX();

    private static int initX() {
        System.out.println("Outer class static field x initialized");
        return 5;
    }

    // Static nested class
    private static class BillPughInner {
        private static final BillPughSolution billPughSolution = initInstance();

        private static BillPughSolution initInstance() {
            System.out.println("Inner class static field billPughSolution initialized");
            return new BillPughSolution();
        }
    }

    public static BillPughSolution getInstance() {
        System.out.println("getInstance() called");
        return BillPughInner.billPughSolution;
    }

    public static void main(String[] args) {
        System.out.println("Main started");

        // Access static field
        System.out.println("x = " + BillPughSolution.x);

        // Now call getInstance
        BillPughSolution instance = BillPughSolution.getInstance();
    }
}

 Expected output:

 Main started
 Outer class static field x initialized
 x = 5
 getInstance() called
 Inner class static field billPughSolution initialized
 BillPughSolution constructor called



 **/



class EagerInitialization {
    private static final EagerInitialization eagerInitialization = new EagerInitialization();

    private EagerInitialization() {}
    public static EagerInitialization getInstance() {
        return eagerInitialization;
    }
}

/*
class DatabaseManager {

// Eager initialization - instance created when class loads
private static final DatabaseManager instance = new DatabaseManager();

private DatabaseManager() {
    // Expensive: opens database connections, loads config files, etc.
}

// Instance methods that use the singleton
public void saveData(String data) { }
public String loadData(int id) {  }

// Utility static methods that don't need the singleton instance
public static boolean isValidConnectionString(String connStr) {  }
public static String formatQuery(String sql) {  }
public static final String DEFAULT_TIMEOUT = "30000";

public static DatabaseManager getInstance() {
    return instance;
}
}

The Problem:
If someone does this (uses just a utility method):
String formatted = DatabaseManager.formatQuery("SELECT * FROM users");
String timeout = DatabaseManager.DEFAULT_TIMEOUT;

The entire DatabaseManager instance gets created with all its expensive initialization (database connections, file I/O, etc.)
even though they never needed the singleton instance at all.
 */

class LazyInitialization {
    private static LazyInitialization lazyInitialization;

    private LazyInitialization() {}
    public static LazyInitialization getInstance() {
        if(lazyInitialization == null)
            lazyInitialization = new LazyInitialization();
        return lazyInitialization;
    }
}

class SynchronizationLazyInitialization {
    private static SynchronizationLazyInitialization synchronizationLazyInitialization;

    private SynchronizationLazyInitialization() {}
    public static synchronized SynchronizationLazyInitialization getInstance() {
        if(synchronizationLazyInitialization == null)
            synchronizationLazyInitialization = new SynchronizationLazyInitialization();
        return synchronizationLazyInitialization;
    }
}

class DoubleCheckLockingMechanism {
    private static volatile DoubleCheckLockingMechanism doubleCheckLockingMechanism;
    // volatile is important here to push/access from memory rather than local cache.

    private DoubleCheckLockingMechanism() {}
    public static DoubleCheckLockingMechanism getInstance() {
        if(doubleCheckLockingMechanism == null) {
            synchronized (DoubleCheckLockingMechanism.class) {
                if(doubleCheckLockingMechanism == null)
                doubleCheckLockingMechanism = new DoubleCheckLockingMechanism();
            }
        }
        return doubleCheckLockingMechanism;
    }
}



class BillPughSolution {
    private BillPughSolution() {}

    // when BillPughSolution.getInstance() is called JVM must load and initialize the outer class BillPughSolution.
    // Any static fields or static blocks in BillPughSolution run now.
    // At this point, the inner class BillPughInner is not loaded yet, because you haven’t touched it.
    // At this stage, loading of the outer class is done.

    //    Inside getInstance(), JVM references BillPughInner.billPughSolution
    //    That reference triggers the class loader to load and initialize BillPughInner.
    //    During BillPughInner initialization:
    //    Its static field billPughSolution is assigned → which means a new BillPughSolution() object is constructed.
    private static class BillPughInner {
        private static final BillPughSolution billPughSolution = new BillPughSolution();
    }

    public static BillPughSolution getInstance() {
        return BillPughInner.billPughSolution;
    }
}

/**
 *
 * The Bill Pugh pattern is thread-safe because it leverages the JVM's class loading mechanism, which is inherently thread-safe.
 * The JVM specification guarantees that:
 *
 * Class initialization is thread-safe - Only one thread can initialize a class at a time
 * Static field initialization is atomic - The assignment happens completely or not at all
 * Memory visibility is ensured - Once class initialization completes, all threads see the initialized state
 *
 * How This Works in Bill Pugh
 * private static class BillPughInner {
 *     private static final BillPughSolution billPughSolution = new BillPughSolution();
 * }
 * When multiple threads call getInstance() simultaneously:
 * Scenario: Thread A and Thread B call getInstance() at the same time
 *
 * Both threads reference BillPughInner.billPughSolution
 * JVM class loader takes over - it has internal synchronization
 * Only one thread is allowed to initialize BillPughInner class
 * The other thread waits until initialization is complete
 * Both threads see the same, fully-constructed instance
 *
 * The JVM Does the Heavy Lifting
 * java// This is essentially what the JVM does internally:
 * synchronized (BillPughInner.class) {
 *     if (!BillPughInner.isInitialized()) {
 *         // Only one thread executes this
 *         BillPughInner.billPughSolution = new BillPughSolution();
 *         BillPughInner.markAsInitialized();
 *     }
 * }
 * // All threads now see the same instance
 * Why This is Better Than Manual Synchronization
 * Manual Double-Checked Locking:
 * public static BillPughSolution getInstance() {
 *     if (instance == null) {  // Performance overhead on every call
 *         synchronized (BillPughSolution.class) {
 *             if (instance == null) {
 *                 instance = new BillPughSolution();
 *             }
 *         }
 *     }
 *     return instance;
 * }
 * Bill Pugh:
 * public static BillPughSolution getInstance() {
 *     return BillPughInner.billPughSolution;  // No synchronization overhead!
 * }
 * Key Advantages
 *
 * No synchronization overhead after initialization
 * No volatile keyword needed
 * Lazy loading - instance created only when needed
 * Thread-safe by design - relies on JVM guarantees
 * Simple and clean code
 *
 * The Bill Pugh pattern is brilliant because it delegates the thread-safety complexity to the JVM's already-optimized class loading mechanism!
 */


/**
 *
 *
 * The classloader is not a separate thread from the JVM. It is a component/subsystem of the JVM. It's code that runs within whatever thread is requesting the class loading.
 * How Class Loading Actually Works
 * When T1 calls getInstance():
 * // T1 thread executes this
 * return BillPughInner.billPughSolution;  // T1's thread triggers class loading
 * What Happens Inside T1's Thread:
 *
 * T1's thread encounters the reference to BillPughInner
 * T1's thread calls into the JVM's classloader subsystem
 * T1's thread executes the classloader code to load BillPughInner
 * T1's thread runs the static initialization: new BillPughSolution()
 * T1's thread completes the class initialization
 * T1's thread returns the instance
 *
 */

enum DatabaseManager {
    INSTANCE;

    // Your singleton methods
    public void saveData(String data) {
        // implementation
    }

    public String loadData(int id) {
        // implementation
        return "data";
    }
}


/**
 *
 * Usage for enum as a singleton:
 *
 * DatabaseManager.INSTANCE.saveData("user data");
 * String data = DatabaseManager.INSTANCE.loadData(123);
 * The JVM handles enum initialization just like static final fields - it's inherently thread-safe.
 **
 *
 * When Enum Singleton Gets Created
 * Just like Bill Pugh, it's lazy loaded:
 * public class Main {
 *     public static void main(String[] args) {
 *         System.out.println("App started");
 *         // ConfigurationManager enum not loaded yet!
 *
 *         String dbUrl = ConfigurationManager.INSTANCE.getProperty("db.url");
 *         // NOW the enum is loaded and INSTANCE is created
 *     }
 * }
 *
 *
 * The Only Downside
 * Enums can't extend classes (but they can implement interfaces):
 * // This won't work:
 * public enum DatabaseManager extends BaseManager { // ❌ Not allowed
 *     INSTANCE;
 * }
 *
 * // But this works:
 * public enum DatabaseManager implements DataManager { // ✅ Allowed
 *     INSTANCE;
 *
 *     @Override
 *     public void save(String data) {
 *         // implementation
 *     }
 * }
 * Bottom Line: If you don't need inheritance, enum singletons are often the best choice - simple, thread-safe, serialization-safe, and reflection-proof!
 */


enum Counter {
    INSTANCE;

    private int count = 0; // Mutable state!

    // Constructor sets initial state (optional)
    Counter() {
        // Could initialize here, but not required
        System.out.println("Counter initialized");
    }

    // Methods that modify state after construction
    public void increment() {
        count++; // Changing state!
    }

    public int getCount() {
        return count;
    }
}


// I will have only two instances for CustomEnum, no matter how many threads try to access its instance methods
enum CustomEnum {
    INSTANCE_A,
    INSTANCE_B;

    public int x;
    public void setX(int x) {
        this.x = x;
    }
}


public class Main {
    static int x;
    static {
        x = 10;
    }
    public static void main(String[] args) {
        CustomEnum.INSTANCE_A.setX(10);
        CustomEnum.INSTANCE_B.setX(-10);
        System.out.println(CustomEnum.INSTANCE_A.x);
        System.out.println(CustomEnum.INSTANCE_B.x);
    }
}



/*
Immutable Classes

-> cannot change the value of an object once it is created. Cannot be extended.
-> Class members should be private and can only be initialized using the constructors, so no setters
-> For collections, it returns a deep copy.
Eg: String, Wrapper classes.
 */

final class ImmutableClass {
    private final String name;
    private final List<String> petNames;

    ImmutableClass(String name, List<String> petNames) {
        this.name = name;
        this.petNames = new ArrayList<>(petNames); // defensive programming
    }

    public String getName() {
        return name;
    }

    public List<String> getPetNames() {
        // this is incorrect: return petNames;
        return new ArrayList<>(petNames);
    }
}