package com.jda.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {
	public static void main(String[] args) {
		FileUtil obj = new FileUtil();
		String movies = "dataset/movies.txt";
		Map<String,String> result = obj.load(movies, "\\|");
		System.out.println(result);
	}

	//returns first, second column of any file (assuming first column is id, second is name)
	public Map<String,String> load(String filename, String separator) {
		BufferedReader br = null;
		String line = "";
		Map<String, String> dataMap = new HashMap<String, String>();
		
		try {
			br = new BufferedReader(new FileReader(filename));
			while ((line = br.readLine()) != null) {
				String[] dataArray = line.split(separator);
				//System.out.println(dataArray[0] + "-" + dataArray[1]);
				dataMap.put(dataArray[0], dataArray[1]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dataMap;
	}
}