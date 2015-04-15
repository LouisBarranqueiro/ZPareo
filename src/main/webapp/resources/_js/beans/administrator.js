/**
 * Create an administrator
 */
var createAdmin = function() {
    $('#create-administrator').submit(function(event) {
        event.preventDefault();
        var lastName     = $('#create-administrator input[name=lastName]').val();
        var firstName    = $('#create-administrator input[name=firstName]').val();
        var emailAddress = $('#create-administrator input[name=emailAddress]').val();
        var password     = $('#create-administrator input[name=password]').val();
        var confirmation = $('#create-administrator input[name=confirmation]').val();
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
                confirmation: confirmation
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
 * Edits an administrator
 */
var editAdmin = function() {
    $('#edit-administrator').submit(function(event) {
        event.preventDefault();
        var id           = $('#edit-administrator input[name=id]').val();
        var lastName     = $('#edit-administrator input[name=lastName]').val();
        var firstName    = $('#edit-administrator input[name=firstName]').val();
        var emailAddress = $('#edit-administrator input[name=emailAddress]').val();
        var password     = $('#edit-administrator input[name=password]').val();
        var confirmation = $('#edit-administrator input[name=confirmation]').val();
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
                confirmation: confirmation
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
 * Delete an administrator
 */
var deleteAdmin = function() {
    $('#delete-administrator').submit(function(event) {
        event.preventDefault();
        var id          = $('#delete-administrator input[name=id]').val();
        var modalWindow = $('#modal');
        var url         = $(this).attr('action');

        $.ajax({
            type:    'POST',
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
