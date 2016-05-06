package br.com.ufg.compiladores.analisadores.lexico;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.ufg.compiladores.inicializadores.Tokens;
import br.com.ufg.compiladores.tabelas.TabelaDePalavrasReservadas;
import br.com.ufg.compiladores.tabelas.TabelaDeTransicao;

/**
 * Created by Rhuan on 15/04/2016.
 */
public class AnalisadorLexico {

	private static final Logger LOG = Logger.getLogger(AnalisadorLexico.class);
	private static final TabelaDeTransicao TABELA_DE_TRANSICAO = TabelaDeTransicao
			.getInstancia();

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

	public static AnalisadorLexico getInstance() {
		if (instancia == null) {
			instancia = new AnalisadorLexico();
			instancia.ultimaLinhaLida = 0;
			instancia.ultimoCaractereLido = 0;
			instancia.tabelaDeSimbolos = new ArrayList<>();
			instancia.isTokenComentarioLido = false;
			instancia.isTokenAspasLido = false;
		}
		return instancia;
	}

	private AnalisadorLexico() {
	}

	public Tokens getProximoToken() {

//		if (tokenAspas == Tokens.ASPAS) {
//			tokenAspas = null;
//			
//			Tokens tokenAux = getProximoToken();
//			while (tokenAux != Tokens.FECHA_CHAVES) {
//				tokenAux = getProximoToken();
//			}
//			
//			return tokenAspas;
//		}
		
		// ignorando tudo dentro dos comentários
		if (tokenComentario == Tokens.ABRE_CHAVES) {
			tokenComentario = null;

			Tokens tokenAux = getProximoToken();
			while (tokenAux != Tokens.FECHA_CHAVES) {
				tokenAux = getProximoToken();
			}

			tokenComentario = tokenAux;
			isTokenComentarioLido = false;

			return tokenComentario;
		}

		boolean isNecessarioIncrementarAPonteira = true;

		// caso o usuÃ¡rio chame o LÃ©xico sem ter um arquivo de cÃ³digo
		// fonte...
		// evita erros do programador, na verdade xD
		if (codigoFonte == null) {
			final String errorMsg = "O Analisador LÃ©xico foi chamado mas nÃ£o hÃ¡ cÃ³digo fonte inserido.";
			LOG.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}

		// caso seja a primeira iteraÃ§Ã£o nÃ£o haverÃ£o caracteres na linha
		if (caracteresNaLinha == null) {
			caracteresNaLinha = linhas.get(ultimaLinhaLida).toCharArray();
		}

		Simbolo simbolo = null;

		// caso nÃ£o haja mais linhas a se ler retorna o EOF e reseta o estado
		// do analisador lÃ©xico
		if (ultimoCaractereLido >= caracteresNaLinha.length
				&& ultimaLinhaLida >= linhas.size()) {
			ultimoCaractereLido = 0;
			ultimaLinhaLida = 0;
			caracteresNaLinha = null;
			linhas = null;

			simbolo = new Simbolo(Tokens.EOF, "", "");
			tabelaDeSimbolos.add(tabelaDeSimbolos.size(), simbolo);

			LOG.info("TOKEN reconhecido: [ " + Tokens.EOF.toString() + " ]");
			return Tokens.EOF;
		}

		// caso o Ãºltimo caractere na linha tenha sido lido Ã© necessÃ¡rio
		// pegar a prÃ³xima linha
		if (ultimoCaractereLido >= caracteresNaLinha.length) {
			caracteresNaLinha = linhas.get(ultimaLinhaLida).toCharArray();
			ultimoCaractereLido = 0;
			ultimaLinhaLida++;
		}

		// lendo um token e incrementando a ponteira de leitura da linha
		Tokens token = Tokens
				.getTokenPeloSimbolo(caracteresNaLinha[ultimoCaractereLido]
						+ "");

		// tratando comentários
		if (token == Tokens.ABRE_CHAVES) {
			tokenComentario = token;
			isTokenComentarioLido = true;
		}

		// tratando literais
//		if (token == Tokens.ASPAS) {
//			tokenAspas = token;
//			isTokenAspasLido = true;
//		}

		// o tratamento de erro deve ser feito aqui caso nenhum token seja
		// encontrado para o sÃ­mbolo
		// nesse ponto pode acontecer de o token ser NULL porque foi encontrado
		// um numero ou identificador
		// ou simplesmente aconteceu um erro
		if (token == null) {
			if (Character.isDigit(caracteresNaLinha[ultimoCaractereLido])) {
				// se for um numero seguirÃ¡ por estados diferentes
				Character numero = caracteresNaLinha[ultimoCaractereLido];
				final StringBuilder sb = new StringBuilder();

				// procura formar a palavra completa
				numero = concatenarEnquantoForNumero(numero, sb);

				// verificando se foi inserido um ponto flutuante
				if (ultimoCaractereLido != caracteresNaLinha.length
						&& numero.equals('.')) {
					numero = concatenarEIterar(numero, sb);
				}

				// verificando se depois do ponto flutuante foram inseridos mais
				// nÃºmeros
				if (ultimoCaractereLido != caracteresNaLinha.length
						&& Character.isDigit(numero)) {
					numero = concatenarEnquantoForNumero(numero, sb);
				}

				// aqui jÃ¡ teremos o nÃºmero completo
				final String numeroCompleto = sb.toString();

				// salvando o token de numeral
				token = Tokens.NUMERO;
				simbolo = new Simbolo(token, numeroCompleto, "");

				// a ponteira jÃ¡ foi incrementada
				isNecessarioIncrementarAPonteira = false;

			} else if (Character
					.isAlphabetic(caracteresNaLinha[ultimoCaractereLido])) {
				// se for uma letra verifica se Ã© palavra interna ou
				// identificador
				Character letra = caracteresNaLinha[ultimoCaractereLido];
				final StringBuilder sb = new StringBuilder();

				// procura formar a palavra completa
				while (isCaractereAceitavel(letra)) {
					letra = concatenarEIterar(letra, sb);
				}

				// aqui jÃ¡ teremos a palavra completa
				final String palavra = sb.toString();

				// verificando se o TOKEN Ã© uma palavra reservada ou um
				// identificador
				token = TabelaDePalavrasReservadas.isPalavraReservada(palavra) ? Tokens.PALAVRA_RESERVADA
						: Tokens.IDENTIFICADOR;
				simbolo = new Simbolo(token, palavra, "");

				// a ponteira jÃ¡ foi incrementada
				isNecessarioIncrementarAPonteira = false;
			}
		}

		// incrementando a ponteira de leitura para o proximo caractere que
		// serÃ¡ lido
		if (isNecessarioIncrementarAPonteira)
			ultimoCaractereLido++;

		// tratando comentarios
		if (token == null && isTokenComentarioLido) {
			return Tokens.COMENTARIO;
		}

		// se o token chegou NULL aqui Ã© porque houve algum erro
		// e esse erro deve ser tratado parando o programa e exibindo a linha
		// e a coluna do mesmo
		if (token == null) {
			LOG.error("Um caractere estÃ¡ incorreto no arquivo ["
					+ codigoFonte.getName() + "]");
			LOG.error("Linha: " + ultimaLinhaLida + " | Coluna: "
					+ (ultimoCaractereLido + 1));
			throw new RuntimeException("Erro de compilaÃ§Ã£o.");
		}

		// adicionando o TOKEN encontrado ao simbolo
		simbolo = simbolo == null ? new Simbolo(token, token.getSimbolo(), "")
				: simbolo;

		// armazenando o último simbolo lido na memória
		ultimoSimboloLido = simbolo;

		// apenas adiciona os IDENTIFICADORES que jÃ¡ nÃ£o estejam contidos na
		// tabela
		if (token.equals(Tokens.IDENTIFICADOR)
				&& !tabelaDeSimbolos.contains(simbolo)
				&& !isTokenComentarioLido) {
			LOG.info("Adicionando o IDENTIFICADOR [ " + simbolo.getTipo()
					+ ": " + simbolo.getLexema() + " ]");
			tabelaDeSimbolos.add(tabelaDeSimbolos.size(), simbolo);
		}

		if (token != Tokens.ESPACO && token != Tokens.FECHA_CHAVES
				&& !isTokenComentarioLido && !isTokenAspasLido)
			LOG.info("TOKEN reconhecido: [ " + simbolo.getToken().toString()
					+ " | " + simbolo.getLexema() + " ]");
		return token;
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
	 * @param letra
	 *            Letra que serÃ¡ verificada
	 * @return <b>true</b> caso a letra se enquadre nos requisitos de um
	 *         identificador ou <b>false</b> em caso contrÃ¡rio
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
