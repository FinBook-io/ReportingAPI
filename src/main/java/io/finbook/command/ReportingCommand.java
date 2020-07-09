package io.finbook.command;

import io.finbook.chart.BarChart;
import io.finbook.chart.PieChart;
import io.finbook.pdf.IVAReport;
import io.finbook.pdf.VATReturnsCanary420;
import io.finbook.pdf.VATReturnsCanary425;
import io.finbook.mail.Mail;
import io.finbook.model.Invoice;
import io.finbook.model.InvoiceType;
import io.finbook.service.InvoiceService;
import io.finbook.util.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class ReportingCommand {

    private static InvoiceService invoiceService = new InvoiceService();

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
                startDate = getFirstDateOfCustomTrimester(Utils.getCurrentMonth());
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

        List<Invoice> invoicesEgress = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.EGRESS, startDate, endDate);
        Double periodEgressTaxes = invoiceService.getSumTotalTaxes(invoicesEgress);
        data.put("egress", periodEgressTaxes);

        Double totalTaxesDue = periodIncomesTaxes - periodEgressTaxes;

        data.put("totalTaxesDue", totalTaxesDue);

        BarChart barChart = new BarChart();
        barChart.getData().setLabels(getMonthsNamesForChart(startDate, amountMonths));

        barChart.getData().addDataset("Incomes", "#5cb85c", getIncomesPerMonth(currentUserId, startDate, amountMonths));
        barChart.getData().addDataset("Egress", "#d9534f", getEgressPerMonth(currentUserId, startDate, amountMonths));
        barChart.getData().addDataset("Total taxes due", "#f0ad4e", getTotalTaxesDuePerMonth(currentUserId, startDate, amountMonths));

        data.put("barChart", barChart.toJSON());

        PieChart pieChart = new PieChart();

        JSONArray backgrounds = new JSONArray();
        backgrounds.put("#5cb85c");
        backgrounds.put("#d9534f");

        pieChart.getData().addOneLabel("Incomes");
        pieChart.getData().addOneLabel("Egress");

        JSONArray pieData = new JSONArray();
        pieData.put(periodIncomesTaxes);
        pieData.put(periodEgressTaxes);
        pieChart.getData().addPieDataset(backgrounds, pieData);

        data.put("pieChart", pieChart.toJSON());

        List<Invoice> invoicesList = new ArrayList<>();
        invoicesList.addAll(invoicesIncomes);
        invoicesList.addAll(invoicesEgress);
        invoicesList.sort(Comparator.comparing(Invoice::getInvoiceDate).reversed());

        data.put("invoicesList", invoicesList);

        return new JSONObject(data);
    }

    public static JSONObject sendReport(String currentUserId, String period, String email){
        Map<String, Object> content = new HashMap<>();
        LocalDateTime startDate;
        LocalDateTime endDate = Utils.getCurrentDate();

        switch (period){
            case "monthly":
                startDate = Utils.getFirstDayCurrentMonth();
                break;
            case "trimester":
                startDate = getFirstDateOfCustomTrimester(Utils.getCurrentMonth());
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
        String filename = currentUserId + "_" + endDate.toString().replace(":", "");

        List<Invoice> invoicesIncomes = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.INCOME, startDate, endDate);
        Double periodIncomesTaxes = invoiceService.getSumTotalTaxes(invoicesIncomes);

        List<Invoice> invoicesEgress = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.EGRESS, startDate, endDate);
        Double periodEgressTaxes = invoiceService.getSumTotalTaxes(invoicesEgress);

        double totalTaxesDue = periodIncomesTaxes - periodEgressTaxes;

        List<Invoice> invoicesList = new ArrayList<>();
        invoicesList.addAll(invoicesIncomes);
        invoicesList.addAll(invoicesEgress);
        invoicesList.sort(Comparator.comparing(Invoice::getInvoiceDate).reversed());

        content.put("filename", filename);
        content.put("currentUserId", currentUserId);
        content.put("period", startDate.toLocalDate().toString() + " | " + endDate.toLocalDate().toString());
        content.put("incomesTaxes", periodIncomesTaxes.toString());
        content.put("egressTaxes", periodEgressTaxes.toString());
        content.put("totalTaxes", Double.toString(totalTaxesDue));
        content.put("invoicesList", invoicesList);

        IVAReport IVAReport = new IVAReport();
        try {
            IVAReport.create(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mail mail = new Mail();
        mail.sendMailAttachFile(email, filename.concat(".pdf"));

        return new JSONObject();
    }

    public static JSONObject sendVATReturnsReport(String currentUserId, String period, String whichPeriod, String email){
        Map<String, Object> content = new HashMap<>();
        LocalDateTime startDate;
        LocalDateTime endDate = Utils.getCurrentDate();

        String model;
        switch (period){
            case "trimester":
                int statingMonth;
                switch (whichPeriod) {
                    case "1T":
                        statingMonth = 1;
                        break;
                    case "2T":
                        statingMonth = 4;
                        break;
                    case "3T":
                        statingMonth = 7;
                        break;
                    case "4T":
                        statingMonth = 10;
                        break;
                    default:
                        return null;
                }
                startDate = getFirstDateOfCustomTrimester(statingMonth);
                model = "420";
                break;
            case "annual":
                startDate = Utils.getDateOfSpecificMonth(1); // starting in January
                model = "425";
                break;
            default:
                return null;
        }
        String filename = currentUserId + "_" + endDate.toString().replace(":", "");

        List<Invoice> invoicesIncomes = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.INCOME, startDate, endDate);
        Double incomesWithoutTaxes = invoiceService.getSumTotalWithoutTaxes(invoicesIncomes);
        Double incomesTaxes = invoiceService.getSumTotalTaxes(invoicesIncomes);

        List<Invoice> invoicesEgress = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.EGRESS, startDate, endDate);
        Double egressWithoutTaxes = invoiceService.getSumTotalWithoutTaxes(invoicesEgress);
        Double egressTaxes = invoiceService.getSumTotalTaxes(invoicesEgress);

        double totalTaxesDue = incomesTaxes - egressTaxes;

        content.put("filename", filename);
        content.put("currentUserId", currentUserId);
        content.put("year", Utils.getCurrentYear());
        content.put("period", startDate.toLocalDate().toString() + " | " + endDate.toLocalDate().toString());
        content.put("signDate", Utils.getCurrentDate());
        content.put("incomesWithoutTaxes", incomesWithoutTaxes);
        content.put("taxRate", "6.50");
        content.put("incomesTaxes", incomesTaxes);
        content.put("egressWithoutTaxes", egressWithoutTaxes);
        content.put("egressTaxes", egressTaxes);
        content.put("totalTaxesDue", totalTaxesDue);

        try {
            if (model.equals("420")){
                content.put("whichPeriod", whichPeriod);
                VATReturnsCanary420 vatReturnsCanary420 = new VATReturnsCanary420();
                vatReturnsCanary420.create(content);
            }else{
                VATReturnsCanary425 vatReturnsCanary425 = new VATReturnsCanary425();
                vatReturnsCanary425.create(content);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Mail mail = new Mail();
        mail.sendMailAttachFile(email, filename.concat(".pdf"));

        return new JSONObject();
    }

    /*
     *
     * Useful methods
     *
     * */
    private static LocalDateTime getFirstDateOfCustomTrimester(int currentMonthNumber){
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

    private static JSONArray getEgressPerMonth(String currentUserId, LocalDateTime startDate, int amountMonths){
        JSONArray data = new JSONArray();

        for (int i = 0; i < amountMonths; i++) {
            LocalDateTime auxStartDate = startDate.plusMonths(i);
            LocalDateTime endDate = auxStartDate.with(TemporalAdjusters.lastDayOfMonth());
            List<Invoice> invoices = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.EGRESS, auxStartDate, endDate);

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

            invoices = getInvoicesListPerPeriodAndType(currentUserId, InvoiceType.EGRESS, auxStartDate, endDate);
            Double periodEgressTaxes = invoiceService.getSumTotalTaxes(invoices);

            data.put(Utils.formatDoubleTwoDecimals(periodIncomesTaxes - periodEgressTaxes));
        }

        return data;
    }


}
