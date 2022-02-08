const http = require('http');
const url = require('url');
const fs = require('fs');

http.createServer((req, res) => {
    res.writeHead(200, {'Content-Type': 'text/html; charset=utf-8'});

    fs.readFile('./index.html',  function(err, data){
        if(err){
            throw err;
        }else{
            res.end(data);
        }
    })
}).listen(3000);