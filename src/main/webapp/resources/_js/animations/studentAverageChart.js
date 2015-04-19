+function($) {
    /**
     * Initializes the averages's student chart
     */
    var initAverageStudentChart = function() {
        var subjects = new Array();
        var averages = new Array();
        var average  = null;

        $('.averages').each(function() {
            average = $(this).data('average');

            if (String(average).match(',')) {
                average = String(average).replace(',', '.');
            }

            averages.push(parseFloat(average));
        });

        $('.subjects').each(function() {
            subjects.push($(this).data('subject'));
        });

        $('#averages-student-chart').highcharts(
            {
                chart:    {
                    height: 200,
                },
                title:    {
                    text: '',
                    x:    -20 //center
                },
                subtitle: {
                    text: '',
                    x:    -20
                },
                xAxis:    {
                    categories: subjects,
                },
                yAxis:    {
                    title:         {
                        text: 'Moyenne'
                    },
                    min:           0,
                    max:           20,
                    gridLineColor: '#ececec',
                    plotLines:     [
                        {
                            value: 0,
                            width: 0.5,
                            color: '#ff6b6b'
                        }]
                },
                legend:   {
                    enabled:       true,
                    layout:        'horizontal',
                    align:         'middle',
                    verticalAlign: 'top',
                    borderWidth:   0
                },
                tooltip:  {
                    pointFormat: '{series.name}: <b>{point.y}</b><br/>',
                    shared:      true,
                },
                series:   [
                    {
                        name:  'Student',
                        data:  averages,
                        color: '#ff6b6b'
                    }],
                credits:  {
                    enabled: false
                },
            });
    };

    $(document).ready(function() {
        if ($('.averages').length) {
            initAverageStudentChart();
        }
    })
}(jQuery);