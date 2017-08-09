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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RunHH02 {

	private static final String FILENAME_CAMPAIGN = "g:\\temp\\15\\hh\\01\\campaign.txt";
	private static final String FILENAME_INPUTS = "g:\\temp\\15\\hh\\01\\input.txt";

	public static void main(String[] args) {
		List<Campaign> campaignsInit;
		try {
			campaignsInit = FileReaderUtils.readCampaigns(FILENAME_CAMPAIGN);
		} catch (IOException e) {
			System.out.println("Can't read campaign file");
			return;
		}
		Map<Integer, List<Campaign>> campaigns = new HashMap(50000, 0.75f);
		for (Campaign campaign : campaignsInit) {
			for (Integer segment : campaign.getSegments()) {
				if (!campaigns.containsKey(segment)) {
					campaigns.put(segment, new LinkedList<Campaign>());
				}
				campaigns.get(segment).add(campaign);
			}
		}
		
		StringBuffer results = new StringBuffer("");
		AtomicInteger counter = new AtomicInteger(0);
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME_INPUTS))) {
			for (String line; (line = br.readLine()) != null;) {
				List<Integer> inputSegments = FileReaderUtils.readInput(line);

				Map<Campaign, Integer> campaign2Sum = new HashMap();
				for (Integer inputSegment : inputSegments) {
					if (!campaigns.containsKey(inputSegment)) {
						continue;
					}
					for (Campaign campaign : campaigns.get(inputSegment)) {
						if (campaign2Sum.containsKey(campaign)) {
							campaign2Sum.put(campaign, campaign2Sum.get(campaign) + 1);
						} else {
							campaign2Sum.put(campaign, 1);
						}
					}
				}
				Optional<Entry<Campaign, Integer>> entry = campaign2Sum.entrySet().stream().collect(Collectors.maxBy((campaign1, campaign2) -> campaign1.getValue().compareTo(campaign2.getValue())));
				if (entry.isPresent()) {
					results.append("input: " + inputSegments + "\r\n");
					if (entry.get().getValue() == 0) {
						results.append("no campaign\r\n");
					} else {
						results.append("campaign: " + entry.get().getKey().getTitle() + " " + entry.get().getKey().getSegments() + " value=" + entry.get().getValue() + "\r\n");
					}
					if (counter.incrementAndGet() % 1000 == 0) {
						System.out.println(results.toString());
						results = new StringBuffer("");
						System.out.println(new Date() + " counter: " + counter.get());
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Can't read input file");
			return;
		}
	}

}
