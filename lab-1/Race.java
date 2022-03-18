public class Race {
    public static void main(String[] args) {
        Counter counter = new Counter(0);

        var iThread = new IThread(counter);
        iThread.start();

        var dThread = new DThread(counter);
        dThread.start();

        System.out.println(counter.value());
    }
}

class IThread extends Thread {

    Counter _counter;

    public IThread(Counter counter) {
        _counter = counter;
    }

    public void run() {
        for (int i = 0; i < 100_000_000; i++) {
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
        for (int i = 0; i < 100_000_000; i++) {
            _counter.dec();
        }

    }
}

class Counter {
    private int _val;

    public Counter(int n) {
        _val = n;
    }

    public void inc() {
        _val++;
    }

    public void dec() {
        _val--;
    }

    public int value() {
        return _val;
    }
}