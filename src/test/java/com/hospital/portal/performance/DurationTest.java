package com.hospital.portal.performance;

import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.JUnitPerfTestActiveConfig;
import com.github.noconnor.junitperf.JUnitPerfTestRequirement;
import com.github.noconnor.junitperf.JUnitPerfInterceptor;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

import com.hospital.portal.service.AdminService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(JUnitPerfInterceptor.class)
public class DurationTest {

    @Autowired
    private AdminService adminService;

    @JUnitPerfTestActiveConfig
    private static final com.github.noconnor.junitperf.JUnitPerfReportingConfig PERF_CONFIG =
            com.github.noconnor.junitperf.JUnitPerfReportingConfig.builder()
                    .reportGenerator(new HtmlReportGenerator(System.getProperty("user.dir") + "/target/reports/duration-perf-report.html"))
                    .build();

    @BeforeEach
    void setup() {
        // Optional pre-test setup
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 15000, warmUpMs = 2000)
    @JUnitPerfTestRequirement(percentiles = "95:1000ms")
    public void testGetAllAppointmentsStrictDuration() {
        adminService.getAllAppointments();
    }
}
