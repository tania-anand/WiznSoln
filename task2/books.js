var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://mongo:mongo@ds243008.mlab.com:43008/mongo";
var express = require('express');
var app = express();


// for creating collection
// MongoClient.connect(url, function(err, db) {
//   if (err) throw err;
//   var dbo = db.db("mongo");
//   dbo.createCollection("books", function(err, res) {
//     if (err) throw err;
//     console.log("collection books created!");
//     db.close();
//   });
// });

var book = [{
      "Book Name" : "Head First Html",
      "Book Publisher Date" : new Date("30-05-1976"),
      "Book Author" : "Agatha Christie",
      "Book ISBN": "9801234563670",
      "Book Price": 450
}, {
      "Book Name" : "Stilliness Speak",
      "Book Publisher Date" : new Date("30-05-1994"),
      "Book Author" : "Eckhart Tolle",
      "Book ISBN": "69851234583670",
      "Book Price": 300
},{
      "Book Name" : "Distilled Computer Science",
      "Book Publisher Date" : new Date("30-05-2017"),
      "Book Author" : " Wladston Ferreira Filho",
      "Book ISBN": "9801234583670",
      "Book Price": 650
}, {
      "Book Name" : "Murder On Orient Express",
      "Book Publisher Date" : new Date("30-05-1976"),
      "Book Author" : "Agatha Christie",
      "Book ISBN": "9801234583670",
      "Book Price": 100
}, {
      "Book Name" : "Alchemist",
      "Book Publisher Date" : new Date("30-05-1976"),
      "Book Author" : "Paul Coehlo",
      "Book ISBN": "7801234083670",
      "Book Price": 150
}, {
      "Book Name" : "The Immortals Of Meluha",
      "Book Publisher Date" : new Date("30-05-2000"),
      "Book Author" : "Amish Tripathi",
      "Book ISBN": "4801234083670",
      "Book Price": 350
}];


app.get('/books/create', function (req, response) {
         console.log( "in function books /create " );
   // First read existing users.

   // for inserting data
   MongoClient.connect(url, function(err, db) {
     if (err) throw err;
     var dbo = db.db("mongo");

     dbo.collection("books").save(book[0], function(err, res) {
       if (err) throw err;
       console.log("1 document saved");
       dbo.collection("books").find({}).toArray(function(err, result) {
         if (err) throw err;
         console.log(result);
         response.send(result);
         db.close();
       });

     });
   });
 });


app.get('/books', function (req, response) {
  MongoClient.connect(url, function(err, db) {
  if (err) throw err;
  var dbo = db.db("mongo");

  dbo.collection("books").find({}).toArray(function(err, result) {
    if (err) throw err;
    console.log(result);
    response.send(result);
    db.close();
  });
});
});

app.get('/books/sorted', function (req, response) {
  MongoClient.connect(url, function(err, db) {
  if (err) throw err;
  var dbo = db.db("mongo");
  var mysort = {"Book Price": 1 };
  dbo.collection("books").find().sort(mysort).toArray(function(err, result) {
    if (err) throw err;
    console.log(result);
    response.send(result);
    db.close();
  });
});
});

  var server= app.listen(8000, () => {
    var host = server.address().address;
    var port = server.address().port;
    console.log("Example app listening at http://%s:%s", host, port);
  })
