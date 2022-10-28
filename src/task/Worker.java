package task;

import java.io.IOException;
import java.util.Stack;

public class Worker extends Thread {
    private final int p;

    public Worker(int p) {
        this.p = p;
    }
}
