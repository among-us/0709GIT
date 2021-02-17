package trustnet.auth.log.repository.entity;

import lombok.Data;

@Data
public class AuditLogInfoENTITY {
	int id;
	String date;
	String description;
}
