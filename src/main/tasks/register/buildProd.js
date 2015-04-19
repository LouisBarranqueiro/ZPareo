module.exports = function(grunt) {
    // Build (environment : production)
    grunt.registerTask('BuildProd', [
        'clean:build',
        'CompileAssets',
        'concat',
        'cssmin',
        'uglify',
    ]);
};
