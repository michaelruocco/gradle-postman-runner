"use strict";

const newman = require('newman');

var config = JSON.parse(
    unescape(process.argv[2])
    .replace(new RegExp('<>', 'g'), '"'));

newman.run(config, function(err, summary) {
    if (err) {
       console.log(JSON.stringify(err, null, 1));
       process.exit(2);
    } else {
        if (summary.run.failures && summary.run.failures.length > 0) {
            process.exit(1);
        }
    }
});
