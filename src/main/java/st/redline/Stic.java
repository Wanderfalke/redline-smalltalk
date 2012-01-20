/* Redline Smalltalk, Copyright (c) James C. Ladd. All rights reserved. See LICENSE in the root of this distribution */
package st.redline;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Stic {

	public static void main(String[] args) throws Exception {
		invokeWith(args);
	}

	public static PrimObject invokeWith(String[] args) throws Exception {
		CommandLine commandLine = createCommandLineWith(args);
		if (commandLine.haveNoArguments()) {
			commandLine.printHelp(new PrintWriter(System.out));
			return null;
		}
		String inputFilename = (String) commandLine.arguments().get(0);
		if (commandLine.executeNowRequested()) {
			inputFilename = writeInputCodeToTemporaryFile(commandLine).getName();
			inputFilename = inputFilename.substring(0, inputFilename.lastIndexOf("."));
		}
		return new Stic(commandLine).invoke(inputFilename);
	}

	static File writeInputCodeToTemporaryFile(CommandLine commandLine) throws Exception {
		File input = File.createTempFile("Tmp" + commandLine.hashCode(), ".st", new File(commandLine.userPath()));
		input.deleteOnExit();
		BufferedWriter out = new BufferedWriter(new FileWriter(input));
		try {
			out.write(commandLine.input());
			out.write("\n\n");
			out.flush();
		} finally {
			out.close();
		}
		return input;
	}

	static CommandLine createCommandLineWith(String[] args) {
		return new CommandLine(args);
	}

	Stic(CommandLine commandLine) throws ClassNotFoundException {
		initializeClassLoader(commandLine);
		bootstrap();
	}

	void bootstrap() throws ClassNotFoundException {
		classLoader().bootstrap();
	}

	void initializeClassLoader(CommandLine commandLine) {
		Thread.currentThread().setContextClassLoader(createClassLoader(commandLine));
	}

	ClassLoader createClassLoader(CommandLine commandLine) {
		return new SmalltalkClassLoader(Thread.currentThread().getContextClassLoader(), commandLine);
	}

	SmalltalkClassLoader classLoader() {
		return SmalltalkClassLoader.instance();
	}

	PrimObject invoke(String className) throws Exception {
		return (PrimObject) classLoader().loadClass(className).newInstance();
	}
}
