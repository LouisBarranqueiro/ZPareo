module.exports = function (grunt) {
    // Synchronize all assets (css and js) after changes
    grunt.registerTask('SyncAssets', ['CompileAssets']);
};
