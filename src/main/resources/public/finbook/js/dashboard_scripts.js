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
    let pageBarChart = $('#barChart');
    if (pageBarChart.length){
        let areaChartData = {
            labels  : ['January'],
            datasets: [
                {
                    label               : 'Digital Goods',
                    backgroundColor     : 'rgba(60,141,188,0.9)',
                    borderColor         : 'rgba(60,141,188,0.8)',
                    pointRadius          : false,
                    pointColor          : '#3b8bba',
                    pointStrokeColor    : 'rgba(60,141,188,1)',
                    pointHighlightFill  : '#fff',
                    pointHighlightStroke: 'rgba(60,141,188,1)',
                    data                : [200]
                },
                {
                    label               : 'Electronics',
                    backgroundColor     : 'rgba(210, 214, 222, 1)',
                    borderColor         : 'rgba(210, 214, 222, 1)',
                    pointRadius         : false,
                    pointColor          : 'rgba(210, 214, 222, 1)',
                    pointStrokeColor    : '#c1c7d1',
                    pointHighlightFill  : '#fff',
                    pointHighlightStroke: 'rgba(220,220,220,1)',
                    data                : [1000]
                },
                {
                    label               : 'Digital Goods',
                    backgroundColor     : 'rgba(60,141,188,0.9)',
                    borderColor         : 'rgba(60,141,188,0.8)',
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
        let temp0 = areaChartData.datasets[0];
        barChartData.datasets[0] = areaChartData.datasets[1];
        barChartData.datasets[1] = temp0;

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
