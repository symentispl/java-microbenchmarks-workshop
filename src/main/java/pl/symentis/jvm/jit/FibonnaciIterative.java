package pl.symentis.jvm.jit;

public class FibonnaciIterative {
    public static long fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        int fib = 1;
        int prevFib = 1;

        for (int i = 2; i < n; i++) {
            int temp = fib;
            fib += prevFib;
            prevFib = temp;
        }
        return fib;
    }

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 200; i++) {
            fibonacci(32);
        }
        time = System.currentTimeMillis() - time;
        System.out.printf("time is %d\n", time);
    }
}
