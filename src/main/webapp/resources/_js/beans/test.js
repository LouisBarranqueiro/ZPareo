/**
 * Create a test
 */
var createTest = function() {
    $('#create-test').submit(function(event) {
        event.preventDefault();
        var coefficient = $('#create-test input[name=coefficient]').val();
        var teacher     = $('#create-test input[name=teacher]').val();
        var format      = $('#create-test select[name=format]').val();
        var title       = $('#create-test input[name=title]').val();
        var date        = $('#create-test input[name=date]').val();
        var subject     = $('#create-test select[name=subject]').val();
        var group       = $('#create-test select[name=group]').val();
        var modalWindow = $('#modal');
        var url         = $(this).attr('action');

        $.ajax({
            type:    'post',
            url:     baseURL + url,
            data:    {
                coefficient: coefficient,
                teacher:     teacher,
                format:      format,
                title:       title,
                date:        date,
                subject:     subject,
                group:       group
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
 * Edit a test
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
        var subject     = $('#edit-test select[name=subject]').val();
        var modalWindow = $('#modal');
        var url         = $(this).attr('action');

        $('input[name="students[]"]').each(function() {
            students.push($(this).val());
        });

        $('input[name="scores[]"]').each(function() {
            scores.push($(this).val());
        });

        students = students.join("-");
        scores   = scores.join("-");

        $.ajax({
            type:    'post',
            url:     baseURL + url,
            data:    {
                id:          id,
                teacher:     teacher,
                format:      format,
                title:       title,
                date:        date,
                coefficient: coefficient,
                subject:     subject,
                students:    students,
                scores:      scores
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
 * Delete a test
 */
var deleteTest = function() {
    $('#delete-test').submit(function(event) {
        event.preventDefault();
        var id          = $('#delete-test input[name=id]').val();
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
                    initModalWindow(600);
                }
            }
        });
    });
};