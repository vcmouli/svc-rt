package com.jda.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {
	private static final String ONE_MILLION_DATASET_PATH = "dataset/01-million";
	private static final String DATASET_PATH = ONE_MILLION_DATASET_PATH;
	public static final String MOVIES_FILE_PATH = DATASET_PATH + "/movies.txt";
	public static final String RATINGS_FILE_PATH = DATASET_PATH + "/ratings.txt";

	public static void main(String[] args) {
		FileUtil obj = new FileUtil();
		Map<String,String> result = obj.load(MOVIES_FILE_PATH, "\\|");
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