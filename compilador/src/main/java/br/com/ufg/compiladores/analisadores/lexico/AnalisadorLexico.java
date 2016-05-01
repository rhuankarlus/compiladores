package br.com.ufg.compiladores.analisadores.lexico;

import br.com.ufg.compiladores.inicializadores.Tokens;
import br.com.ufg.compiladores.tabelas.TabelaDeTransicao;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by Rhuan on 15/04/2016.
 */
public class AnalisadorLexico {

    private static final Logger LOG = Logger.getLogger(AnalisadorLexico.class);
    private static final TabelaDeTransicao TABELA_DE_TRANSICAO = TabelaDeTransicao.getInstancia();

    private static AnalisadorLexico instancia;

    private File codigoFonte;
    private List<String> linhas;
    private Integer ultimaLinhaLida;

    private char[] caracteresNaLinha;
    private Integer ultimoCaractereLido;

    public static AnalisadorLexico getInstance() {
        if (instancia == null) {
            instancia = new AnalisadorLexico();
            instancia.ultimaLinhaLida = 0;
            instancia.ultimoCaractereLido = 0;
        }
        return instancia;
    }

    private AnalisadorLexico() {
    }

    public Tokens getProximoToken() {

        // caso o usuário chame o Léxico sem ter um arquivo de código fonte...
        // evita erros do programador, na verdade xD
        if (codigoFonte == null) {
            final String errorMsg = "O Analisador Léxico foi chamado mas não há código fonte inserido.";
            LOG.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }

        // caso seja a primeira iteração não haverão caracteres na linha
        if (caracteresNaLinha == null) {
            caracteresNaLinha = linhas.get(ultimaLinhaLida).toCharArray();
        }

        // caso não haja mais linhas a se ler retorna o EOF e reseta o estado do analisador léxico
        if (ultimoCaractereLido == caracteresNaLinha.length && ultimaLinhaLida == linhas.size()) {
            ultimoCaractereLido = 0;
            ultimaLinhaLida = 0;
            caracteresNaLinha = null;
            linhas = null;
            LOG.info("TOKEN reconhecido: [ " + Tokens.EOF.toString() + " ]");
            return Tokens.EOF;
        }

        // caso o último caractere na linha tenha sido lido é necessário pegar a próxima linha
        if (ultimoCaractereLido == caracteresNaLinha.length) {
            caracteresNaLinha = linhas.get(ultimaLinhaLida).toCharArray();
            ultimoCaractereLido = 0;
            ultimaLinhaLida++;
        }

        // lendo um token e incrementando a ponteira de leitura da linha
        Tokens token = Tokens.getTokenPeloSimbolo(caracteresNaLinha[ultimoCaractereLido] + "");
        ultimoCaractereLido++;

        // o tratamento de erro deve ser feito aqui caso nenhum token seja encontrado para o símbolo
        if (token == null) {
            return null;
        }

        LOG.info("TOKEN reconhecido: [ " + token.toString() + " ]");
        return token;
    }

    public void setCodigoFonte(File codigoFonte) {
        this.codigoFonte = codigoFonte;
        try {
            this.linhas = Files.readAllLines(this.codigoFonte.toPath());
        } catch (IOException e) {
            final String errorMsg = "Não consegui ler as linhas do arquivo [" + codigoFonte.getAbsolutePath() + "]";
            LOG.error(errorMsg, e);
            throw new RuntimeException(errorMsg);
        }
    }
}
