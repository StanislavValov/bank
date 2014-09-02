module.exports = function (config) {
    config.set({

        basePath: '../',

        files: [
            'bower_components/angular/angular.js',
            'bower_components/angular-route/angular-route.js',
            'bower_components/angular-resource/angular-resource.js',
            'bower_components/angular-animate/angular-animate.js',
            'bower_components/angular-mocks/angular-mocks.js',
//      'bank/modules/httpModule.js',
            'test/unit/*.js'
        ],

        autoWatch: true,

        frameworks: ['jasmine'],

        browsers: ['phantomjs'],

        plugins: [
            'karma-jasmine',
            'karma-phantomjs-launcher',
            'karma-chrome-launcher'
        ],

        junitReporter: {
            outputFile: 'test_out/unit.xml',
            suite: 'unit'
        }

    });
};