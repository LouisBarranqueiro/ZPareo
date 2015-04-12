/**
 * Slices a view (main wrap)
 * @param {string} view
 * @return {string}
 */
var sliceMainView = function(view) {
    return view.slice(view.search('<div id=\"main-wrap\" class=\"main\">'), view.search('<!-- End main-wrap -->'));
};