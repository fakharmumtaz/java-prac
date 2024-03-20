package com.javaprac.thrds;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public class ThreadEx001 {
    // Thread example. Lock explained

    private static int count = 0;

    //in concurrent programming, a monitor is an object intended to be used safely by more than one thread
    //the Object class was created in a way to have characteristics that allow us to use any object as a monitor
    //this monitor, a.k.a. monitor-lock or intrinsic-lock manages synchronization in java
    //all threads calling the same synchronized method will share the same monitor
    //all implicit monitors implement the reentrant characters
    //reentrant means a thread can safely reacquire the same lock multiple times without running into deadlocks
    //Reentrant Locks
    //it is a mutual exclusion lock with the same basic behavior as the implicit monitors accessed via the synchronized keyword
    //if a thread has acquired the lock, new calls to the lock method pause their thread until the lock has been released
    // it has lock method and unlock method
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Runnable r = () -> {incrementWithoutLocking();};
        //executorService.submit(r);
        IntStream.range(0, 1000).forEach(i -> executorService.submit(r));
        Thread.sleep(5_000);
        //System.out.println("args = " + Arrays.toString(args));
        System.out.println("count = " + ThreadEx001.count);

        ThreadEx001.count = 0 ;
        Runnable r1 = () -> {incrementWithLock();};
        IntStream.range(1, 2000).forEach(i -> executorService.submit(r1));
        Thread.sleep(5_000);
        System.out.println("count = " + ThreadEx001.count);
        executorService.shutdown();

        functionalityReentratLock();
        functionalityReadWriteLock();

    }

    private static  void incrementWithoutLocking(){
        ///System.out.println("A="+ThreadEx001.count);
        ThreadEx001.count++;
    }

    private static void incrementWithLock() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            count = count + 1;
        } finally {
            lock.unlock();
        }
    }

    private static void functionalityReentratLock() {
        var lock = new ReentrantLock();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(
            () -> {
                lock.lock();
                try {
                    try {
                        System.out.println("functionalityReentratLock");
                        Thread.sleep(2_000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }finally {
                    lock.unlock();
                }

            }
        );
        executor.shutdown();

       /* ExecutorService executor1 = Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(()-> {}, 0, 1 , TimeUnit.SECONDS);
        Runnable r = () -> {
            System.out.println("lock = "+lock.isLocked());
            System.out.println("Held by this Thread = "+lock.isHeldByCurrentThread().isLocked());
            boolean locked = lock.tryLock();
            System.out.println("Lock="+locked);
        };
        //executor1.scheduleAtFixedRate();
        */
    }

    //Read Write Locks
    //the ReadWriteLock interface specifies a type of lock that maintains two locks, one for read and another for write access
    //it's usually safe to read mutable variables concurrently as long as nobody is writing into this variable
    //the read-lock can be held simultaneously by multiple threads as long as no threads hold the lock write-lock
    private static void functionalityReadWriteLock() throws InterruptedException {
        System.out.println("functionalityReadWriteLock");
        var list = new ArrayList<String>();
        var lock = new ReentrantReadWriteLock();
        var executor = Executors.newFixedThreadPool(2);
        Runnable writeTask = () -> {
          lock.writeLock().lock();
          try {
              list.add("geekific");
              Thread.sleep(2_000);

          } catch (InterruptedException e) {
              throw new RuntimeException(e);
          } finally {
              lock.writeLock().unlock();
          }
        };

        Runnable readTask = () -> {
          lock.readLock().lock();
          try{
              System.out.println(list.getFirst());
              Thread.sleep(2_000);
          } catch (InterruptedException e) {
              throw new RuntimeException(e);
          } finally {
              lock.readLock().unlock();
          }
        };
        executor.submit(writeTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.shutdown();
        Thread.sleep(5_000);
        System.out.println("list="+list);
    }

}
