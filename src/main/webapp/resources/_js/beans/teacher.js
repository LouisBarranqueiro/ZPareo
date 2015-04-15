/**
 * Create a teacher
 */
var createTeacher = function() {
    $('#create-teacher').submit(function(event) {
        event.preventDefault();
        var lastName     = $('#create-teacher input[name=lastName]').val();
        var firstName    = $('#create-teacher input[name=firstName]').val();
        var emailAddress = $('#create-teacher input[name=emailAddress]').val();
        var password     = $('#create-teacher input[name=password]').val();
        var confirmation = $('#create-teacher input[name=confirmation]').val();
        var groups       = $('#create-teacher select[name=groups]').val();
        var subjects     = $('#create-teacher select[name=subjects]').val();
        var modalWindow  = $('#modal');
        var url          = $(this).attr('action');

        $.ajax({
            type:    'post',
            url:     baseURL + url,
            data:    {
                lastName:     lastName,
                firstName:    firstName,
                emailAddress: emailAddress,
                password:     password,
                confirmation: confirmation,
                groups:       groups,
                subjects:     subjects
            },
            success: function(view) {
                if (view.search('<div id=\"main-wrap\"') > 0) {
                    removeModalWindow();
                    $('#main-wrap').replaceWith(sliceMainView(view));
                }
                else {
                    modalWindow.html(view);
                    initModalWindow(600);
                    initSelect2();
                }
            }
        });
    });
};

/**
 * Edit a teacher
 */
var editTeacher = function() {
    $('#edit-teacher').submit(function(event) {
        event.preventDefault();
        var id           = $('#edit-teacher input[name=id]').val();
        var lastName     = $('#edit-teacher input[name=lastName]').val();
        var firstName    = $('#edit-teacher input[name=firstName]').val();
        var emailAddress = $('#edit-teacher input[name=emailAddress]').val();
        var password     = $('#edit-teacher input[name=password]').val();
        var confirmation = $('#edit-teacher input[name=confirmation]').val();
        var groups       = $('#edit-teacher select[name=groups]').val();
        var subjects     = $('#edit-teacher select[name=subjects]').val();
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
                password:     password,
                confirmation: confirmation,
                groups:       groups,
                subjects:     subjects
            },
            success: function(view) {
                if (view.search('<div id=\"main-wrap\"') > 0) {
                    removeModalWindow();
                    $('#main-wrap').replaceWith(sliceMainView(view));
                }
                else {
                    modalWindow.html(view);
                    initModalWindow(600);
                }
            }
        });
    });
};

/**
 * Delete a teacher
 */
var deleteTeacher = function() {
    $('#delete-teacher').submit(function(event) {
        event.preventDefault();
        var id          = $('#delete-teacher input[name=id]').val();
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
                    initModalWindow('auto');
                }
            }
        });
    });
};