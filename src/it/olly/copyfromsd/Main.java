package it.olly.copyfromsd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.tools.FileObject;

public class Main {
	static final String sourcePath = "/Volumes/NO NAME/DCIM/Camera/";
	static final String destPath = "/Users/olly/temp/";
	public static void main(String[] args) {
		int toBeImported=0;
		List<String> errors = new ArrayList<String>();
		File sourceDir = new File(sourcePath);
		File destDir = new File(destPath);
		String[] sourceArr = sourceDir.list();
		List<String> destList = Arrays.asList(destDir.list());
		for (String f:sourceArr) {
			if (destList.contains(f)) {
				System.out.println(f);
			} else {
				toBeImported++;
				//copy source to dest
				try {
					System.out.println("NEW FILE TO BE IMPORTED ["+toBeImported+"] > "+f);
					Files.copy(Paths.get(sourcePath+f), Paths.get(destPath+f),StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					System.out.println("exceptions importing: "+f+". "+e.getMessage());
					errors.add(f);
				}
			}
		}
		try {
			FileOutputStream fout = new FileOutputStream(new File(destPath+"_log.txt"));
			fout.write(((new Date())+"\n").getBytes());
			for (String err:errors)
				fout.write((err+"\n").getBytes());
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("done "+toBeImported+" ("+errors.size()+" err)");
	}
}
