package trustnet.auth.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SecurityAuthInfoVO {

	boolean usable = true;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String value;
	
	int user_no;
	String user_id;
	String permissions;
	
	public SecurityAuthInfoVO(boolean usable, String value) {
		this.usable = usable;
		this.value = value;
	}
}
