package com.simbirsoft.internship.service.impl;

import com.simbirsoft.internship.dto.report.RevenueReport;
import com.simbirsoft.internship.dto.report.WriteOffReport;
import com.simbirsoft.internship.repository.PurchaseRepository;
import com.simbirsoft.internship.repository.WriteOffRepository;
import com.simbirsoft.internship.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static com.simbirsoft.internship.testdata.ReportTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class ReportServiceImplTest {
    @MockBean
    private PurchaseRepository purchaseRepository;
    @MockBean
    private WriteOffRepository writeOffRepository;
    @Autowired
    private ReportService reportService;

    @Test
    void getRevenueReport() {
        // when
        when(purchaseRepository.getAllBetween(START_DATE_TIME, END_DATE_TIME)).thenReturn(Collections.singletonList(PURCHASE_ENTITY));
        RevenueReport result = reportService.getRevenueReport(START_DATE, END_DATE);
        assertEquals(REVENUE_REPORT, result);
        // than
        verify(purchaseRepository, times(1)).getAllBetween(any(), any());

    }

    @Test
    void getWriteOffReport() {
        when(writeOffRepository.getAllBetween(START_DATE_TIME, END_DATE_TIME)).thenReturn(WRITEOFF_ENTITIES);
        WriteOffReport result = reportService.getWriteOffReport(START_DATE, END_DATE);
        assertEquals(WRITEOFF_REPORT, result);
        verify(writeOffRepository, times(1)).getAllBetween(any(), any());
    }
}