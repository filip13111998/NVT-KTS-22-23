package ftn.uns.ac.rs.NVTKTS20222023.converter;

import org.junit.jupiter.api.Test;

import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateConverterTest {

    @Test
    void testConverDateToDayStart() {
        DateConverter dateConverter = new DateConverter();

        // Define the input date in milliseconds
        long inputDateMillis = 1658637000000L; // April 23, 2022 13:30:00

        // Define the expected output date in milliseconds (start of day)
        long expectedOutputMillis = 1658613600000l; // April 23, 2022 00:00:00

        // Call the method to convert the date
        long actualOutputMillis = dateConverter.converDateToDayStart(inputDateMillis);

        // Assert that the actual output matches the expected output
        assertEquals(expectedOutputMillis, actualOutputMillis);
    }

    @Test
    void testConverDateToDayStart_MiddleOfDay() {
        DateConverter dateConverter = new DateConverter();

        // Define the input date in milliseconds
        long inputDateMillis = 1658637000000L; // April 23, 2022 13:30:00

        // Define the expected output date in milliseconds (start of day)
        long expectedOutputMillis = 1658613600000l; // April 23, 2022 00:00:00

        // Call the method to convert the date
        long actualOutputMillis = dateConverter.converDateToDayStart(inputDateMillis);

        // Assert that the actual output matches the expected output
        assertEquals(expectedOutputMillis, actualOutputMillis);
    }

    @Test
    void testConverDateToDayStart_StartOfDay() {
        DateConverter dateConverter = new DateConverter();

        // Define the input date in milliseconds
        long inputDateMillis = 1658582400000L; // April 23, 2022 00:00:00

        // Define the expected output date in milliseconds (start of day)
        long expectedOutputMillis = 1658527200000L; // April 23, 2022 00:00:00

        // Call the method to convert the date
        long actualOutputMillis = dateConverter.converDateToDayStart(inputDateMillis);

        // Assert that the actual output matches the expected output
        assertEquals(expectedOutputMillis, actualOutputMillis);
    }


}
