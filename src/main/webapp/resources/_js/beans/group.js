/**
 * Creates a group
 */
var createGroup = function() {
    $('#create-group').submit(function(event) {
        event.preventDefault();
        var name        = $('#create-group input[name=name]').val();
        var modalWindow = $('#modal');

        $.ajax(
            {
                type:    'POST',
                url:     baseURL + '/zpareo/ai/groupe/creation',
                data:    {
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
 * Edits a group
 */
var editGroup = function() {
    $('#edit-group').submit(function(event) {
        event.preventDefault();
        var id          = $('#edit-group input[name=id]').val();
        var name        = $('#edit-group input[name=name]').val();
        var modalWindow = $('#modal');

        $.ajax(
            {
                type:    'POST',
                url:     baseURL + '/zpareo/ai/groupe/edition',
                data:    {
                    id:   id,
                    name: name
                },
                error:   function() {
                    alert("erreur !");
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
 * Deletes a group
 */
var deleteGroup = function() {
    $('#delete-group').submit(function(event) {
        event.preventDefault();
        var id          = $('#delete-group input[name=id]').val();
        var modalWindow = $('#modal');

        $.ajax(
            {
                type:    "POST",
                url:     "http://localhost:8080/zpareo/ai/groupe/suppression",
                data:    {
                    id: id
                },
                error:   function() {
                    alert("erreur !");
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
