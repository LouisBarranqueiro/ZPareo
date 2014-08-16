/**
 * Initializes the Table Sorter library
 */ 
var initTableSorter = function()
{
	$('table').tablesorter(); 
};

/**
 * Initializes Select2 library
 */ 
var initSelect2 = function()
{
	$('.select2').select2(); 
};

/**
 * Initializes the averages's student chart
 */
var initAverageStudentChart = function()
{
	var matters  = new Array();
	var averages = new Array();
	var average  = null;
	
	$('.averages').each(function()
	{
		average = $(this).data('average');
		
		if(String (average).match(',')) average = String (average).replace(',','.');
		
		averages.push(parseFloat(average));
	});
	
	$('.matters').each(function()
	{
		matters.push($(this).data('matter'));
	});
	
	$('#averages-student-chart').highcharts(
	{
		chart: 
		{
			height: 200,
		},
        title: 
        {
            text: '',
            x:    -20 //center
        },
        subtitle: 
        {
            text: '',
            x:    -20
        },
        xAxis: 
        {
            categories: matters,
        },
        yAxis: 
        {
            title: 
            {
                text: 'Moyenne'
            },
            min:           0,
            max:           20,
            gridLineColor: '#ececec',
            plotLines:     [
            {
                value: 0,
                width: 0.5,
                color: '#B9B9B9'
            }]
        },
        legend: 
        {
        	enabled:       true,
            layout:        'horizontal',
            align:         'middle',
            verticalAlign: 'top',
            borderWidth:   0
        },
        tooltip: 
        {
        	pointFormat: '{series.name}: <b>{point.y}</b><br/>',
        	shared:      true,
        },
        series: [
        {
            name:  'Etudiant',
            data:  averages,
            color: '#a48ad4'
        }],
        credits: 
        {
            enabled: false
        },
    });
};
 
/**
 * Initializes the datapicker library
 */ 
var initDatepicker = function()
{
	$('.datepicker').datepicker(); 
};

/**
 * Animates sortable array column
 */
var animSortableColumn = function() 
{
	$('th.sortable').click(function() 
	{
		if($(this).hasClass('asc'))
		{
			$(this).removeClass('asc');
			$(this).addClass('desc');
		}
		else
		{
			$('.sortable').removeClass('asc');
			$('.sortable').removeClass('desc');
			$(this).addClass('asc');
		}
	});
};

/**
 * Centers a modal window
 * 
 * @param modalWidth
 */
var centerModalWindow = function(modalWidth)
{
	var modalWindow   = $('#modal');
    var modalMargTop  = null;
    var modalMargLeft = null;
    
    modalWindow.css(
    {
        'width'   : modalWidth
    });
    
    modalMargTop = modalWindow.height() / 2;
    modalMargLeft = modalWindow.width() / 2;
    
    modalWindow.css(
    {
        'position':     'fixed',
        'top' :         '50%',
        'left':         '50%',
        'margin-top' :  -modalMargTop,
        'margin-left' : -modalMargLeft
    });
};

/**
 * Displays a mask
 */ 
var displayMask = function() 
{
	$('body').append('<div id="mask"></div>');
	$('#mask').css(
	{
		'background': 'rgba(0, 0, 0, 0.7)'
	}).fadeIn();
};

/**
 * Displays the modal window
 */ 
var displayModalWindow = function() 
{
	$('#modal').show();
};

/**
 * Removes the mask
 */
var removeMask = function() 
{
	$('#mask').remove();
};

/**
 * hides the modal window and remove the mask
 */ 
var hideModalWindow = function() 
{
	$('#modal').hide();
	removeMask();
};

/**
 * Removes the modal window
 */
var removeModalWindow = function() 
{
	hideModalWindow();
	$('#modal').html('');
};

/**
 * Initializes the modal window
 * 
 * @param modalWidth
 */
var initModalWindow = function(modalWidth) 
{
	centerModalWindow(modalWidth);
	removeMask();
	displayMask();
	displayModalWindow();
	initDatepicker();
	initSelect2();
};

/**
 * Slices a view (main wrap)
 * 
 * @param view
 * @return 
 */
var sliceMainView = function(view)
{
	return view.slice(view.search('<div id=\"main-wrap\" class=\"main\">'), view.search('<!-- End main-wrap -->'));
};

/**
 * Displays response into modal window
 * 
 * @param url
 * @param modalWidth
 */
var displayRespModal = function(url, modalWidth)
{
	var modalWindow = $('#modal');
	
	$.ajax(
	{ 
		type: 'GET', 
		url:  'http://localhost:8080/ZPareo/' + url,  
	    error: function() 
	    { 
	    	alert('error !'); 
	    },
	    success: function(data) 
	    {
	    	modalWindow.html(data);
	    	initModalWindow(modalWidth);
	    } 
	});
};

/**
 * Creates a matter in database
 */ 
var createMatter = function()  
{
	$('#create-matter').submit(function(event)
	{
		event.preventDefault();
		var name        = $('#create-matter input[name=name]').val();
		var modalWindow = $('#modal');
		
		$.ajax(
		{ 
		    type: 'POST', 
		    url:  'http://localhost:8080/ZPareo/ai/matiere/creation', 
		    data: 
		    {
		    	name: name
		    }, 
		    error: function() 
		    { 
		    	alert('error !'); 
		    },
		    success: function(view) 
		    { 
		    	if(view.search('<div id=\"main-wrap\"') > 0) 
		    	{
		    		removeModalWindow();
		    		$('#main-wrap').replaceWith(sliceMainView(view));
		    	}
		    	else 
		    	{
		    		modalWindow.html(view);
		    		initModalWindow(300);
		    		reloadCreateFunctions();
		    	}
		    } 
		});
	});
};

/**
 * Edits a matter in database
 */
var editMatter = function()  
{
	$('#edit-matter').submit(function(event)
	{
		event.preventDefault();
		var id           = $('#edit-matter input[name=id]').val();
		var name         = $('#edit-matter input[name=name]').val();
		var modalWindow  = $('#modal');
		
		$.ajax(
		{ 
			type: 'POST', 
		    url:  'http://localhost:8080/ZPareo/ai/matiere/edition', 
		    data: 
		    {
		    	id:   id,
		    	name: name
		    }, 
		    error: function() 
		    { 
		    	alert("error !"); 
		    },
		    success: function(view) 
		    { 
		    	if(view.search('<div id=\"main-wrap\"') > 0) 
		    	{
		    		removeModalWindow();
		    		$('#main-wrap').replaceWith(sliceMainView(view));
		    	}
		    	else 
		    	{
		    		modalWindow.html(view);
		    		initModalWindow(300);
		    		reloadCreateFunctions();
		    	}
		    } 
		});
	});
};

/**
 * Deletes a matter in database
 */
var deleteMatter = function()  
{
	$('#delete-matter').submit(function(event)
	{
		event.preventDefault();
		var id          = $('#delete-matter input[name=id]').val();
		var modalWindow = $('#modal');
		
		$.ajax(
		{ 
			type: 'POST', 
		    url:  'http://localhost:8080/ZPareo/ai/matiere/suppression', 
		    data: 
		    {
		    	id: id
		    }, 
		    error: function() 
		    { 
		    	alert("error !"); 
		    },
		    success: function(view) 
		    { 
		    	if(view.search('<div id=\"main-wrap\"') > 0) 
		    	{
		    		removeModalWindow();
		    		$('#main-wrap').replaceWith(sliceMainView(view));
		    	}
		    	else 
		    	{
		    		modalWindow.html(view);
		    		initModalWindow('auto');
		    		reloadCreateFunctions();
		    	}
		    } 
		});
	});
};

/**
 * Creates a group in database
 */ 
var createGroup = function()  
{
	$('#create-group').submit(function(event)
	{
		event.preventDefault();
		var name        = $('#create-group input[name=name]').val();
		var modalWindow = $('#modal');
		
		$.ajax(
		{ 
		    type: 'POST', 
		    url:  'http://localhost:8080/ZPareo/ai/groupe/creation', 
		    data: 
		    {
		    	name: name
		    }, 
		    error: function() 
		    { 
		    	alert("error !"); 
		    },
		    success: function(view) 
		    { 
		    	if(view.search('<div id=\"main-wrap\"') > 0) 
		    	{
		    		removeModalWindow();
		    		$('#main-wrap').replaceWith(sliceMainView(view));
		    	}
		    	else 
		    	{
		    		modalWindow.html(view);
		    		initModalWindow(300);
		    		reloadCreateFunctions();
		    	}
		    } 
		});
	});
};

/**
 * Edits a group in database
 */
var editGroup = function()  
{
	$('#edit-group').submit( function( event )
	{
		event.preventDefault();
		var id          = $('#edit-group input[name=id]').val();
		var name        = $('#edit-group input[name=name]').val();
		var modalWindow = $('#modal');
		
		$.ajax(
		{ 
			type: 'POST', 
		    url:  'http://localhost:8080/ZPareo/ai/groupe/edition', 
		    data: 
		    {
		    	id:   id,
		    	name: name
		    }, 
		    error: function() 
		    { 
		    	alert("erreur !"); 
		    },
		    success: function(view) 
		    { 
		    	if(view.search('<div id=\"main-wrap\"') > 0) 
		    	{
		    		removeModalWindow();
		    		$('#main-wrap').replaceWith(sliceMainView(view));
		    	}
		    	else 
		    	{
		    		modalWindow.html(view);
		    		initModalWindow(300);
		    		reloadCreateFunctions();
		    	}
		    } 
		});
	});
};

/**
 * Deletes a group in database
 */
var deleteGroup = function()  
{
	$('#delete-group').submit(function(event)
	{
		event.preventDefault();
		var id          = $('#delete-group input[name=id]').val();
		var modalWindow = $('#modal');
		
		$.ajax(
		{ 
			type: "POST", 
		    url: "http://localhost:8080/ZPareo/ai/groupe/suppression", 
		    data: 
		    {
		    	id: id
		    }, 
		    error: function() 
		    { 
		    	alert("erreur !"); 
		    },
		    success: function(view) 
		    { 
		    	if(view.search('<div id=\"main-wrap\"') > 0) 
		    	{
		    		removeModalWindow();
		    		$('#main-wrap').replaceWith(sliceMainView(view));
		    	}
		    	else 
		    	{
		    		modalWindow.html(view);
		    		initModalWindow(300);
		    		reloadCreateFunctions();
		    	}
		    } 
		});
	});
};

/**
 * Creates a student in database
 */ 
var createStudent = function()  
{
	$('#create-student').submit(function(event)
	{
		event.preventDefault();
		var lastName     = $('#create-student input[name=lastName]').val();
		var firstName    = $('#create-student input[name=firstName]').val();
		var emailAddress = $('#create-student input[name=emailAddress]').val();
		var group        = $('#create-student select[name=group]').val();
		var modalWindow  = $('#modal');

		$.ajax(
		{ 
		    type: 'POST', 
		    url:  'http://localhost:8080/ZPareo/ai/etudiant/creation', 
		    data: 
		    {
		    	lastName:     lastName,
		    	firstName:    firstName,
		    	emailAddress: emailAddress,
		    	group:        group
		    }, 
		    error: function() 
		    { 
		    	alert('error !'); 
		    },
		    success: function(view) 
		    { 
		    	if(view.search('<div id=\"main-wrap\"') > 0) 
		    	{
		    		removeModalWindow();
		    		$('#main-wrap').replaceWith(sliceMainView(view));
		    	}
		    	else 
		    	{
		    		modalWindow.html(view);
		    		initModalWindow(300);
		    		reloadCreateFunctions();
		    	}
		    } 
		});
	});
};

/**
 * Edits a student in database
 */
var editStudent = function()  
{
	$('#edit-student').submit(function(event)
	{
		event.preventDefault();
		var id           = $('#edit-student input[name=id]').val();
		var lastName     = $('#edit-student input[name=lastName]').val();
		var firstName    = $('#edit-student input[name=firstName]').val();
		var emailAddress = $('#edit-student input[name=emailAddress]').val();
		var group        = $('#edit-student select[name=group]').val();
		var modalWindow  = $('#modal');

		$.ajax(
		{ 
			type: 'POST', 
		    url:  'http://localhost:8080/ZPareo/ai/etudiant/edition', 
		    data: 
		    {
		    	id:           id,
		    	lastName:     lastName,
		    	firstName:    firstName,
		    	emailAddress: emailAddress,
		    	group:        group
		    }, 
		    error: function() 
		    { 
		    	alert("error !"); 
		    },
		    success: function(view) 
		    { 
		    	if(view.search('<div id=\"main-wrap\"') > 0) 
		    	{
		    		removeModalWindow();
		    		$('#main-wrap').replaceWith(sliceMainView(view));
		    	}
		    	else 
		    	{
		    		modalWindow.html(view);
		    		initModalWindow(300);
		    		reloadCreateFunctions();
		    	}
		    } 
		});
	});
};

/**
 * Deletes a student in database
 */
var deleteStudent = function()  
{
	$('#delete-student').submit(function(event)
	{
		event.preventDefault();
		var id          = $('#delete-student input[name=id]').val();
		var modalWindow = $('#modal');
		
		$.ajax({ 
			type: 'POST', 
		    url:  'http://localhost:8080/ZPareo/ai/etudiant/suppression', 
		    data: 
		    {
		    	id: id
		    }, 
		    error: function() 
		    { 
		    	alert('error !'); 
		    },
		    success: function(view) 
		    { 
		    	if(view.search('<div id=\"main-wrap\"') > 0) 
		    	{
		    		removeModalWindow();
		    		$('#main-wrap').replaceWith(sliceMainView(view));
		    	}
		    	else 
		    	{
		    		modalWindow.html(view);
		    		initModalWindow(300);
		    		reloadCreateFunctions();
		    	}
		    } 
		});
	});
};

/**
 * Resets a user password
 */
var resetPassStudent = function()  
{
	$('#reinit-pass-student').submit(function(event)
	{
		event.preventDefault();
		var id = $('#reinit-pass-student input[name=id]').val();
		
		$.ajax(
		{ 
			type: 'POST', 
		    url:  'http://localhost:8080/ZPareo/ai/etudiant/reinit-mot-de-passe', 
		    data: 
		    {
		    	id: id
		    }, 
		    error: function() 
		    { 
		    	alert("error !"); 
		    },
		    success: function(data) 
		    { 
		   		removeModalWindow();
		    } 
		});
	});
};

/**
 * Creates a teacher in database
 */ 
var createTeacher = function()  
{
	$('#create-teacher').submit( function( event )
	{
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
		    type: 'POST', 
		    url:  'http://localhost:8080/ZPareo/ai/professeur/creation', 
		    data: 
		    {
		    	lastName:     lastName,
		    	firstName:    firstName,
		    	emailAddress: emailAddress,
		    	password:     password,
		    	confirmation: confirmation,
		    	groups:       groups,
		    	matters:      matters
		    }, 
		    error: function() 
		    { 
		    	alert('error !'); 
		    },
		    success: function(view) 
		    { 
		    	if(view.search('<div id=\"main-wrap\"') > 0) 
		    	{
		    		removeModalWindow();
		    		$('#main-wrap').replaceWith(sliceMainView(view));
		    	}
		    	else 
		    	{
		    		modalWindow.html(view);
		    		initModalWindow(600);
		    		reloadCreateFunctions();
		    	}
		    } 
		});
	});
};

/**
 * Edits a teacher in database
 */ 
var editTeacher = function()  
{
	$('#edit-teacher').submit(function(event)
	{
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
		    type: "POST", 
		    url: "http://localhost:8080/ZPareo/ai/professeur/edition", 
		    data: 
		    {
		    	id:           id,
		    	lastName:     lastName,
		    	firstName:    firstName,
		    	emailAddress: emailAddress,
		    	password:     password,
		    	confirmation: confirmation,
		    	groups:       groups,
		    	matters:      matters
		    }, 
		    error: function() 
		    { 
		    	alert("erreur !"); 
		    },
		    success: function(view) 
		    { 
		    	if(view.search('<div id=\"main-wrap\"') > 0) 
		    	{
		    		removeModalWindow();
		    		$('#main-wrap').replaceWith(sliceMainView(view));
		    	}
		    	else 
		    	{
		    		modalWindow.html(view);
		    		initModalWindow(600);
		    		reloadCreateFunctions();
		    	}
		    } 
		});
	});
};

/**
 * Deletes a teacher in database
 */
var deleteTeacher = function()  
{
	$('#delete-teacher').submit(function(event)
	{
		event.preventDefault();
		var id          = $('#delete-teacher input[name=id]').val();
		var modalWindow = $('#modal');
		
		$.ajax(
		{ 
			type: 'POST', 
		    url:  'http://localhost:8080/ZPareo/ai/professeur/suppression', 
		    data: 
		    {
		    	id: id
		    }, 
		    error: function() 
		    { 
		    	alert('error !'); 
		    },
		    success: function(view) 
		    { 
		    	if(view.search('<div id=\"main-wrap\"') > 0) 
		    	{
		    		removeModalWindow();
		    		$('#main-wrap').replaceWith(sliceMainView(view));
		    	}
		    	else 
		    	{
		    		modalWindow.html(view);
		    		initModalWindow('auto');
		    		reloadCreateFunctions();
		    	}
		    } 
		});
	});
};

/**
 * Creates an administrator in database
 */ 
var createAdmin = function()  
{
	$('#create-administrator').submit(function(event)
	{
		event.preventDefault();
		var lastName     = $('#create-administrator input[name=lastName]').val();
		var firstName    = $('#create-administrator input[name=firstName]').val();
		var emailAddress = $('#create-administrator input[name=emailAddress]').val();
		var password     = $('#create-administrator input[name=password]').val();
		var confirmation = $('#create-administrator input[name=confirmation]').val();
		var modalWindow  = $('#modal');
		
		$.ajax(
		{ 
		    type: 'POST', 
		    url:  'http://localhost:8080/ZPareo/ai/administrateur/creation', 
		    data: 
		    {
		    	lastName:     lastName,
		    	firstName:    firstName,
		    	emailAddress: emailAddress,
		    	password:     password,
		    	confirmation: confirmation
		    }, 
		    error: function() 
		    { 
		    	alert('erreur !'); 
		    },
		    success: function(view) 
		    { 
		    	if(view.search('<div id=\"main-wrap\"') > 0) 
		    	{
		    		removeModalWindow();
		    		$('#main-wrap').replaceWith(sliceMainView(view));
		    	}
		    	else 
		    	{
		    		modalWindow.html(view);
		    		initModalWindow(300);
		    		reloadCreateFunctions();
		    	}
		    } 
		});
	});
};

/**
 * Edits an administrator in database
 */ 
var editAdmin = function()  
{
	$('#edit-administrator').submit(function(event)
	{
		event.preventDefault();
		var id           = $('#edit-administrator input[name=id]').val();
		var lastName     = $('#edit-administrator input[name=lastName]').val();
		var firstName    = $('#edit-administrator input[name=firstName]').val();
		var emailAddress = $('#edit-administrator input[name=emailAddress]').val();
		var password     = $('#edit-administrator input[name=password]').val();
		var confirmation = $('#edit-administrator input[name=confirmation]').val();
		var modalWindow  = $('#modal');
		
		$.ajax(
		{ 
		    type: 'POST', 
		    url:  'http://localhost:8080/ZPareo/ai/administrateur/edition', 
		    data: 
		    {
		    	id:           id,
		    	lastName:     lastName,
		    	firstName:    firstName,
		    	emailAddress: emailAddress,
		    	password:     password,
		    	confirmation: confirmation
		    }, 
		    error: function() 
		    { 
		    	alert('erreur !'); 
		    },
		    success: function(view) 
		    { 
		    	if(view.search('<div id=\"main-wrap\"') > 0) 
		    	{
		    		removeModalWindow();
		    		$('#main-wrap').replaceWith(sliceMainView(view));
		    	}
		    	else 
		    	{
		    		modalWindow.html(view);
		    		initModalWindow(300);
		    		reloadEditFunctions();
		    	}
		    } 
		});
	});
};

/**
 * Deletes an administrator in database
 */
var deleteAdmin = function()  
{
	$('#delete-administrator').submit(function(event)
	{
		event.preventDefault();
		var id          = $('#delete-administrator input[name=id]').val();
		var modalWindow = $('#modal');
		
		$.ajax(
		{ 
			type: 'POST', 
		    url:  'http://localhost:8080/ZPareo/ai/administrateur/suppression', 
		    data: 
		    {
		    	id: id
		    }, 
		    error: function() 
		    { 
		    	alert('erreur !'); 
		    },
		    success: function(view) 
		    { 
		    	if(view.search('<div id=\"main-wrap\"') > 0) 
		    	{
		    		removeModalWindow();
		    		$('#main-wrap').replaceWith(sliceMainView(view));
		    	}
		    	else 
		    	{
		    		modalWindow.html(view);
		    		initModalWindow('auto');
		    		reloadDeleteFunctions();
		    	}
		    } 
		});
	});
};

/**
 * Creates a test in database
 */ 
var createTest = function()  
{
	$('#create-test').submit(function(event)
	{
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
		    type: 'POST', 
		    url:  'http://localhost:8080/ZPareo/pi/examen/creation', 
		    data: 
		    {
		    	coefficient: coefficient,
		    	teacher:     teacher,
		    	format:      format,
		    	title:       title,
		    	date:        date,
		    	matter:      matter,
		    	group:       group
		    }, 
		    error: function() 
		    { 
		    	alert('error !'); 
		    },
		    success: function(view) 
		    { 
		    	if(view.search('<form id=\"edit-test\"') > 0) 
		    	{
		    		removeModalWindow();
		    		modalWindow.html(view);
		    		initModalWindow(600);
		    		reloadEditFunctions();
		    	}
		    	else 
		    	{
		    		modalWindow.html(view);
		    		initModalWindow(300);
		    		reloadDeleteFunctions();
		    	}
		    } 
		});
	});
};

/**
 * Edits a test in database
 */ 
var editTest = function()  
{
	$('#edit-test').submit(function(event)
	{
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
		
		$('input[name="students[]"]').each(function()
				{
			students.push($(this).val());
		});
		
		$('input[name="scores[]"]').each(function()
		{
			scores.push($(this).val());
		});
		
		students = students.join("-");
		scores   = scores.join("-");

		$.ajax(
		{ 
		    type: 'POST', 
		    url:  'http://localhost:8080/ZPareo/pi/examen/edition', 
		    data: 
		    {
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
		    error: function() 
		    { 
		    	alert("error !"); 
		    },
		    success: function(view) 
		    { 
		    	if(view.search('<div id=\"main-wrap\"') > 0) 
		    	{
		    		removeModalWindow();
		    		$('#main-wrap').replaceWith(sliceMainView(view));
		    	}
		    	else 
		    	{
		    		modalWindow.html(view);
		    		initModalWindow(600);
		    		reloadDeleteFunctions();
		    	}
		    } 
		});
	});
};

/**
 * Deletes a test in database
 */
var deleteTest = function()  
{
	$('#delete-test').submit(function(event)
	{
		event.preventDefault();
		var id          = $('#delete-test input[name=id]').val();
		var modalWindow = $('#modal');
		
		$.ajax({ 
			type: 'POST', 
		    url:  'http://localhost:8080/ZPareo/pi/examen/suppression', 
		    data: 
		    {
		    	id: id
		    }, 
		    error: function() 
		    { 
		    	alert("error !"); 
		    },
		    success: function(view) 
		    { 
		    	if(view.search('<div id=\"main-wrap\"') > 0) 
		    	{
		    		removeModalWindow();
		    		$('#main-wrap').replaceWith(sliceMainView(view));
		    	}
		    	else 
		    	{
		    		modalWindow.html(view);
		    		initModalWindow(600);
		    		reloadDeleteFunctions();
		    	}
		    } 
		});
	});
};

/**
 * Checks login in database
 */ 
var checkLogin = function()  
{
	$('#check-login').submit(function(event)
	{
		event.preventDefault();
		var emailAddress = $('#check-login input[name=emailAddress]').val();
		var password     = $('#check-login input[name=password]').val();
		var formHeight   = $('#check-login').height();
		var iconHeight   = $('.icon-load').height();
		
		// Animation 
		$('#check-login').fadeOut(300);
		$('.icon-load-wrap').delay(300).css(
		{
			'height': formHeight - iconHeight,
			'margin-top': (formHeight / 2)
		}).fadeIn(1500, function()
		{
			$.ajax(
			{ 
			    type: 'POST', 
			    url:  'http://localhost:8080/ZPareo/connexion', 
			    data: 
			    {
			    	emailAddress: emailAddress,
			    	password:     password
			    }, 
			    error: function() 
			    { 
			    	alert('error !'); 
			    },
			    success: function(view) 
			    {
			    	if (view.search('<div id=\"main-wrap\"') > 0) 
			    	{
			    		$('#mod-connection').delay(1300).animate({
			    			left: '-=2000'
			    		},1000, function()
			    		{
				    		$('body').hide();
				    		$('#site-wrap').replaceWith(view.slice(view.search('<div id=\"site-wrap\">'), view.search('<!-- End site-wrap -->')));
				    		$('body').fadeIn();
				    		
				    		if (view.search('Liste des administrateurs') > 0) 
				    		{
				    			history.pushState(
				    			{ 
				    				path: this.path 
				    			}, 
				    				'', 
				    				'http://localhost:8080/ZPareo/ai/administrateur'
				    			);
				    			document.title = 'ZPareo - Liste des administrateurs';
				    		}
				    		else if (view.search('Liste de vos examens') > 0) 
				    		{
				    			history.pushState(
				    			{ 
				    				path: this.path 
				    			}, 
				    				'', 
				    				'http://localhost:8080/ZPareo/pi/examen'
				    			);
				    			document.title = 'ZPareo - Liste de vos examens';
				    		}
				    		else if (view.search('Bulletin de notes') > 0) 
				    		{
				    			history.pushState(
				    			{ 
				    				path: this.path 
				    			},
				    				'', 
				    				'http://localhost:8080/ZPareo/ei/mon-bulletin'
				    			);
				    			document.title = 'ZPareo - Mon bulletin';
				    			initAverageStudentChart();
				    		}
			    		});
			    	}
			    	else 
			    	{
			    		$('#site-wrap').replaceWith(view.slice(view.search('<div id=\"site-wrap\" class=\"bg-white\">'), view.search('<!-- End site-wrap -->')));
			    		$('#mod-connection').animate(
			    		{
			    			left:"+=5"
			    		},60).animate(
			    		{
			    			left:"-=10"
			    		},60).animate(
			    		{
			    			left:"+=10"
			    		},60).animate(
			    		{
			    			left:"-=10"
			    		},60).animate(
			    		{
			    			left:"+=5"
			    		},60);
			    	}
			    	
			    	initDatepicker();
			    	initSelect2();
			    } 
			});
			
		});
	});
};
jQuery(document).ready(function($) 
{
	initTableSorter();
	initDatepicker();
	initSelect2();
	animSortableColumn();
});