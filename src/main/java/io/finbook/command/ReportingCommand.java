package io.finbook.command;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.model.Invoice;
import io.finbook.service.InvoiceService;
import io.finbook.sparkcontroller.ResponseCreator;
import io.finbook.util.Utils;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class ReportingCommand {

    private static InvoiceService invoiceService = new InvoiceService();

    public static ResponseCreator index(String currentUserId) {
        HashMap<String, Object> data = new HashMap<>();

        // MONTHLY
        data.putAll(getDataForCurrentMonth(currentUserId));

        // TRIMESTER
        data.putAll(getDataForCurrentTrimester(currentUserId));

        // semester
        data.putAll(getDataForCurrentsemester(currentUserId));

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

    private static List<Invoice> getInvoicesListByIssuerIdListPerPeriod(String currentUserId, LocalDateTime startDate, LocalDateTime endDate){
        return invoiceService.getAllInvoicesByIssuerIdPerPeriod(currentUserId, startDate , endDate);
    }

    private static List<Invoice> getInvoicesListByReceiverIdListPerPeriod(String currentUserId, LocalDateTime startDate, LocalDateTime endDate){
        return invoiceService.getAllInvoicesByReceiverIdPerPeriod(currentUserId, startDate , endDate);
    }

    private static HashMap<String, Object> getDataForCurrentMonth(String currentUserId){
        // monthlyIncomesTaxes -  monthlyRefundsTaxes -  monthlyTotalTaxesDue
        HashMap<String, Object> data = new HashMap<>();

        LocalDateTime startDate = Utils.getFirstDayCurrentMonth();
        LocalDateTime endDate = Utils.getCurrentDate();

        List<Invoice> invoices = getInvoicesListByIssuerIdListPerPeriod(currentUserId, startDate, endDate);
        Double monthlyIncomesTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("monthlyIncomesTaxes", Utils.formatDouble(monthlyIncomesTaxes));

        invoices = getInvoicesListByReceiverIdListPerPeriod(currentUserId, startDate, endDate);
        Double monthlyRefundsTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("monthlyRefundsTaxes", Utils.formatDouble(monthlyRefundsTaxes));

        data.put("monthlyTotalTaxesDue", Utils.formatDouble(monthlyIncomesTaxes - monthlyRefundsTaxes));

        return data;
    }

    private static LocalDateTime getFirstDateOfCurrentTrimester(){
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

    private static HashMap<String, Object> getDataForCurrentTrimester(String currentUserId){
        // trimesterIncomesTaxes - trimesterRefundsTaxes - trimesterTotalTaxesDue
        HashMap<String, Object> data = new HashMap<>();

        LocalDateTime startDate = getFirstDateOfCurrentTrimester();
        LocalDateTime endDate = Utils.getCurrentDate();

        List<Invoice> invoices = getInvoicesListByIssuerIdListPerPeriod(currentUserId, startDate, endDate);
        Double trimesterIncomeTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("trimesterIncomesTaxes", Utils.formatDouble(trimesterIncomeTaxes));

        invoices = getInvoicesListByReceiverIdListPerPeriod(currentUserId, startDate, endDate);
        Double trimesterRefundTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("trimesterRefundsTaxes", Utils.formatDouble(trimesterRefundTaxes));

        data.put("trimesterTotalTaxesDue", Utils.formatDouble(trimesterIncomeTaxes - trimesterRefundTaxes));

        return data;
    }

    private static LocalDateTime getFirstDateOfCurrentSemester(){
        int currentMonthNumber = Utils.getCurrentMonth();
        if (currentMonthNumber >= 1 && currentMonthNumber <= 6){
            return Utils.getDateOfSpecificMonth(1); // starting in January
        }else{
            return Utils.getDateOfSpecificMonth(7); // starting in July
        }
    }

    private static HashMap<String, Object> getDataForCurrentsemester(String currentUserId){
        // semesterIncomesTaxes - semesterRefundsTaxes - semesterTotalTaxesDue
        HashMap<String, Object> data = new HashMap<>();

        LocalDateTime startDate = getFirstDateOfCurrentSemester();
        LocalDateTime endDate = Utils.getCurrentDate();

        List<Invoice> invoices = getInvoicesListByIssuerIdListPerPeriod(currentUserId, startDate, endDate);
        Double semesterIncomesTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("semesterIncomesTaxes", Utils.formatDouble(semesterIncomesTaxes));

        invoices = getInvoicesListByReceiverIdListPerPeriod(currentUserId, startDate, endDate);
        Double semesterRefundsTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("semesterRefundsTaxes", Utils.formatDouble(semesterRefundsTaxes));

        data.put("semesterTotalTaxesDue", Utils.formatDouble(semesterIncomesTaxes - semesterRefundsTaxes));

        return data;
    }

    private static HashMap<String, Object> getDataForCurrentAnnual(String currentUserId){
        // annualIncomesTaxes - annualRefundsTaxes - annualTotalTaxesDue
        HashMap<String, Object> data = new HashMap<>();

        LocalDateTime startDate = Utils.getDateOfSpecificMonth(1); // starting in January
        LocalDateTime endDate = Utils.getCurrentDate();

        List<Invoice> invoices = getInvoicesListByIssuerIdListPerPeriod(currentUserId, startDate, endDate);
        Double annualIncomesTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("annualIncomesTaxes", Utils.formatDouble(annualIncomesTaxes));

        invoices = getInvoicesListByReceiverIdListPerPeriod(currentUserId, startDate, endDate);
        Double annualRefundsTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("annualRefundsTaxes", Utils.formatDouble(annualRefundsTaxes));

        data.put("annualTotalTaxesDue", Utils.formatDouble(annualIncomesTaxes - annualRefundsTaxes));

        return data;
    }

    /*
    *
    *
    * AJAX REQUESTS
    *
    *
    * */
    public static JSONObject getDataForPeriod(String currentUserId, String period){
        HashMap<String, Object> data = new HashMap<>();
        LocalDateTime startDate;
        LocalDateTime endDate = Utils.getCurrentDate();

        switch (period){
            case "monthly":
                startDate = Utils.getFirstDayCurrentMonth();
                break;
            case "trimester":
                startDate = getFirstDateOfCurrentTrimester();
                break;
            case "semester":
                startDate = getFirstDateOfCurrentSemester();
                break;
            case "annual":
                startDate = Utils.getDateOfSpecificMonth(1); // starting in January
                break;
            default:
                return null;
        }

        List<Invoice> invoices = getInvoicesListByIssuerIdListPerPeriod(currentUserId, startDate, endDate);
        Double monthlyIncomesTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("incomes", Utils.formatDouble(monthlyIncomesTaxes));

        invoices = getInvoicesListByReceiverIdListPerPeriod(currentUserId, startDate, endDate);
        Double monthlyRefundsTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("refunds", Utils.formatDouble(monthlyRefundsTaxes));

        data.put("totalTaxesDue", Utils.formatDouble(monthlyIncomesTaxes - monthlyRefundsTaxes));

        return new JSONObject(data);
    }

}
