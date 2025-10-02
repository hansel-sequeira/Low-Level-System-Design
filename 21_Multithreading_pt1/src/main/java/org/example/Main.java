package org.example;

import com.sun.security.jgss.GSSUtil;

import java.util.concurrent.*;

/**
 *
 *
 * Threads -> fundamental unit of execution in Java. It allows for processing concurrent tasks thereby creating responsive
 * applications, utilizing multi cores effectively and improving overall application performance.
 *
 *  Process -> is a executable instance of a program. It can contain one or many threads. It has its own dedicated
 *  memory space and the overhead in creating a new process is pretty high. IPC requires complex mechanisms, and context switching
 *  is also pretty expensive. One process crash doesn't typically impact other processes. Hence, if you need strong isolation
 *  between different parts of your application, create new processes.
 *
 *  Thread -> fundamental unit of execution a process. Threads of a process share the same memory and resources. Overhead to create
 *  a new thread is lighter and communication is easy since they share memory. Context switching also is a lower overhead.
 *  Impacts to a thread could impact the entire process.
 *
 *
 *
 *
 * Two ways to create a thread in java -> extending the thread class, implementing the runnable interface.
 *
 * There's also a callable interface which is somewhat better than the runnable interface (which returns void).
 * The Callable interface can return results and throw checked exceptions through the call() method.
 * (Unchecked exceptions can be thrown from either thread, runnable or callable)
 * Callable returns a Future object using which you can retrieve the execution result. To spawn a Callable thread,
 * you need to pass the callable implementation to an ExecutorService framework.
 *
 *
 * Exceptions do not propagate to the parent thread.
 */


class ClassImplementingCallable implements Callable<Integer> {
    @Override
    public Integer call() throws InterruptedException{
        System.out.println("Callable thread: " + Thread.currentThread().getName());
        //throw new InterruptedException("Interrupted exception");
        Thread.sleep(3000);
        return 100;
    }
}

class ClassImplementingRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " in Runnable is now running");
    }
}


class ClassExtendingThread extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is now running");
    }
}

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ClassExtendingThread classExtendingThread = new ClassExtendingThread();
        System.out.println("The main thread: " + Thread.currentThread().getName());
        classExtendingThread.start();
        Runnable r1 = ()->{
            System.out.println(Thread.currentThread().getName() + " is running");
            throw new RuntimeException("Runtime exception");

        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " is running");
            }
        };
        Thread t1 = new Thread(r1);
        t1.start();

        // to interrupt a thread: t1.interrupt();

        Thread.sleep(1500);
        System.out.println("The main thread is still running even though a child thread threw a runtime exception");
        Thread.sleep(4000);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Callable<Integer> callable1 = new ClassImplementingCallable();
        Callable<Integer> callable2 = new ClassImplementingCallable();
        Future<Integer> future1 =  executorService.submit(callable1);
        Future<Integer> future2 = executorService.submit(callable2);
        System.out.println("Future 1 result: " + future1.get());
        // future1.get() blocks the main thread until result is received
        System.out.println("The main thread has resumed");
        System.out.println("Future 2 result: " + future2.get());
        System.out.println("The main thread has ended");

        executorService.shutdown();
    }
}


/**
 *
 *
 *  *
 *  * sleep() vs wait()
 *  * sleep -> current thread pauses execution for a specified time WITHOUT releasing the locks.
 *  * wait -> current thread will wait UNTIL it is awoken by some other thread using notify or notifyAll.
 *  *  It releases the lock before it goes to wait..
 *
 *
 * wait(), notify(), and notifyAll() must always be called while holding the lock (monitor) of the object
 * they’re invoked on. If you call them without synchronization, the JVM throws an IllegalMonitorStateException.
 *
 */


// class level lock in java
class ClassLock {

    // by default this is a class level lock
    static synchronized void bar() {}

    void foo() {
        synchronized (ClassLock.class) {
        }
    }
}

class SharedResource {}

// you call wait on an object.
class SleepWaitExample {

    // at this stage, you have a lock over a specific instance (inst1) of SleepWaitExample
    synchronized void waitExample() throws InterruptedException{
        System.out.println("Thread will now wait for some notification..");
        wait(); // wait is being called on inst1.

        // now if someone calls sharedResource.notify(), it is useless, because you are waiting on something else!
    }

    public static void main(String[] args) throws InterruptedException{
        SharedResource resource = new SharedResource();
        Thread t1 = new Thread(()->{
            System.out.println("Wait example over the shared resource. Thread: " + Thread.currentThread().getName());
            synchronized(resource) { // acquire the lock
                try {
                    resource.wait(); // release the lock
                    // can also do wait with timeout: resource.wait(3000); to prevent leaks.
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Wait over. Thread: " + Thread.currentThread().getName() + " back in action");
        });

        t1.start();
        Thread.sleep(7000);
        synchronized (resource) { // in order to notify some thread in the waiting queue of an object lock,
            // you need to possess that lock in the first place!

            // do stuff.
            System.out.println("Notifying the thread who possesses the lock over the shared resource");
            resource.notify(); // release the lock and poll from waiting queue.
        }
    }
}


/**
 *
 *
 * Thread life cycle:
 *
 *
 * New state -> Runnable -> Running -> Terminated (execution completed or stop() invoked)
    TimedWaiting -> Sleep
    Waiting -> wait() or join()
    Blocked -> IO activities.
 */


/**
 *
 *
 * Thread pools -> Managed collection of reusable threads: no need to engage in manual creation and termination and
 * allocation of threads to a request.
 */


class ThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3); // three reusable threads

        ExecutorService executorService1 = Executors.newCachedThreadPool(); // when you want to scale threads dynamically
        // used primarily for IO bound tasks which spend a significant amount of time waiting for IO operations to complete.
        // In a newFixedThreadPool(), the fixed number of threads might be blocked waiting, even if there's other work that
        // could be done. newCachedThreadPool() spins up new threads when needed, allowing more tasks to be processed
        // concurrently while others are waiting on some IO. This maximizes the CPU utilization during the waiting period.
        // So, cached thread pool can be more responsive to burst of IO bound operations. Idle threads are reclaimed
        // after a period of inactivity.

        // in short, for CPU bound tasks used newFixedThreadPool(), for IO bound tasks, use newCachedThreadPool() and for
        // a mixed workload, make use of ThreadPoolExecutor -> define custom config policies.

        for(int i=0;i<5;i++) {
            // executorService.submit() expects a runnable or a callable. But in both cases, it returns a Future!
            executorService.submit(()->{
                System.out.println("Runnable impl by thread: " + Thread.currentThread().getName());
            });
        }


        // How does a ThreadPoolExecutor's queue size affect its behavior?
        // -> The queue stores tasks when all core threads are busy. A larger queue can handle more pending tasks
        // but consumes more memory. If the queue reaches capacity, the pool creates additional threads up to
        // the 'maxPoolSize'. If this too is reached, the rejection policy is applied.

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                4,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2),
                new ThreadPoolExecutor.AbortPolicy()
        );
        for(int i=0;i<10;i++) {
            threadPoolExecutor.submit(()->{
            });
        }


        executorService.shutdown(); // shutdown() allows for graceful termination. shutdownNow() is abrupt.
    }
}