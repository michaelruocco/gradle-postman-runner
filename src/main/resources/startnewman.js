"use strict";

const newman = require('newman');

var collectionFile = process.argv[2];
var environmentFile = process.argv[3];

var toString = function(value) {JSON.stringify(value, null, 1)};

var config = {};
config.collection = collectionFile;
config.reporters = ['cli'];

if (environmentFile !== undefined) {
    config.environment = environmentFile;
}

newman.run(config, function(err, summary) {
    if (err) {
       console.log(toString(err));
       var failed = true;
    } else {
       var failed = summary.run.failures && summary.run.failures.length > 0;
    }

    if (failed) {
        process.exit(1);
    }
});
