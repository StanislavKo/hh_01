package com.cv.hh01stream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RunHH01 {

	private static final String FILENAME_CAMPAIGN = "g:\\temp\\15\\hh\\01\\campaign.txt";
	private static final String FILENAME_INPUTS = "g:\\temp\\15\\hh\\01\\input.txt";

	public static int compareCampaignsBySegments(List<Integer> campaign1, List<Integer> campaign2, List<Integer> segments) {
		Integer sum1 = segments.stream().reduce(0, (acc, value) -> acc + (campaign1.contains(value) ? 1 : 0));
//		System.out.println(campaign1 + " = " + sum1);
		Integer sum2 = segments.stream().reduce(0, (acc, value) -> acc + (campaign2.contains(value) ? 1 : 0));
//		System.out.println(campaign2 + " = " + sum2);
		return sum1.compareTo(sum2);
	}

	public static int getIntersection(List<Integer> campaign, List<Integer> segments) {
		return segments.stream().reduce(0, (acc, value) -> acc + (campaign.contains(value) ? 1 : 0));
	}

	public static void main(String[] args) {
		List<Campaign> campaigns;
		try {
			campaigns = FileReaderUtils.readCampaigns(FILENAME_CAMPAIGN);
		} catch (IOException e) {
			System.out.println("Can't read campaign file");
			return;
		}
		StringBuffer results = new StringBuffer("");
		AtomicInteger counter = new AtomicInteger(0);
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME_INPUTS))) {
			for (String line; (line = br.readLine()) != null;) {
				List<Integer> inputSegments = FileReaderUtils.readInput(line);

				Optional<Campaign> entry = campaigns.parallelStream().collect(Collectors.maxBy((campaign1, campaign2) -> compareCampaignsBySegments(campaign1.getSegments(), campaign2.getSegments(), inputSegments)));
				if (entry.isPresent()) {
					results.append("input: " + inputSegments + "\r\n");
					if (getIntersection(entry.get().getSegments(), inputSegments) == 0) {
						results.append("no campaign\r\n");
					} else {
						results.append("campaign: " + entry.get().getTitle() + " " + entry.get().getSegments() + " value=" + getIntersection(entry.get().getSegments(), inputSegments) + "\r\n");
					}
					if (counter.incrementAndGet() % 1000 == 0) {
						System.out.println(results.toString());
						results = new StringBuffer("");
						System.out.println(new Date() + " counter: " + counter.get());
						Collections.shuffle(campaigns);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Can't read input file");
			return;
		}
	}

}
