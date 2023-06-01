package pl.symentis.jvm.jit;

public class UncommonTrapTypeProfile {

    private static final int CHUNK_SIZE = 1000;

    public static void main(String[] argv) {
        Calculate trap = new Sum();
        for (int i = 0; i < 400; ++i) {
            for (int j = 0; j < CHUNK_SIZE; ++j) {
                trap.calc(i, j);
            }
            if (i == 300) {
                trap = new Multiplay();
            }
        }
    }

    interface Calculate {
        int calc(int i, int j);
    }

    static class Sum implements Calculate {

        @Override
        public int calc(int i, int j) {
            return i + j;
        }
    }

    static class Multiplay implements Calculate {

        @Override
        public int calc(int i, int j) {
            return i * j;
        }
    }
}
