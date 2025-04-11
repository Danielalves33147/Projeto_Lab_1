package utils;

public class ValidadorUtil {
    public static boolean isVazioOuNulo(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
