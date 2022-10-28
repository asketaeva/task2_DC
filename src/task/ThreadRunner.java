package task;

import task.Input;
import task.Processor;
import task.ThreadSafeQueue;

import java.io.IOException;

public class ThreadRunner extends Thread {
    private final ThreadSafeQueue queue;

    public ThreadRunner(ThreadSafeQueue queue) {
        this.queue = queue;
    }

    public void StartProcessor(Input input) throws IOException {
        Processor proc = new Processor(input.socket, input.request);
        proc.process();
    }

    public void run() {
        try {
            while(true) {
                Input elem = this.queue.pop();
                if (elem == null) {
                    return;
                }

                this.StartProcessor(elem);
            }
        } catch (InterruptedException var2) {
            var2.printStackTrace();
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }
}
