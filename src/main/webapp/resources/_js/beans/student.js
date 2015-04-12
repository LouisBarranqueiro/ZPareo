/**
 * Creates a student
 */
var createStudent = function() {
    $('#create-student').submit(function(event) {
        event.preventDefault();
        var lastName     = $('#create-student input[name=lastName]').val();
        var firstName    = $('#create-student input[name=firstName]').val();
        var emailAddress = $('#create-student input[name=emailAddress]').val();
        var group        = $('#create-student select[name=group]').val();
        var modalWindow  = $('#modal');

        $.ajax(
            {
                type:    'POST',
                url:     baseURL + '/zpareo/ai/etudiant/creation',
                data:    {
                    lastName:     lastName,
                    firstName:    firstName,
                    emailAddress: emailAddress,
                    group:        group
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
 * Edits a student
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

        $.ajax(
            {
                type:    'POST',
                url:     baseURL + '/zpareo/ai/etudiant/edition',
                data:    {
                    id:           id,
                    lastName:     lastName,
                    firstName:    firstName,
                    emailAddress: emailAddress,
                    group:        group
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
 * Deletes a student
 */
var deleteStudent = function() {
    $('#delete-student').submit(function(event) {
        event.preventDefault();
        var id          = $('#delete-student input[name=id]').val();
        var modalWindow = $('#modal');

        $.ajax({
            type:    'POST',
            url:     baseURL + '/zpareo/ai/etudiant/suppression',
            data:    {
                id: id
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
 * Resets a user password
 */
var resetPassStudent = function() {
    $('#reinit-pass-student').submit(function(event) {
        event.preventDefault();
        var id = $('#reinit-pass-student input[name=id]').val();

        $.ajax(
            {
                type:    'POST',
                url:     baseURL + '/zpareo/ai/etudiant/reinit-mot-de-passe',
                data:    {
                    id: id
                },
                error:   function() {
                    alert("error !");
                },
                success: function(data) {
                    removeModalWindow();
                }
            });
    });
};
