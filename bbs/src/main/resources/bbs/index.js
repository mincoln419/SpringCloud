const http = require('http');
const url = require('url');
const fs = require('fs');

http.createServer((req, res) => {
    res.writeHead(200, {'Content-Type': 'text/html; charset=utf-8'});
    console.log(req.url);
    if(req.url == "/"){
        fs.readFile('./index.html',  function(err, data){
            if(err){
                throw err;
            }else{
                res.end(data);
            }
        })
    }else if(req.url.startsWith("/detail")){
        fs.readFile('./detail.html',  function(err, data){
            if(err){
                throw err;
            }else{
                res.end(data);
            }
        })
    }else{
        console.log("잘못된 페이지");
    }
}).listen(3000);