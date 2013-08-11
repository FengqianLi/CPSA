package utils;

import java.io.IOException;

import org.slf4j.LoggerFactory;

import protocol.Config;

import ch.qos.logback.classic.Logger;

public class Executor {
	private final static Logger logger = (Logger) LoggerFactory
			.getLogger("Executor");

	public static void execute(int sId) {

		String[] cmd = { "/bin/sh", "-c",
				Config.prop.getProperty("run_path") + " " + sId };
		logger.debug(Config.prop.getProperty("run_path") + " " + sId);
		try {
			Process proc = Runtime.getRuntime().exec(cmd);

			StreamGobbler errorGobbler = new StreamGobbler(
					proc.getErrorStream(), "ERROR");

			// kick off stderr
			errorGobbler.start();

			StreamGobbler outGobbler = new StreamGobbler(proc.getInputStream(),
					"STDOUT");
			// kick off stdout
			outGobbler.start();

			// BufferedReader in = new BufferedReader(new InputStreamReader(
			// proc.getInputStream()));
			// String line;
			// while ((line = in.readLine()) != null) {
			// System.out.println(line);
			// logger.debug(line);
			// }
			proc.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
