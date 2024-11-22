package com.inbox.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inbox.model.Folder;
import com.inbox.model.UnReadEmailStat;
import com.inbox.repo.UnReadEmailStatRepo;

@Service
public class FolderService {

	@Autowired
	UnReadEmailStatRepo unReadEmailStatRepo;

	public List<Folder> fecthDefaultFolders(String userId) {

		return Arrays.asList(new Folder(userId, "Inbox", "blue"), new Folder(userId, "Sent", "green"),
				new Folder(userId, "Important ", "red  ")

		);

	}

	public Map<String, Integer> mapCountToLabel(String userId) {

		List<UnReadEmailStat> countEmailStats = unReadEmailStatRepo.findAllById(userId);
		return countEmailStats.stream()
				.collect(Collectors.toMap(UnReadEmailStat::getLabel, UnReadEmailStat::getUnreadCount));
	}
}
