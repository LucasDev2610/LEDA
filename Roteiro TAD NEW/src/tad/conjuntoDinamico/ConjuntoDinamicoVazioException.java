package tad.conjuntoDinamico;

public class ConjuntoDinamicoVazioException extends Exception {
    public ConjuntoDinamicoVazioException() {
        super("Conjunto dinâmico Vazio!!!");
    }
    public ConjuntoDinamicoVazioException(String msg) {
        super(msg);
    }
}
