
const newman = require('newman');

var collectionFile = process.argv[2]

var toString = function(value) {JSON.stringify(value, null, 1)};

var TestCollection = function (collection) {
    var failed = false;
    this.run = function (){
        newman.run({
            collection: collectionFile,
            reporters: ['cli', 'junit'],
            reporter: { junit: { export: 'result.xml' }}
        }, function(err, summary) {
            if (err) {
                console.log(toString(err));
                failed = true;
            } else {
                failed = summary.run.failures && summary.run.failures.length > 0;
            }
        });
    };
    this.failed = function(){return failed};
}


var exec = new TestCollection(collectionFile);
exec.run();
if (exec.failed()) {
    process.exit(1);
}