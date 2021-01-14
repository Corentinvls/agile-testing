const report = require('multiple-cucumber-html-reporter');

report.generate({
    jsonDir: './cucumber-reports',
    reportPath: './reportPretty',
    metadata:{
        browser: {
            name: 'chrome',
            version: '87'
        },
        device: 'Local test machine',
        platform: {
            name: 'osx',
            version: '10.15.7'
        }
    }
});