/**
 * Centers a modal window
 * @param {Number} modalWidth
 */
var centerModalWindow = function(modalWidth) {
    var modalWindow   = $('#modal');
    var modalMargTop  = null;
    var modalMargLeft = null;

    modalWindow.css({
        'width': modalWidth
    });

    modalMargTop  = modalWindow.height() / 2;
    modalMargLeft = modalWindow.width() / 2;

    modalWindow.css({
        'position':    'fixed',
        'top':         '50%',
        'left':        '50%',
        'margin-top':  -modalMargTop,
        'margin-left': -modalMargLeft
    });
};

/**
 * Displays a mask
 */
var displayMask = function() {
    $('body').append('<div id="mask"></div>');
    $('#mask')
        .css({'background': 'rgba(0, 0, 0, 0.7)'})
        .fadeIn();
};

/**
 * Displays the modal window
 */
var displayModalWindow = function() {
    $('#modal').show();
};

/**
 * Removes the mask
 */
var removeMask = function() {
    $('#mask').remove();
};

/**
 * hides the modal window and remove the mask
 */
var hideModalWindow = function() {
    $('#modal').hide();
    removeMask();
};

/**
 * Removes the modal window
 */
var removeModalWindow = function() {
    hideModalWindow();
    $('#modal').html('');
};

/**
 * Initializes the modal window
 *
 * @param modalWidth
 */
var initModalWindow = function(modalWidth) {
    centerModalWindow(modalWidth);
    removeMask();
    displayMask();
    displayModalWindow();

    // Call back all libraries to affect components
    // in the modal window (select2 nand datepicker)
    initLibraries();
};

/**
 * Displays response into modal window
 *
 * @param {string} url
 * @param {Number} modalWidth
 */
var displayRespModal = function(url, modalWidth) {
    var modalWindow = $('#modal');

    $.ajax({
        type:    'GET',
        url:     baseURL + url,
        success: function(data) {
            modalWindow.html(data);
            initModalWindow(modalWidth);
        }
    })
};