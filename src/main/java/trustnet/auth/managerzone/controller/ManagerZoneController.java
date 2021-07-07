package trustnet.auth.managerzone.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.service.SecurityService;
import trustnet.auth.common.vo.SecurityAuthInfoVO;
import trustnet.auth.manager.controller.dto.ManagerInfoDTO;
import trustnet.auth.manager.service.ManagerService;
import trustnet.auth.manager.service.vo.ManagerInfoVO;
import trustnet.auth.managerzone.controller.dto.ManagerZoneInfoDTO;
import trustnet.auth.managerzone.controller.dto.ManagerZoneMatchInfoDTO;
import trustnet.auth.managerzone.service.ManagerZoneService;
import trustnet.auth.managerzone.service.vo.ManagerZoneInfoVO;
import trustnet.auth.managerzone.service.vo.ManagerZoneMatchInfoVO;
import trustnet.auth.zone.controller.dto.ZoneInfoDTO;
import trustnet.auth.zone.service.ZoneService;
import trustnet.auth.zone.service.vo.ZoneInfoVO;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ManagerZoneController {

	private final ManagerZoneService managerZoneService;
	private final ManagerService managerService;
	private final ZoneService zoneService;
	private final ModelMapper modelMapper;
	private final SecurityService securityService;
	
	@GetMapping("/managerZone/enrollPage")
	public ModelAndView managerZoneEnrollPage(@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,ManagerZoneInfoDTO dto){
		log.info("[enrollCompany => CompanyInfoDTO\n] {}", dto.toString());

		if(dto.getTam_no() == 0) {
			log.warn("MANAGER - PROJECT MATCHING NULL POINTER EXCEPTION");
			throw new NullPointerException();
		}
		
		ModelAndView mav = new ModelAndView("thymeleaf/managerzone/Enroll");
		ManagerZoneInfoVO managerZoneInfoVo = modelMapper.map(dto, ManagerZoneInfoVO.class);
		List<ManagerZoneMatchInfoVO> voList = managerZoneService.findAllHavingZone(managerZoneInfoVo);
		List<ManagerZoneMatchInfoDTO> afterZoneInfoDTOList = modelMapper.map(voList, new TypeToken<List<ManagerZoneMatchInfoDTO>>() {}.getType());
		
		ManagerInfoVO managerInfoVO = managerZoneService.findManager(managerZoneInfoVo);
		ManagerInfoDTO managerInfoDTO = modelMapper.map(managerInfoVO, ManagerInfoDTO.class);
		List<ZoneInfoVO> zoneVoList = zoneService.findAllZoneListOnlyExist();
		List<ZoneInfoDTO> beforeZoneInfoDTOList = modelMapper.map(zoneVoList, new TypeToken<List<ZoneInfoDTO>>() {}.getType());
		
		boolean flag = false;
		
		for(ZoneInfoDTO beforeDTO : beforeZoneInfoDTOList) {
			for(ManagerZoneMatchInfoDTO afterDTO : afterZoneInfoDTOList) {
				if(beforeDTO.getZone_no() == afterDTO.getZone_no()) {
					flag = true;
				}
			}
			beforeDTO.setEnrolled(flag);
			flag = false;
		}
		
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();
		
		mav.addObject("permCode", perm_code);
		mav.addObject("managerInfo", managerInfoDTO);
		mav.addObject("beforeZoneList", beforeZoneInfoDTOList);
		mav.addObject("afterZoneList", afterZoneInfoDTOList);
		
		return mav;
	}
	
	
	@GetMapping("/managerZone/listPage")
	public ModelAndView indexPage(@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/managerzone/List");
		List<ManagerInfoVO> voList = managerService.findAllManagerListOnlyExist();
		List<ManagerInfoDTO> dtoList = modelMapper.map(voList, new TypeToken<List<ManagerInfoDTO>>() {}.getType());
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();
		
		mav.addObject("permCode", perm_code);
		mav.addObject("managerList", dtoList);
		return mav;
	}
}
