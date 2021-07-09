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
import trustnet.auth.company.service.CompanyService;
import trustnet.auth.company.service.vo.CompProjZoneListHistoryVO;
import trustnet.auth.company.service.vo.CompProjZoneListVO;
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
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		log.info("Post Company => " + securityVO.getUser_id());
		if (errors.hasErrors()) {
			CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		if (dto.getComp_name().isEmpty()) {
			throw new ParameterException();
		}

		CompanyInfoVO vo = modelMapper.map(dto, CompanyInfoVO.class);
		vo.setExist("Y");
		boolean result = companyService.saveCompanyInfo(vo);
		CompanyResultEnum retEnum = result ? CompanyResultEnum.SUCCESS
				: CompanyResultEnum.FAIL;
		CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(retEnum);
		CompanyHistoryInfoVO historyVO = modelMapper.map(vo, CompanyHistoryInfoVO.class);
		historyVO.setAction("C");
		historyVO.setIssuer_user_no(securityVO.getUser_no());
		result = companyService.saveCompanyHistory(historyVO);
		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@PutMapping("/company")
	public CommonResponseDTO<Object> updateCompany(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			CompanyInfoDTO dto) {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		log.info("Put Company => " + securityVO.getUser_id());
		if (String.valueOf(dto.getComp_no()).isEmpty() || dto.getComp_name().isEmpty()) {
			throw new ParameterException();
		}

		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!securityVO.getUser_id().equalsIgnoreCase("admin"))
			throw new TokenValidationException();

		CompanyInfoVO vo = modelMapper.map(dto, CompanyInfoVO.class);
		boolean result = companyService.updateCompanyInfo(vo);
		CompanyResultEnum retEnum = result ? CompanyResultEnum.SUCCESS
				: CompanyResultEnum.FAIL;
		CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(retEnum);

		CompanyHistoryInfoVO historyVO = new CompanyHistoryInfoVO();
		CompanyHistoryInfoVO historyInfoVO = modelMapper.map(vo,
				CompanyHistoryInfoVO.class);

		historyInfoVO.setIssuer_user_no(securityVO.getUser_no());
		historyInfoVO.setAction("U");
		result = companyService.saveCompanyHistory(historyInfoVO);

		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@DeleteMapping("/company")
	public CommonResponseDTO<Object> deleteCompany(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			CompanyInfoDTO dto) {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		log.info("Delete Company => " + securityVO.getUser_id());
		if (String.valueOf(dto.getComp_no()).isEmpty()) {
			throw new ParameterException();
		}

		if (securityVO.getUser_no() == 0 || securityVO.getUser_id() == null)
			throw new TokenValidationException();

		if (!securityVO.getUser_id().equalsIgnoreCase("admin"))
			throw new TokenValidationException();

		CompanyInfoVO vo = modelMapper.map(dto, CompanyInfoVO.class);
		int comp_no = vo.getComp_no();
		boolean result = companyService.deleteCompanyInfo(vo);
		CompanyResultEnum retEnum = result ? CompanyResultEnum.SUCCESS
				: CompanyResultEnum.FAIL;
		CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(retEnum);

		CompanyHistoryInfoVO historyInfoVO = modelMapper.map(vo,
				CompanyHistoryInfoVO.class);
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
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		log.info("Post Comapny Project => " + securityVO.getUser_id());

		if (errors.hasErrors()) {
			CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(-7070,
					errors.getFieldError().getDefaultMessage());
			return CommonResponseDTO.builder().data(resDTO).build();
		}

		CompanyProjectInfoVO vo = modelMapper.map(dto, CompanyProjectInfoVO.class);
		int comp_no = vo.getComp_no();
		int zone_no = vo.getZone_no();

		boolean CheckResult = companyService.isDupCompZoneMatching(vo);
		if (CheckResult == true) {
			return CommonResponseDTO.builder().data(true).build();
		} else {
			vo.setExist("Y");
			boolean result = companyService.saveCompanyProjectInfo(vo);
			CompProjZoneListVO matchVO = new CompProjZoneListVO();
			matchVO.setComp_no(comp_no);
			matchVO.setZone_no(zone_no);
			CompProjZoneListHistoryVO matchHistoryVO = new CompProjZoneListHistoryVO();
			matchHistoryVO.setComp_no(comp_no);
			matchHistoryVO.setZone_no(zone_no);
			matchHistoryVO.setAction("C");
			matchHistoryVO.setIssuer_user_no(securityVO.getUser_no());
			boolean matchHistoryResult = companyService.saveMatchHistory(matchHistoryVO);
			CompanyResultEnum retEnum = result ? CompanyResultEnum.SUCCESS
					: CompanyResultEnum.FAIL;
			CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(retEnum);

			return CommonResponseDTO.builder().data(resDTO).build();
		}
	}

	@GetMapping("/company/project-check")
	public CommonResponseDTO<Object> checkGroup(@Validated CompanyProjectInfoDTO dto,
			BindingResult errors) {
		CompanyProjectInfoVO vo = modelMapper.map(dto, CompanyProjectInfoVO.class);
		boolean result = companyService.isFindProject(vo);
		CompanyResultEnum err = result ? CompanyResultEnum.EXISTFAIL
				: CompanyResultEnum.EXISTSUCCESS;
		CompanyResponseInfoDTO resDTO = new CompanyResponseInfoDTO(err);
		return CommonResponseDTO.builder().data(resDTO).build();
	}

	@GetMapping("/company/project/matching")
	public String[] getProjZoneMatchName(@Validated CompanyProjectInfoDTO dto,
			BindingResult errors) {
		int comp_no = dto.getComp_no();
		CompanyProjectInfoVO vo = modelMapper.map(dto, CompanyProjectInfoVO.class);
		String VOLIST[] = companyService.getProjZoneMatchName(comp_no);

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
		CompanyHistoryInfoVO vo = modelMapper.map(dto, CompanyHistoryInfoVO.class);
		List<CompanyHistoryInfoVO> volist = companyService.findCompanyHistoryAll(vo);
		int totalCount = companyService.countHistoryList();
		String strcount = Integer.toString(totalCount);
		JSONObject result = new JSONObject();
		result.put("data", volist);
		result.put("totalCount", totalCount);
		return result;
	}

	@GetMapping("/company/project-history")
	public JSONObject companyProjectHistoryPage(@Validated CompanyProjectHistoryDTO dto,
			HttpServletRequest request) throws Exception {
		CompanyProjectHistoryVO vo = modelMapper.map(dto, CompanyProjectHistoryVO.class);
		List<CompanyProjectHistoryVO> volist = companyService
				.findCompanyProjectHistoryAll(vo);
		int totalCount = companyService.countProjectHistoryList();
		String strcount = Integer.toString(totalCount);
		JSONObject result = new JSONObject();
		result.put("data", volist);
		result.put("totalCount", totalCount);
		return result;
	}

	@PutMapping("/project")
	public CommonResponseDTO<Object> updateProject(
			@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token,
			CompanyProjectInfoDTO dto) {
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		log.info("Put Group x Project => " + securityVO.getUser_id());
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
			return CommonResponseDTO.builder().data(error).build();
		} else {
			for (int i = 0; i < size; i++) {
				projectName.add(voList.get(i).getZone_name());
			}

			return CommonResponseDTO.builder().data(projectName).build();
		}
	}

} // APIController End
