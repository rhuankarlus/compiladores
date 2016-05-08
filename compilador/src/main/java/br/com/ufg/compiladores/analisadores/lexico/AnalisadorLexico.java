package br.com.ufg.compiladores.analisadores.lexico;

import br.com.ufg.compiladores.estados.Estado;
import br.com.ufg.compiladores.estados.EstadoHandler;
import br.com.ufg.compiladores.inicializadores.TiposDeTokens;
import br.com.ufg.compiladores.inicializadores.Tokens;
import br.com.ufg.compiladores.tabelas.TabelaDeTransicao;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
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

    private Tokens tokenComentario;
    private Boolean isTokenComentarioLido;

    private Tokens tokenAspas;
    private Boolean isTokenAspasLido;

    private Simbolo ultimoSimboloLido;

    private List<Simbolo> tabelaDeSimbolos;

    private EstadoHandler estadoHandler;

    public static AnalisadorLexico getInstance() {
        if (instancia == null) {
            instancia = new AnalisadorLexico();
            instancia.resetarAnalisador();
        }
        return instancia;
    }

    private AnalisadorLexico() {
    }

    public Tokens getProximoToken() {

        Estado estadoAtual = TABELA_DE_TRANSICAO.getEstadoInicial();
        Tokens token = null;
        String lexema = "";
        TiposDeTokens tipo = TiposDeTokens.NENHUM;

        boolean isNecessarioIncrementarAPonteira = true;

        // caso o usuário chame o Léxico sem ter um arquivo de código fonte...
        // evita erros do programador, na verdade xD
        if (codigoFonte == null) {
            final String errorMsg = "O Analisador L\u00e9xico foi chamado mas n\u00e3o h\u00e1 c\u00f3igo fonte inserido.";
            LOG.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }

        // caso seja a primeira iteração não haverão caracteres na linha
        if (caracteresNaLinha == null) {
            caracteresNaLinha = linhas.get(ultimaLinhaLida).toCharArray();
        }

        // caso não haja mais linhas a se ler retorna o EOF e reseta o estado do analisador léxico
        if (ultimoCaractereLido >= caracteresNaLinha.length && (ultimaLinhaLida >= linhas.size() || ultimaLinhaLida == linhas.size() - 1)) {
            // resetando o estado do analisador para ler o próximo código fonte
            resetarAnalisador();

            // retornando o token EOF
            token = Tokens.EOF;
            exibirDadosDoToken(token, "", TiposDeTokens.NENHUM);
            return token;
        }

        // caso o último caractere na linha tenha sido lido é necessário pegar a próxima linha
        if (ultimoCaractereLido >= caracteresNaLinha.length) lerProximaLinha();

        // pegando o próximo estado a partir do estado atual
        Character proximoCaractere = lerProximoCaractere();
        lexema += proximoCaractere;
        Estado proximoEstado = estadoHandler.getProximoEstado(estadoAtual, proximoCaractere);

        // para o primeiro estado a primeira iteração se torna o estado atual
        if (proximoEstado != null) estadoAtual = proximoEstado;

        // verificando se o próximo estado é final ou não
        Boolean isProximoEstadoFinal = proximoEstado != null && proximoEstado.isFinal();
        if (isProximoEstadoFinal) {
            while (isProximoEstadoFinal) {
                estadoAtual = proximoEstado;
                proximoCaractere = lerProximoCaractere();
                proximoEstado = estadoHandler.getProximoEstado(proximoEstado, proximoCaractere);
                isProximoEstadoFinal = proximoEstado != null && proximoEstado.isFinal();
                if (isProximoEstadoFinal) lexema += proximoCaractere;
            }
            // se ainda não estiver no fim da linha então volta o último caractere lido
            // para que todos os caracteres possam ser lidos
            if (ultimoCaractereLido < caracteresNaLinha.length) ultimoCaractereLido--;
        }

        // tratando aspas - formando literais
        // E07 : aspas - literal
        if (estadoAtual.getId().equals("E07")) {
            tipo = TiposDeTokens.LITERAL;

            // E08 : fecha aspas - fim do literal
            while (estadoAtual != null && !estadoAtual.getId().equals("E08")) {
                estadoAtual = proximoEstado;
                proximoCaractere = lerProximoCaractere();
                proximoEstado = estadoHandler.getProximoEstado(proximoEstado, proximoCaractere);
                if (estadoAtual != null && !estadoAtual.getId().equals("E08")) lexema += proximoCaractere;
            }
        }

        // tratando comentario - ignorando
        // E10 : abre chaves - comentario
        if (estadoAtual != null && estadoAtual.getId().equals("E10")) {
            // E00 : quando o comentário finaliza voltamos ao estado inicial
            while (estadoAtual != null && !estadoAtual.getId().equals("E00")) {
                estadoAtual = proximoEstado;
                proximoCaractere = lerProximoCaractere();
                // se leu vazio é porque tem mais de uma linha comentada
                if (proximoCaractere.equals(Character.MIN_VALUE)) lerProximaLinha();
                proximoEstado = estadoHandler.getProximoEstado(proximoEstado, proximoCaractere);
            }
            token = Tokens.COMENTARIO;
        }

        // tratando identificadores
        // E09 : letra - quando ler uma letra pode ler outra letra ou um dígito qualquer formando um identificador
        if (estadoAtual != null && estadoAtual.getId().equals("E09")) {
            // E08 : fecha aspas - fim do literal
            while (proximoEstado != null && proximoEstado.getId().equals("E09")) {
                estadoAtual = proximoEstado;
                proximoCaractere = lerProximoCaractere();
                proximoEstado = estadoHandler.getProximoEstado(proximoEstado, proximoCaractere);
                if (proximoEstado != null && proximoEstado.getId().equals("E08")) lexema += proximoCaractere;
            }
        }

        // tratando identificadores
        // E01 : número - quando ler um número ele pode ser inteiro ou real
        if (estadoAtual != null && estadoAtual.getId().equals("E01")) {
            // pode-se pegar o próximo caractere visto que o '.' já foi adicionado anteriormente
            lexema += proximoCaractere;
            ultimoCaractereLido++;

            // tratando o tipo de numero
            tipo = tipo == TiposDeTokens.NENHUM ? TiposDeTokens.INTEIRO : tipo;
            if (proximoEstado != null && proximoEstado.getId().equals("E02")) tipo = TiposDeTokens.REAL;

            // E08 : fecha aspas - fim do literal
            while (
                    proximoEstado != null &&
                            (proximoEstado.getId().equals("E01") ||
                                    proximoEstado.getId().equals("E02") ||
                                    proximoEstado.getId().equals("E03") ||
                                    proximoEstado.getId().equals("E04") ||
                                    proximoEstado.getId().equals("E05") ||
                                    proximoEstado.getId().equals("E06")
                            )
                    ) {
                estadoAtual = proximoEstado;
                proximoCaractere = lerProximoCaractere();
                proximoEstado = estadoHandler.getProximoEstado(proximoEstado, proximoCaractere);
                lexema += proximoCaractere;
            }
        }

        // verificando se o estado atual é final ou não
        if (estadoAtual != null && estadoAtual.isFinal()) {
            token = Tokens.getTokenDoEstadoFinal(estadoAtual.getId());
            exibirDadosDoToken(token, lexema, tipo);
            return token;
        }

        // como não é um estado final pode ser que o token retornado
        // seja para um comentário, espaço, final de linha, etc...
        token = token == null ? Tokens.getTokenPeloSimbolo(proximoCaractere) : token;
        exibirDadosDoToken(token, lexema, tipo);

        return token;
    }

    private void exibirDadosDoToken(Tokens token, String lexema, TiposDeTokens tipo) {
        if (token != Tokens.ESPACO && token != Tokens.COMENTARIO) {
            LOG.info("TOKEN reconhecido: [ " + token.getTokenId() + " | " + lexema + " | " + tipo.getTipo() + " ]");
        }
    }

    private Character lerProximoCaractere() {
        // se for o último caractere lido já retorna vazio
        if (ultimoCaractereLido >= caracteresNaLinha.length) return Character.MIN_VALUE;

        // pegando o próximo caractere no código fonte
        final Character caractere = caracteresNaLinha[ultimoCaractereLido];
        ultimoCaractereLido++;
        return caractere;
    }

    private void lerProximaLinha() {
        if (ultimaLinhaLida < linhas.size() - 1) {
            ultimaLinhaLida++;
            caracteresNaLinha = linhas.get(ultimaLinhaLida).toCharArray();
            ultimoCaractereLido = 0;
        }
    }

    private void resetarAnalisador() {
        ultimaLinhaLida = 0;
        ultimoCaractereLido = 0;
        tabelaDeSimbolos = new ArrayList<>();
        isTokenComentarioLido = false;
        isTokenAspasLido = false;
        estadoHandler = new EstadoHandler();
        caracteresNaLinha = null;
        linhas = null;
    }

    private Character concatenarEnquantoForNumero(Character caractere,
                                                  StringBuilder stringBuilder) {
        while (Character.isDigit(caractere)) {
            caractere = concatenarEIterar(caractere, stringBuilder);
        }
        return caractere;
    }

    private Character concatenarEIterar(Character caractere,
                                        StringBuilder stringBuilder) {
        stringBuilder.append(caractere);
        ultimoCaractereLido++;
        caractere = ultimoCaractereLido != caracteresNaLinha.length ? caracteresNaLinha[ultimoCaractereLido]
                : ' ';
        return caractere;
    }

    /**
     * Verifica se o caractere lido nÃ£o Ã© vazio e nem quebra de linha e se Ã©
     * uma LETRA, um DÃ�GITO ou o 'underline'
     *
     * @param letra Letra que serÃ¡ verificada
     * @return <b>true</b> caso a letra se enquadre nos requisitos de um
     * identificador ou <b>false</b> em caso contrÃ¡rio
     */
    private boolean isCaractereAceitavel(Character letra) {
        return !letra.toString().replaceAll(" ", "").equals("")
                && !letra.toString().replaceAll(" ", "")
                .equals(System.getProperty("line.separator"))
                && (Character.isAlphabetic(letra) || Character.isDigit(letra) || letra
                .equals('_'));
    }

    public void setCodigoFonte(File codigoFonte) {
        this.codigoFonte = codigoFonte;
        try {
            this.linhas = Files.readAllLines(this.codigoFonte.toPath());
        } catch (IOException e) {
            final String errorMsg = "NÃ£o consegui ler as linhas do arquivo ["
                    + codigoFonte.getAbsolutePath() + "]";
            LOG.error(errorMsg, e);
            throw new RuntimeException(errorMsg);
        }
    }

    public List<Simbolo> getTabelaDeSimbolos() {
        return tabelaDeSimbolos;
    }
}
