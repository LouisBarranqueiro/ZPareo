/**
 * Creates a matter
 */
var createMatter = function() {
    $('#create-matter').submit(function(event) {
        event.preventDefault();
        var name        = $('#create-matter input[name=name]').val();
        var modalWindow = $('#modal');

        $.ajax(
            {
                type:    'POST',
                url:     baseURL + '/zpareo/ai/matiere/creation',
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
 * Edits a matter
 */
var editMatter = function() {
    $('#edit-matter').submit(function(event) {
        event.preventDefault();
        var id          = $('#edit-matter input[name=id]').val();
        var name        = $('#edit-matter input[name=name]').val();
        var modalWindow = $('#modal');

        $.ajax(
            {
                type:    'POST',
                url:     baseURL + '/zpareo/ai/matiere/edition',
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
 * Deletes a matter
 */
var deleteMatter = function() {
    $('#delete-matter').submit(function(event) {
        event.preventDefault();
        var id          = $('#delete-matter input[name=id]').val();
        var modalWindow = $('#modal');

        $.ajax(
            {
                type:    'POST',
                url:     baseURL + '/zpareo/ai/matiere/suppression',
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
