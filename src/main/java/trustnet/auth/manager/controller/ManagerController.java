package trustnet.auth.manager.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.manager.controller.dto.ManagerHistoryInfoDTO;
import trustnet.auth.manager.controller.dto.ManagerInfoDTO;
import trustnet.auth.manager.service.ManagerService;
import trustnet.auth.manager.service.vo.ManagerHistoryInfoVO;
import trustnet.auth.manager.service.vo.ManagerInfoVO;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ManagerController {

	private final ManagerService managerService;
	private final ModelMapper modelMapper;

	@GetMapping("/manager/enrollPage")
	public ModelAndView enrollPage() {
		ModelAndView mav = new ModelAndView("thymeleaf/manager/Enroll");
		return mav;
	}

	@GetMapping("/manager/listPage")
	public ModelAndView listPage() {
		ModelAndView mav = new ModelAndView("thymeleaf/manager/List");
		List<ManagerInfoVO> voList = managerService.findAllManagerList();
		List<ManagerInfoDTO> dtoList = modelMapper.map(voList, new TypeToken<List<ManagerInfoDTO>>() {
		}.getType());
		mav.addObject("managerList", dtoList);
		return mav;
	}

	@GetMapping("/manager/updatePage")
	public ModelAndView updatePage(ManagerInfoDTO dto) {
		log.info(dto.toString());
		ModelAndView mav = new ModelAndView("thymeleaf/manager/Update");
		ManagerInfoVO vo = modelMapper.map(dto, ManagerInfoVO.class);
		ManagerInfoVO retVO = managerService.findManagerWithNO(vo);
		ManagerInfoDTO retDTO = modelMapper.map(retVO, ManagerInfoDTO.class);
		mav.addObject("managerInfo", retDTO);
		return mav;
	}

	@GetMapping("/manager/historyPage")
	public ModelAndView historyPage(ManagerInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/manager/History");
		List<ManagerInfoVO> voList = managerService.findAllManagerList();
		List<ManagerInfoDTO> dtoList = modelMapper.map(voList, new TypeToken<List<ManagerInfoDTO>>() {
		}.getType());
		mav.addObject("managerList", dtoList);
		return mav;
	}

	@GetMapping("/manager/historyDetailPage")
	public ModelAndView historyDetailPage(ManagerInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/manager/HistoryDetail");
		ManagerInfoVO vo = modelMapper.map(dto, ManagerInfoVO.class);
		List<ManagerHistoryInfoVO> voList = managerService.findAllManagerHistoryAsTamNo(vo);
		List<ManagerHistoryInfoDTO> dtoList = modelMapper.map(voList, new TypeToken<List<ManagerHistoryInfoDTO>>() {
		}.getType());
		mav.addObject("historyList", dtoList);
		return mav;
	}

	@GetMapping("/manager/createFilePage")
	public ModelAndView createFilePage(ManagerInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/manager/createFile");
		return mav;
	}
	
	@GetMapping("/manager/verifyFilePage")
	public ModelAndView verifyFilePage(ManagerInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/manager/VerifyFile");
		return mav;
	}
	
	/*
	 * @GetMapping("/manager/taCreateFilePage") public ModelAndView
	 * taCreateFilePage(ManagerInfoDTO dto) { ModelAndView mav = new
	 * ModelAndView("thymeleaf/zone/TaCreateFile"); return mav; }
	 */
	
}
