package utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataUtil {
    public static String formatarData(LocalDate data) {
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static LocalDate converterParaLocalDate(Date data) {
        return data.toLocalDate();
    }

    public static Date converterParaSQLDate(LocalDate data) {
        return Date.valueOf(data);
    }
}
