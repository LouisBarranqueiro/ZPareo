/**
 * Initialize tableSorter library
 */
var initTableSorter = function() {
    $('table').tablesorter();
};

/**
 * Initialize select2 library
 */
var initSelect2 = function() {
    $('.select2').select2();
};

/**
 * Initialize the datapicker library
 */
var initDatepicker = function() {
    $('.datepicker').datepicker();
};

/**
 * Initialize javascript libraries
 */
+function($) {
    $(document).ready(function() {
        // Initialize tablesorter library
        initTableSorter();
        // Initialize select2 library
        initSelect2();
        // Initialize datepicker library
        initDatepicker();
    })
}(jQuery);

