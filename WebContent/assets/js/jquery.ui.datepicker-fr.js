(function( factory ) {
if ( typeof define === "function" && define.amd ) {

// AMD. Register as an anonymous module.
define([ "../datepicker" ], factory );
} else {

// Browser globals
factory( jQuery.datepicker );
}
}(function( datepicker ) {

datepicker.regional['fr'] = {
closeText: 'Fermer',
prevText: 'Pr残仕ent',
nextText: 'Suivant',
currentText: 'Aujourd\'hui',
monthNames: ['janvier', 'fﾃｩvrier', 'mars', 'avril', 'mai', 'juin',
'juillet', 'aout', 'septembre', 'octobre', 'novembre', 'd残embre'],
monthNamesShort: ['janv.', 'fﾃｩvr.', 'mars', 'avril', 'mai', 'juin',
'juil.', 'aout', 'sept.', 'oct.', 'nov.', 'd残.'],
dayNames: ['dimanche', 'lundi', 'mardi', 'mercredi', 'jeudi', 'vendredi', 'samedi'],
dayNamesShort: ['dim.', 'lun.', 'mar.', 'mer.', 'jeu.', 'ven.', 'sam.'],
dayNamesMin: ['D','L','M','M','J','V','S'],
weekHeader: 'Sem.',
dateFormat: 'dd/mm/yy',
firstDay: 1,
isRTL: false,
showMonthAfterYear: false,
yearSuffix: ''};
datepicker.setDefaults(datepicker.regional['fr']);

return datepicker.regional['fr'];

}));