package pl.symentis.jvm.jit;

public class Fibonnaci {
    public static long fibonacci(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
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
