package com.inbox.repo;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.inbox.model.UnReadEmailStat;

public interface UnReadEmailStatRepo extends CassandraRepository<UnReadEmailStat, String> {

	@Query("update unread_email_stat set unreadcount = unreadcount - 1 where user_id = ?0 and label = ?1")
	public void decrementCounter(String userId, String label);

	List<UnReadEmailStat> findAllById(String id);

	@Query("update unread_email_stat set unreadcount = unreadcount + 1 where user_id = ?0 and label = ?1")
	public void incrementCounter(String userId, String label);

}
