package br.com.ufg.compiladores.estados;

import br.com.ufg.compiladores.inicializadores.TiposDeTokens;
import br.com.ufg.compiladores.tabelas.TabelaDeTransicao;

/**
 * Created by Rhuan on 15/04/2016.
 */
public class EstadoHandler {

    private static final TabelaDeTransicao TABELA_DE_TRANSICAO = TabelaDeTransicao.getInstancia();

    public Estado getProximoEstado(Estado estadoAtual, Character entrada) {
        if (estadoAtual != null) {
            // tratando aspas ou comentário
            // E07 : aspas - literal
            // E10 : abre chaves - comentario
            if (estadoAtual.getId().equals("E07") || estadoAtual.getId().equals("E10")) {
                final String estadoId = estadoAtual.getProximosEstados().get(entrada + "");
                if (estadoId != null && !estadoId.equals("")) {
                    return TABELA_DE_TRANSICAO.getEstado(estadoId);
                }
                return estadoAtual;
            }

            // se for diferente de aspas ou comentario
            if (estadoAtual.getProximosEstados() != null) {
                final String estadoId = estadoAtual.getProximosEstados().get(entrada + "");
                if (estadoId != null && !estadoId.trim().equals("")) {
                    return TABELA_DE_TRANSICAO.getEstado(estadoId);
                }
            }

            // tratando identificadores e numeros
            if (estadoAtual.getId().equals("E00")) {
                // se estiver no primeiro estado

                if (Character.isDigit(entrada)) {
                    // E01 : numero
                    // o primeiro de um numero é o E01
                    return TABELA_DE_TRANSICAO.getEstado("E01");
                } else if (Character.isLetter(entrada)) {
                    // E09 : identificador
                    // o primeiro de um identificador é o E09
                    return TABELA_DE_TRANSICAO.getEstado("E09");
                }

            } else {
                // se já estiver no mínimo no segundo estado
                if (estadoAtual.getId().equals("E09")) {
                    // identificador
                    if (Character.isLetter(entrada) || Character.isDigit(entrada) || "_".equals(entrada.toString())) {
                        // o identificador pode conter letras e números
                        return estadoAtual;
                    }
                } else if (estadoAtual.getId().equals("E01")) {
                    // numero
                    if (Character.isDigit(entrada)) {
                        // continua o número inteiro
                        return estadoAtual;
                    }
                    final String estadoId = estadoAtual.getProximosEstados().get(entrada + "");
                    if (estadoId != null && !estadoId.trim().equals("")) {
                        return TABELA_DE_TRANSICAO.getEstado(estadoId);
                    }
                } else if (estadoAtual.getId().equals("E02")) {
                    // numero com ponto flutuante
                    if (Character.isDigit(entrada)) {
                        // continua o número real
                        return TABELA_DE_TRANSICAO.getEstado("E03");
                    }
                } else if (estadoAtual.getId().equals("E03")) {
                    if (Character.isDigit(entrada)) {
                        // continua o número real
                        return TABELA_DE_TRANSICAO.getEstado("E03");
                    } else if ("E".equals(entrada.toString())) {
                        // eleva o numero a alguma potencia
                        return TABELA_DE_TRANSICAO.getEstado("E04");
                    }
                } else if (estadoAtual.getId().equals("E04")) {
                    // o numero será elevado a alguma potencia
                    if (Character.isDigit(entrada)) {
                        // continua o número real
                        return TABELA_DE_TRANSICAO.getEstado("E06");
                    } else if ("+".equals(entrada.toString()) || "-".equals(entrada.toString())) {
                        // eleva o numero a alguma potencia
                        return TABELA_DE_TRANSICAO.getEstado("E05");
                    }
                } else if (estadoAtual.getId().equals("E05")) {
                    // o numero será elevado a alguma potencia
                    if (Character.isDigit(entrada)) {
                        // continua o número real
                        return TABELA_DE_TRANSICAO.getEstado("E06");
                    }
                } else if (estadoAtual.getId().equals("E06")) {
                    // o numero foi elevado a alguma potencia
                    if (Character.isDigit(entrada)) {
                        // continua o número real
                        return TABELA_DE_TRANSICAO.getEstado("E06");
                    }
                }
            }
        }
        return null;
    }

}
