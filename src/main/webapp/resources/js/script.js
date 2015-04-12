var baseURL = window.location.protocol + "//" + window.location.host;;/**
 * Initialize tableSorter library
 */
var initTableSorter = function() {
    $('table').tablesorter();
};

/**
 * Initialize select2 library
 */
var initSelect2 = function() {
    $('.select2').select2();
};

/**
 * Initialize the datapicker library
 */
var initDatepicker = function() {
    $('.datepicker').datepicker();
};

/**
 * Initialize javascript libraries
 */
+function($) {
    $(document).ready(function() {
        // Initialize tablesorter library
        initTableSorter();
        // Initialize select2 library
        initSelect2();
        // Initialize datepicker library
        initDatepicker();
    })
}(jQuery);

;/**
 * Slices a view (main wrap)
 * @param {string} view
 * @return {string}
 */
var sliceMainView = function(view) {
    return view.slice(view.search('<div id=\"main-wrap\" class=\"main\">'), view.search('<!-- End main-wrap -->'));
};;/**
 * Centers a modal window
 * @param {Number} modalWidth
 */
var centerModalWindow = function(modalWidth) {
    var modalWindow   = $('#modal');
    var modalMargTop  = null;
    var modalMargLeft = null;

    modalWindow.css({
        'width': modalWidth
    });

    modalMargTop  = modalWindow.height() / 2;
    modalMargLeft = modalWindow.width() / 2;

    modalWindow.css({
        'position':    'fixed',
        'top':         '50%',
        'left':        '50%',
        'margin-top':  -modalMargTop,
        'margin-left': -modalMargLeft
    });
};

/**
 * Displays a mask
 */
var displayMask = function() {
    $('body').append('<div id="mask"></div>');
    $('#mask')
        .css({'background': 'rgba(0, 0, 0, 0.7)'})
        .fadeIn();
};

/**
 * Displays the modal window
 */
var displayModalWindow = function() {
    $('#modal').show();
};

/**
 * Removes the mask
 */
var removeMask = function() {
    $('#mask').remove();
};

/**
 * hides the modal window and remove the mask
 */
var hideModalWindow = function() {
    $('#modal').hide();
    removeMask();
};

/**
 * Removes the modal window
 */
var removeModalWindow = function() {
    hideModalWindow();
    $('#modal').html('');
};

/**
 * Initializes the modal window
 *
 * @param modalWidth
 */
var initModalWindow = function(modalWidth) {
    centerModalWindow(modalWidth);
    removeMask();
    displayMask();
    displayModalWindow();
};

 /**
 * Displays response into modal window
 *
 * @param {string} url
 * @param {Number} modalWidth
 */
var displayRespModal = function(url, modalWidth) {
    var modalWindow = $('#modal');

    $.ajax({
        type:    'GET',
        url:     baseURL + url,
        error:   function() {
            alert('error !');
        },
        success: function(data) {
            modalWindow.html(data);
            initModalWindow(modalWidth);
        }
    }).done(function() {
        initSelect2();
    }).always(function() {
        initDatepicker();
    });
};;+function($) {
    /**
     * Animates sortable table column
     */
    var tableSortable = function() {
        $('table th.sortable').click(function() {
            if ($(this).hasClass('asc')) {
                $(this)
                    .removeClass('asc')
                    .addClass('desc');
            }
            else {
                $('th.sortable')
                    .removeClass('asc')
                    .removeClass('desc');
                $(this).addClass('asc');
            }
        });
    };

    $(document).ready(function() {
        tableSortable();
    })
}(jQuery);

;/**
 * Creates an administrator
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

        $.ajax(
            {
                type:    'POST',
                url:     baseURL + url,
                data:    {
                    lastName:     lastName,
                    firstName:    firstName,
                    emailAddress: emailAddress,
                    password:     password,
                    confirmation: confirmation
                },
                error:   function() {
                    alert('erreur !');
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

        $.ajax(
            {
                type:    'POST',
                url:     baseURL + url,
                data:    {
                    id:           id,
                    lastName:     lastName,
                    firstName:    firstName,
                    emailAddress: emailAddress,
                    password:     password,
                    confirmation: confirmation
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
 * Deletes an administrator
 */
var deleteAdmin = function() {
    $('#delete-administrator').submit(function(event) {
        event.preventDefault();
        var id          = $('#delete-administrator input[name=id]').val();
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
                    alert('erreur !');
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
;/**
 * Create a group
 */
var createGroup = function() {
    $('#create-group').submit(function(event) {
        event.preventDefault();
        var name        = $('#create-group input[name=name]').val();
        var modalWindow = $('#modal');
        var url         = $(this).attr('action');

        $.ajax({
            type:    'POST',
            url:     baseURL + url,
            data:    {
                name: name
            },
            error:   function() {
                alert("error!");
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
            type:    'POST',
            url:     baseURL + url,
            data:    {
                id:   id,
                name: name
            },
            error:   function() {
                alert("error!");
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
            type:    "POST",
            url:     baseURL + url,
            data:    {
                id: id
            },
            error:   function() {
                alert("error!");
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
;/**
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
;/**
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
;/**
 * Creates a teacher
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
        var matters      = $('#create-teacher select[name=matters]').val();
        var modalWindow  = $('#modal');

        $.ajax(
            {
                type:    'POST',
                url:     baseURL + '/zpareo/ai/professeur/creation',
                data:    {
                    lastName:     lastName,
                    firstName:    firstName,
                    emailAddress: emailAddress,
                    password:     password,
                    confirmation: confirmation,
                    groups:       groups,
                    matters:      matters
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
                        initModalWindow(600);
                        initSelect2();
                    }
                }
            });
    });
};

/**
 * Edits a teacher
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
        var matters      = $('#edit-teacher select[name=matters]').val();
        var modalWindow  = $('#modal');

        $.ajax({
            type:    "POST",
            url:     "http://localhost:8080/zpareo/ai/professeur/edition",
            data:    {
                id:           id,
                lastName:     lastName,
                firstName:    firstName,
                emailAddress: emailAddress,
                password:     password,
                confirmation: confirmation,
                groups:       groups,
                matters:      matters
            },
            error:   function() {
                alert("erreur !");
            },
            success: function(view) {
                if (view.search('<div id=\"main-wrap\"') > 0) {
                    document.location = "http://localhost:8080/zpareo/ai/professeur";
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
 * Deletes a teacher
 */
var deleteTeacher = function() {
    $('#delete-teacher').submit(function(event) {
        event.preventDefault();
        var id          = $('#delete-teacher input[name=id]').val();
        var modalWindow = $('#modal');

        $.ajax(
            {
                type:    'POST',
                url:     baseURL + '/zpareo/ai/professeur/suppression',
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
                        initModalWindow('auto');

                    }
                }
            });
    });
};

;/**
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