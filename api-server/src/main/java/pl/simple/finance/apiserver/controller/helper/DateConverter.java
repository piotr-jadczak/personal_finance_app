package pl.simple.finance.apiserver.controller.helper;

import java.time.LocalDate;

public class DateConverter {

    public static LocalDate convertDate(String date) {

        String[] el = date.split("-");
        int year = Integer.parseInt(el[0]);
        int month = Integer.parseInt(el[1]);
        int day = Integer.parseInt(el[2]);
        return LocalDate.of(year, month, day);
    }
}
