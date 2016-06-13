package br.com.ufg.compiladores.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.BsonBoolean;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * @author Rhuan Karlus
 * @since 10/06/2016
 */
public class TransicoesSintaticas {

    private static final String BANCO_URL = "localhost";
    private static final Integer BANCO_PORTA = 27017;

    private static final String DB_NAME = "compiladores";
    private static final String COLLECTION_NAME = "transicoes_sintaticas";

    private static MongoCollection colecaoTransicoes;

    // o bloco estático já carrega a conexão com o banco no início antes de executar qualquer método na classe
    static {
        // abrindo conexão com o MongoDB
        final MongoClient mongo = new MongoClient(BANCO_URL, BANCO_PORTA);

        // database que contém as coleções de transição sintática
        final MongoDatabase bancoCompilador = mongo.getDatabase(DB_NAME);

        // coleção de transições
        colecaoTransicoes = bancoCompilador.getCollection(COLLECTION_NAME);
    }

    public static Document getTransicao(String estadoAtual, String entrada) {

        // encontrando qual deve ser a transicao
        final FindIterable resultado = colecaoTransicoes.find(
                // estado de ID 3
                new BsonDocument("estado", new BsonString(estadoAtual))
                        // que possui campo entradas.leia
                        .append("entradas." + entrada, new BsonDocument("$exists", new BsonBoolean(true)))
        );

        // se não encontrar qualquer resultado significa que essa entrada não possui
        // transição para esse estado e o analisador sintático precisa retornar um erro
        if (resultado.first() == null) {
            return null;
        }

        // obtendo o documento encontrado no banco
        final Document documentoCompleto = (Document) resultado.first();
        final Document documentosDeEntrada = (Document) documentoCompleto.get("entradas");
        return (Document) documentosDeEntrada.get(entrada);
    }

    public static void salvar(Bson filtro, Bson documento) {
        colecaoTransicoes.updateOne(filtro, documento, new UpdateOptions().upsert(true));
    }

}
