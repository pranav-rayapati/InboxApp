package com.inbox.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.inbox.model.Email;
import com.inbox.model.EmailListItem;
import com.inbox.model.EmailListItemKey;
import com.inbox.repo.EmailListRepo;
import com.inbox.repo.EmailRepo;
import com.inbox.repo.UnReadEmailStatRepo;

@Service
public class EmailService {

	@Autowired
	EmailRepo emailRepo;
	@Autowired
	EmailListRepo emailListRepo;

	@Autowired
	UnReadEmailStatRepo unReadEmailStatRepo;

	private EmailListItem createEmailListItem(String from, String subject, List<String> toIds, Email email,
			String folder) {
		EmailListItemKey key = new EmailListItemKey();
		key.setId(from);
		key.setTimeUUID(email.getId());
		key.setLabel(folder);

		EmailListItem emailListItem = new EmailListItem();
		emailListItem.setKey(key);
		emailListItem.setSubject(subject);
		emailListItem.setUnread(true);
		emailListItem.setTo(toIds);
		return emailListItem;
	}

	public String getReplyBody(Email email) {
		// Build the reply body with proper formatting
		return "\n--------------------\n" + "From: " + email.getFrom() + "\n" + "To: " + email.getTo() + "\n"
				+ "Body:\n" + email.getBody();
	}

	public String getReplySubject(String subject) {

		return "Re:" + subject;

	}

	public void sendEmail(String from, String subject, List<String> toIds, String body) {

		Email email = new Email();
		email.setBody(body);
		email.setFrom(from);
		email.setId(Uuids.timeBased());
		email.setSubject(subject);
		email.setTo(toIds);

		emailRepo.save(email);

		toIds.forEach(toId -> {

			EmailListItem emailListItem = createEmailListItem(from, subject, toIds, email, "Inbox");
			unReadEmailStatRepo.incrementCounter(toId, "Inbox");
			emailListRepo.save(emailListItem);

		});
		EmailListItem sentEmailListItem = createEmailListItem(from, subject, toIds, email, "Sent");
		unReadEmailStatRepo.incrementCounter(email.getFrom(), "Sent");
		sentEmailListItem.setUnread(true);
		emailListRepo.save(sentEmailListItem);

	}

}
