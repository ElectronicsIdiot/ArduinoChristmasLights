var express = require('express');
var app = express();

var bodyParser = require('body-parser')

var SerialPort = require('serialport');
var serialport = new SerialPort('/dev/ttyUSB0');

app.use(bodyParser.urlencoded({extended: true}));

app.get('/', function (req, res) {
  console.log("Connection from " + req.connection.remoteAddress);
  res.sendFile('index.html', {root: __dirname});
});

app.post('/', function (req, res) {
  var body = req.body;
  var buffer = new Buffer(4);
  buffer[0] = parseInt(body.b);
  buffer[1] = parseInt(body.r)
  buffer[2] = parseInt(body.y);
  buffer[3] = parseInt(body.g);
  serialport.write(buffer);
  res.send('ok');
});

serialport.on('open', function(){
  setTimeout(function() {
    app.listen(80);
  }, 2000);
});
