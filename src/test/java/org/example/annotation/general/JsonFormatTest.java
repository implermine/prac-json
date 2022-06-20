package org.example.annotation.general;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

/**
 * @JsonFormat Date / Time 관련
 */
public class JsonFormatTest {

    private static ObjectMapper objectMapper;

    static{
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Date 타입을 어떤식으로 직렬화 할지 결정")
    public void test1() throws ParseException, JsonProcessingException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        String toParse = "20-12-2014 02:30:00";
        Date date = df.parse(toParse);
        ObjectForTest obj = new ObjectForTest("obj", date, date, LocalDateTime.now(), LocalDateTime.now());


        String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);

        System.out.println(jsonString);

        /**
         * {
         *   "name" : "obj",
         *   "date" : "2014-12-20 02:30:00",
         *   "dateWithNoAnnotation" : 1419042600000,
         *   "localDateTime" : [ 2022, 6, 20, 22, 49, 21, 317738500 ],
         *   "localDateTimeWithJsonFormat" : "2022-06-20 10:49:21"
         * }
         */
    }


    // ================================================================================================================

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ObjectForTest {
        private String name;

        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd hh:mm:ss")
        private Date date;

        private Date dateWithNoAnnotation;

        private LocalDateTime localDateTime;

        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
        private LocalDateTime localDateTimeWithJsonFormat;
    }
}
