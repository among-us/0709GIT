package trustnet.auth.common.service;



import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.manager.controller.dto.ManagerDBInfoDTO;
import trustnet.tas.except.TNAuthException;
import trustnet.tas.valid.Integrity;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommonService {
	
	public String getHMAC() throws TNAuthException {
		String key = "a";
		String data = "b";
		
		String hmac= Integrity.tasEncrypt(key, data);
		
		return hmac;
//		return "0cc175b9c0f1b6a831c399e269772661";
	}
	
	public String getPermissions() {
		return "operator";
	}
}
