package com.inbox.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.inbox.model.Folder;
import com.inbox.repo.EmailListRepo;
import com.inbox.repo.EmailRepo;
import com.inbox.repo.FolderRepo;
import com.inbox.service.EmailService;
import com.inbox.service.FolderService;

@Controller
public class EmailComposeController {

	@Autowired
	FolderRepo folderRepo;
	@Autowired
	FolderService folderService;
	@Autowired
	EmailListRepo emailListRepo;

	@Autowired
	EmailService emailService;

	@Autowired
	EmailRepo emailRepo;

	@GetMapping("/compose")
	public String getEmailCompose(@AuthenticationPrincipal OAuth2User principal, Model model,
			@RequestParam(required = false) String to, @RequestParam(required = false) UUID id

	) {

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
		String userNameString = "pranavrayapati";
		model.addAttribute("name", userNameString);

		// check if we have to message

		List<String> uniqueId = splitIds(to);
		model.addAttribute("toIds", String.join(", ", uniqueId));
		// System.out.print("reached");

		if (id != null) {
			emailRepo.findById(id).ifPresent(email -> {
				model.addAttribute("subject", emailService.getReplySubject(email.getSubject()));
				model.addAttribute("body", emailService.getReplyBody(email));
			});
		}
		// to String

		return "compose-page";

	}

	@PostMapping("/sendEmail")
	public ModelAndView sendEmail(@AuthenticationPrincipal OAuth2User principal,
			@RequestBody MultiValueMap<String, String> formData

	) {

		if (principal == null || !org.springframework.util.StringUtils.hasText(principal.getAttribute("login"))) {

			return new ModelAndView("redirect:/");

		}

		String subject = formData.getFirst("subject");
		String body = formData.getFirst("body");
		List<String> toIds = splitIds(formData.getFirst("toIds"));
		// String from = principal.getAttribute("login");
		String from = "pranavrayapati";

		emailService.sendEmail(from, subject, toIds, body);

		return new ModelAndView("redirect:/");

	}

	private List<String> splitIds(String to) {

		if (!StringUtils.hasText(to)) {
			return new ArrayList<String>();
		}
		String[] splitIds = to.split(",");
		// remove white spaces and convert to normal list and pass it to model
		List<String> uniqueId = Arrays.asList(splitIds).stream().map(String::trim).filter(id -> StringUtils.hasText(id))
				.distinct().collect(Collectors.toList());
		return uniqueId;
	}

}
