package trustnet.auth.common.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommonService {

	public String getHMAC() {
		return "0cc175b9c0f1b6a831c399e269772661";
	}
	
	public String getPermissions() {
		return "operator";
	}
}
