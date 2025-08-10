package pl.symentis.jvm.jit;

public class UncommonTrap {

    private static final int CHUNK_SIZE = 1000;

    private static Object uncommonTrap(Object trap) {
        if (trap == null) {
            System.out.println("I am being trapped!");
        }
        return null;
    }

    public static void main(String[] argv) {
        Object trap = null;
        for (int i = 0; i < 400; ++i) {
            for (int j = 0; j < CHUNK_SIZE; ++j) {
                trap = uncommonTrap(trap);
            }
            if (i == 300) {
                trap = new Object();
            }
        }
    }
}
