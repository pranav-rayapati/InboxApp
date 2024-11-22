package com.inbox.model;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;


@Table(value = "folders_by_user")
public class Folder {
	 @PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	    private String id;

	    @PrimaryKeyColumn(name = "label", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
	    @CassandraType(type = CassandraType.Name.TEXT)
	    private String label;

	    // @PrimaryKeyColumn(name = "created_time_uuid", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
	    // @CassandraType(type = CassandraType.Name.TEXT)
	    // private UUID createdTimeUuid;

	    @CassandraType(type = CassandraType.Name.TEXT)
	    private String color;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Folder() {
			super();
		}

	

		public Folder(String id, String label, String color) {
			super();
			this.id = id;
			this.label = label;
			this.color = color;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

}
 