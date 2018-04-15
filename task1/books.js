var express = require('express');
var fs = require("fs");

var app = express();

var book = [{
      "Book Name" : "Head First Html",
      "Book Publisher Date" : "30-05-1976",
      "Book Author" : "Agatha Christie",
      "Book ISBN": "9801234563670",
      "Book Price": 450
}, {
      "Book Name" : "Stilliness Speak",
      "Book Publisher Date" : "30-05-1994",
      "Book Author" : "Eckhart Tolle",
      "Book ISBN": "69851234583670",
      "Book Price": 300
},{
      "Book Name" : "Distilled Computer Science",
      "Book Publisher Date" : "30-05-2017",
      "Book Author" : " Wladston Ferreira Filho",
      "Book ISBN": "9801234583670",
      "Book Price": 650
}, {
      "Book Name" : "Murder On Orient Express",
      "Book Publisher Date" : "30-05-1976",
      "Book Author" : "Agatha Christie",
      "Book ISBN": "9801234583670",
      "Book Price": 100
}, {
      "Book Name" : "Alchemist",
      "Book Publisher Date" : "30-05-1976",
      "Book Author" : "Paul Coehlo",
      "Book ISBN": "7801234083670",
      "Book Price": 150
}, {
      "Book Name" : "The Immortals Of Meluha",
      "Book Publisher Date" : "30-05-200",
      "Book Author" : "Amish Tripathi",
      "Book ISBN": "4801234083670",
      "Book Price": 350
}];

app.get('/books/create', function (req, res) {
         console.log( "in function books /create " );
   // First read existing users.

  fs.exists("books.json",function(exists){
    if(exists){
      fs.readFile("books.json", function (err, data) {
          data = JSON.parse(data);
          data.push(book[5]);

          fs.writeFile('books.json',  JSON.stringify(data), function (err) {
          if (err) throw err;
          console.log('Saved!');
            });
          console.log(data);
          console.log(err);
          res.end(JSON.stringify(data));
      });
    }
    else{

 }
 });
});

app.get('/books', function (req, res) {
   fs.readFile("books.json", function (err, data) {
       console.log( data );
       res.end( data );
   });
});

app.get('/books/sorted', function (req, res) {
   fs.readFile("books.json", function (err, data) {
     console.log( data );
     res.end( data );
   });
})

var server = app.listen(8000, function () {

 var host = server.address().address;
 var port = server.address().port;
 console.log("Example app listening at http://%s:%s", host, port);

});
