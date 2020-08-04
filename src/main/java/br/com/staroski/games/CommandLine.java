package br.com.staroski.games;
import java.io.*;

/**
 * Classe utilitária para facilitar a execução de linhas de comando em java
 *
 * @author Ricardo Artur Staroski
 */
public final class CommandLine {

	// classe interna utilizada para ler os stream de saida e de erro do processo
	private class StreamReader implements Runnable {

		private final InputStream stream;
		private final StringBuilder text;

		StreamReader(InputStream stream, StringBuilder text) {
			this.stream = stream;
			this.text = text;
		}

		@Override
		public void run() {
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			try {
				byte[] buffer = new byte[4096];
				for (int read = -1; (read = stream.read(buffer)) != -1; bytes.write(buffer, 0, read)) {}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				text.append(new String(bytes.toByteArray()));
			}
		}
	}

	private final String[] command;
	private final StringBuilder errorString;
	private final StringBuilder outputString;

	/**
	 * Instancia uma nova linha de comando
	 * @param exec O caminho do executável
	 * @param params Opcional, os argumentos do executável
	 */
	public CommandLine(String exec, String... params) {
		int args = 1 + params.length;
		command = new String[args];
		command[0] = exec;
		for (int i = 1; i < args; i++) {
			command[i] = params[i-1];
		}
		errorString = new StringBuilder();
		outputString = new StringBuilder();
	}

	/**
	 * Executa esta linha de comando
	 * @return O código de saída do processo
	 * @throws IOException
	 */
	public int execute() throws IOException {
		try {
			Process process = Runtime.getRuntime().exec(command);

			InputStream errors = process.getErrorStream();
			InputStream output = process.getInputStream();

			Thread errorsReader = new Thread(new StreamReader(errors, errorString));
			Thread outputReader = new Thread(new StreamReader(output, outputString));

			errorsReader.start();
			outputReader.start();

			return process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * @return o texto contendo as mensagens de erro do processo
	 */
	public String getError() {
		return errorString.toString();
	}

	/**
	 * @return o texto contendo as mensagens de saída do processo
	 */
	public String getOutput() {
		return outputString.toString();
	}
}