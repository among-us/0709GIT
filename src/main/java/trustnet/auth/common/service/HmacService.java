//package trustnet.auth.common.service;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import trustnet.auth.common.dto.HmacAPIController;
//import trustnet.auth.common.dto.HmacDTO;
//import trustnet.auth.common.vo.HmacVO;
//import trustnet.auth.manager.controller.dto.ManagerGenerateHmacDTO;
//import trustnet.auth.manager.service.vo.ManagerGenerateHmacVO;
//import trustnet.tas.except.TNAuthException;
//import trustnet.tas.valid.Integrity;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class HmacService {
//
//	private final ModelMapper modelMapper;
//	
//	public String getHMAC() throws TNAuthException {
//		HmacDTO hmacDTO = new HmacDTO();
//		HmacVO vo = modelMapper.map(hmacDTO, HmacVO.class);
//		log.info("===============VOLIST===============" + vo.toString());
//
//		String tam_name = vo.getTam_name();
//		String tam_local_ip = vo.getTam_local_ip();
//		int tam_local_port = vo.getTam_local_port();
//		int tam_adm_port = vo.getTam_adm_port();
//
//		String ip = vo.getTam_name();
//		String tam_license = Integrity.generateTAMlicense(ip);
//
//		String integrity = Integrity.generateTAMDbIntegrity(tam_name, tam_local_ip,
//				tam_local_port, tam_adm_port, tam_license);
//		log.info("+++++++++++++++++++++++++++++++++++++++++++++" + integrity.toString());
//		return integrity;
//	}
//}
