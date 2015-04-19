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
    if ($('.select2').length) {
        $('.select2').select2();
    }
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
var initLibraries = function() {
    // Initialize tablesorter library
    initTableSorter();
    // Initialize select2 library
    initSelect2();
    // Initialize datepicker library
    initDatepicker();
};

/**
 * Initialize javascript libraries
 */
+function($) {
    $(document).ready(function() {
        initLibraries();
    })
}(jQuery);

