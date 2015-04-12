+function($) {
    /**
     * Animates sortable table column
     */
    var tableSortable = function() {
        $('table th.sortable').click(function() {
            if ($(this).hasClass('asc')) {
                $(this)
                    .removeClass('asc')
                    .addClass('desc');
            }
            else {
                $('th.sortable')
                    .removeClass('asc')
                    .removeClass('desc');
                $(this).addClass('asc');
            }
        });
    };

    $(document).ready(function() {
        tableSortable();
    })
}(jQuery);

