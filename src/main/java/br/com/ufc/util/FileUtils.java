package br.com.ufc.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

	public static void savePhoto(String path, MultipartFile photo) {
		File file = new File(path);
		try {
			org.apache.commons.io.FileUtils.writeByteArrayToFile(file, photo.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
