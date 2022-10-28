package task;
import java.util.LinkedList;
import java.util.Queue;

public class ThreadSafeQueue {
    private final Queue<Input> queue = new LinkedList();

    public ThreadSafeQueue() {
    }

    public synchronized void add(Input elem) {
        this.queue.add(elem);
        this.notify();
    }

    public synchronized Input pop() throws InterruptedException {
        while(this.queue.isEmpty()) {
            this.wait();
        }

        return (Input)this.queue.poll();
    }

    public synchronized int size() {
        return this.queue.size();
    }
}
