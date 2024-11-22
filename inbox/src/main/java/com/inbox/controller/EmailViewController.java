package com.inbox.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.inbox.model.Email;
import com.inbox.model.EmailListItem;
import com.inbox.model.EmailListItemKey;
import com.inbox.model.Folder;
import com.inbox.repo.EmailListRepo;
import com.inbox.repo.EmailRepo;
import com.inbox.repo.FolderRepo;
import com.inbox.repo.UnReadEmailStatRepo;
import com.inbox.service.FolderService;

@Controller
public class EmailViewController {

	@Autowired
	FolderRepo folderRepo;
	@Autowired
	FolderService folderService;
	@Autowired
	EmailListRepo emailListRepo;

	@Autowired
	EmailRepo emailRepo;

	@Autowired
	UnReadEmailStatRepo unReadEmailStatRepo;

	@GetMapping("/user/{id}")
	public String getAllUsers(@AuthenticationPrincipal OAuth2User principal, Model model, @PathVariable UUID id,
			@RequestParam String folder) {

		if (principal == null || !org.springframework.util.StringUtils.hasText(principal.getAttribute("login"))) {

			return "index";

		}

		// fetch folders

		String userIdString = principal.getAttribute("token");

		List<Folder> usersFolders = folderRepo.findAllById("pranavrayapati");

		model.addAttribute("usersFolders", usersFolders);
		List<Folder> defaultFolders = folderService.fecthDefaultFolders("pranavrayapati");

		model.addAttribute("defaultFolders", defaultFolders);
		model.addAttribute("name", "pranavrayapati");

		// System.out.print("reached");

		// fetch messages
		String label = "Inbox";
		List<EmailListItem> emailListItems = emailListRepo.findAllByKey_IdAndKey_Label("pranavrayapati", label);

		// emailListItems.forEach(a -> a.toString());

		// this is used to calculate min ago
		PrettyTime p = new PrettyTime();

		emailListItems.stream().forEach(emailItems -> {

			UUID timeUuid = emailItems.getKey().getTimeUUID();

			Date date = new Date(Uuids.unixTimestamp(timeUuid));

			emailItems.setAgoTimeString(p.format(date));

		});

		model.addAttribute("emailList", emailListItems);

		java.util.Optional<Email> emailOptional = emailRepo.findById(id);

		if (!emailOptional.isPresent()) {
			return "inbox-page";
		}
		// to String
		Email email = emailOptional.get();
		String toidString = String.join(",", email.getTo());
		/*
		 * String user = "pranavrayapati";
		 *
		 * if (!!user.equals(email.getFrom()) && !!email.getTo().contains(user)) {
		 *
		 * return "redirect:/"; }
		 */

		model.addAttribute("email", email);
		model.addAttribute("toId", toidString);

		EmailListItemKey emailListItemKey = new EmailListItemKey();
		emailListItemKey.setId("pranavrayapati");
		emailListItemKey.setLabel(folder);
		emailListItemKey.setTimeUUID(email.getId());

		java.util.Optional<EmailListItem> optionalEmailListItem = emailListRepo.findById(emailListItemKey);

		if (optionalEmailListItem.isPresent()) {

			EmailListItem emailListItem = optionalEmailListItem.get();
			if (emailListItem.isUnread()) {
				emailListItem.setUnread(false);
				emailListRepo.save(emailListItem);
				unReadEmailStatRepo.decrementCounter("pranavrayapati", folder);
			}

		}

		model.addAttribute("stats", folderService.mapCountToLabel("pranavrayapati"));

		return "email-page";
	}

}
