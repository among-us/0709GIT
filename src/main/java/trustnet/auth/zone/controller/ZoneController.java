package trustnet.auth.zone.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.service.SecurityService;
import trustnet.auth.common.vo.SecurityAuthInfoVO;
import trustnet.auth.company.controller.dto.CompanyInfoDTO;
import trustnet.auth.company.service.CompanyService;
import trustnet.auth.company.service.vo.CompanyInfoVO;
import trustnet.auth.manager.controller.dto.ManagerCreateFileDTO;
import trustnet.auth.manager.controller.dto.ManagerHistoryInfoDTO;
import trustnet.auth.manager.controller.dto.ManagerInfoDTO;
import trustnet.auth.manager.service.vo.ManagerCreateFileVO;
import trustnet.auth.manager.service.vo.ManagerInfoVO;
import trustnet.auth.zone.controller.dto.TESTDTO;
import trustnet.auth.zone.controller.dto.ZoneHistoryInfoDTO;
import trustnet.auth.zone.controller.dto.ZoneInfoDTO;
import trustnet.auth.zone.controller.dto.ZoneLicenseCodeInfoDTO;
import trustnet.auth.zone.controller.dto.ZoneLicenseInfoDTO;
import trustnet.auth.zone.controller.dto.ZoneNLicenseInfoDTO;
import trustnet.auth.zone.controller.dto.ZoneWetherLicenseDTO;
import trustnet.auth.zone.service.ZoneService;
import trustnet.auth.zone.service.vo.ZoneHistoryInfoVO;
import trustnet.auth.zone.service.vo.ZoneInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseCodeInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseHistoryInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseInfoVO;
import trustnet.auth.zone.service.vo.ZoneNLicenseInfoVO;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ZoneController {

	private final ZoneService zoneService;
	private final ModelMapper modelMapper;
	private final CompanyService companyService;
	private final SecurityService securityService;

	@RequestMapping("/zone/enrollPage")
	public ModelAndView index(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/ZoneEnroll");
		List<CompanyInfoVO> companyVOLIST = companyService.findAllCompanyInfoAll();
		List<CompanyInfoDTO> companyDTOLIST = modelMapper.map(companyVOLIST,
				new TypeToken<List<CompanyInfoDTO>>() {
				}.getType());
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("companyList", companyDTOLIST);

		return mav;
	}

	//  수정 수정 수정 
	@RequestMapping("/zone/listPage")
	public ModelAndView list(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/ZoneList");
		List<ZoneInfoVO> voList = zoneService.findAllZone();
		List<ZoneInfoDTO> dtoList = modelMapper.map(voList,
				new TypeToken<List<ZoneInfoDTO>>() {
				}.getType());
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("zoneList", dtoList);
		return mav;
	}

	@GetMapping("/zoneLicense/enrollPage")
	public ModelAndView zoneLicnese(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/LicenseEnroll");

		List<ZoneInfoVO> voList = zoneService.findAllZoneListOnlyExist();
		List<ZoneInfoDTO> dtoList = modelMapper.map(voList,
				new TypeToken<List<ZoneInfoDTO>>() {
				}.getType());

		List<ZoneLicenseCodeInfoVO> codeVOLIST = zoneService.findAllZoneCodeInfo();
		List<ZoneLicenseCodeInfoDTO> codeDTOLIST = modelMapper.map(codeVOLIST,
				new TypeToken<List<ZoneLicenseCodeInfoDTO>>() {
				}.getType());
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("zoneList", dtoList);
		mav.addObject("codeList", codeDTOLIST);
		return mav;
	}

	@GetMapping("/test/page")
	public ModelAndView test(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/TEST");

		List<TESTDTO> testList = new ArrayList<>();

		try {
			Properties prop = System.getProperties();
			Enumeration clsEnum = prop.propertyNames();
			String key;
			String value;

			while (clsEnum.hasMoreElements()) {
				key = (String) clsEnum.nextElement();
				value = prop.getProperty(key);
				String a = key + " : " + value;
				log.info(a);
				testList.add(new TESTDTO(key, value));
			}

			mav.addObject("testList", testList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@GetMapping("/zoneLicense/listPage")
	public ModelAndView zoneLicneseListPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/LicenseList");

		List<ZoneNLicenseInfoVO> voList = zoneService.findAllZoneLicenseList();
		List<ZoneNLicenseInfoDTO> dtoList = modelMapper.map(voList,
				new TypeToken<List<ZoneNLicenseInfoDTO>>() {
				}.getType());

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("zoneLicenseList", dtoList);
		return mav;
	}

	@GetMapping("/zoneLicense/updatePage")
	public ModelAndView zoneLicneseUpdatePage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ZoneLicenseInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/LicenseUpdate");
		ZoneLicenseInfoVO vo = modelMapper.map(dto, ZoneLicenseInfoVO.class);

		ZoneNLicenseInfoVO retVO = zoneService.findAllZoneLicenseListAsZoneNO(vo);

		ZoneNLicenseInfoDTO retDTO = modelMapper.map(retVO, ZoneNLicenseInfoDTO.class);

		List<ZoneLicenseCodeInfoVO> codeVOLIST = zoneService.findAllZoneCodeInfo();

		List<ZoneLicenseCodeInfoDTO> codeDTOLIST = modelMapper.map(codeVOLIST,
				new TypeToken<List<ZoneLicenseCodeInfoDTO>>() {
				}.getType());
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("zoneNLicense", retDTO);
		mav.addObject("codeList", codeDTOLIST);
		return mav;
	}

	@GetMapping("/zone/updatePage")
	public ModelAndView zoneUpdatePage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ZoneInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/ZoneUpdate");
		ZoneInfoVO vo = modelMapper.map(dto, ZoneInfoVO.class);
		ZoneInfoVO retVO = zoneService.findZoneWithNO(vo);
		List<CompanyInfoVO> companyVOLIST = companyService.findAllCompanyInfoAll();
		List<CompanyInfoDTO> companyDTOLIST = modelMapper.map(companyVOLIST,
				new TypeToken<List<CompanyInfoDTO>>() {
				}.getType());
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("companyList", companyDTOLIST);
		mav.addObject("zoneInfo", retVO);
		return mav;
	}

	@GetMapping("/zone/historyPage")
	public ModelAndView zoneHistoryPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/History");
		List<ZoneInfoVO> voList = zoneService.findAllZone();
		List<ZoneInfoDTO> dtoList = modelMapper.map(voList,
				new TypeToken<List<ZoneInfoDTO>>() {
				}.getType());
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("zoneList", dtoList);
		return mav;
	}

	@GetMapping("/zone/historyDetailPage")
	public ModelAndView zoneHistoryDetailPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ZoneInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/HistoryDetail");
		ZoneInfoVO vo = modelMapper.map(dto, ZoneInfoVO.class);
		List<ZoneHistoryInfoVO> voList = zoneService.findAllZoneHistoryAsZoneNo(vo);
		List<ZoneHistoryInfoDTO> dtoList = modelMapper.map(voList,
				new TypeToken<List<ZoneHistoryInfoDTO>>() {
				}.getType());
		List<ZoneLicenseHistoryInfoVO> historyVOList = zoneService
				.findZoneLicenseHistoryInfo(vo);
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("historyList", dtoList);
		mav.addObject("licenseHistoryList", historyVOList);
		return mav;
	}

	//	라이선스 관리 datatables 
	@GetMapping("/zone/licenseStateHistoryPage")
	public ModelAndView licenseStateHistoryPage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/LicenseStateHistory");
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		return mav;
	}

	@GetMapping("/zoneLicense/taCreateFilePage")
	public ModelAndView TaCreateFile(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/TaCreateFile");

		List<ZoneInfoVO> voList = zoneService.findAllZoneListOnlyExist();
		List<ZoneInfoDTO> dtoList = modelMapper.map(voList,
				new TypeToken<List<ZoneInfoDTO>>() {
				}.getType()); //zone name list 
		List<ZoneLicenseCodeInfoVO> codeVOLIST = zoneService.findAllZoneCodeInfo();
		List<ZoneLicenseCodeInfoDTO> codeDTOLIST = modelMapper.map(codeVOLIST,
				new TypeToken<List<ZoneLicenseCodeInfoDTO>>() {
				}.getType()); // code list 
		List<ManagerCreateFileVO> tamNameVOList = zoneService.findAllManagerName();
		List<ManagerCreateFileDTO> tamNameDTOList = modelMapper.map(tamNameVOList,
				new TypeToken<List<ManagerHistoryInfoDTO>>() {
				}.getType()); // tam name list 
		List<ZoneLicenseInfoVO> taaIpVOList = zoneService.findTaaIp();
		List<ZoneLicenseInfoDTO> taaIpDTOList = modelMapper.map(taaIpVOList,
				new TypeToken<List<ZoneLicenseInfoDTO>>() {
				}.getType()); // taa ip list 

		List<ManagerInfoVO> tamIPVOList = zoneService.findTamIp();
		List<ManagerInfoDTO> tamIPDTOList = modelMapper.map(tamIPVOList,
				new TypeToken<List<ManagerInfoDTO>>() {
				}.getType());

		List<ManagerInfoVO> tamLocalPortVOList = zoneService.findTamLocalPort();
		List<ManagerInfoDTO> tamLocalPortDTOList = modelMapper.map(tamLocalPortVOList,
				new TypeToken<List<ManagerInfoDTO>>() {
				}.getType());

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("zoneList", dtoList);
		mav.addObject("codeList", codeDTOLIST);
		mav.addObject("tamList", tamNameDTOList);
		mav.addObject("taaIpList", taaIpDTOList);
		mav.addObject("tamIpList", tamIPDTOList); // 2 26 add
		mav.addObject("tamLocalPortList", tamLocalPortDTOList); // 2 26 add
		return mav;

	}

	@GetMapping("/zoneLicense/taVerifyFilePage")
	public ModelAndView TaVerifyFile(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/TaVerifyFile");
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		return mav;
	}

	// 수정 수정 수정 
	@GetMapping("/zone/taWhetherPage")
	public ModelAndView TaWhetherFile(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			ZoneWetherLicenseDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/TaWhether");
		List<ZoneInfoVO> voList = zoneService.findAllZone();
		List<ZoneInfoDTO> dtoList = modelMapper.map(voList,
				new TypeToken<List<ZoneInfoDTO>>() {
				}.getType());

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		mav.addObject("zoneList", dtoList);
		return mav;
	}

	@GetMapping("/zoneLicense/LicenseAllowInfoPage")
	public ModelAndView LicenseAllowInfo(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/LicenseAllowInfo");
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		return mav;
	}

	@GetMapping("/zoneLicense/LicenseAllowEnrollPage")
	public ModelAndView LicenseAllowEnroll(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/LicenseAllowEnroll");
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		return mav;
	}

	@GetMapping("/license/ChangeHistoryPage")
	public ModelAndView liststatePage(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token) {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/LicenseChangeHistory");
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		int perm_code = securityVO.getPerm_no();

		mav.addObject("permCode", perm_code);
		return mav;
	}
}
