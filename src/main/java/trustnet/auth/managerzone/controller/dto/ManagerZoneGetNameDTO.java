package trustnet.auth.managerzone.controller.dto;

import lombok.Data;
import trustnet.auth.managerzone.service.vo.ManagerZoneInfoVO;

@Data
public class ManagerZoneGetNameDTO {
	int zone_no;
	String zone_name;
	
}
