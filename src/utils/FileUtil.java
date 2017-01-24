package utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtil {

	public static void writeToFile(String fileName, String content) {
		BufferedOutputStream bufferedOutput;
		try {
			bufferedOutput = new BufferedOutputStream(new FileOutputStream(fileName));
			bufferedOutput.write(content.getBytes());
			bufferedOutput.close();
			// System.out.println("Write to file Success");
		} catch (IOException e) {
			System.out.println("Write to file Error");
		}
	}

	public static void writeToFile(String fileName, Object object) {
		try {
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(object);
			out.close();
			fileOut.close();
			System.out.println("Object is saved in: " + fileName);
		} catch (IOException i) {
			System.out.println("Write object Error");
		}
	}

	public static Object readFromFile(String fileName) {
		try {
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Object object = in.readObject();
			in.close();
			fileIn.close();
			System.out.println("Read object success");
			return object;
		} catch (Exception e) {
			System.out.println("Read object fail");
			return null;
		}
	}

	public static void appendToFile(String fileName, String content) {
		try {
			File tmp = new File(fileName);
			tmp.createNewFile();
			Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			// exception handling left as an exercise for the reader
			e.printStackTrace();
		}
	}

}
