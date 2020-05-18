function highlightCurrentPageInNav(){
    // Split route from admin e.g. "/admin/some/thing"
    let routeFromAdmin = location.pathname.split("/admin/");
    let highlightRoute = routeFromAdmin[1];

    // Split the route after admin e.g. "some/thing"
    let routeWithoutAdmin = routeFromAdmin[1].split("/");
    if (routeWithoutAdmin.length > 1){
        for (let i = 1; i <= routeWithoutAdmin.length; i++) {
            highlightRoute.concat("/").concat(routeWithoutAdmin[i]);
        }
    }

    // Add active class to the properly link
    $('#sidebar .nav-sidebar a[href="/admin/' + highlightRoute + '"]').addClass('active');
}

function format_amounts() {
    let amounts = $('.currency');
    if (amounts.length){
        amounts.each(function() {
            let monetary_value = $(this).text();
            let i = new Intl.NumberFormat('de-DE', {
                style: 'currency',
                currency: 'EUR'
            }).format(monetary_value);
            $(this).text(i);
        });
    }
}

function cleanChart(chartToClean){
    if(chartToClean != null){
        chartToClean.destroy();
    }
}

function ajaxChangeReportPeriod(valueOfSelect){
    $.ajax({
        url: "/admin/reporting/ajax-datepicker",
        method: "POST",
        data: { datepicker_value : valueOfSelect },
        dataType:"JSON",
        success: function(data){
            $('#incomes').text(data.incomes);
            $('#refunds').text(data.refunds);
            $('#totalTaxesDue').text(data.totalTaxesDue);
            drawBarChart(data.barChart);
            drawPieChart(data.pieChart);
            fillOutTableBodyInvoiceList(data.invoicesList);
            format_amounts();
        },
        error: function () {
            Toast.fire({
                icon: 'error',
                title: 'Something was wrong!'
            })
        }
    });
}

let myBarChart = null;
function drawBarChart(barChartObject){
    let canvasBarChart = $('#canvasBarChart');
    if (canvasBarChart.length){
        let barChartOptions = {
            responsive              : true,
            maintainAspectRatio     : false,
            datasetFill             : false
        };

        cleanChart(myBarChart);

        let barChart = canvasBarChart.get(0).getContext('2d');

        myBarChart = new Chart(barChart, {
            type: barChartObject["type"],
            data: barChartObject["data"],
            options: barChartOptions
        });
    }
}

let myPieChart = null;
function drawPieChart(pieChartObject){
    let canvasPieChart = $('#canvasPieChart');
    if (canvasPieChart.length){
        let pieChartOptions = {
            maintainAspectRatio : false,
            responsive : true,
        };

        cleanChart(myPieChart);

        let pieChart = canvasPieChart.get(0).getContext('2d');

        myPieChart = new Chart(pieChart, {
            type: pieChartObject["type"],
            data: pieChartObject["data"],
            options: pieChartOptions
        });
    }
}

function fillOutTableBodyInvoiceList(invoicesList){
    let tableToFill = $('#datatables_list_with_pagination').DataTable();
    tableToFill.clear();

    $.each(invoicesList, function (i, invoice) {
        tableToFill.row.add( [
            invoice.invoiceDate,
            invoice.issuerFullName,
            invoice.receiverFullName,
            invoice.subtotal,
            invoice.totalTaxes,
            invoice.totalDue
        ] );
    });

    tableToFill.draw();
}

$(function() {
    const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000
    });

    /*
    *
    * HIGHLIGHT NAV WITH CURRENT PAGE
    *
    * */
    highlightCurrentPageInNav();

    /*
    *
    * TABLES WITH DATATABLES
    *
    * */
    let datepicker = $('#datepicker');
    if (datepicker.length){
        ajaxChangeReportPeriod("monthly")
    }
    datepicker.on('change', function() {
        ajaxChangeReportPeriod(this.value)
    });


    /*
    *
    * TABLES WITH DATATABLES
    *
    * */
    $('#datatables_list_with_pagination').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "responsive": true,
    });



    /*
    *
    * FORMAT AMOUNTS INTO CURRENCIES
    *
    * */
    format_amounts();

});
