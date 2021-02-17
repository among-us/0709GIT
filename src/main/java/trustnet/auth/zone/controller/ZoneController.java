package trustnet.auth.zone.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.company.controller.dto.CompanyInfoDTO;
import trustnet.auth.company.service.CompanyService;
import trustnet.auth.company.service.vo.CompanyInfoVO;
import trustnet.auth.zone.controller.dto.ZoneHistoryInfoDTO;
import trustnet.auth.zone.controller.dto.ZoneInfoDTO;
import trustnet.auth.zone.controller.dto.ZoneLicenseCodeInfoDTO;
import trustnet.auth.zone.controller.dto.ZoneLicenseInfoDTO;
import trustnet.auth.zone.controller.dto.ZoneLicenseStateHistoryInfoDTO;
import trustnet.auth.zone.controller.dto.ZoneNLicenseInfoDTO;
import trustnet.auth.zone.service.ZoneService;
import trustnet.auth.zone.service.vo.ZoneHistoryInfoVO;
import trustnet.auth.zone.service.vo.ZoneInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseCodeInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseHistoryInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseStateHistoryInfoVO;
import trustnet.auth.zone.service.vo.ZoneNLicenseInfoVO;
import trustnet.auth.zone.service.vo.ZoneVerifyIntegrityVO;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ZoneController {
	
	private final ZoneService zoneService;
	private final ModelMapper modelMapper;
	private final CompanyService companyService;
	
	@RequestMapping("/zone/enrollPage")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/ZoneEnroll");
		List<CompanyInfoVO> companyVOLIST = companyService.findAllCompanyInfoAll();
		List<CompanyInfoDTO> companyDTOLIST = modelMapper.map(companyVOLIST, new TypeToken<List<CompanyInfoDTO>>() {}.getType() );
		mav.addObject("companyList", companyDTOLIST);
		
		return mav;
	}

	@RequestMapping("/zone/listPage")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/ZoneList");
		List<ZoneInfoVO> voList = zoneService.findAllZoneList();
		List<ZoneInfoDTO> dtoList =  
				modelMapper.map(voList, new TypeToken<List<ZoneInfoDTO>>() {}.getType());
		mav.addObject("zoneList", dtoList);
		return mav;
	}
	
	@GetMapping("/zoneLicense/enrollPage")
	public ModelAndView zoneLicnese() {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/LicenseEnroll");
		
		List<ZoneInfoVO> voList = zoneService.findAllZoneListOnlyExist();
		List<ZoneInfoDTO> dtoList =  
				modelMapper.map(voList, new TypeToken<List<ZoneInfoDTO>>() {}.getType());
		
		List<ZoneLicenseCodeInfoVO> codeVOLIST = zoneService.findAllZoneCodeInfo();
		List<ZoneLicenseCodeInfoDTO> codeDTOLIST = modelMapper.map(codeVOLIST, new TypeToken<List<ZoneLicenseCodeInfoDTO>>() {}.getType());
		mav.addObject("zoneList", dtoList);
		mav.addObject("codeList", codeDTOLIST);
		return mav;
	}
	
	@GetMapping("/zoneLicense/listPage")
	public ModelAndView zoneLicneseListPage() {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/LicenseList");
		
		List<ZoneNLicenseInfoVO> voList = zoneService.findAllZoneLicenseList();
		List<ZoneNLicenseInfoDTO> dtoList =  
				modelMapper.map(voList, new TypeToken<List<ZoneNLicenseInfoDTO>>() {}.getType());

		mav.addObject("zoneLicenseList", dtoList);
		return mav;
	}
	
	@GetMapping("/zoneLicense/updatePage")
	public ModelAndView zoneLicneseUpdatePage(ZoneLicenseInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/LicenseUpdate");
		ZoneLicenseInfoVO vo = modelMapper.map(dto, ZoneLicenseInfoVO.class);
		ZoneNLicenseInfoVO retVO = zoneService.findAllZoneLicenseListAsZoneNO(vo);
		ZoneNLicenseInfoDTO retDTO = modelMapper.map(retVO, ZoneNLicenseInfoDTO.class);
		
		List<ZoneLicenseCodeInfoVO> codeVOLIST = zoneService.findAllZoneCodeInfo();
		List<ZoneLicenseCodeInfoDTO> codeDTOLIST = modelMapper.map(codeVOLIST, new TypeToken<List<ZoneLicenseCodeInfoDTO>>() {}.getType());
		mav.addObject("zoneNLicense", retDTO);
		mav.addObject("codeList", codeDTOLIST);
		return mav;
	}
	
	@GetMapping("/zone/updatePage")
	public ModelAndView zoneUpdatePage(ZoneInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/ZoneUpdate");
		ZoneInfoVO vo = modelMapper.map(dto, ZoneInfoVO.class);
		ZoneInfoVO retVO = zoneService.findZoneWithNO(vo);
		List<CompanyInfoVO> companyVOLIST = companyService.findAllCompanyInfoAll();
		List<CompanyInfoDTO> companyDTOLIST = modelMapper.map(companyVOLIST, new TypeToken<List<CompanyInfoDTO>>() {}.getType() );
		mav.addObject("companyList", companyDTOLIST);
		mav.addObject("zoneInfo", retVO);
		return mav;
	}
	
	@GetMapping("/zone/historyPage")
	public ModelAndView zoneHistoryPage() {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/History");
		List<ZoneInfoVO> voList = zoneService.findAllZoneList();
		List<ZoneInfoDTO> dtoList =  
				modelMapper.map(voList, new TypeToken<List<ZoneInfoDTO>>() {}.getType());
		mav.addObject("zoneList", dtoList);
		return mav;
	}
	
	@GetMapping("/zone/historyDetailPage")
	public ModelAndView zoneHistoryDetailPage(ZoneInfoDTO dto) {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/HistoryDetail");
		ZoneInfoVO vo = modelMapper.map(dto, ZoneInfoVO.class);
		List<ZoneHistoryInfoVO> voList = zoneService.findAllZoneHistoryAsZoneNo(vo);
		List<ZoneHistoryInfoDTO> dtoList = modelMapper.map(voList, new TypeToken<List<ZoneHistoryInfoDTO>>() {}.getType());
		List<ZoneLicenseHistoryInfoVO> historyVOList = zoneService.findZoneLicenseHistoryInfo(vo);
		mav.addObject("historyList", dtoList);
		mav.addObject("licenseHistoryList", historyVOList);
		return mav;
	}
	
	@GetMapping("/zone/licenseStateHistoryPage")
	public ModelAndView licenseStateHistoryPage() {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/LicenseStateHistory");
		List<ZoneLicenseStateHistoryInfoVO> voList = zoneService.findAllLicenseStateHistoryInfo();
		List<ZoneLicenseStateHistoryInfoDTO> dtoList = modelMapper.map(voList, new TypeToken<List<ZoneLicenseStateHistoryInfoDTO>> () {}.getType());
		mav.addObject("stateHistoryList", dtoList);
		return mav;
	}
	
	@GetMapping("/zoneLicense/taCreateFilePage")
	public ModelAndView TaCreateFile() {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/TaCreateFile");
		List<ZoneInfoVO> voList = zoneService.findAllZoneListOnlyExist();
		List<ZoneInfoDTO> dtoList = modelMapper.map(voList, new TypeToken<List<ZoneInfoDTO>>() {}.getType());
		List<ZoneLicenseCodeInfoVO> codeVOLIST = zoneService.findAllZoneCodeInfo();
		List<ZoneLicenseCodeInfoDTO> codeDTOLIST = modelMapper.map(codeVOLIST, new TypeToken<List<ZoneLicenseCodeInfoDTO>>() {}.getType());
		mav.addObject("zoneList", dtoList);
		mav.addObject("codeList", codeDTOLIST);
		return mav;
	}
	
	@GetMapping("/zoneLicense/taVerifyFilePage")
	public ModelAndView TaVerifyFile() {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/TaVerifyFile");
		return mav;
	}
	
	@GetMapping("/zone/taWhetherPage")
	public ModelAndView TaWhetherFile() {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/TaWhether");
		List<ZoneInfoVO> voList = zoneService.findAllZoneList();
		List<ZoneInfoDTO> dtoList =  modelMapper.map(voList, new TypeToken<List<ZoneInfoDTO>>() {}.getType());
		mav.addObject("zoneList", dtoList);
		return mav;
	}
	
	@GetMapping("/zoneLicense/LicenseAllowInfoPage")
	public ModelAndView LicenseAllowInfo() {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/LicenseAllowInfo");
		List<ZoneInfoVO> voList = zoneService.findAllZoneList();
		List<ZoneInfoDTO> dtoList =  modelMapper.map(voList, new TypeToken<List<ZoneInfoDTO>>() {}.getType());
		mav.addObject("zoneList", dtoList);
		return mav;
	}
	
	@GetMapping("/zoneLicense/LicenseAllowEnrollPage")
	public ModelAndView LicenseAllowEnroll() {
		ModelAndView mav = new ModelAndView("thymeleaf/zone/LicenseAllowEnroll");
		return mav;
	}
	
	
	
}
