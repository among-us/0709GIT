package trustnet.auth.company.controller;

import static org.hamcrest.CoreMatchers.both;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.dto.CommonResponseDTO;
import trustnet.auth.common.service.SecurityService;
import trustnet.auth.common.vo.SecurityAuthInfoVO;
import trustnet.auth.company.code.CompanyResultEnum;
import trustnet.auth.company.controller.dto.CompProjZoneListDTO;
import trustnet.auth.company.controller.dto.CompanyHistoryInfoDTO;
import trustnet.auth.company.controller.dto.CompanyInfoDTO;
import trustnet.auth.company.controller.dto.CompanyMatchingDTO;
import trustnet.auth.company.controller.dto.CompanyProjZoneDTO;
import trustnet.auth.company.controller.dto.CompanyProjectHistoryDTO;
import trustnet.auth.company.controller.dto.CompanyProjectInfoDTO;
import trustnet.auth.company.controller.dto.CompanyResponseInfoDTO;
import trustnet.auth.company.service.CompProjZoneListVO;
import trustnet.auth.company.service.CompanyService;
import trustnet.auth.company.service.vo.CompProjZoneListHistoryVO;
import trustnet.auth.company.service.vo.CompanyHistoryInfoVO;
import trustnet.auth.company.service.vo.CompanyInfoVO;
import trustnet.auth.company.service.vo.CompanyMatchingVO;
import trustnet.auth.company.service.vo.CompanyProjZoneVO;
import trustnet.auth.company.service.vo.CompanyProjectHistoryVO;
import trustnet.auth.company.service.vo.CompanyProjectInfoVO;
import trustnet.auth.manager.code.ManagerResultEnum;
import trustnet.auth.manager.controller.dto.ManagerCheckInfoDTO;
import trustnet.auth.manager.controller.dto.ManagerHistoryInfoDTO;
import trustnet.auth.manager.controller.dto.ManagerInfoResponseDTO;
import trustnet.auth.manager.controller.except.ParameterException;
import trustnet.auth.manager.service.vo.ManagerHistoryInfoVO;
import trustnet.auth.manager.service.vo.ManagerInfoVO;
import trustnet.auth.user.controller.dto.UserInfoDTO;
import trustnet.auth.user.controller.except.TokenValidationException;
import trustnet.auth.zone.controller.dto.ZoneLicenseHistoryInfoDTO;
import trustnet.auth.zone.service.ZoneService;
import trustnet.auth.zone.service.vo.ZoneLicenseHistoryInfoVO;
import trustnet.tas.except.TNAuthException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CompanyAPIController {

	private final CompanyService companyService;
	private final ModelMapper modelMapper;
	private final SecurityService securityService;

	@PostMapping("/company")
	public CommonResponseDTO<Object> enrollCompany(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated CompanyInfoDTO dto, BindingResult errors) {
		log.info("[enrollCompany => CompanyInfoDTO\n] {}", dto.toString());
		log.info("errors ==> {}", errors.hasErrors());

		if (errors.hasErrors()) {
			CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		if (dto.getComp_name().isEmpty()) {
			log.warn("PARAMETER EXCEPTION");
			throw new ParameterException();
		}

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);

		CompanyInfoVO vo = modelMapper.map(dto, CompanyInfoVO.class);
		// 초기 등록할때에 default 값으로 설정 
		vo.setExist("Y");
		boolean result = companyService.saveCompanyInfo(vo);
		int a = vo.getComp_no();
		String b = Integer.toString(a);
		log.info(b);
		CompanyResultEnum retEnum = result ? CompanyResultEnum.SUCCESS
				: CompanyResultEnum.FAIL;
		CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(retEnum);

		CompanyHistoryInfoVO historyVO = modelMapper.map(vo, CompanyHistoryInfoVO.class);
		historyVO.setAction("C");
		historyVO.setIssuer_user_no(securityVO.getUser_no());

		log.info("security vo issuer user no :" + securityVO.toString());

		log.info("HISTORY VO TEST :" + historyVO.toString());
		result = companyService.saveCompanyHistory(historyVO);
		log.info("[enrollCompany => CompanyResponseInfoDTO\n] {}", resDTO.toString());

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@PutMapping("/company")
	public CommonResponseDTO<Object> updateCompany(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			CompanyInfoDTO dto) {
		log.info("[updateCompany => CompanyInfoDTO\n] {}", dto.toString());
		if (String.valueOf(dto.getComp_no()).isEmpty() || dto.getComp_name().isEmpty()) {
			log.warn("PARAMETER EXCEPTION");
			throw new ParameterException();
		}

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!securityVO.getUser_id().equalsIgnoreCase("admin"))
			throw new TokenValidationException();

		CompanyInfoVO vo = modelMapper.map(dto, CompanyInfoVO.class);
		boolean result = companyService.updateCompanyInfo(vo);
		CompanyResultEnum retEnum = result ? CompanyResultEnum.SUCCESS
				: CompanyResultEnum.FAIL;
		CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(retEnum);

		log.info("[updateCompany => CompanyResponseInfoDTO\n] {}", resDTO.toString());

		CompanyHistoryInfoVO historyVO = new CompanyHistoryInfoVO();
		CompanyHistoryInfoVO historyInfoVO = modelMapper.map(vo,
				CompanyHistoryInfoVO.class);

		log.info(" HISTORY VO >>>>> " + historyInfoVO.toString());
		historyInfoVO.setIssuer_user_no(securityVO.getUser_no());
		historyInfoVO.setAction("U");
		result = companyService.saveCompanyHistory(historyInfoVO);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@DeleteMapping("/company")
	public CommonResponseDTO<Object> deleteCompany(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			CompanyInfoDTO dto) {
		log.info("deleteCompany => CompanyInfoDTO : \n {}", dto.toString());
		if (String.valueOf(dto.getComp_no()).isEmpty()) {
			log.warn("PARAMETER EXCEPTION");
			throw new ParameterException();
		}

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!securityVO.getUser_id().equalsIgnoreCase("admin"))
			throw new TokenValidationException();

		CompanyInfoVO vo = modelMapper.map(dto, CompanyInfoVO.class);
		int comp_no = vo.getComp_no();
		log.info("DELETE FLOW VO STRING > >>> >> " + vo.toString());
		boolean result = companyService.deleteCompanyInfo(vo);
		CompanyResultEnum retEnum = result ? CompanyResultEnum.SUCCESS
				: CompanyResultEnum.FAIL;
		CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(retEnum);

		log.info("deleteCompany => CompanyResponseInfoDTO : \n {}", resDTO.toString());

		CompanyHistoryInfoVO historyInfoVO = modelMapper.map(vo,
				CompanyHistoryInfoVO.class);
		log.info("DELETE FLOW >>> " + historyInfoVO);
		historyInfoVO.setComp_no(comp_no);
		historyInfoVO.setIssuer_user_no(securityVO.getUser_no());
		historyInfoVO.setAction("D");
		result = companyService.deleteCompanyHistory(historyInfoVO);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@GetMapping("/company/check")
	public CommonResponseDTO<Object> checkGroup(@Validated CompanyInfoDTO dto,
			BindingResult errors) {

		CompanyInfoVO vo = modelMapper.map(dto, CompanyInfoVO.class);
		boolean result = companyService.isFindGroup(vo);
		CompanyResultEnum err = result ? CompanyResultEnum.EXISTFAIL
				: CompanyResultEnum.EXISTSUCCESS;
		CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(err);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@GetMapping("/company/update-check")
	public CommonResponseDTO<Object> checkUpdateGroupName(@Validated CompanyInfoDTO dto,
			BindingResult errors) {

		int comp_no = dto.getComp_no();
		//		String asis_comp_name = companyService.getCompNameWithCompNo(comp_no);

		String asis_comp_name = dto.getAsis_comp_name();
		String tobe_comp_name = dto.getComp_name();

		CompanyInfoVO vo = modelMapper.map(dto, CompanyInfoVO.class);

		JSONObject obj = new JSONObject();

		if (asis_comp_name.equals(tobe_comp_name)) {
			obj.put("errorCode", -1);
			obj.put("errorMessage", "기존에 사용하던 GROUP명과 동일합니다.");
			return CommonResponseDTO.builder().data(obj).build();
		} else {
			boolean result = companyService.isFindGroup(vo);
			if (result == true) {
				obj.put("errorCode", 1);
				obj.put("errorMessage", "사용하고있는 GROUP명 입니다.");
				return CommonResponseDTO.builder().data(obj).build();
			} else {
				obj.put("errorCode", 0);
				obj.put("errorMessage", "사용할 수 있는 GROUP명 입니다.");
				return CommonResponseDTO.builder().data(obj).build();
			}
		}
	}

	@PostMapping("/company/project")
	public CommonResponseDTO<Object> enrollProject(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated CompanyProjectInfoDTO dto, BindingResult errors) {
		log.info("[enrollCompanyProjectInfoDTO=> CompanyProjectDTO\n] {}",
				dto.toString());
		log.info("errors ==> {}", errors.hasErrors());

		if (errors.hasErrors()) {
			CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);

		CompanyProjectInfoVO vo = modelMapper.map(dto, CompanyProjectInfoVO.class);
		int comp_no = vo.getComp_no();
		int zone_no = vo.getZone_no();

		boolean CheckResult = companyService.isDupCompZoneMatching(vo);
		log.info("Check Result >>> " + CheckResult);
		if (CheckResult == true) {
			return CommonResponseDTO.builder().data(true).build();
		} else {

			vo.setExist("Y");
			boolean result = companyService.saveCompanyProjectInfo(vo);
			log.info("SAVE COMPANY PROJECT INFO" + vo.toString());

			CompProjZoneListVO matchVO = new CompProjZoneListVO();
			matchVO.setComp_no(comp_no);
			matchVO.setZone_no(zone_no);

			CompProjZoneListHistoryVO matchHistoryVO = new CompProjZoneListHistoryVO();
			matchHistoryVO.setComp_no(comp_no);
			matchHistoryVO.setZone_no(zone_no);
			matchHistoryVO.setAction("C");
			matchHistoryVO.setIssuer_user_no(securityVO.getUser_no());
			boolean matchHistoryResult = companyService.saveMatchHistory(matchHistoryVO);
			log.info("MATCHING HISTORY RESULT ? : " + matchHistoryResult);

			CompanyResultEnum retEnum = result ? CompanyResultEnum.SUCCESS
					: CompanyResultEnum.FAIL;
			CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(retEnum);

			//		CompanyProjectHistoryVO historyVO = modelMapper.map(vo,
			//				CompanyProjectHistoryVO.class);
			//		historyVO.setAction("C");
			//		historyVO.setIssuer_user_no(securityVO.getUser_no());
			//		log.info("SAVE PROJECT HISTORY VALUE : " + historyVO.toString());
			//		result = companyService.saveCompanyProjectHistory(historyVO);

			log.info("[enrollProjectCompany => CompanyResponseInfoDTO\n] {}",
					resDTO.toString());

			return CommonResponseDTO.builder().data(resDTO).build();
		} //else flow 
	}

	@GetMapping("/company/project-check")
	public CommonResponseDTO<Object> checkGroup(@Validated CompanyProjectInfoDTO dto,
			BindingResult errors) {

		CompanyProjectInfoVO vo = modelMapper.map(dto, CompanyProjectInfoVO.class);
		boolean result = companyService.isFindProject(vo);
		//				boolean result = companyService.isFindGroup(vo);
		CompanyResultEnum err = result ? CompanyResultEnum.EXISTFAIL
				: CompanyResultEnum.EXISTSUCCESS;
		CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(err);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	//0422
	@GetMapping("/company/project/matching")
	public String[] getProjZoneMatchName(@Validated CompanyProjectInfoDTO dto,
			BindingResult errors) {
		log.info("DTO STRING " + dto.toString());
		int comp_no = dto.getComp_no();
		CompanyProjectInfoVO vo = modelMapper.map(dto, CompanyProjectInfoVO.class);
		String VOLIST[] = companyService.getProjZoneMatchName(comp_no);
		log.info("ARRAYS VO LIST TEST " + Arrays.toString(VOLIST));

		return VOLIST;

	}

	@GetMapping("/company/project-list")
	public JSONObject findAllProjectList(@Validated CompanyProjectInfoDTO dto,
			HttpServletRequest request) throws Exception {
		CompanyProjectInfoVO vo = modelMapper.map(dto, CompanyProjectInfoVO.class);
		List<CompanyProjectInfoVO> volist = companyService.findAllProjectList(vo);
		int totalCount = companyService.countProjectList();
		String strcount = Integer.toString(totalCount);

		JSONObject result = new JSONObject();
		result.put("data", volist);
		result.put("totalCount", totalCount);

		return result;
	}

	@GetMapping("/company/history")
	public JSONObject companyHistoryPage(@Validated CompanyHistoryInfoDTO dto,
			HttpServletRequest request) throws Exception {
		log.info("dto list : " + dto.toString());
		CompanyHistoryInfoVO vo = modelMapper.map(dto, CompanyHistoryInfoVO.class);

		List<CompanyHistoryInfoVO> volist = companyService.findCompanyHistoryAll(vo);
		log.info("COMPANY HISTORY LIST " + volist.toString());
		int totalCount = companyService.countHistoryList();
		String strcount = Integer.toString(totalCount);
		log.info("COMPANY LIST COUNT : " + strcount);

		JSONObject result = new JSONObject();
		result.put("data", volist);
		result.put("totalCount", totalCount);

		return result;
	}

	@GetMapping("/company/project-history")
	public JSONObject companyProjectHistoryPage(@Validated CompanyProjectHistoryDTO dto,
			HttpServletRequest request) throws Exception {
		log.info("dto list : " + dto.toString());
		CompanyProjectHistoryVO vo = modelMapper.map(dto, CompanyProjectHistoryVO.class);

		List<CompanyProjectHistoryVO> volist = companyService
				.findCompanyProjectHistoryAll(vo);
		log.info("기업 프로젝트 히스토리 " + volist.toString());

		int totalCount = companyService.countProjectHistoryList();
		String strcount = Integer.toString(totalCount);
		log.info("COMPANY PROJECT LIST COUNT : " + strcount);

		JSONObject result = new JSONObject();
		result.put("data", volist);
		result.put("totalCount", totalCount);

		return result;
	}

	@PutMapping("/company/match")
	public CommonResponseDTO<Object> enrollProjZoneMatching(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			@Validated CompanyMatchingDTO dto, BindingResult errors) {
		log.info("[enrollMatching => CompanyMatchingDTO\n] {}", dto.toString());
		log.info("errors ==> {}", errors.hasErrors());

		int comp_no = dto.getComp_no();
		String comp_name = dto.getComp_name();
		String proj_name = dto.getProj_name();
		int proj_no = companyService.getProjectNoWithName(proj_name);

		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		log.info("SECURITY VO STRING : " + securityVO.toString());
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!(securityVO.getPerm_no() == 1)) {
			throw new TokenValidationException();
		}

		CompanyMatchingVO vo = modelMapper.map(dto, CompanyMatchingVO.class);
		vo.setProj_no(proj_no);

		int project_number = vo.getProj_no();
		int zone_no = vo.getZone_no();
		log.info("project no : " + project_number + "  zone no : " + zone_no);

		int Projcheck = companyService.existNumber(project_number);
		int Zonecheck = companyService.existZoneNumber(zone_no);
		log.info("PROJECT CHECK : " + Projcheck + "   ZONE CHECK : " + Zonecheck);

		if (Zonecheck == 1) {
			log.info("true"); // 이미 존재함
			return CommonResponseDTO.builder().data("실패").build();
		} else {
			log.info("false"); // 기존에 없던 번호들 -> 등록 가능
			vo.setZone_no(zone_no);
			boolean result = companyService.saveMatchingProZone(vo);
			log.info("ELSE FLOW MATCHING VO VALUE : " + vo.toString());
			CompanyResultEnum retEnum = result ? CompanyResultEnum.SUCCESS
					: CompanyResultEnum.FAIL;
			CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(retEnum);

			// INSERT MATCHING HISTORY 
			CompProjZoneListHistoryVO matchHistoryVO = new CompProjZoneListHistoryVO();
			matchHistoryVO.setProj_name(proj_name);
			matchHistoryVO.setComp_name(comp_name);
			matchHistoryVO.setZone_no(zone_no);
			matchHistoryVO.setIssuer_user_no(securityVO.getUser_no());
			matchHistoryVO.setAction("U");
			log.info("MATCH HISTORY VO : " + matchHistoryVO.toString());
			boolean matchHistoryResult = companyService
					.saveMatchZoneHistory(matchHistoryVO);
			log.info("MATCH HISTORY RESULT : " + matchHistoryResult);

			return CommonResponseDTO.builder().data("성공").build();

		}
	}

	@PutMapping("/project")
	public CommonResponseDTO<Object> updateProject(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			CompanyProjectInfoDTO dto) {
		log.info("[updateProject => CompanyProjectInfoDTO\n] {}", dto.toString());
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!(securityVO.getPerm_no() == 1)) {
			throw new TokenValidationException();
		}

		CompanyProjectInfoVO vo = modelMapper.map(dto, CompanyProjectInfoVO.class);
		boolean checkResult = companyService.isFindProject(vo);

		if (checkResult == true) {
			JSONObject exist = new JSONObject();
			exist.put("errorCode", -1);
			exist.put("errorMessage", "이미 매칭되어있는 PROJECT입니다.");
			return CommonResponseDTO.builder().data(exist).build();
		} else {
			boolean result = companyService.updateProject(vo);
			log.info("PROJECT UPDATE DTO : " + vo.toString());

			String proj_name = vo.getProj_name();
			int proj_no = vo.getProj_no();
			int zone_no = vo.getZone_no();

			CompanyProjectHistoryVO historyVO = new CompanyProjectHistoryVO();
			int comp_no = dto.getComp_no();
			historyVO.setComp_no(comp_no);
			historyVO.setZone_no(zone_no);
			historyVO.setIssuer_user_no(securityVO.getUser_no());
			historyVO.setAction("U");
			boolean historyResult = companyService.updateProjectHistory(historyVO);

			CompanyResultEnum retEnum = result ? CompanyResultEnum.SUCCESS
					: CompanyResultEnum.FAIL;
			CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(retEnum);
			log.info("[updateCompany => CompanyResponseInfoDTO\n] {}", resDTO.toString());

			return CommonResponseDTO.builder().data(resDTO).build();
		}
	}

	@GetMapping("/main/group")
	public CommonResponseDTO<Object> getProjectWithCompNo(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			CompProjZoneListDTO dto) throws TNAuthException {

		CompProjZoneListVO vo = modelMapper.map(dto, CompProjZoneListVO.class);
		List<CompProjZoneListVO> voList = companyService.getProjectWithCompNo(vo);
		int size = voList.size();
		List<String> projectName = new ArrayList<String>();

		if (size == 0) {
			JSONObject error = new JSONObject();
			error.put("errorCode", -1);
			error.put("errorMessage", "size Error");
			log.info("SIZE ERROR");
			return CommonResponseDTO.builder().data(error).build();
		} else {
			for (int i = 0; i < size; i++) {
				projectName.add(voList.get(i).getZone_name());
			}

			return CommonResponseDTO.builder().data(projectName).build();
		}
	}

} // controller end
