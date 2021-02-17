package trustnet.auth.managerzone.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
	
	@GetMapping("/managerZone/enrollPage")
	public ModelAndView managerZoneEnrollPage(ManagerZoneInfoDTO dto){
		log.info("[enrollCompany => CompanyInfoDTO\n] {}", dto.toString());

		if(dto.getTam_no() == 0) {
			log.warn("NULL POINTER EXCEPTION");
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
		
		//////////////////////////////////////////////////////// 이러면 안되는줄 알면서도 하고있네 하
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
		////////////////////////////////////////////////////////
		
		mav.addObject("beforeZoneList", beforeZoneInfoDTOList);
		mav.addObject("managerInfo", managerInfoDTO);
		mav.addObject("afterZoneList", afterZoneInfoDTOList);
		
		return mav;
	}
	
	
	@GetMapping("/managerZone/listPage")
	public ModelAndView indexPage() {
		ModelAndView mav = new ModelAndView("thymeleaf/managerzone/List");
		List<ManagerInfoVO> voList = managerService.findAllManagerListOnlyExist();
		List<ManagerInfoDTO> dtoList = modelMapper.map(voList, new TypeToken<List<ManagerInfoDTO>>() {}.getType());
		mav.addObject("managerList", dtoList);
		return mav;
	}
}
