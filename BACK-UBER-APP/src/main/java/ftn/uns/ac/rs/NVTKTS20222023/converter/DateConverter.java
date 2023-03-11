package ftn.uns.ac.rs.NVTKTS20222023.converter;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

@Component
public class DateConverter {

    //Konvertuje datum 23.04.2022 13:30:00 u 23.04.2022 00:00:00. Datum se salje u milisekundama i vraca se u milisekundama novi.
    public long converDateToDayStart(long date){
        LocalDateTime inputDate = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDateTime();

        // Set the time of day to midnight (start of day)
        LocalDateTime startOfDay = inputDate.with(LocalTime.MIN);

        // Get the milliseconds value for the start of day
        long startOfDayMillis = startOfDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        return startOfDayMillis;
    }

}
