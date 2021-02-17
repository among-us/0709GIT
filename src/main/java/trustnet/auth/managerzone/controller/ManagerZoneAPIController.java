package trustnet.auth.managerzone.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.dto.CommonResponseDTO;
import trustnet.auth.common.service.SecurityService;
import trustnet.auth.common.vo.SecurityAuthInfoVO;
import trustnet.auth.manager.controller.except.ParameterException;
import trustnet.auth.managerzone.code.ManagerZoneResultEnum;
import trustnet.auth.managerzone.controller.dto.ManagerZoneInfoDTO;
import trustnet.auth.managerzone.controller.dto.ManagerZoneResponseDTO;
import trustnet.auth.managerzone.service.ManagerZoneService;
import trustnet.auth.managerzone.service.vo.ManagerZoneInfoVO;
import trustnet.auth.user.controller.except.TokenValidationException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ManagerZoneAPIController {

	private final ModelMapper modelMapper;
	private final ManagerZoneService managerZoneService;
	private final SecurityService securityService;
	
	@PostMapping("/managerZone")
	public CommonResponseDTO<Object> enrollMatch(@CookieValue(value = "UNETAUTHTOKEN", defaultValue = "") String token, @RequestBody List<ManagerZoneInfoDTO> dtoList) {
		log.info("[enrollMatch => CompanyInfoDTO\n] {}", dtoList.toString());

		if(dtoList.size() == 0) {
			log.warn("PARAMETER EXCEPTION");
			throw new ParameterException();
		}
		
		List<ManagerZoneInfoVO> voList = modelMapper.map(dtoList, new TypeToken<List<ManagerZoneInfoVO>>() {}.getType());
		boolean result = managerZoneService.isFindManagerZone(voList.get(0));
		if(result) 
			result = managerZoneService.deleteManagerZoneWithTAMNO(voList.get(0));
		
		ManagerZoneResultEnum resultEnum;
		ManagerZoneResponseDTO resDTO;
		for(ManagerZoneInfoVO vo : voList) {
			boolean ret = managerZoneService.saveManagerZone(vo);
			if(!ret) {
				resultEnum = ManagerZoneResultEnum.FAIL;
				resDTO = new ManagerZoneResponseDTO(resultEnum);
				log.info("[enrollMatch => CompanyResponseInfoDTO\n] {}", resDTO.toString());

				return CommonResponseDTO.builder().data(resDTO).build();
			}
		}
		
		SecurityAuthInfoVO securityVO = securityService.checkTokenValidation(token);
		if(securityVO.getUser_no() == 0 || securityVO.getUser_id() == null) 
			throw new TokenValidationException();
		
		if(!securityVO.getPermissions().equalsIgnoreCase("admin"))
			throw new TokenValidationException();
		
		resultEnum = ManagerZoneResultEnum.SUCCESS;
		resDTO = new ManagerZoneResponseDTO(resultEnum);
		log.info("[enrollMatch => CompanyResponseInfoDTO\n] {}", resDTO.toString());
		
		return CommonResponseDTO.builder().data(resDTO).build();
	}
	
	
	
}
