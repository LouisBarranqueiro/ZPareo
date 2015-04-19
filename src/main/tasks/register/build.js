module.exports = function(grunt) {
    // Build (environment : development)
    grunt.registerTask('Build', [
        'clean:build',
        'CompileAssets',
    ]);
};
