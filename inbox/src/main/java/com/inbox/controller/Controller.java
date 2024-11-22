package com.inbox.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.inbox.model.EmailListItem;
import com.inbox.model.Folder;
import com.inbox.repo.EmailListRepo;
import com.inbox.repo.EmailRepo;
import com.inbox.repo.FolderRepo;
import com.inbox.repo.UnReadEmailStatRepo;
import com.inbox.service.EmailService;
import com.inbox.service.FolderService;

import jakarta.annotation.PostConstruct;

@org.springframework.stereotype.Controller
public class Controller {

	@Autowired
	EmailListRepo emailListRepo;
	@Autowired
	EmailRepo emailRepo;
	@Autowired
	FolderRepo folderRepo;

	@Autowired
	FolderService folderService;

	@Autowired
	EmailService emailService;

	@Autowired
	UnReadEmailStatRepo unReadEmailStatRepo;

	@GetMapping("/")
	public String getAllUsers(@AuthenticationPrincipal OAuth2User principal, Model model,

			@RequestParam(required = false) String folder) {

		if (principal == null || !org.springframework.util.StringUtils.hasText(principal.getAttribute("login"))) {

			return "index";

		}

		// fetch folders
		String userIdString = principal.getAttribute("token");

		List<Folder> usersFolders = folderRepo.findAllById("pranavrayapati");

		model.addAttribute("usersFolders", usersFolders);
		List<Folder> defaultFolders = folderService.fecthDefaultFolders("pranavrayapati");

		model.addAttribute("defaultFolders", defaultFolders);
		model.addAttribute("stats", folderService.mapCountToLabel("pranavrayapati"));
		model.addAttribute("name", "pranavrayapati");

		// System.out.print("reached");

		// fetch messages
		// String label = "Inbox";

		// if this is not added then we get error
		if (!StringUtils.hasText(folder)) {
			folder = "Inbox";
		}
		List<EmailListItem> emailListItems = emailListRepo.findAllByKey_IdAndKey_Label("pranavrayapati", folder);

		emailListItems.forEach(a -> a.toString());

		// this is used to calculate min ago
		PrettyTime p = new PrettyTime();

		emailListItems.stream().forEach(emailItems -> {

			UUID timeUuid = emailItems.getKey().getTimeUUID();

			Date date = new Date(Uuids.unixTimestamp(timeUuid));

			emailItems.setAgoTimeString(p.format(date));

		});

		model.addAttribute("emailList", emailListItems);
		model.addAttribute("folderName", folder);

		return "inbox-page";
	}

	@PostConstruct
	@Bean
	void save() {
		folderRepo.save(new Folder("pranavrayapati", "Family", "blue"));
		folderRepo.save(new Folder("pranavrayapati", "Good", "blue"));
		folderRepo.save(new Folder("pranavrayapati", "Bad    ", "blue"));

		unReadEmailStatRepo.incrementCounter("pranavrayapati", "Sent");

		for (int i = 0; i < 10; i++) {

			emailService.sendEmail("pranavrayapati", "hello", Arrays.asList("pranavrayapati", "abc  "), "body" + i);

		}

	}

}
