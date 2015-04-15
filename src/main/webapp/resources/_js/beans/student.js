/**
 * Create a student
 */
var createStudent = function() {
    $('#create-student').submit(function(event) {
        event.preventDefault();
        var lastName     = $('#create-student input[name=lastName]').val();
        var firstName    = $('#create-student input[name=firstName]').val();
        var emailAddress = $('#create-student input[name=emailAddress]').val();
        var group        = $('#create-student select[name=group]').val();
        var modalWindow  = $('#modal');
        var url          = $(this).attr('action');

        $.ajax({
            type:    'post',
            url:     baseURL + url,
            data:    {
                lastName:     lastName,
                firstName:    firstName,
                emailAddress: emailAddress,
                group:        group
            },
            success: function(view) {
                if (view.search('<div id=\"main-wrap\"') > 0) {
                    removeModalWindow();
                    $('#main-wrap').replaceWith(sliceMainView(view));
                }
                else {
                    modalWindow.html(view);
                    initModalWindow(300);
                }
            }
        });
    });
};

/**
 * Edit a student
 */
var editStudent = function() {
    $('#edit-student').submit(function(event) {
        event.preventDefault();
        var id           = $('#edit-student input[name=id]').val();
        var lastName     = $('#edit-student input[name=lastName]').val();
        var firstName    = $('#edit-student input[name=firstName]').val();
        var emailAddress = $('#edit-student input[name=emailAddress]').val();
        var group        = $('#edit-student select[name=group]').val();
        var modalWindow  = $('#modal');
        var url          = $(this).attr('action');

        $.ajax({
            type:    'post',
            url:     baseURL + url,
            data:    {
                id:           id,
                lastName:     lastName,
                firstName:    firstName,
                emailAddress: emailAddress,
                group:        group
            },
            success: function(view) {
                if (view.search('<div id=\"main-wrap\"') > 0) {
                    removeModalWindow();
                    $('#main-wrap').replaceWith(sliceMainView(view));
                }
                else {
                    modalWindow.html(view);
                    initModalWindow(300);
                }
            }
        });
    });
};

/**
 * Delete a student
 */
var deleteStudent = function() {
    $('#delete-student').submit(function(event) {
        event.preventDefault();
        var id          = $('#delete-student input[name=id]').val();
        var modalWindow = $('#modal');
        var url         = $(this).attr('action');

        $.ajax({
            type:    'post',
            url:     baseURL + url,
            data:    {
                id: id
            },
            success: function(view) {
                if (view.search('<div id=\"main-wrap\"') > 0) {
                    removeModalWindow();
                    $('#main-wrap').replaceWith(sliceMainView(view));
                }
                else {
                    modalWindow.html(view);
                    initModalWindow(300);
                }
            }
        });
    });
};

/**
 * Reset a user password
 */
var resetPasswordStudent = function() {
    $('#reset-pass-student').submit(function(event) {
        event.preventDefault();
        var id  = $('#reinit-pass-student input[name=id]').val();
        var url = $(this).attr('action');

        $.ajax({
            type:    'POST',
            url:     baseURL + url,
            data:    {
                id: id
            },
            success: function(data) {
                removeModalWindow();
            }
        });
    });
};
