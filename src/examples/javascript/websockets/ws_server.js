load('vertx.js')

var server = new vertx.HttpServer();

server.websocketHandler(function(ws) {
  var p = new vertx.Pump(ws, ws);
  p.start();
})

server.listen(8181, 'localhost');

function vertxStop() {
  server.close
}
