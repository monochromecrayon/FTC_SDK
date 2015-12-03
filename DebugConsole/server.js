var express = require('express');
var app = express()
var http = require('http').Server(app);
var io = require('socket.io')(http);

app.use(express.static('public'));

app.get('/', function(req, res){
  res.sendFile(__dirname + '/index.html');
});

io.on('connection', function(socket){
  socket.on('disconnect', function(){
  });

  socket.on('i connected', function(msg){
    console.log("bot connected");
  });

  socket.on('console connected', function(msg){
    console.log("console connected");
  });

  socket.on('leftp', function(msg){
    socket.emit('leftp', msg);
  });

  socket.on('rightp', function(msg){
    socket.emit('rightp', msg);
  });

  socket.on('setRightP', function(msg){
    socket.emit('setRightP', msg);
  });

  socket.on('setLeftP', function(msg){
    socket.emit('setLeftP', msg);
  });
});

http.listen(8080, function(){
  console.log('listening on *:8080');
});
