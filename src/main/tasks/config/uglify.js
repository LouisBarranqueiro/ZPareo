module.exports = function(grunt) {
    grunt.config.set('uglify', {
        // Minify `script.js` file into `script.min.js`
        dev: {
            options: {
                mangle: {
                    except: [
                        'jQuery',
                        'tablesorter'
                    ]
                }
            },
            files:   {
                'src/main/webapp/resources/js/script.min.js': ['src/main/webapp/resources/js/script.js']
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-uglify');
};