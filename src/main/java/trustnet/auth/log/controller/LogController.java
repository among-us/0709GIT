package trustnet.auth.log.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.log.controller.dto.AuditLogInfoDTO;
import trustnet.auth.log.controller.dto.ServiceLogInfoDTO;
import trustnet.auth.log.service.LogService;
import trustnet.auth.log.service.vo.AuditLogInfoVO;
import trustnet.auth.log.service.vo.ServiceLogInfoVO;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LogController {
	
	private final ModelAndView mav;
	private final LogService logService;
	private final ModelMapper modelMapper;
	@GetMapping("/log/serviceLog")
	public ModelAndView serviceLogIndex() {
		List<ServiceLogInfoVO> voList = logService.findAllServiceLogList();
		for(ServiceLogInfoVO vo : voList) {
			log.info(vo.toString());
		}
		
		modelMapper.getConfiguration()
		  .setFieldMatchingEnabled(true)
		  .setFieldAccessLevel(Configuration.AccessLevel.PUBLIC);
		
		List<ServiceLogInfoDTO> dtoList =  
				modelMapper.map(voList, new TypeToken<List<ServiceLogInfoDTO>>() {}.getType());
		
		mav.setViewName("/log/ServiceLogList");
		mav.addObject("serviceLogList", dtoList);
		
		return mav;
	}
	
	@GetMapping("/log/auditLog")
	public ModelAndView auditLogIndex() {
		List<AuditLogInfoVO> voList = logService.findAllAuditLogList();
		for(AuditLogInfoVO vo : voList) {
			log.info(vo.toString());
		}
		
		modelMapper.getConfiguration()
		  .setFieldMatchingEnabled(true)
		  .setFieldAccessLevel(Configuration.AccessLevel.PUBLIC);
		
		List<AuditLogInfoDTO> dtoList =  
				modelMapper.map(voList, new TypeToken<List<AuditLogInfoDTO>>() {}.getType());
		
		mav.setViewName("/log/AuditLogList");
		mav.addObject("auditLogList", dtoList);
		
		return mav;
	}
	
	
}
