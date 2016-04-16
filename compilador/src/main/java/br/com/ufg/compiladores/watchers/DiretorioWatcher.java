package br.com.ufg.compiladores.watchers;

public class DiretorioWatcher implements Runnable {

	// tempo de espera para leitura do diretorio
	private static final Long TEMPO_DE_ESPERA = 2000L;

	public DiretorioWatcher(String diretorioMonitorado) {

	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(TEMPO_DE_ESPERA);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
