package io.finbook.controller;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.model.Invoice;
import io.finbook.service.InvoiceService;
import io.finbook.spark.ResponseCreator;
import io.finbook.util.Utils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class ReportingController {

    private static InvoiceService invoiceService = new InvoiceService();

    private static LocalDateTime today;
    private static int currentMonthNumber;

    public static ResponseCreator index(String currentUserId) {
        HashMap<String, Object> data = new HashMap<>();

        today = Utils.getCurrentDate();
        currentMonthNumber = Utils.getCurrentMonth();

        // MONTHLY
        data.putAll(getDataForCurrentMonth(currentUserId));

        // QUARTERLY
        data.putAll(getDataForCurrentQuarterly(currentUserId));

        // BIANNUAL
        data.putAll(getDataForCurrentBiannual(currentUserId));

        // ANNUAL
        data.putAll(getDataForCurrentAnnual(currentUserId));

        return MyResponse.ok(
                new StandardResponse(data, "dashboard/reporting/index")
        );
    }

    public static ResponseCreator currentMonth(String currentUserId) {
        return MyResponse.ok(
                new StandardResponse(new HashMap<>(getDataForCurrentMonth(currentUserId)), "dashboard/reporting/current-month")
        );
    }

    private static List<Invoice> getInvoicesByIssuerIdListBetweenTwoDates(String currentUserId, LocalDateTime startDate){
        return invoiceService.getAllInvoicesByIssuerIdBetweenTwoDates(currentUserId, startDate , today);
    }

    private static List<Invoice> getInvoicesByReceiverIdListBetweenTwoDates(String currentUserId, LocalDateTime startDate){
        return invoiceService.getAllInvoicesByReceiverIdBetweenTwoDates(currentUserId, startDate , today);
    }

    private static HashMap<String, Object> getDataForCurrentMonth(String currentUserId){
        // monthlyIncomesTaxes -  monthlyRefundsTaxes -  monthlyTotalTaxesDue
        HashMap<String, Object> data = new HashMap<>();

        LocalDateTime startDate = Utils.getFirstDayCurrentMonth();

        List<Invoice> invoices = getInvoicesByIssuerIdListBetweenTwoDates(currentUserId, startDate);
        Double monthlyIncomesTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("monthlyIncomesTaxes", Utils.formatDouble(monthlyIncomesTaxes));

        invoices = getInvoicesByReceiverIdListBetweenTwoDates(currentUserId, startDate);
        Double monthlyRefundsTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("monthlyRefundsTaxes", Utils.formatDouble(monthlyRefundsTaxes));

        data.put("monthlyTotalTaxesDue", Utils.formatDouble(monthlyIncomesTaxes - monthlyRefundsTaxes));

        return data;
    }

    private static LocalDateTime getFirstDateOfCurrentQuarter(){
        if (currentMonthNumber >= 1 && currentMonthNumber <= 3){
            return Utils.getDateOfSpecificMonth(1); // starting in January
        }else if (currentMonthNumber >= 4 && currentMonthNumber <= 6){
            return Utils.getDateOfSpecificMonth(4); // starting in April
        }else if (currentMonthNumber >= 7 && currentMonthNumber <= 9){
            return Utils.getDateOfSpecificMonth(7); // starting in July
        }else{
            return Utils.getDateOfSpecificMonth(10); // starting in July
        }
    }

    private static HashMap<String, Object> getDataForCurrentQuarterly(String currentUserId){
        // quarterlyIncomesTaxes - quarterlyRefundsTaxes - quarterlyTotalTaxesDue
        HashMap<String, Object> data = new HashMap<>();

        LocalDateTime startDate = getFirstDateOfCurrentQuarter();

        List<Invoice> invoices = getInvoicesByIssuerIdListBetweenTwoDates(currentUserId, startDate);
        Double quarterlyIncomeTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("quarterlyIncomesTaxes", Utils.formatDouble(quarterlyIncomeTaxes));

        invoices = getInvoicesByReceiverIdListBetweenTwoDates(currentUserId, startDate);
        Double quarterlyRefundTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("quarterlyRefundsTaxes", Utils.formatDouble(quarterlyRefundTaxes));

        data.put("quarterlyTotalTaxesDue", Utils.formatDouble(quarterlyIncomeTaxes - quarterlyRefundTaxes));

        return data;
    }

    private static LocalDateTime getFirstDateOfCurrentBiannual(){
        if (currentMonthNumber >= 1 && currentMonthNumber <= 6){
            return Utils.getDateOfSpecificMonth(1); // starting in January
        }else{
            return Utils.getDateOfSpecificMonth(7); // starting in July
        }
    }

    private static HashMap<String, Object> getDataForCurrentBiannual(String currentUserId){
        // biannualIncomesTaxes - biannualRefundsTaxes - biannualTotalTaxesDue
        HashMap<String, Object> data = new HashMap<>();

        LocalDateTime startDate = getFirstDateOfCurrentBiannual();

        List<Invoice> invoices = getInvoicesByIssuerIdListBetweenTwoDates(currentUserId, startDate);
        Double biannualIncomesTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("biannualIncomesTaxes", Utils.formatDouble(biannualIncomesTaxes));

        invoices = getInvoicesByReceiverIdListBetweenTwoDates(currentUserId, startDate);
        Double biannualRefundsTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("biannualRefundsTaxes", Utils.formatDouble(biannualRefundsTaxes));

        data.put("biannualTotalTaxesDue", Utils.formatDouble(biannualIncomesTaxes - biannualRefundsTaxes));

        return data;
    }

    private static HashMap<String, Object> getDataForCurrentAnnual(String currentUserId){
        // annualIncomesTaxes - annualRefundsTaxes - annualTotalTaxesDue
        HashMap<String, Object> data = new HashMap<>();

        LocalDateTime startDate = Utils.getDateOfSpecificMonth(1); // starting in January

        List<Invoice> invoices = getInvoicesByIssuerIdListBetweenTwoDates(currentUserId, startDate);
        Double annualIncomesTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("annualIncomesTaxes", Utils.formatDouble(annualIncomesTaxes));

        invoices = getInvoicesByReceiverIdListBetweenTwoDates(currentUserId, startDate);
        Double annualRefundsTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("annualRefundsTaxes", Utils.formatDouble(annualRefundsTaxes));

        data.put("annualTotalTaxesDue", Utils.formatDouble(annualIncomesTaxes - annualRefundsTaxes));

        return data;
    }

}
