module.exports = function(grunt) {
    grunt.config.set('sass', {
        // Compile `style.scss` file into `style.css`
        dev: {
            options: {
                sourcemap: 'none'
            },
            files:   [{
                expand: true,
                cwd:    'src/main/webapp/resources/_scss',
                src:    ['style.scss'],
                dest:   'src/main/webapp/resources/css',
                ext:    '.css'
            }]
        }
    });

    grunt.loadNpmTasks('grunt-contrib-sass');
};