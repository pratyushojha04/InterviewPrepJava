
package practice.MultiThreading;

public class Count {

    // Shared counter class
    static class Counter {
        private int count = 0;

        // synchronized for thread safety
        public synchronized void increment() {
            count++;
        }

        public int getCount() {
            return count;
        }
    }

    // Worker thread
    static class Worker implements Runnable {
        private final Counter counter;

        public Worker(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        }
    }

    public void threadImp() {

        Counter counter = new Counter();

        Thread t1 = new Thread(new Worker(counter));
        Thread t2 = new Thread(new Worker(counter));
        Thread t3 = new Thread(new Worker(counter));
        Thread t4 = new Thread(new Worker(counter));

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final counter value: " + counter.getCount());
    }

    public static void main(String[] args) {
        Count c = new Count();
        System.out.println("Thread Implementation:");
        c.threadImp();
    }
}
