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
prevText: 'Précédent',
nextText: 'Suivant',
currentText: 'Aujourd\'hui',
monthNames: ['janvier', 'février', 'mars', 'avril', 'mai', 'juin',
'juillet', 'aout', 'septembre', 'octobre', 'novembre', 'd�cembre'],
monthNamesShort: ['janv.', 'févr.', 'mars', 'avril', 'mai', 'juin',
'juil.', 'aout', 'sept.', 'oct.', 'nov.', 'déc.'],
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