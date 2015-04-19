module.exports = function(grunt) {
    grunt.config.set('cssmin', {
        // Minify `style.css` file into `style.min.css`
        prod: {
            files: [{
                expand: true,
                cwd:    'src/main/webapp/resources/css',
                src:    ['style.css'],
                dest:   'src/main/webapp/resources/css',
                ext:    '.min.css'
            }]
        }
    });
    grunt.loadNpmTasks('grunt-contrib-cssmin');
};