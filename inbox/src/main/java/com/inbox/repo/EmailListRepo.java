package com.inbox.repo;

import java.util.List;


import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.inbox.model.EmailListItem;
import com.inbox.model.EmailListItemKey;

@Repository
public interface EmailListRepo extends CassandraRepository<EmailListItem, EmailListItemKey> {

	// anything after by is where clause
	// so here we are telling to finding the id, label from key

	List<EmailListItem> findAllByKey_IdAndKey_Label(String id, String label);
}
