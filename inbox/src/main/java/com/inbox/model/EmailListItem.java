package com.inbox.model;

import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "messages_y_user_folders")
public class EmailListItem {

	// for primary key class so that spring knows it is a primary key class
	@PrimaryKey
	private EmailListItemKey key;

	// it says it is a list item with type string
	@CassandraType(type = CassandraType.Name.LIST, typeArguments = Name.TEXT)
	private List<String> to;



	@CassandraType(type = CassandraType.Name.TEXT)
	private String subject;

	@CassandraType(type = CassandraType.Name.BOOLEAN)
	private boolean isUnread;


	@Transient
	private String agoTimeString;

	public EmailListItemKey getKey() {
		return key;
	}

	public void setKey(EmailListItemKey key) {
		this.key = key;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public boolean isUnread() {
		return isUnread;
	}

	public void setUnread(boolean isUnread) {
		this.isUnread = isUnread;
	}

	@Override
	public String toString() {
		return "EmailListItem [key=" + key + ", to=" + to + ", subject=" + subject + ", isUnread=" + isUnread + "]";
	}

	public String getAgoTimeString() {
		return agoTimeString;
	}

	public void setAgoTimeString(String agoTimeString) {
		this.agoTimeString = agoTimeString;
	}

}
