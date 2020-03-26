package com.simbirsoft.internship.service;

import com.simbirsoft.internship.dto.report.MainKpiReport;

import java.time.LocalDate;

public interface ReportService {
   MainKpiReport getMainKpi(LocalDate startDate, LocalDate endDate);
}
