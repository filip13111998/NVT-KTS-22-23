package ftn.uns.ac.rs.NVTKTS20222023.service;

import ftn.uns.ac.rs.NVTKTS20222023.dto.response.DayDTO;
import ftn.uns.ac.rs.NVTKTS20222023.dto.response.ReportRideDTO;
import ftn.uns.ac.rs.NVTKTS20222023.model.Ride;
import ftn.uns.ac.rs.NVTKTS20222023.sorter.RideDateSorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ReportService {

    @Autowired
    private RideDateSorter rds;

    public ReportRideDTO rideNumber(Long start, Long end) {
        Map<LocalDate, List<Ride>> map =  rds.sortRidesByDayInterval(start,end);

        if(map.keySet().size() == 0){
            return null;
        }

        List<DayDTO> days = new ArrayList<>();

        long sum = 0;

        for(Map.Entry<LocalDate , List<Ride>> entry : map.entrySet()){
            sum += entry.getValue().stream().count();
            ZonedDateTime zonedDateTime = entry.getKey().atStartOfDay(ZoneOffset.UTC);
            long milliseconds = zonedDateTime.toInstant().toEpochMilli();
            DayDTO day = DayDTO.builder().date(milliseconds).data( entry.getValue().stream().count()).build();
            days.add(day);
        }

        long average = sum/map.keySet().size();

        return ReportRideDTO.builder()
                .sumData(sum)
                .averageData(average)
                .dayDTOList(days)
                .build();

    }

    public ReportRideDTO rideMeters(Long start, Long end) {
        Map<LocalDate, List<Ride>> map =  rds.sortRidesByDayInterval(start,end);

        List<DayDTO> days = new ArrayList<>();

        long sum = 0;

        for(Map.Entry<LocalDate , List<Ride>> entry : map.entrySet()){
            ZonedDateTime zonedDateTime = entry.getKey().atStartOfDay(ZoneOffset.UTC);
            long milliseconds = zonedDateTime.toInstant().toEpochMilli();

            long daysum = entry.getValue().stream().mapToLong(Ride::getMeters).reduce(0, (a, b) -> a + b);

            sum += daysum;

            DayDTO day = DayDTO.builder().date(milliseconds).data(daysum).build();

            days.add(day);
        }

        long average = sum/map.keySet().size();

        return ReportRideDTO.builder()
                .sumData(sum)
                .averageData(average)
                .dayDTOList(days)
                .build();

    }

    public ReportRideDTO ridePrice(Long start, Long end) {
        Map<LocalDate, List<Ride>> map =  rds.sortRidesByDayInterval(start,end);

        List<DayDTO> days = new ArrayList<>();

        long sum = 0;


        for(Map.Entry<LocalDate , List<Ride>> entry : map.entrySet()){

            ZonedDateTime zonedDateTime = entry.getKey().atStartOfDay(ZoneOffset.UTC);
            long milliseconds = zonedDateTime.toInstant().toEpochMilli();

            long daysum = (long) entry.getValue().stream().mapToDouble(Ride::getPrice).reduce(0, (a, b) -> a + b);

            sum += daysum;

            DayDTO day = DayDTO.builder().date(milliseconds).data(daysum).build();

            days.add(day);
        }

        long average = sum/map.keySet().size();

        return ReportRideDTO.builder()
                .sumData(sum)
                .averageData(average)
                .dayDTOList(days)
                .build();

    }
}
