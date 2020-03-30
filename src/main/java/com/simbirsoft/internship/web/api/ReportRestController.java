package com.simbirsoft.internship.web.api;

import com.simbirsoft.internship.dto.report.RevenueReport;
import com.simbirsoft.internship.dto.report.StoreSalesReport;
import com.simbirsoft.internship.dto.report.WriteOffReport;
import com.simbirsoft.internship.service.ReportService;
import io.swagger.annotations.ApiOperation;
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
     * Main KPI report.
     * @see ReportService#getRevenueReport
     *
     * @param a_startDate generate report from startDate. If null, report will be generated from first record.
     * @param b_endDate generate report up to endDate. If null, report will be generated from startDate and up to date.
     *                  If both null, report will be on current date.
     * @return report or NotFoundException if no data in DB.
     */
    @ApiOperation(value = "Get main KPI report", notes = "Input startDate and endDate to generate a report. " +
            "If startDate and endDate not provided, will be generated report on today's date.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "")
    public RevenueReport getRevenueReport(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate a_startDate,
                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate b_endDate) {
        return reportService.getRevenueReport(a_startDate, b_endDate);
    }

    /**
     * Store sales report.
     * @see ReportService#getStoreSalesReport 
     *
     * @param a_startDate generate report from startDate. If null, report will be generated from first record.
     * @param b_endDate generate report up to endDate. If null, report will be generated from startDate and up to date.
     *                  If both null, report will be on current date.
     * @return report or NotFoundException if no data in DB.
     */
    @ApiOperation(value = "Get sales report on single store", notes = "Input startDate and endDate to generate a report. " +
            "If startDate and endDate not provided, will be generated report on today's date.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/store/{storeId}")
    public StoreSalesReport getStoreSalesReport(@PathVariable int storeId,
                                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate a_startDate,
                                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate b_endDate) {
        return reportService.getStoreSalesReport(storeId, a_startDate, b_endDate);
    }

    /**
     * Write-off report.
     * @see ReportService#getWriteOffReport 
     *
     * @param a_startDate generate report from startDate. If null, report will be generated from first record.
     * @param b_endDate generate report up to endDate. If null, report will be generated from startDate and up to date.
     *                  If both null, report will be on current date.
     * @return report or NotFoundException if no data in DB.
     */
    @ApiOperation(value = "Get write-off report", notes = "Input startDate and endDate to generate a report. " +
            "If startDate and endDate not provided, will be generated report on today's date.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/writeoff")
    public WriteOffReport getWriteOffReport(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate a_startDate,
                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate b_endDate) {
        return reportService.getWriteOffReport(a_startDate, b_endDate);
    }
}
