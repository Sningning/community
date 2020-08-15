package sningning.community;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author: Song Ningning
 * @date: 2020-08-15 19:58
 */
public class BlockingQueueTests {

    public static void main(String[] args) {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(20);
        new Thread(new Producer(blockingQueue), "producer").start();
        new Thread(new Consumer(blockingQueue), "consumer1").start();
        new Thread(new Consumer(blockingQueue), "consumer2").start();
        new Thread(new Consumer(blockingQueue), "consumer3").start();
    }
}

class Producer implements Runnable {

    private BlockingQueue<Integer> queue;

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 100; i++) {
                TimeUnit.MILLISECONDS.sleep(20);
                queue.put(i);
                System.out.println(Thread.currentThread().getName() + " 生产了 " + i + ", 队列中有 " + queue.size() + " 个数字");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {

    private BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
                Integer num = queue.take();
                System.out.println(Thread.currentThread().getName() + " 消费了 " + num + ", 队列中还有 " + queue.size() + " 个数字");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
