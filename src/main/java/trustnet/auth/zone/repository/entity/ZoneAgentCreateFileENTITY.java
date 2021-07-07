package trustnet.auth.zone.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ZoneAgentCreateFileENTITY {
	String zone_name;
	String tam_local_ip_1;
	String tam_local_port_1;
	String taa_ip;
	String license;
	
}
