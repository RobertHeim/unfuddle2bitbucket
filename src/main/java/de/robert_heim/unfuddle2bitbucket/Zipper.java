package de.robert_heim.unfuddle2bitbucket;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {
	private static final String DBJSON_FILE = "db-1.0.json";

	public static void createZipArchive(String archiveFile, String jsonData) {

		byte[] buffer = new byte[1024];

		try {

			FileOutputStream fos = new FileOutputStream(archiveFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			ZipEntry ze = new ZipEntry(DBJSON_FILE);
			zos.putNextEntry(ze);

			InputStream in = new ByteArrayInputStream(
					jsonData.getBytes(StandardCharsets.UTF_8));

			int len;
			while ((len = in.read(buffer)) > 0) {
				zos.write(buffer, 0, len);
			}

			in.close();

			zos.closeEntry();
			zos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
