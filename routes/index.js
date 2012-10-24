var moment = require('moment'),
  mongo = require('mongodb'),
  Server = mongo.Server,
  Db = mongo.Db,
  ReplSetServers = mongo.ReplSetServers;

var replSet = new ReplSetServers( [
    new Server( 'localhost', 27017, { auto_reconnect: true } ),
    new Server( 'localhost', 27018, { auto_reconnect: true } ),
    new Server( 'localhost', 27019, { auto_reconnect: true } )
  ],
  {rs_name:'rs0'}
);

var db = new Db('test', replSet);

/*
 * Post message.
 */
function postFunc(req, res, err, timeout){
	var _timeout = timeout || moment().add('minutes', 1);
	db.open(function(err, db) {
    if(!err) {
    	db.collection('queue', function(err, collection) {
		  	collection.insert( { "status": "new", "message": "Hello World!"} , function(err, cursor) {
		  		res.render('post', { title: "Message posted" });
		  		console.log('post OK');
		      db.close();
		    });
      });
    } else {
    	var now = moment();
    	if(now.diff(_timeout) < 0) {
    		setTimeout(function(){
					console.log('post failed, try again');
    		  postFunc(req, res, undefined, _timeout);
    		}, 100);
      } else {
      	console.log('post failed, no more retry');
      	res.render('post', { title: "Error posting message" });
      }
    }
  });
};

exports.post = postFunc;

/*
 * GET message.
 */
exports.get = function(req, res){
	db.open(function(err, db) {
    if(!err) {
    	db.collection('queue', function(err, collection) {
		  	collection.findAndModify({status: "new"}, [['_id','asc']], {$set: {status: 'processed'} }, {}, function(err, object) {
		  		if(object)
		  			res.render('get', { title: "Message processed", message: object });
		  		else
		  			res.render('empty', { title: "No pending messages"});
		      db.close();
		    });
      });
    }
  });
};
