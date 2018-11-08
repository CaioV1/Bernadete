package com.digitalindividual.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.jetbrains.anko.db.*

/**
 * Created by 17170077 on 17/10/2018.
 */
class DBHelper: ManagedSQLiteOpenHelper {

    constructor(context: Context):super(context, "bernadete.db", null, 1);

    companion object {

        private var instance:DBHelper? = null

        fun getInstance(context: Context):DBHelper{

            if(instance == null){

                instance = DBHelper(context.applicationContext)

            }

            return instance!!

        }

    }

    override fun onCreate(database: SQLiteDatabase) {

        database.createTable("tbl_usuario", true,
                             "id_usuario" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                                       "email" to TEXT + NOT_NULL,
                                       "nome" to TEXT + NOT_NULL)

        database.createTable("tbl_categoria", true,
                "id_categoria" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "nome" to TEXT + NOT_NULL)

        database.insert("tbl_categoria","nome" to "Camiseta")
        database.insert("tbl_categoria","nome" to "Calçado")
        database.insert("tbl_categoria","nome" to "Terno")
        database.insert("tbl_categoria","nome" to "Calça")
        database.insert("tbl_categoria","nome" to "Bermuda")
        database.insert("tbl_categoria","nome" to "Blusa")

        database.createTable("tbl_tipo_filtro", true,
                "id_tipo" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "nome" to TEXT + NOT_NULL)

        database.insert("tbl_tipo_filtro", "nome" to "Cor")
        database.insert("tbl_tipo_filtro", "nome" to "Estado")
        database.insert("tbl_tipo_filtro", "nome" to "Marca")
        database.insert("tbl_tipo_filtro", "nome" to "Tamanho")

        database.createTable("tbl_filtro", true,
                "id_filtro" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "nome" to TEXT + NOT_NULL,
                "id_tipo" to INTEGER + NOT_NULL,
                FOREIGN_KEY("id_tipo", "tbl_tipo_filtro", "id_tipo"))

        database.insert("tbl_filtro", "nome" to "Azul", "id_tipo" to 1)
        database.insert("tbl_filtro", "nome" to "Branco", "id_tipo" to 1)
        database.insert("tbl_filtro", "nome" to "Marrom", "id_tipo" to 1)
        database.insert("tbl_filtro", "nome" to "Preto", "id_tipo" to 1)
        database.insert("tbl_filtro", "nome" to "Vermelho", "id_tipo" to 1)
        database.insert("tbl_filtro", "nome" to "Nova", "id_tipo" to 2)
        database.insert("tbl_filtro", "nome" to "Conservada", "id_tipo" to 2)
        database.insert("tbl_filtro", "nome" to "Usada", "id_tipo" to 2)
        database.insert("tbl_filtro", "nome" to "Velha", "id_tipo" to 2)
        database.insert("tbl_filtro", "nome" to "Adidas", "id_tipo" to 3)
        database.insert("tbl_filtro", "nome" to "Hering", "id_tipo" to 3)
        database.insert("tbl_filtro", "nome" to "Nike", "id_tipo" to 3)
        database.insert("tbl_filtro", "nome" to "Via Marte", "id_tipo" to 3)
        database.insert("tbl_filtro", "nome" to "G", "id_tipo" to 4)
        database.insert("tbl_filtro", "nome" to "M", "id_tipo" to 4)
        database.insert("tbl_filtro", "nome" to "P", "id_tipo" to 4)
        database.insert("tbl_filtro", "nome" to "PP", "id_tipo" to 4)

        database.createTable("tbl_peca", true,
                "id_peca" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "nome" to TEXT + NOT_NULL,
                "descricao" to TEXT,
                "imagem" to BLOB + NOT_NULL,
                "id_categoria" to INTEGER + NOT_NULL,
                FOREIGN_KEY("id_categoria", "tbl_categoria", "id_categoria"))

        database.createTable("tbl_notificacao", true,
                "id_notificacao" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "titulo" to TEXT + NOT_NULL,
                "data" to INTEGER + NOT_NULL,
                "local" to TEXT,
                "descricao" to TEXT)

        database.createTable("tbl_peca_filtro", true,
                "id_peca_filtro" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "id_peca" to INTEGER + NOT_NULL,
                "id_filtro" to INTEGER + NOT_NULL,
                FOREIGN_KEY("id_peca", "tbl_peca", "id_peca"),
                FOREIGN_KEY("id_filtro", "tbl_filtro", "id_filtro"))

        database.createTable("tbl_peca_notificacao", true,
                "id_peca_notificacao" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "id_peca" to INTEGER + NOT_NULL,
                "id_notificacao" to INTEGER + NOT_NULL,
                FOREIGN_KEY("id_peca", "tbl_peca", "id_peca"),
                FOREIGN_KEY("id_notificacao", "tbl_notificacao", "id_notificacao"))

//        var SQL: String = "CREATE TABLE tbl_categoria(" +
//                          "_id_categoria INTEGER PRIMARY KEY AUTOINCREMENT," +
//                          "nome TEXT);"
//
//        database?.execSQL(SQL)

//        SQL = "INSERT INTO tbl_categoria(nome) VALUES('Bota'),('Tênis'),('Regata'),('Camiseta'),('Camisa'),('Smoking'),('Gravata')," +
//                                                    "('Vestido'),('Calça'),('Bermuda'),('Chapéu'),('Jaqueta');"
//
//        database?.execSQL(SQL)

//        SQL = "CREATE TABLE tbl_tipo_filtro(" +
//              "_id_tipo INTEGER PRIMARY KEY AUTOINCREMENT," +
//              "nome TEXT);"
//
//        database?.execSQL(SQL)
//
//        SQL = "INSERT INTO tbl_tipo_filtro(nome) VALUES('Marca'),('Cor'),('Tamanho'),('Estado');"

//        database?.execSQL(SQL)

//        SQL = "CREATE TABLE tbl_filtro(" +
//                "_id_filtro INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "nome TEXT," +
//                "id_tipo INT, " +
//                "FOREIGN KEY(id_tipo) REFERENCES tbl_tipo_filtro(_id_tipo));"
//
//        database?.execSQL(SQL)

//        SQL = "INSERT INTO tbl_filtro(nome, id_tipo) VALUES('Nike', 1),('Adidas', 1),('Hering', 1),('Via Marte', 1)," +
//                                                          "('Vermelho', 2),('Azul', 2),('Preto', 2),('Branco', 2),('Marrom', 2)," +
//                                                          "('G', 3),('M', 3),('P', 3),('PP', 3)," +
//                                                          "('Nova', 4),('Conservada', 4),('Usada', 4),('Velha', 4);"
//
//        database?.execSQL(SQL)

//        SQL = "CREATE TABLE tbl_peca("+
//              "_id_peca INTEGER PRIMARY KEY AUTOINCREMENT,"+
//              "nome TEXT," +
//              "descricao TEXT," +
//              "id_categoria INT," +
//              "imagem BLOB," +
//              "FOREIGN KEY(id_categoria) REFERENCES tbl_categoria(_id_categoria));"
//
//        database?.execSQL(SQL)

//        SQL = "CREATE TABLE tbl_notificacao(" +
//              "_id_notificacao INTEGER PRIMARY KEY AUTOINCREMENT,"+
//              "titulo TEXT," +
//              "data INT," +
//              "local TEXT," +
//              "descricao TEXT);"
//
//        database?.execSQL(SQL)

//        SQL = "CREATE TABLE tbl_peca_filtro(" +
//              "_id_peca_filtro INTEGER PRIMARY KEY AUTOINCREMENT,"+
//              "id_peca INT," +
//              "id_filtro INT," +
//              "FOREIGN KEY(id_peca) REFERENCES tbl_peca(_id_peca)," +
//              "FOREIGN KEY(id_filtro) REFERENCES tbl_filtro(_id_filtro));"
//
//        database?.execSQL(SQL)

//        SQL = "CREATE TABLE tbl_peca_notificacao(" +
//                "_id_peca_notificacao INTEGER PRIMARY KEY AUTOINCREMENT,"+
//                "id_peca INT," +
//                "id_notificacao INT," +
//                "FOREIGN KEY(id_peca) REFERENCES tbl_peca(_id_peca)," +
//                "FOREIGN KEY(id_notificacao) REFERENCES tbl_filtro(_id_notificacao));"
//
//        database?.execSQL(SQL)

    }

    override fun onUpgrade(database: SQLiteDatabase, p1: Int, p2: Int) {

        onCreate(database)

    }

}