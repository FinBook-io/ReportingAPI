function highlightCurrentPageInNav(){
    // Split route from /admin/
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

function drawChart(dataChart){

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
    *
    * HIGHLIGHT NAV WITH CURRENT PAGE
    *
    *
    * */
    highlightCurrentPageInNav();


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
                // drawChart(data.dataChart)
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
    *
    * TABLES WITH DATATABLES
    *
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
    *
    * CHARTS
    *
    *
    * */
    let pageBarChart = $('#chartToDraw');
    if (pageBarChart.length){
        let areaChartData = {
            labels  : ['January'],
            datasets: [
                {
                    label               : 'Incomes',
                    backgroundColor     : '#5cb85c',
                    borderColor         : '#5cb85c',
                    pointRadius          : false,
                    pointColor          : '#3b8bba',
                    pointStrokeColor    : 'rgba(60,141,188,1)',
                    pointHighlightFill  : '#fff',
                    pointHighlightStroke: 'rgba(60,141,188,1)',
                    data                : [800]
                },
                {
                    label               : 'Refunds',
                    backgroundColor     : '#d9534f',
                    borderColor         : '#d9534f',
                    pointRadius         : false,
                    pointColor          : 'rgba(210, 214, 222, 1)',
                    pointStrokeColor    : '#c1c7d1',
                    pointHighlightFill  : '#fff',
                    pointHighlightStroke: 'rgba(220,220,220,1)',
                    data                : [1000]
                },
                {
                    label               : 'Total due',
                    backgroundColor     : '#f0ad4e',
                    borderColor         : '#f0ad4e',
                    pointRadius          : false,
                    pointColor          : '#3b8bba',
                    pointStrokeColor    : 'rgba(60,141,188,1)',
                    pointHighlightFill  : '#fff',
                    pointHighlightStroke: 'rgba(60,141,188,1)',
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
    }

});
