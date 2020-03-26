package com.simbirsoft.internship.web.api;

import com.simbirsoft.internship.dto.report.MainKpiReport;
import com.simbirsoft.internship.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/report")
public class ReportRestController {
    // DB doesn't support LocalDate.MIN/MAX
    private static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    private ReportService reportService;

    public ReportRestController(ReportService reportService) {
        this.reportService = reportService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "")
    public MainKpiReport getMainKpi(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return reportService.getMainKpi(
                startDate != null ? startDate : MIN_DATE,
                endDate != null ? endDate: MAX_DATE);
    }
}
