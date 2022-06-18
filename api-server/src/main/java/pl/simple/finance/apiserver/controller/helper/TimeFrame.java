package pl.simple.finance.apiserver.controller.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Month;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeFrame {

    private LocalDate start;
    private LocalDate end;

    public static TimeFrame getTimeFrameOfMonthAndYear(int monthN, int yearN) {

        Month month = Month.of(monthN);
        LocalDate startDate = LocalDate.of(yearN, month, 1);
        LocalDate endDate = LocalDate.of(yearN, month, month.length(true));
        return new TimeFrame(startDate, endDate);
    }
}
