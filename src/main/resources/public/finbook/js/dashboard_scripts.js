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
    * FORMAT AMOUNTS INTO CURRENCIES
    *
    * */
    format_amounts();

    /*
    *
    *
    * TABLES WITH DATATABLES
    *
    *
    * */
    $('#datepicker').on('change', function() {
        $.ajax({
            url: "/admin/reporting/ajax-datepicker",
            method: "POST",
            data: { datepicker_value : this.value },
            dataType:"JSON",
            success: function(data){
                $('#incomes').text(data.incomes);
                $('#refunds').text(data.refunds);
                $('#totalTaxesDue').text(data.totalTaxesDue);
                format_amounts();
                drawBarChart(data.barChart);
                // alert(data.barChart["type"]);
            },
            error: function () {
                Toast.fire({
                    icon: 'error',
                    title: 'Something was wrong!'
                })
            }
        });
    });


    /*
    *
    * TABLES WITH DATATABLES
    *
    * */
    $('#datatables_list_with_agination').DataTable({
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
    * CHARTS
    *
    * */
    /*let pageBarChart = $('#barChart');
    if (pageBarChart.length){
        let areaChartData = {
            labels  : ['January'],
            datasets: [
                {
                    label               : 'Incomes',
                    backgroundColor     : '#5cb85c',
                    data                : [800]
                },
                {
                    label               : 'Refunds',
                    backgroundColor     : '#d9534f',
                    data                : [1000]
                },
                {
                    label               : 'Total due',
                    backgroundColor     : '#f0ad4e',
                    data                : [356]
                }
            ]
        };

        //-------------
        //- BAR CHART -
        //-------------
        let barChartCanvas = pageBarChart.get(0).getContext('2d');
        let barChartData = jQuery.extend(true, {}, areaChartData);

        let barChartOptions = {
            responsive              : true,
            maintainAspectRatio     : false,
            datasetFill             : false
        };

        new Chart(barChartCanvas, {
            type: 'bar',
            data: barChartData,
            options: barChartOptions
        })
    }*/

    let pagePieChart = $('#pieChart');
    if (pagePieChart.length){
        let donutData        = {
            labels: [
                'Incomes',
                'Refunds',
            ],
            datasets: [
                {
                    data: [700,500],
                    backgroundColor : ['#5cb85c', '#d9534f'],
                }
            ]
        };

        //-------------
        //- PIE CHART -
        //-------------
        // Get context with jQuery - using jQuery's .get() method.
        let pieChartCanvas = pagePieChart.get(0).getContext('2d');
        let pieData        = donutData;
        let pieOptions     = {
            maintainAspectRatio : false,
            responsive : true,
        };
        //Create pie or douhnut chart
        // You can switch between pie and douhnut using the method below.
        let pieChart = new Chart(pieChartCanvas, {
            type: 'pie',
            data: pieData,
            options: pieOptions
        })
    }

});
