package br.com.ufg.compiladores.analisadores.sintatico;

import br.com.ufg.compiladores.analisadores.lexico.AnalisadorLexico;
import br.com.ufg.compiladores.analisadores.lexico.LexicoException;
import br.com.ufg.compiladores.analisadores.sintatico.estados.Estado;
import br.com.ufg.compiladores.mongodb.TransicoesSintaticas;
import br.com.ufg.compiladores.tokens.Tokens;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.io.File;
import java.util.Stack;

/**
 * Created by rhuan on 30/04/2016.
 */
public class AnalisadorSintatico {

    private static final Object LOCK = new Object();
    private static final Logger LOG = Logger.getLogger(AnalisadorSintatico.class);

    private static AnalisadorSintatico instance;

    private Stack<String> pilhaSintatica;
    private AnalisadorLexico analisadorLexico;

    public static AnalisadorSintatico getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new AnalisadorSintatico();
                instance.pilhaSintatica = new Stack<>();
            }
        }
        return instance;
    }


    /**
     * Analisa um determinado arquivo encontrado pelo watcher de diretório.
     *
     * @param arquivo Arquivo encontrado pelo Watcher de diretórios
     */
    public Boolean analisarArquivo(File arquivo) {
        LOG.info("Iniciando an\u00e1lise.");

        analisadorLexico = AnalisadorLexico.getInstance();
        analisadorLexico.resetarAnalisador();
        analisadorLexico.setCodigoFonte(arquivo);

        // começando no estado inicial
        Estado estadoAtual = new Estado();

        // inicializando a pilha
        pilhaSintatica.clear();
        pilhaSintatica.push(estadoAtual.getNumero());

        // pegando o primeiro elemento reconhecido pelo lexico
        Tokens ip = getProximoToken(analisadorLexico, null);
        while (true) {

            estadoAtual.setNumero(pilhaSintatica.peek()); // S agora eh o elemento ao topo da pilha
            final Document transicao = TransicoesSintaticas.getTransicao(estadoAtual.getNumero(), ip.getTokenId());
            final String acao = transicao != null ? (String) transicao.get("acao") : null;

            if (acao == null) {
//                displayError("Nenhuma a\u00e7\u00e3o encontrada do estado [" + estadoAtual.getNumero() + "] para a entrada [" + ip.getTokenId() + "].");
                displayError("Erro na linha [" + (analisadorLexico.getUltimaLinhaLida() + 1) + "] em torno de [" + ip.getTokenId() + "].");
            }

            switch (acao) {
                case "shift":
                    shift(ip, transicao);
                    ip = getProximoToken(analisadorLexico, ip);
                    break;

                case "reduce":
                    reduce(estadoAtual, transicao);
                    break;

                case "acc":
                    LOG.info("O arquivo foi analisado.");
                    return true;
            }

            if (ip == Tokens.EOF) {
                ip.setTokenId("#");
            }
        }

    }

    private Tokens getProximoToken(AnalisadorLexico analisadorLexico, Tokens ip) {
        // garantindo que o primeiro TOKEN encontrado e valido
        do {
            try {
                ip = analisadorLexico.getProximoToken();
            } catch (LexicoException e) {
                e.printStackTrace();
            }
        } while (ip == null || ip == Tokens.COMENTARIO || ip == Tokens.ESPACO);
        return ip;
    }

    private void shift(Tokens ip, Document transicao) {
        pilhaSintatica.push(ip.getTokenId()); // empilhando o simbolo que acaba de ser lido
        pilhaSintatica.push((String) transicao.get("desvio")); // empilhando o desvio
    }

    private void reduce(Estado estadoAtual, Document transicao) {
        // desempilhando a quantidade de tokens a direita da produção
        final Integer quantidadeDeTokensADireitaDaProducao = 2 * Integer.valueOf((String) transicao.get("quantidadeDeTokensProducaoDireita"));
        for (int i = 0; i < quantidadeDeTokensADireitaDaProducao; i++) {
            pilhaSintatica.pop();
        }

        // S' agora eh o elemento ao topo da pilha
        estadoAtual.setNumero(pilhaSintatica.peek());

        // empilhando o elemento a esquerda da producao na pilha
        pilhaSintatica.push((String) transicao.get("producaoEsquerda"));

        // empilhando o desvio, ou seja, a transicao do estado anterior com o TERMINAL que acaba de ser inserido na pilha
        final Document novaTransicao = TransicoesSintaticas.getTransicao(estadoAtual.getNumero(), pilhaSintatica.peek());
        if (novaTransicao == null) { // caso haja algum erro no codigo fonte nem termina o reduce
//            displayError("REDUCE: Nenhuma a\u00e7\u00e3o encontrada do estado [" + estadoAtual.getNumero() + "] para a entrada [" + pilhaSintatica.peek() + "].");
            displayError("Erro na linha [" + analisadorLexico.getUltimaLinhaLida() + "] em torno de [" + pilhaSintatica.peek() + "].");
        }
        pilhaSintatica.push((String) novaTransicao.get("desvio"));

        // escrevendo a producao atraves da qual houve a redução
        System.out.println(Producoes.fromNumero((String) transicao.get("producaoNumero")).getProducao());
    }

    private void displayError(String descricao) {
        throw new RuntimeException(descricao);
    }
}
