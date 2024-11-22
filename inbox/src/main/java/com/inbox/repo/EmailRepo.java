package com.inbox.repo;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.inbox.model.Email;

@Repository
public interface EmailRepo extends CassandraRepository<Email, UUID> {

}
