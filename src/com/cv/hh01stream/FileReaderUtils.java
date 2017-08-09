package com.cv.hh01stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class FileReaderUtils {

	public static List<Campaign> readCampaigns(String filename) throws IOException {
		Set<Campaign> campaignsSet = new HashSet();
		try (Stream<String> stream = Files.lines(Paths.get(filename))) {
			stream.forEach(line -> {
				String[] campParts = line.split("[\\s]+");
				List<Integer> segments = new LinkedList();
				for (int i = 1; i < campParts.length; i++) {
					segments.add(Integer.valueOf(campParts[i]));
				}
				campaignsSet.add(new Campaign(campParts[0], segments));
			});
		}
		return new ArrayList<Campaign>(campaignsSet);
	}
	
	public static List<Integer> readInput(String line) {
		String[] segmentParts = line.split("[\\s]+");
		List<Integer> segments = new LinkedList();
		for (int i = 1; i < segmentParts.length; i++) {
			segments.add(Integer.valueOf(segmentParts[i]));
		}
		return segments;
	}

}
