package com.inbox.model;

import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;


//we have created this class because we have two primary keys and if we create a repository 
//we cannot pass all three values into it 
//to create a primary key we need to annotate it with primary key class
//and annotate it with @primarykey in the main class


@PrimaryKeyClass
public class EmailListItemKey {
	
	
	 @PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
		private String id;
		 @PrimaryKeyColumn(name = "label", ordinal = 1, type = PrimaryKeyType.PARTITIONED) 
		private String label;
		 
		 @PrimaryKeyColumn(name = "created_time_uuid", ordinal = 2 , type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
		private UUID timeUUID ;
 
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public UUID getTimeUUID() {
			return timeUUID;
		}

		public void setTimeUUID(UUID timeUUID) {
			this.timeUUID = timeUUID;
		}
		

}
