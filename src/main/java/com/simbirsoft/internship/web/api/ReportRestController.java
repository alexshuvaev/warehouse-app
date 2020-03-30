package com.simbirsoft.internship.web.api;

import com.simbirsoft.internship.dto.report.RevenueReport;
import com.simbirsoft.internship.dto.report.StoreSalesReport;
import com.simbirsoft.internship.dto.report.WriteOffReport;
import com.simbirsoft.internship.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/report")
public class ReportRestController {
    private ReportService reportService;

    @Autowired
    public ReportRestController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     *
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "")
    public RevenueReport getRevenueReport(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return reportService.getRevenueReport(startDate, endDate);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/store/{storeId}")
    public StoreSalesReport getStoreSalesReport(@PathVariable int storeId,
                                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return reportService.getStoreSalesReport(storeId, startDate, endDate);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/writeoff")
    public WriteOffReport getWriteOffReport(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return reportService.getWriteOffReport(startDate, endDate);
    }
}
