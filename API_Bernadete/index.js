const express = require("express")
const fs = require("fs")
const app = express()
const http = require("http").createServer(app)
const bodyParser = require("body-parser")
const conexao = require("./conexaoBanco").mysql()

app.use(bodyParser.urlencoded({extended:true}))

app.get("/imagens/:filename", function(req, res) {

    const filename = req.params.filename

    console.log(__dirname + "\\" + filename);

    fs.readFile("./imagens/" + filename, function(err, content){

        if (err) {

            res.writeHead(400, {'Content-type':'text/html'})
            console.log(err);
            res.end("No such image");

        } else {

            res.writeHead(200,{'Content-type':'image/jpg', 'Content-type':'image/png'});
            res.end(content);

        }

    })

});

app.post("/promocao", (req, res)=>{

	//alert("!!!")

    let categoria = req.body.categoria

    const SQL = "SELECT * FROM view_produto_promocao_api WHERE estilo = '" + categoria  + "'";

    conexao.query(SQL, function(err, result){

        if(err) throw err

        res.send(result)

    })

})

app.post("/produto", (req, res)=>{

    let SQL = "SELECT * FROM view_produto_pedido WHERE id_cliente = ?"

    let idCliente = req.body.id_cliente

    conexao.query(SQL, idCliente, function(err, result){

        if(err) throw err

        res.send(result)

    })

})

app.post("/autenticacao", (req, res) => {

    let email = req.body.email
    let senha = req.body.senha

    let SQL = "SELECT * FROM tbl_cliente WHERE email = ? AND senha = ?"

    conexao.query(SQL, [email, senha], function(err, result){

        if(result.length == 0){

            res.send({"autenticacao":false})

        } else {

            res.send({"autenticacao":true, "nome":result[0]["nome"], "email":result[0]["email"]})

        }

    })

})

http.listen(8888, () => {

    console.log("Servidor escutando na porta 8888")

})
