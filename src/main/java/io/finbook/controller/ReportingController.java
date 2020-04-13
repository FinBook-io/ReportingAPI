package io.finbook.controller;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.model.Invoice;
import io.finbook.service.InvoiceService;
import io.finbook.spark.ResponseCreator;
import io.finbook.util.Utils;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ReportingController {

    private static InvoiceService invoiceService = new InvoiceService();

    public static ResponseCreator index(String currentUserId) {
        HashMap<String, Object> data = new HashMap<>();

        // MONTHLY
        // monthlyIncomesTaxes -  monthlyRefundsTaxes -  monthlyTotalTaxesDue
        Date startDate = null;
        try {
            startDate = Utils.parseStringToDate("2020-04-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date today = Utils.getCurrentDate();


        List<Invoice> invoices = invoiceService.getAllInvoicesByIssuerIdBetweenTwoDates(
                currentUserId, startDate , today);
        Double monthlyIncomesTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("monthlyIncomesTaxes", Utils.formatDouble(monthlyIncomesTaxes));
        invoices = invoiceService.getAllInvoicesByReceiverIdBetweenTwoDates(
                currentUserId, startDate , today);
        Double monthlyRefundsTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("monthlyRefundsTaxes", Utils.formatDouble(monthlyRefundsTaxes));
        data.put("monthlyTotalTaxesDue", Utils.formatDouble(monthlyIncomesTaxes - monthlyRefundsTaxes));

        // QUARTERLY
        // quarterlyIncomeTaxes - quarterlyRefundTaxes - quarterlyTotalTaxesDue


        // BIANNUAL
        // biannualIncomeTaxes - biannualRefundTaxes - biannualTotalTaxesDue



        // ANNUAL
        // annualIncomeTaxes - annualRefundTaxes - annualTotalTaxesDue

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
