/**
 * Create a group
 */
var createGroup = function() {
    $('#create-group').submit(function(event) {
        event.preventDefault();
        var name        = $('#create-group input[name=name]').val();
        var modalWindow = $('#modal');
        var url         = $(this).attr('action');

        $.ajax({
            type:    'post',
            url:     baseURL + url,
            data:    {
                name: name
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
 * Edit a group
 */
var editGroup = function() {
    $('#edit-group').submit(function(event) {
        event.preventDefault();
        var id          = $('#edit-group input[name=id]').val();
        var name        = $('#edit-group input[name=name]').val();
        var modalWindow = $('#modal');
        var url         = $(this).attr('action');

        alert(baseURL + url);
        $.ajax({
            type:    'post',
            url:     baseURL + url,
            data:    {
                id:   id,
                name: name
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
 * Delete a group
 */
var deleteGroup = function() {
    $('#delete-group').submit(function(event) {
        event.preventDefault();
        var id          = $('#delete-group input[name=id]').val();
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
