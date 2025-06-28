package edu.uade.cookingrecipes.service.processors;

import edu.uade.cookingrecipes.entity.AttendanceRecord;
import edu.uade.cookingrecipes.repository.AttendanceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceProcessor {

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    @Scheduled(cron = "0 0 0 * * ?") // Ejecuta a medianoche todos los días
    public void processDailyAttendance() {
        LocalDate yesterday = LocalDate.now().minusDays(1);

        List<AttendanceRecord> records = attendanceRecordRepository.findAllByDateAndCountedFalse(yesterday);

        for (AttendanceRecord record : records) {
            if (record.isPresentSite() && record.isPresentClassroom()) {
                record.setCounted(true);
            }
        }

        attendanceRecordRepository.saveAll(records);

        System.out.println("Attendance records processed for date: " + yesterday +
                           ". Counted records updated: " + records.size());
    }
}
