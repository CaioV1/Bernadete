package com.digitalindividual.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by 17170077 on 17/10/2018.
 */
class DBHelper: SQLiteOpenHelper {

    constructor(context: Context):super(context, "bernadete.db", null, 1);

    override fun onCreate(database: SQLiteDatabase?) {

        var SQL: String = "CREATE TABLE tbl_categoria(" +
                          "_id_categoria INTEGER PRIMARY KEY AUTOINCREMENT," +
                          "nome TEXT);"

        database?.execSQL(SQL)

        SQL = "INSERT INTO tbl_categoria(nome) VALUES('Bota'),('Tênis'),('Regata'),('Camiseta'),('Camisa'),('Smoking'),('Gravata')," +
                                                    "('Vestido'),('Calça'),('Bermuda'),('Chapéu'),('Jaqueta');"

        database?.execSQL(SQL)

        SQL = "CREATE TABLE tbl_tipo_filtro(" +
              "_id_tipo INTEGER PRIMARY KEY AUTOINCREMENT," +
              "nome TEXT);"

        database?.execSQL(SQL)

        SQL = "INSERT INTO tbl_tipo_filtro(nome) VALUES('Marca'),('Cor'),('Tamanho'),('Estado');"

        database?.execSQL(SQL)

        SQL = "CREATE TABLE tbl_filtro(" +
                "_id_filtro INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "id_tipo INT, " +
                "FOREIGN KEY(id_tipo) REFERENCES tbl_tipo_filtro(_id_tipo));"

        database?.execSQL(SQL)

        SQL = "INSERT INTO tbl_filtro(nome, id_tipo) VALUES('Nike', 1),('Adidas', 1),('Hering', 1),('Via Marte', 1)," +
                                                          "('Vermelho', 2),('Azul', 2),('Preto', 2),('Branco', 2),('Marrom', 2)," +
                                                          "('G', 3),('M', 3),('P', 3),('PP', 3)," +
                                                          "('Nova', 4),('Conservada', 4),('Usada', 4),('Velha', 4);"

        database?.execSQL(SQL)

        SQL = "CREATE TABLE tbl_peca("+
              "_id_peca INTEGER PRIMARY KEY AUTOINCREMENT,"+
              "nome TEXT," +
              "descricao TEXT," +
              "id_categoria INT," +
              "imagem BLOB," +
              "FOREIGN KEY(id_categoria) REFERENCES tbl_categoria(_id_categoria));"

        database?.execSQL(SQL)

        SQL = "CREATE TABLE tbl_notificacao(" +
              "_id_notificacao INTEGER PRIMARY KEY AUTOINCREMENT,"+
              "titulo TEXT," +
              "data INT," +
              "local TEXT," +
              "descricao TEXT);"

        database?.execSQL(SQL)

        SQL = "CREATE TABLE tbl_peca_filtro(" +
              "_id_peca_filtro INTEGER PRIMARY KEY AUTOINCREMENT,"+
              "id_peca INT," +
              "id_filtro INT," +
              "FOREIGN KEY(id_peca) REFERENCES tbl_peca(_id_peca)," +
              "FOREIGN KEY(id_filtro) REFERENCES tbl_filtro(_id_filtro));"

        database?.execSQL(SQL)

        SQL = "CREATE TABLE tbl_peca_notificacao(" +
                "_id_peca_notificacao INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "id_peca INT," +
                "id_notificacao INT," +
                "FOREIGN KEY(id_peca) REFERENCES tbl_peca(_id_peca)," +
                "FOREIGN KEY(id_notificacao) REFERENCES tbl_filtro(_id_notificacao));"

        database?.execSQL(SQL)

    }

    override fun onUpgrade(database: SQLiteDatabase?, p1: Int, p2: Int) {

        onCreate(database)

    }

}