package com.inbox.model;

import java.util.List;
import java.util.UUID;


import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "message_by_id")
public class Email {
	
	//if its one primary key then we need add @Id  
	
	@Id    @PrimaryKeyColumn(name = "id ", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private UUID id;  

	 @CassandraType(type = CassandraType.Name.TEXT)
	 private String from; 
	
	 @CassandraType(type = CassandraType.Name.LIST, typeArguments = Name.TEXT)
	private List<String> to;
	
	 @CassandraType(type = CassandraType.Name.TEXT)
	 private String subject; 
	
	
	 @CassandraType(type = CassandraType.Name.TEXT) 
	 private String body;


	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
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


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	} 
	
	

}
