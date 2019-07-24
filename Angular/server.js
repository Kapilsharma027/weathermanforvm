/*
Put content of angular2 build into 'public' folder.
*/
const html = __dirname + '/public';

const port = 3300;
const host = '192.168.97.229';
const apiUrl = '/api';

// Express
const bodyParser = require('body-parser');
const compression = require('compression');

var fs = require('fs');
var http = require('http');
var https = require('https');
var privateKey  = fs.readFileSync('ssl/server.key', 'utf8');
var certificate = fs.readFileSync('ssl/server.crt', 'utf8');

var credentials = {key: privateKey, cert: certificate};
var express = require('express');

var app = express();

var httpServer = http.createServer(app);
var httpsServer = https.createServer(credentials, app);
httpsServer.listen(8443);

app
    .use(compression())
    .use(bodyParser.json())
    // Static content
    .use(express.static(html))
    // Default route
    .use(function(req, res) {
      res.sendFile(html + '/index.html');
    })
    // Start server
    .listen(port, function () {
        console.log('Host: ' + host);
        console.log('Port: ' + port +' & 8443');
        console.log('Html: ' + html);
    });

// continue with api code below ...