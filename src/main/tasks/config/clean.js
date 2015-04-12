module.exports = function(grunt) {
    grunt.config.set('clean', {
        build: [
            'src/main/webapp/resources/js',
            'src/main/webapp/resources/css'
        ]
    });

    grunt.loadNpmTasks('grunt-contrib-clean');
};