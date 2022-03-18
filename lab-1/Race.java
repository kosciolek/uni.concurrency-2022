import java.util.concurrent.atomic.AtomicBoolean;

public class Race {
    public static void main(String[] args) throws Exception {
        Counter counter = new Counter(0);

        var iThread = new IThread(counter);
        iThread.start();

        var dThread = new DThread(counter);
        dThread.start();

        iThread.join();
        dThread.join();

        System.out.println(counter.value());
    }
}

class IThread extends Thread {

    Counter _counter;

    public IThread(Counter counter) {
        _counter = counter;
    }

    public void run() {
        for (int i = 0; i < 1_000_000; i++) {
            _counter.inc();
        }
    }
}

class DThread extends Thread {

    Counter _counter;

    public DThread(Counter counter) {
        _counter = counter;
    }

    public void run() {
        for (int i = 0; i < 1_000_000; i++) {
            _counter.dec();
        }

    }
}

class Counter {
    private int _val;
    private Lock _lock = new Lock();

    public Counter(int n) {
        _val = n;
    }

    public void inc() {
        //_lock.lock();
        _val++;
        //_lock.unlock();
    }

    public void dec() {
        //_lock.lock();
        _val--;
        //_lock.unlock();
    }

    public int value() {
        return _val;
    }
}

class Lock {
    private AtomicBoolean isLocked = new AtomicBoolean(false);

    public void lock() {
        while (!isLocked.weakCompareAndSetAcquire(false, true))
            Thread.yield();
    }

    public void unlock() {
        isLocked.set(false);
    }
}