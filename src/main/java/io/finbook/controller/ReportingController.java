package io.finbook.controller;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.model.Invoice;
import io.finbook.service.InvoiceService;
import io.finbook.spark.ResponseCreator;
import io.finbook.util.Utilities;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ReportingController {

    private static InvoiceService invoiceService = new InvoiceService();

    public static ResponseCreator index() {
        HashMap<String, Object> data = new HashMap<>();

        // MONTHLY
        // monthlyIncomes -  monthlyIncomeTaxes -  monthlyRefunds -  monthlyRefundTaxes -  monthlyTotalTaxesDue
        /*Date startDate = new Date();
        try {
            startDate = Utilities.parseStringToDate("2020-04-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date today = Utilities.getCurrentDateAndHour();
        List<Invoice> invoices = invoiceService.getAllInvoiceByIssuerIdBetweenTwoDates(Utilities.getCurrentUser(), startDate , today);
        Double monthlyIncomes = 0.0;
        Double monthlyIncomeTaxes = 0.0;
        for(Invoice invoice : invoices){
            monthlyIncomes += invoice.getTotalDue();
            monthlyIncomeTaxes += invoice.getTotalTaxes();
        }
        data.put("monthlyIncomes", monthlyIncomes);
        data.put("monthlyIncomeTaxes", monthlyIncomeTaxes);*/

        // QUARTERLY
        // quarterlyIncomes - quarterlyIncomeTaxes - quarterlyRefunds - quarterlyRefundTaxes - quarterlyTotalTaxesDue


        // BIANNUAL
        // biannualIncomes - biannualIncomeTaxes - biannualRefunds - biannualRefundTaxes - biannualTotalTaxesDue



        // ANNUAL
        // annualIncomes - annualIncomeTaxes - annualRefunds - annualRefundTaxes - annualTotalTaxesDue

        return MyResponse.ok(
                new StandardResponse(data, "dashboard/reporting/index")
        );
    }

    public static ResponseCreator currentMonth() {
        HashMap<String, Object> data = new HashMap<>();
        return MyResponse.ok(
                new StandardResponse(data, "dashboard/reporting/current-month")
        );
    }

}
