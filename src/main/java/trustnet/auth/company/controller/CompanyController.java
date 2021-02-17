package trustnet.auth.company.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.company.controller.dto.CompNoInfoDTO;
import trustnet.auth.company.controller.dto.CompanyInfoDTO;
import trustnet.auth.company.controller.dto.CompanyLicenseDTO;
import trustnet.auth.company.controller.dto.CompanyNGroupInfoDTO;
import trustnet.auth.company.controller.dto.GroupInfoDTO;
import trustnet.auth.company.service.CompanyService;
import trustnet.auth.company.service.vo.CompNoInfoVO;
import trustnet.auth.company.service.vo.CompanyInfoVO;
import trustnet.auth.company.service.vo.CompanyLicenseVO;
import trustnet.auth.company.service.vo.CompanyNGroupInfoVO;
import trustnet.auth.company.service.vo.GroupInfoVO;
import trustnet.auth.zone.controller.dto.ZoneInfoDTO;
import trustnet.auth.zone.service.ZoneService;
import trustnet.auth.zone.service.vo.ZoneInfoVO;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CompanyController {

	private final ZoneService zoneService;
	private final CompanyService companyService;
	private final ModelMapper modelMapper;
	
	@GetMapping("/company/enrollPage")
	public ModelAndView enrollPage() {
		ModelAndView mav = new ModelAndView("thymeleaf/company/CompanyEnroll");
		List<GroupInfoVO> voList =  companyService.findAllGroupInfoAll();
		List<GroupInfoDTO> dtoList = modelMapper.map(voList, new TypeToken<List<GroupInfoDTO>>() {}.getType());
		mav.addObject("groupList", dtoList);
		return mav;
	}
	
	
	@GetMapping("/company/listPage")
	public ModelAndView companyListPage() {
		ModelAndView mav = new ModelAndView("thymeleaf/company/CompanyList");
		List<CompanyNGroupInfoVO> voList = companyService.findAllCompanyNGroupInfo();
		List<CompanyNGroupInfoDTO> dtoList = modelMapper.map(voList, new TypeToken<List<CompanyNGroupInfoDTO>>() {}.getType());
		mav.addObject("companyNGroupInfoList", dtoList);
		return mav;
	}
	
	@GetMapping("/company/updatePage")
	public ModelAndView companyUpdatePage(CompanyInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/company/CompanyUpdate");
		CompanyInfoVO vo = modelMapper.map(dto, CompanyInfoVO.class);
		CompanyNGroupInfoVO retVO = companyService.findCompanyInfoAsCompNO(vo);
		CompanyNGroupInfoDTO retDTO = modelMapper.map(retVO, CompanyNGroupInfoDTO.class);
		
		List<GroupInfoVO> voList = companyService.findAllGroupInfoAll();
		List<GroupInfoDTO> dtoList = modelMapper.map(voList, new TypeToken<List<GroupInfoDTO>>() {}.getType());
		
		mav.addObject("companyNGroup",retDTO);
		mav.addObject("groupInfoList", dtoList);
		return mav;
	}
	
	
	@GetMapping("/company/licenseStatusPage")
	public ModelAndView listPage(CompNoInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/company/LicenseStatusList");
		List<CompanyInfoVO> voList = companyService.findAllCompanyInfoAll();
		List<CompanyInfoDTO> dtoList = modelMapper.map(voList, new TypeToken<List<CompanyInfoDTO>>() {}.getType());
		
		ZoneInfoVO zoneInfoVO = new ZoneInfoVO(dto.getC_comp_no());
		List<ZoneInfoVO> zoneInfoVOLIST = zoneService.findZoneWithCompNoWithAlive(zoneInfoVO);
		List<ZoneInfoDTO> zoneInfoDTOLIST = modelMapper.map(zoneInfoVOLIST, new TypeToken<List<ZoneInfoDTO>>() {}.getType());
		
		CompNoInfoVO vo = modelMapper.map(dto, CompNoInfoVO.class);
		List<CompanyLicenseVO> companyMatchZoneVOLIST = companyService.findAllCompanyLicenseWithCompNO(vo);
		List<CompanyLicenseDTO> companyMatchZoneDTOLIST = modelMapper.map(companyMatchZoneVOLIST, new TypeToken<List<CompanyLicenseDTO>>() {}.getType());
		
		mav.addObject("companyList", dtoList);
		mav.addObject("companyZoneList", companyMatchZoneDTOLIST);
		mav.addObject("compNoInfo", dto);
		mav.addObject("zoneList", zoneInfoDTOLIST);
		
		return mav;
	}

	
}
