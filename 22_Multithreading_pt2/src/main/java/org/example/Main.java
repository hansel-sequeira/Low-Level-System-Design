package org.example;


import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * Thread Executors -> concurrency framework that provides an abstraction over thread management. Creation, scheduling handled.
 * ExecutorService.execute() vs submit():
 *  -> execute: simple fire and forget (no mechanism of obtaining any result or tracking the status).
 *  -> submit: returns a Future that allows you to retrieve the result.
 *  Execute(only accepts a Runnable)
 *  Submit(accepts both Runnable and Callable). In case of Runnable, it returns a Future<?>
 *
 *
 *      future.get() might throw an exception, so it's important to handle it.
 *
 *      volatile: any changes to a volatile variable will be strongly consistent across all the other threads. Prevents
 *      caching of the value.
 *
 *      Atomic variables -> designed to support lock-free, thread-safe operations on variables. Use when need to perform simple
 *      operations like incr/decr/update. They don't have any locks even under the hood. They make use of hardware level
 *      atomic operations (CAS)
 */



class TempClass {}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    // Executor functional interface : execute()
        // -> ExecutorService interface (impl provided by instances returned by Factory: Executors.newFixedThreadPool(), etc)
                // -> ScheduledExecutorService

                // -> ThreadPoolExecutor (impl)

    public static void main(String[] args) throws InterruptedException {

        AtomicInteger atomicInteger = new AtomicInteger(4);
        int val = atomicInteger.incrementAndGet();
        val = atomicInteger.get();

        List<TempClass> tempClasses = Arrays.asList(new TempClass(), new TempClass());

         ExecutorService executorService = Executors.newFixedThreadPool(3);
         Future<Integer> retval = executorService.submit(()->{
             return 5;
         });

        Thread t = new Thread(()->{
            System.out.println("Runnable");
        });

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        scheduledExecutorService.schedule(()->{ // delay task execution
            System.out.println("Scheduled task");
        }, 3000, TimeUnit.MILLISECONDS);

        scheduledExecutorService.scheduleAtFixedRate(()->{
            System.out.println("Cron job running at a fixed interval");
        }, 3000, 2000, TimeUnit.MILLISECONDS);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 3, 5000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(3));

        // ScheduledThreadPoolExecutor implements ScheduledExecutorService. You can also implement it directly with the
        // help of instances provided by the factory class: Executors
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);

        // let's see one more method provided by the executorService: invokeAll() -> runs all tasks in parallel.
        // Arrays.asList(T obj) -> We're providing objects/impls of the Callable type.
        List<Callable<String>> allTasks = Arrays.asList(()->"CallableA", ()->"CallableB");
        List<Future<String>> results = executorService.invokeAll(allTasks);


        scheduledExecutorService.shutdown();
    }
}

/**
 *
 * Producer-Consumer Demo
 */

class SharedResource {
    public Queue<Integer> sharedQueue = new ArrayDeque<>(5);
}


class Producer {
    private SharedResource sharedResource;
    public Producer(SharedResource sharedResource){
        this.sharedResource = sharedResource;
    }

    public void produce() throws InterruptedException {
        while(true) {
            synchronized(sharedResource) {

                while(sharedResource.sharedQueue.size() == 5) {
                    System.out.println("Capacity reached. Producer will wait");
                    sharedResource.wait();
                    System.out.println("Producer back in action and resuming production");
                }
                sharedResource.sharedQueue.offer(100);
                System.out.println("Producer produced.");
                sharedResource.notifyAll();
            }
        }
    }
}

class Consumer {
    private SharedResource sharedResource;
    public Consumer(SharedResource sharedResource){
        this.sharedResource = sharedResource;
    }

    public void consume() throws InterruptedException {

        while(true) {
            synchronized (sharedResource) {
                while(sharedResource.sharedQueue.isEmpty()) {
                    System.out.println("Capacity is empty and consumer will wait.");
                    sharedResource.wait();
                }
                sharedResource.sharedQueue.poll();
                System.out.println("Consumer consumed");
                sharedResource.notifyAll();
            }
        }
    }
}

class ProducerConsumerDemo {
    public static void main(String[] args){
        SharedResource sharedResource = new SharedResource();
        Thread[] producers = new Thread[10];
        for(int i=0;i<10;i++) {
            Producer producer = new Producer(sharedResource);
            producers[i] = new Thread(()-> {
                try {
                    producer.produce();
                    Thread.sleep(1800);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        Thread[] consumers = new Thread[15];
        for(int i=0;i<15;i++) {
            Consumer consumer = new Consumer(sharedResource);
            consumers[i] = new Thread(()-> {
                try {
                    consumer.consume();
                    Thread.sleep(1100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        for(int i=0;i<15;i++) {
            consumers[i].start();
            if(i < 10) producers[i].start();
        }
    }
}


/**
 *
 * Types of locks -> Reentrant and ReadWrite locks.
 * They offer fine grained control over synchronization compared to synchronization keyword.
 * 'Reentrant' lock because the thread that holds the lock can reacquire it without causing a deadlock.
 *
 */


class ReentrantLockDemo {
    public int counter = 0;
    private final ReentrantLock reentrantLock = new ReentrantLock();

    public void increment() throws InterruptedException {

        // unlike a synchronized block, Reentrant lock's provie a tryLock in which you can specify
        // a timeout duration to keep rechecking for the lock.
        // Reentrant locks are great when you have various different methods doing various different operations
        // in your class and it doesn't make sense to have a lock on the instance or the class but rather to have
        // operational variable level locks. (We can create a reentrant lock per operation).
        // Hence, Reentrant locks are great for fine-grained synchronization.
        if(reentrantLock.tryLock(2, TimeUnit.SECONDS)) {
            reentrantLock.lock();
            System.out.println("Acquired the lock");
            counter++;
            System.out.println("Releasing the lock");
            reentrantLock.unlock();
        } else {
            System.out.println("Lock could not be acquired");
        }

    }

    public static void main(String[] args) throws InterruptedException{
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();
        for(int i=0;i<5;i++) {
            executorService.submit(()->{
                try {
                    reentrantLockDemo.increment();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        Thread.sleep(1500);
        System.out.println("final counter value: " + reentrantLockDemo.counter);
        executorService.shutdown();
    }
}

class ReentrantReadWriteLockDemo {
    public int counter = 0;
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public void incrementCounter() {
        reentrantReadWriteLock.writeLock().lock();
        System.out.println("Locking to write");
        counter++;
        reentrantReadWriteLock.writeLock().unlock();
    }

    public void getCounter() {
        reentrantReadWriteLock.readLock().lock();
        System.out.println("Locking to read.");
        reentrantReadWriteLock.readLock().unlock();
    }

    public static void main(String[] args) {

    }
}


/**
 *
 * Semaphores - can you control how many threads go in at the same time inside a critical section?
 * A semaphore maintains a count of entry permits.
 *
 * acquire() : obtain a permit. Blocking if necessary until a permit becomes available
 * release() : returns a permit back to the semaphore
 *
 * Types of semaphores: binary, counting
 * Binary semaphore: has only two states (0 or 1 permit) and is mainly used to enforce mutex: similar to a lock.
 * Counting semaphore: multiple permits.
 *
 *
 * calling release() without an acquire() - it will simply increase the permit count. valid operation.
 */

class BinarySemaphoreExample {
    private static final Semaphore mutex = new Semaphore(1); // binary semaphore with 1 permit

    private static void accessCriticalSection() throws InterruptedException{
        System.out.println("acquiring the lock");
        mutex.acquire();
        // do stuff.
        mutex.release();
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            try {
                accessCriticalSection();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

/**
 *
 * // Let us implement the Barrier synchronization pattern using semaphores
 * // -> A barrier ensures that no thread can proceed past a certain point until all threads have reached that point.
 * // if at the barrier, the semaphore count is 0, no thread can acquire the permit and move past it.
 * // but assuming the last thread reaches the point, we need to make the permit = max_thread_count.
 *
 * int thread_entered_remaining = max_thread_cnt;
 * Semaphore barrier = new Semaphore(0);
 * Semaphore mutex = new Semaphore(1); // exactly the same as a normal lock
 *
 * void await() {
 *  mutex.acquire();
 *  thread_entered_remaining--;
 *  if(thread_entered_remaining == 0) {
 *      // my job is to release permits for everyone else.
 *      barrier.release(max_threads-1);
 *      mutex.release();
 *
 *  } else {
 *      // do some task and wait in the end due to the barrier.
 *      mutex.release();
 *      barrier.acquire();
 *  }

 * }
 *
 */


/**
 *
 *semaphore s = new Semaphore(100); s.release(0); what are the new number of permits available? It is 100.
 *
 * How to implement a read-write lock using semaphores?
 */


class ReadWriteLockWithSemaphore {
    private static AtomicInteger readerCount = new AtomicInteger(0);
    private static Semaphore mutex = new Semaphore(1);

    public void readerReads() throws InterruptedException {
        if(readerCount.incrementAndGet() == 1) {
            System.out.println("First reader thread will try and grab the exclusive lock");
            mutex.acquire();
        }
        // read stuff
        if(readerCount.decrementAndGet() == 0) {
            System.out.println("Last reader thread will release the exclusive lock");
            mutex.release();
        }
    }

    public void writerWrites() throws InterruptedException {
        mutex.acquire();
        // write stuff
        mutex.release();
    }
}