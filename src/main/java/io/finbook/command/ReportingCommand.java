package io.finbook.command;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.model.Invoice;
import io.finbook.model.InvoiceType;
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

        // Semester
        data.putAll(getDataForCurrentSemester(currentUserId));

        // ANNUAL
        data.putAll(getDataForCurrentAnnual(currentUserId));

        return MyResponse.ok(
                new StandardResponse(data, "dashboard/reporting/index")
        );
    }

    private static HashMap<String, Object> getDataForCurrentMonth(String currentUserId){
        HashMap<String, Object> data = new HashMap<>();

        LocalDateTime startDate = Utils.getFirstDayCurrentMonth();
        LocalDateTime endDate = Utils.getCurrentDate();

        List<Invoice> invoices = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.INCOME, startDate, endDate);
        Double monthlyIncomesTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("monthlyIncomesTaxes", monthlyIncomesTaxes);

        invoices = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.REFUND, startDate, endDate);
        Double monthlyRefundsTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("monthlyRefundsTaxes", monthlyRefundsTaxes);

        data.put("monthlyTotalTaxesDue", monthlyIncomesTaxes - monthlyRefundsTaxes);

        return data;
    }

    private static HashMap<String, Object> getDataForCurrentTrimester(String currentUserId){
        HashMap<String, Object> data = new HashMap<>();

        LocalDateTime startDate = getFirstDateOfCurrentTrimester();
        LocalDateTime endDate = Utils.getCurrentDate();

        List<Invoice> invoices = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.INCOME, startDate, endDate);
        Double trimesterIncomeTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("trimesterIncomesTaxes", trimesterIncomeTaxes);

        invoices = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.REFUND, startDate, endDate);
        Double trimesterRefundTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("trimesterRefundsTaxes", trimesterRefundTaxes);

        data.put("trimesterTotalTaxesDue", trimesterIncomeTaxes - trimesterRefundTaxes);

        return data;
    }

    private static HashMap<String, Object> getDataForCurrentSemester(String currentUserId){
        HashMap<String, Object> data = new HashMap<>();

        LocalDateTime startDate = getFirstDateOfCurrentSemester();
        LocalDateTime endDate = Utils.getCurrentDate();

        List<Invoice> invoices = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.INCOME, startDate, endDate);
        Double semesterIncomesTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("semesterIncomesTaxes", semesterIncomesTaxes);

        invoices = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.REFUND, startDate, endDate);
        Double semesterRefundsTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("semesterRefundsTaxes", semesterRefundsTaxes);

        data.put("semesterTotalTaxesDue", semesterIncomesTaxes - semesterRefundsTaxes);

        return data;
    }

    private static HashMap<String, Object> getDataForCurrentAnnual(String currentUserId){
        HashMap<String, Object> data = new HashMap<>();

        LocalDateTime startDate = Utils.getDateOfSpecificMonth(1); // starting in January
        LocalDateTime endDate = Utils.getCurrentDate();

        List<Invoice> invoices = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.INCOME, startDate, endDate);
        Double annualIncomesTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("annualIncomesTaxes", annualIncomesTaxes);

        invoices = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.REFUND, startDate, endDate);
        Double annualRefundsTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("annualRefundsTaxes", annualRefundsTaxes);

        data.put("annualTotalTaxesDue", annualIncomesTaxes - annualRefundsTaxes);

        return data;
    }

    /*
    *
    * AJAX REQUESTS
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

        List<Invoice> invoices = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.INCOME, startDate, endDate);
        Double periodIncomesTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("incomes", periodIncomesTaxes);

        invoices = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.REFUND, startDate, endDate);
        Double periodRefundsTaxes = invoiceService.getSumTotalTaxes(invoices);
        data.put("refunds", periodRefundsTaxes);

        double totalTaxesDue = periodIncomesTaxes - periodRefundsTaxes;

        data.put("totalTaxesDue", totalTaxesDue < 0 ? 0 : totalTaxesDue);

        return new JSONObject(data);
    }

    /*
     *
     * Useful methods
     *
     * */
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

    private static LocalDateTime getFirstDateOfCurrentSemester(){
        int currentMonthNumber = Utils.getCurrentMonth();
        if (currentMonthNumber >= 1 && currentMonthNumber <= 6){
            return Utils.getDateOfSpecificMonth(1); // starting in January
        }else{
            return Utils.getDateOfSpecificMonth(7); // starting in July
        }
    }

    private static List<Invoice> getInvoicesListPerPeriodAndType(String currentUserId, InvoiceType invoiceType, LocalDateTime startDate, LocalDateTime endDate){
        return invoiceService.getInvoicesPerPeriodAndType(currentUserId, invoiceType, startDate , endDate);
    }

}
