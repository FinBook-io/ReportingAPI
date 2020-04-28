package io.finbook.command;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.model.Invoice;
import io.finbook.service.InvoiceService;
import io.finbook.sparkcontroller.ResponseCreator;
import io.finbook.util.Utils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class ReportingCommand {

    private static InvoiceService invoiceService = new InvoiceService();

    public static ResponseCreator index(String currentUserId) {
        HashMap<String, Object> data = new HashMap<>();

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
        HashMap<String, Object> data = new HashMap<>();
        data.putAll(getDataForCurrentMonth(currentUserId));
        return MyResponse.ok(
                new StandardResponse(data, "dashboard/reporting/current-month")
        );
    }

    private static List<Invoice> getInvoicesByIssuerIdListPerPeriod(String currentUserId, LocalDateTime startDate, LocalDateTime endDate){
        return invoiceService.getAllInvoicesByIssuerIdBetweenTwoDates(currentUserId, startDate , endDate);
    }

    private static List<Invoice> getInvoicesByReceiverIdListPerPeriod(String currentUserId, LocalDateTime startDate, LocalDateTime endDate){
        return invoiceService.getAllInvoicesByReceiverIdBetweenTwoDates(currentUserId, startDate , endDate);
    }

    private static HashMap<String, Object> getDataForCurrentMonth(String currentUserId){
        // monthlyIncomesTaxes -  monthlyRefundsTaxes -  monthlyTotalTaxesDue
        HashMap<String, Object> data = new HashMap<>();

        LocalDateTime startDate = Utils.getFirstDayCurrentMonth();
        LocalDateTime endDate = Utils.getCurrentDate();

        List<Invoice> invoices = getInvoicesByIssuerIdListPerPeriod(currentUserId, startDate, endDate);
        Double monthlyIncomesTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("monthlyIncomesTaxes", Utils.formatDouble(monthlyIncomesTaxes));

        invoices = getInvoicesByReceiverIdListPerPeriod(currentUserId, startDate, endDate);
        Double monthlyRefundsTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("monthlyRefundsTaxes", Utils.formatDouble(monthlyRefundsTaxes));

        data.put("monthlyTotalTaxesDue", Utils.formatDouble(monthlyIncomesTaxes - monthlyRefundsTaxes));

        return data;
    }

    private static LocalDateTime getFirstDateOfCurrentQuarter(){
        int currentMonthNumber = Utils.getCurrentMonth();
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
        LocalDateTime endDate = Utils.getCurrentDate();

        List<Invoice> invoices = getInvoicesByIssuerIdListPerPeriod(currentUserId, startDate, endDate);
        Double quarterlyIncomeTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("quarterlyIncomesTaxes", Utils.formatDouble(quarterlyIncomeTaxes));

        invoices = getInvoicesByReceiverIdListPerPeriod(currentUserId, startDate, endDate);
        Double quarterlyRefundTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("quarterlyRefundsTaxes", Utils.formatDouble(quarterlyRefundTaxes));

        data.put("quarterlyTotalTaxesDue", Utils.formatDouble(quarterlyIncomeTaxes - quarterlyRefundTaxes));

        return data;
    }

    private static LocalDateTime getFirstDateOfCurrentBiannual(){
        int currentMonthNumber = Utils.getCurrentMonth();
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
        LocalDateTime endDate = Utils.getCurrentDate();

        List<Invoice> invoices = getInvoicesByIssuerIdListPerPeriod(currentUserId, startDate, endDate);
        Double biannualIncomesTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("biannualIncomesTaxes", Utils.formatDouble(biannualIncomesTaxes));

        invoices = getInvoicesByReceiverIdListPerPeriod(currentUserId, startDate, endDate);
        Double biannualRefundsTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("biannualRefundsTaxes", Utils.formatDouble(biannualRefundsTaxes));

        data.put("biannualTotalTaxesDue", Utils.formatDouble(biannualIncomesTaxes - biannualRefundsTaxes));

        return data;
    }

    private static HashMap<String, Object> getDataForCurrentAnnual(String currentUserId){
        // annualIncomesTaxes - annualRefundsTaxes - annualTotalTaxesDue
        HashMap<String, Object> data = new HashMap<>();

        LocalDateTime startDate = Utils.getDateOfSpecificMonth(1); // starting in January
        LocalDateTime endDate = Utils.getCurrentDate();

        List<Invoice> invoices = getInvoicesByIssuerIdListPerPeriod(currentUserId, startDate, endDate);
        Double annualIncomesTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("annualIncomesTaxes", Utils.formatDouble(annualIncomesTaxes));

        invoices = getInvoicesByReceiverIdListPerPeriod(currentUserId, startDate, endDate);
        Double annualRefundsTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("annualRefundsTaxes", Utils.formatDouble(annualRefundsTaxes));

        data.put("annualTotalTaxesDue", Utils.formatDouble(annualIncomesTaxes - annualRefundsTaxes));

        return data;
    }

}
