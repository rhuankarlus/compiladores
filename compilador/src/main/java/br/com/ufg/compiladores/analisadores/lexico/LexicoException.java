package br.com.ufg.compiladores.analisadores.lexico;

import br.com.ufg.compiladores.analisadores.lexico.estados.Estado;
import org.apache.log4j.Logger;

/**
 * @author Rhuan Karlus
 * @since 09/05/2016
 */
public class LexicoException extends Exception {

    private static final Logger LOGGER = Logger.getLogger(LexicoException.class);

    public LexicoException(Estado estadoAtual, String entrada, Integer linha, Integer coluna) {

        StringBuilder errorMsgBuilder = new StringBuilder();
        errorMsgBuilder.append("A entrada [")
                .append(entrada)
                .append("] \u00e9 inv\u00e1lida!\n");

        switch (estadoAtual.getId()) {
            case "E01":
                errorMsgBuilder.append("Um n\u00famero segue o formado [0-9] ou [0-9].[0-9] ou [0-9].[0-9]E[0-9] ou [0-9]E[0-9] ou [0-9].[0-9]E(+,-)[0-9]\n");
                errorMsgBuilder.append("As entradas v\u00e1lidas s\u00e3o: '[0-9]', '.' e 'E'\n");
                break;
            case "E03":
                errorMsgBuilder.append("Um n\u00famero segue o formado [0-9] ou [0-9].[0-9] ou [0-9].[0-9]E[0-9] ou [0-9]E[0-9] ou [0-9].[0-9]E(+,-)[0-9]\n");
                errorMsgBuilder.append("As entradas v\u00e1lidas s\u00e3o: '[0-9]' e 'E'\n");
                break;
            case "E04":
                errorMsgBuilder.append("Um n\u00famero segue o formado [0-9] ou [0-9].[0-9] ou [0-9].[0-9]E[0-9] ou [0-9]E[0-9] ou [0-9].[0-9]E(+,-)[0-9]\n");
                errorMsgBuilder.append("As entradas v\u00e1lidas s\u00e3o: '[0-9]', '+' e '-'\n");
                break;
            case "E02":
            case "E05":
            case "E06":
                errorMsgBuilder.append("Um n\u00famero segue o formado [0-9] ou [0-9].[0-9] ou [0-9].[0-9]E[0-9] ou [0-9]E[0-9] ou [0-9].[0-9]E(+,-)[0-9]\n");
                errorMsgBuilder.append("As entradas v\u00e1lidas s\u00e3o: '[0-9]'\n");
                break;
            case "E07":
                errorMsgBuilder.append("O literal ficou incompleto. Um literal tem o formato: \"[.*]\"\n");
                break;
            case "E09":
                errorMsgBuilder.append("Um identificador possui o formado ([a-z][A-Z])([a-z][A-Z][0-9])*\n");
                errorMsgBuilder.append("As entradas v\u00e1lidas s\u00e3o: '[a-z][A-Z][0-9]'\n");
                break;
            case "E10":
                errorMsgBuilder.append("O coment\u00e1rio ficou incompleto. Um coment\u00e1rio tem o formato: {[.*]}\n");
                break;
            case "E12":
                errorMsgBuilder.append("Voc\u00ea deve finalizar cada linha com ponto e v\u00edrgula\n");
                errorMsgBuilder.append("As entradas v\u00e1lidas s\u00e3o: ';'\n");
                break;
            case "E13":
                errorMsgBuilder.append("Entrada incorreta informada.\n");
                errorMsgBuilder.append("As entradas v\u00e1lidas s\u00e3o: '-', '>', '='.\n");
                break;
            case "E17":
                errorMsgBuilder.append("Entrada incorreta informada.\n");
                errorMsgBuilder.append("As entradas v\u00e1lidas s\u00e3o: '='.\n");
                break;
            case "E20":
            case "E21":
            case "E22":
            case "E23":
                errorMsgBuilder.append("Ap\u00f3s um operador de compara\u00e7\u00e3o \u00e9 necess\u00e1rio informar um n\u00famero ou um identificador!.\n");
                errorMsgBuilder.append("As entradas v\u00e1lidas s\u00e3o: 'NUMERO' ou 'IDENTIFICADOR'.\n");
                break;
            default:
                errorMsgBuilder.append("A entrada [")
                        .append(entrada)
                        .append("] n\u00e3o \u00e9 reconhecida pela linguagem\n");
                break;
        }

        errorMsgBuilder.append("Linha: ").append(linha).append("\n");
        errorMsgBuilder.append("Coluna: ").append(coluna).append("\n");

        LOGGER.error("Erro: " + errorMsgBuilder.toString());
        throw new RuntimeException(errorMsgBuilder.toString());
    }

}
