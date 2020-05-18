package io.finbook.command;

import io.finbook.chart.BarChart;
import io.finbook.chart.PieChart;
import io.finbook.responses.MyResponse;
import io.finbook.responses.StandardResponse;
import io.finbook.model.Invoice;
import io.finbook.model.InvoiceType;
import io.finbook.service.InvoiceService;
import io.finbook.sparkcontroller.ResponseCreator;
import io.finbook.util.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
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

        data.put("monthlyTotalTaxesDue", Utils.formatDoubleTwoDecimals(monthlyIncomesTaxes - monthlyRefundsTaxes));

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

        data.put("trimesterTotalTaxesDue", Utils.formatDoubleTwoDecimals(trimesterIncomeTaxes - trimesterRefundTaxes));

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

        data.put("semesterTotalTaxesDue", Utils.formatDoubleTwoDecimals(semesterIncomesTaxes - semesterRefundsTaxes));

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

        data.put("annualTotalTaxesDue", Utils.formatDoubleTwoDecimals(annualIncomesTaxes - annualRefundsTaxes));

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
        int amountMonths;

        switch (period){
            case "monthly":
                startDate = Utils.getFirstDayCurrentMonth();
                amountMonths = 1;
                break;
            case "trimester":
                startDate = getFirstDateOfCurrentTrimester();
                amountMonths = 3;
                break;
            case "semester":
                startDate = getFirstDateOfCurrentSemester();
                amountMonths = 6;
                break;
            case "annual":
                startDate = Utils.getDateOfSpecificMonth(1); // starting in January
                amountMonths = 12;
                break;
            default:
                return null;
        }

        List<Invoice> invoicesIncomes = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.INCOME, startDate, endDate);
        Double periodIncomesTaxes = invoiceService.getSumTotalTaxes(invoicesIncomes);
        data.put("incomes", periodIncomesTaxes);

        List<Invoice> invoicesRefunds = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.REFUND, startDate, endDate);
        Double periodRefundsTaxes = invoiceService.getSumTotalTaxes(invoicesRefunds);
        data.put("refunds", periodRefundsTaxes);

        double totalTaxesDue = periodIncomesTaxes - periodRefundsTaxes;

        data.put("totalTaxesDue", totalTaxesDue);

        BarChart barChart = new BarChart();
        barChart.getData().setLabels(getMonthsNamesForChart(startDate, amountMonths));

        barChart.getData().addDataset("Incomes", "#5cb85c", getIncomesPerMonth(currentUserId, startDate, amountMonths));
        barChart.getData().addDataset("Refunds", "#d9534f", getRefundsPerMonth(currentUserId, startDate, amountMonths));
        barChart.getData().addDataset("Total taxes due", "#f0ad4e", getTotalTaxesDuePerMonth(currentUserId, startDate, amountMonths));

        data.put("barChart", barChart.toJSON());

        PieChart pieChart = new PieChart();

        JSONArray backgrounds = new JSONArray();
        backgrounds.put("#5cb85c");
        backgrounds.put("#d9534f");

        pieChart.getData().addOneLabel("Incomes");
        pieChart.getData().addOneLabel("Refunds");

        JSONArray pieData = new JSONArray();
        pieData.put(periodIncomesTaxes);
        pieData.put(periodRefundsTaxes);
        pieChart.getData().addPieDataset(backgrounds, pieData);

        data.put("pieChart", pieChart.toJSON());

        List<Invoice> invoicesList = new ArrayList<>();
        invoicesList.addAll(invoicesIncomes);
        invoicesList.addAll(invoicesRefunds);
        invoicesList.sort(Comparator.comparing(Invoice::getInvoiceDate).reversed());

        data.put("invoicesList", invoicesList);

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

    private static JSONArray getMonthsNamesForChart(LocalDateTime startDate, int amountMonths){
        JSONArray months = new JSONArray();
        for (int i = 0; i < amountMonths; i++) {
            months.put(startDate.getMonth().plus(i).toString());
        }
        return months;
    }

    private static JSONArray getIncomesPerMonth(String currentUserId, LocalDateTime startDate, int amountMonths){
        JSONArray data = new JSONArray();

        for (int i = 0; i < amountMonths; i++) {
            LocalDateTime auxStartDate = startDate.plusMonths(i);
            LocalDateTime endDate = auxStartDate.with(TemporalAdjusters.lastDayOfMonth());
            List<Invoice> invoices = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.INCOME, auxStartDate, endDate);

            data.put(invoiceService.getSumTotalTaxes(invoices));
        }

        return data;
    }
    private static JSONArray getRefundsPerMonth(String currentUserId, LocalDateTime startDate, int amountMonths){
        JSONArray data = new JSONArray();

        for (int i = 0; i < amountMonths; i++) {
            LocalDateTime auxStartDate = startDate.plusMonths(i);
            LocalDateTime endDate = auxStartDate.with(TemporalAdjusters.lastDayOfMonth());
            List<Invoice> invoices = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.REFUND, auxStartDate, endDate);

            data.put(invoiceService.getSumTotalTaxes(invoices));
        }

        return data;
    }

    private static JSONArray getTotalTaxesDuePerMonth(String currentUserId, LocalDateTime startDate, int amountMonths){
        JSONArray data = new JSONArray();

        for (int i = 0; i < amountMonths; i++) {
            LocalDateTime auxStartDate = startDate.plusMonths(i);
            LocalDateTime endDate = auxStartDate.with(TemporalAdjusters.lastDayOfMonth());

            List<Invoice> invoices = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.INCOME, auxStartDate, endDate);
            Double periodIncomesTaxes = invoiceService.getSumTotalTaxes(invoices);

            invoices = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.REFUND, auxStartDate, endDate);
            Double periodRefundsTaxes = invoiceService.getSumTotalTaxes(invoices);

            data.put(Utils.formatDoubleTwoDecimals(periodIncomesTaxes - periodRefundsTaxes));
        }

        return data;
    }


}
