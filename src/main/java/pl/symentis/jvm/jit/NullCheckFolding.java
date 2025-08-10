package pl.symentis.jvm.jit;

/**
 * java -XX:+UnlockDiagnosticVMOptions -XX:CompileCommand="print *.nullCheckFolding" -Xlog:jit+compilation=debug pl.symentis.jvm.jit.NullCheckFolding
 */
public class NullCheckFolding {

    public static void assertNotNull(Object obj) {
        if (obj == null) { // collect taken branches
            System.out.println(String.format("%s is null", obj));
        }
    }

    public void nullCheckFolding() {
        assertNotNull(this);
    }

    public static void main(String[] argv) {
        NullCheckFolding nullCheckFolding = new NullCheckFolding();
        for (int i = 0; i < 200000; i++) {
            nullCheckFolding.nullCheckFolding();
        }
    }
}
