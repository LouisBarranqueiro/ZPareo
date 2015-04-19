/**
 * Creates a subject
 */
var createSubject = function() {
    $('#create-subject').submit(function(event) {
        event.preventDefault();
        var name        = $('#create-subject input[name=name]').val();
        var modalWindow = $('#modal');
        var url         = $(this).attr('action');

        $.ajax(
            {
                type:    'POST',
                url:     baseURL + url,
                data:    {
                    name: name
                },
                error:   function() {
                    alert('error !');
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
 * Edits a subject
 */
var editSubject = function() {
    $('#edit-subject').submit(function(event) {
        event.preventDefault();
        var id          = $('#edit-subject input[name=id]').val();
        var name        = $('#edit-subject input[name=name]').val();
        var modalWindow = $('#modal');
        var url         = $(this).attr('action');

        $.ajax(
            {
                type:    'POST',
                url:     baseURL + url,
                data:    {
                    id:   id,
                    name: name
                },
                error:   function() {
                    alert("error !");
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
 * Deletes a subject
 */
var deleteSubject = function() {
    $('#delete-subject').submit(function(event) {
        event.preventDefault();
        var id          = $('#delete-subject input[name=id]').val();
        var modalWindow = $('#modal');
        var url         = $(this).attr('action');

        $.ajax(
            {
                type:    'POST',
                url:     baseURL + url,
                data:    {
                    id: id
                },
                error:   function() {
                    alert("error !");
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
