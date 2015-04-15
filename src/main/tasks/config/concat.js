module.exports = function (grunt) {
    grunt.config.set('concat', {
        // Concat all javascript file into `script.js`
        dev:  {
            src:     [
                'src/main/webapp/resources/_js/vendors/*.js',
                'src/main/webapp/resources/_js/utils/*.js',
                'src/main/webapp/resources/_js/components/*.js',
                'src/main/webapp/resources/_js/animations/*.js',
                'src/main/webapp/resources/_js/beans/*.js'
            ],
            dest:    'src/main/webapp/resources/js/script.js',
            options: {
                separator: ';',
            }
        },
    });

    grunt.loadNpmTasks('grunt-contrib-concat');
};