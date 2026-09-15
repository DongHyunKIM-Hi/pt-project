package com.example.ptproject;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeGeneratorTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Test
    void 지금부터_N일_뒤_시각을_출력한다() {
        LocalDateTime oneHourLater = LocalDateTime.now().plusHours(1);
        LocalDateTime oneDayLater = LocalDateTime.now().plusDays(1);
        LocalDateTime oneWeekLater = LocalDateTime.now().plusWeeks(1);

        System.out.println("1시간 후  : " + oneHourLater.format(FORMATTER));
        System.out.println("1일 후    : " + oneDayLater.format(FORMATTER));
        System.out.println("1주일 후  : " + oneWeekLater.format(FORMATTER));
    }

    @Test
    void 내가_원하는_날짜를_직접_지정해서_출력한다() {
        LocalDateTime custom = LocalDateTime.of(2026, 9, 20, 14, 30, 0);

        System.out.println("지정한 시각 : " + custom.format(FORMATTER));
    }

    @Test
    void 과거_시각도_출력해본다_400_테스트용() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

        System.out.println("어제(과거, @Future 검증 실패용) : " + yesterday.format(FORMATTER));
    }
}
