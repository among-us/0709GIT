package trustnet.auth.company.controller;

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
import trustnet.auth.company.controller.dto.CompNoInfoDTO;
import trustnet.auth.company.controller.dto.CompanyInfoDTO;
import trustnet.auth.company.controller.dto.CompanyLicenseDTO;
import trustnet.auth.company.controller.dto.CompanyNGroupInfoDTO;
import trustnet.auth.company.controller.dto.CompanyProjectInfoDTO;
import trustnet.auth.company.service.CompanyService;
import trustnet.auth.company.service.vo.CompNoInfoVO;
import trustnet.auth.company.service.vo.CompanyInfoVO;
import trustnet.auth.company.service.vo.CompanyLicenseVO;
import trustnet.auth.company.service.vo.CompanyNGroupInfoVO;
import trustnet.auth.company.service.vo.CompanyProjectInfoVO;
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
	private final SecurityService securityService;

	@GetMapping("/company/enrollPage")
	public ModelAndView enrollPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/company/CompanyEnroll");
		List<CompanyInfoVO> voAllList = companyService.findAllGroup();
		List<CompanyInfoDTO> dtoAllList = modelMapper.map(voAllList,
				new TypeToken<List<CompanyInfoDTO>>() {
				}.getType());
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("compList", dtoAllList);
		return mav;
	}

	@GetMapping("/company/enrollProjectPage")
	public ModelAndView enrollProjectPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/company/CompanyProjectEnroll");

		List<CompanyInfoVO> voAllList = companyService.findAllGroup();
		List<CompanyInfoDTO> dtoAllList = modelMapper.map(voAllList,
				new TypeToken<List<CompanyInfoDTO>>() {
				}.getType());

		List<CompanyInfoVO> compVOList = companyService.findAllCompanyInfo();
		List<CompanyInfoDTO> compDTOList = modelMapper.map(compVOList,
				new TypeToken<List<CompanyInfoDTO>>() {
				}.getType());

		List<ZoneInfoVO> voZoneList = zoneService.findAllZoneListOnlyExist();
		List<ZoneInfoDTO> dtoZoneList = modelMapper.map(voZoneList,
				new TypeToken<List<ZoneInfoDTO>>() {
				}.getType());
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("zoneList", dtoZoneList);
		mav.addObject("groupList", dtoAllList);
		mav.addObject("compList", compDTOList);
		return mav;
	}

	@GetMapping("/company/projZoneMatchPage")
	public ModelAndView companyListPage(CompNoInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/company/ProjectZoneMatching");
		List<CompanyNGroupInfoVO> voList = companyService.findAllCompanyNGroupInfo();
		List<CompanyNGroupInfoDTO> dtoList = modelMapper.map(voList,
				new TypeToken<List<CompanyNGroupInfoDTO>>() {
				}.getType());
		int zone_no = dto.getZ_zone_no();
		String zno = Integer.toString(zone_no);

		log.info("--------------------------------" + zno);
		CompNoInfoVO vo = modelMapper.map(dto, CompNoInfoVO.class);

		List<CompanyLicenseVO> companyMatchZoneVOLIST = companyService
				.findAllCompanyLicenseWithCompNO(vo);
		List<CompanyLicenseDTO> companyMatchZoneDTOLIST = modelMapper
				.map(companyMatchZoneVOLIST, new TypeToken<List<CompanyLicenseDTO>>() {
				}.getType());

		//		List<CompanyLicenseVO> companyTimelicenseVOLIST = companyService.findTmlicensePubCnt(vo);
		//		List<CompanyLicenseDTO> companyTimelicenseDTOLIST = modelMapper.map(companyTimelicenseVOLIST, new TypeToken<List<CompanyLicenseDTO>>() {}.getType());
		//		mav.addObject("TMList", companyTimelicenseDTOLIST);
		//		

		List<CompanyInfoVO> compVOList = companyService.findAllCompanyInfo();
		log.info("COMPANY VO LIST TEST :  " + compVOList.toString());
		List<CompanyInfoDTO> compDTOList = modelMapper.map(compVOList,
				new TypeToken<List<CompanyInfoDTO>>() {
				}.getType());

		//		List<CompanyProjectInfoVO> projVOList = companyService.findAllProjectInfo();
		//		List<CompanyProjectInfoDTO> projDTOList = modelMapper.map(projVOList,
		//				new TypeToken<List<CompanyProjectInfoDTO>>() {
		//		}.getType());

		List<ZoneInfoVO> voZoneList = zoneService.findAllZoneListOnlyExist();
		List<ZoneInfoDTO> dtoZoneList = modelMapper.map(voZoneList,
				new TypeToken<List<ZoneInfoDTO>>() {
				}.getType());

		//		mav.addObject("projList", projDTOList);
		mav.addObject("compList", compDTOList);
		mav.addObject("zoneList", dtoZoneList);
		mav.addObject("companyNGroupInfoList", dtoList);
		mav.addObject("companyZoneList", companyMatchZoneDTOLIST);
		return mav;
	}

	//	@GetMapping("/company/listPage")
	//	public ModelAndView companyListPage() {
	//		ModelAndView mav = new ModelAndView("thymeleaf/company/CompanyList");
	//		List<CompanyNGroupInfoVO> voList = companyService.findAllCompanyNGroupInfo();
	//		List<CompanyNGroupInfoDTO> dtoList = modelMapper.map(voList, new TypeToken<List<CompanyNGroupInfoDTO>>() {}.getType());
	//		
	//		mav.addObject("companyNGroupInfoList", dtoList);
	//		return mav;
	//	}

	@GetMapping("/company/updatePage")
	public ModelAndView companyUpdatePage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			CompanyInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/company/CompanyUpdate");
		CompanyInfoVO vo = modelMapper.map(dto, CompanyInfoVO.class);
		CompanyNGroupInfoVO retVO = companyService.findCompanyInfoAsCompNO(vo);
		CompanyNGroupInfoDTO retDTO = modelMapper.map(retVO, CompanyNGroupInfoDTO.class);
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("companyNGroup", retDTO);
		return mav;
	}

	@GetMapping("/company/licenseStatusPage")
	public ModelAndView listPage(CompNoInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/company/LicenseStatusList");
		List<CompanyInfoVO> voList = companyService.findAllCompanyInfoAll();
		List<CompanyInfoDTO> dtoList = modelMapper.map(voList,
				new TypeToken<List<CompanyInfoDTO>>() {
				}.getType());

		//		ZoneInfoVO zoneInfoVO = new ZoneInfoVO(dto.getC_comp_no());
		ZoneInfoVO zoneInfoVO = new ZoneInfoVO();
		List<ZoneInfoVO> zoneInfoVOLIST = zoneService
				.findZoneWithCompNoWithAlive(zoneInfoVO);
		List<ZoneInfoDTO> zoneInfoDTOLIST = modelMapper.map(zoneInfoVOLIST,
				new TypeToken<List<ZoneInfoDTO>>() {
				}.getType());

		CompNoInfoVO vo = modelMapper.map(dto, CompNoInfoVO.class);
		List<CompanyLicenseVO> companyMatchZoneVOLIST = companyService
				.findAllCompanyLicenseWithCompNO(vo);
		List<CompanyLicenseDTO> companyMatchZoneDTOLIST = modelMapper
				.map(companyMatchZoneVOLIST, new TypeToken<List<CompanyLicenseDTO>>() {
				}.getType());

		//		기업 관리 > 서비스 현황 이력 table에 들어가는 시간제한 라이선스 발급 개수 

		mav.addObject("companyList", dtoList);
		mav.addObject("companyZoneList", companyMatchZoneDTOLIST);
		mav.addObject("compNoInfo", dto);
		mav.addObject("zoneList", zoneInfoDTOLIST);

		return mav;
	}

	@GetMapping("/company/historyPage")
	public ModelAndView companyHistoryPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/company/CompanyHistory");

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		return mav;
	}

	@GetMapping("/company/projectHistoryPage")
	public ModelAndView companyProjectHistoryPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/company/CompanyProjectHistory");

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		return mav;
	}

	@GetMapping("/project/updatePage")
	public ModelAndView projectUpdatePage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			CompanyProjectInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/company/CompanyProjectUpdate");
		CompanyProjectInfoVO vo = modelMapper.map(dto, CompanyProjectInfoVO.class);
		CompanyProjectInfoVO voList = companyService.getProjectInfo(vo);
		log.info("After Function :" + voList.toString());
		List<ZoneInfoVO> voZoneList = zoneService.findAllZoneListOnlyExist();
		List<ZoneInfoDTO> dtoZoneList = modelMapper.map(voZoneList,
				new TypeToken<List<ZoneInfoDTO>>() {
				}.getType());
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("zoneList", dtoZoneList);
		mav.addObject("projObj", voList);
		return mav;
	}

}// Controller End
