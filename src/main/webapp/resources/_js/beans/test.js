/**
 * Creates a test
 */
var createTest = function() {
    $('#create-test').submit(function(event) {
        event.preventDefault();
        var coefficient = $('#create-test input[name=coefficient]').val();
        var teacher     = $('#create-test input[name=teacher]').val();
        var format      = $('#create-test select[name=format]').val();
        var title       = $('#create-test input[name=title]').val();
        var date        = $('#create-test input[name=date]').val();
        var matter      = $('#create-test select[name=matter]').val();
        var group       = $('#create-test select[name=group]').val();
        var modalWindow = $('#modal');

        $.ajax(
            {
                type:    'POST',
                url:     baseURL + '/zpareo/pi/examen/creation',
                data:    {
                    coefficient: coefficient,
                    teacher:     teacher,
                    format:      format,
                    title:       title,
                    date:        date,
                    matter:      matter,
                    group:       group
                },
                error:   function() {
                    alert('error !');
                },
                success: function(view) {
                    if (view.search('<form id=\"edit-test\"') > 0) {
                        removeModalWindow();
                        modalWindow.html(view);
                        initModalWindow(600);
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
 * Edits a test
 */
var editTest = function() {
    $('#edit-test').submit(function(event) {
        event.preventDefault();
        var students    = new Array();
        var scores      = new Array();
        var id          = $('#edit-test input[name=id]').val();
        var teacher     = $('#edit-test input[name=teacher]').val();
        var format      = $('#edit-test select[name=format]').val();
        var title       = $('#edit-test input[name=title]').val();
        var date        = $('#edit-test input[name=date]').val();
        var coefficient = $('#edit-test input[name=coefficient]').val();
        var matter      = $('#edit-test select[name=matter]').val();
        var modalWindow = $('#modal');

        $('input[name="students[]"]').each(function() {
            students.push($(this).val());
        });

        $('input[name="scores[]"]').each(function() {
            scores.push($(this).val());
        });

        students = students.join("-");
        scores   = scores.join("-");

        $.ajax(
            {
                type:    'POST',
                url:     baseURL + '/zpareo/pi/examen/edition',
                data:    {
                    id:          id,
                    teacher:     teacher,
                    format:      format,
                    title:       title,
                    date:        date,
                    coefficient: coefficient,
                    matter:      matter,
                    students:    students,
                    scores:      scores
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
                        initModalWindow(600);
                    }
                }
            });
    });
};

/**
 * Deletes a test
 */
var deleteTest = function() {
    $('#delete-test').submit(function(event) {
        event.preventDefault();
        var id          = $('#delete-test input[name=id]').val();
        var modalWindow = $('#modal');

        $.ajax({
            type:    'POST',
            url:     baseURL + '/zpareo/pi/examen/suppression',
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
                    initModalWindow(600);
                }
            }
        });
    });
};