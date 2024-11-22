package com.inbox.repo;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.inbox.model.Folder;

@Repository
public interface FolderRepo extends CassandraRepository<Folder, String> {

	List<Folder> findAllById(String id);

}
