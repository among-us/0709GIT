package trustnet.auth.zone.service.vo;

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
public class ZoneAgentCreateFileVO {
	String zone_name;
	String tam_local_ip_1;
	String tam_local_port_1;
	String tam_local_ip_2;
	String tam_local_port_2;
	String taa_ip;
	String license;
}
