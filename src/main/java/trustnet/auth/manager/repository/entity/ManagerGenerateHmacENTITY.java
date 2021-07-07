package trustnet.auth.manager.repository.entity;

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
public class ManagerGenerateHmacENTITY {
	String tam_name;
	String tam_local_ip;
	int tam_local_port;
	int tam_adm_port;
	String tam_license;
	String hmac;
}
