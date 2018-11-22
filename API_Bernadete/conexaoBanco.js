let mysql = require("mysql")

let conexao = mysql.createConnection({

    host:"localhost",
    user:"root",
    password:"bcd127",
    database:"db_brecho"

})

conexao.connect(function(err){

    if(err) throw error

    console.log("Conectado ao banco.")

})

exports.mysql = function(){

    return conexao

}
