package trustnet.auth.zone.service;

import java.lang.management.ManagementPermission;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.service.CommonService;
import trustnet.auth.manager.repository.entity.ManagerCreateFileENTITY;
import trustnet.auth.manager.repository.entity.ManagerHistoryInfoENTITY;
import trustnet.auth.manager.repository.entity.ManagerInfoENTITY;
import trustnet.auth.manager.service.vo.ManagerCreateFileVO;
import trustnet.auth.manager.service.vo.ManagerHistoryInfoVO;
import trustnet.auth.manager.service.vo.ManagerInfoVO;
import trustnet.auth.zone.controller.dto.ZoneSettingFileDTO;
import trustnet.auth.zone.controller.dto.ZoneTimeLicenseHistoryDTO;
import trustnet.auth.zone.controller.dto.ZoneWetherLicenseDTO;
import trustnet.auth.zone.repository.ZoneMapper;
import trustnet.auth.zone.repository.entity.ZoneHistoryInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneLicenseAllowInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneLicenseCodeInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneLicenseHistoryInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneLicenseInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneLicenseStateHistoryInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneLicenseUpdateDBENTITY;
import trustnet.auth.zone.repository.entity.ZoneLicenseUpdateENTITY;
import trustnet.auth.zone.repository.entity.ZoneNLicenseInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneTimeLicenseENTITY;
import trustnet.auth.zone.repository.entity.ZoneTimeLicenseHistoryENTITY;
import trustnet.auth.zone.repository.entity.ZoneWetherLicenseENTITY;
import trustnet.auth.zone.service.vo.ZoneHistoryInfoVO;
import trustnet.auth.zone.service.vo.ZoneInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseAllowInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseCodeInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseHistoryInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseStateHistoryInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseUpdateDBVO;
import trustnet.auth.zone.service.vo.ZoneLicenseUpdateVO;
import trustnet.auth.zone.service.vo.ZoneNLicenseInfoVO;
import trustnet.auth.zone.service.vo.ZoneSettingFileVO;
import trustnet.auth.zone.service.vo.ZoneTimeLicenseHistoryVO;
import trustnet.auth.zone.service.vo.ZoneTimeLicenseVO;
//import trustnet.auth.zone.service.vo.ZoneVerifyIntegrityVO;
//import trustnet.tas.except.TNAuthException;
//import trustnet.tas.valid.Integrity;
import trustnet.auth.zone.service.vo.ZoneWetherLicenseVO;
import trustnet.tas.except.TNAuthException;
import trustnet.tas.valid.Integrity;

@Service
@Slf4j
@RequiredArgsConstructor
public class ZoneService {

	private final ZoneMapper mapper;
	private final CommonService commonService;
	private final ModelMapper modelMapper;

	public boolean saveZoneInfo(ZoneInfoVO vo) {
		int result = mapper.saveZoneInfo(vo);
		return result > 0;
	}

	public boolean saveTimeLicenseHistory(ZoneTimeLicenseHistoryVO vo) {
		int result = mapper.saveTimeLicenseHistory(vo);
		return result > 0;
	}

	public boolean saveZoneLicenseInfo(ZoneLicenseInfoVO vo) {
		int result = mapper.saveZoneLicenseInfo(vo);
		return result > 0;
	}

	public List<ZoneLicenseAllowInfoVO> findAllZoneList(ZoneLicenseAllowInfoVO vo) {
		List<ZoneLicenseAllowInfoENTITY> entityList = mapper.findAllZoneList(vo);
		List<ZoneLicenseAllowInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneLicenseAllowInfoVO>>() {
				}.getType());

		return voList;
	}

	public List<ZoneInfoVO> findAllZone() {
		List<ZoneInfoENTITY> entityList = mapper.findAllZone();
		List<ZoneInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneInfoVO>>() {
				}.getType());

		return voList;
	}

	public List<ZoneInfoVO> findAllZoneListOnlyExist() {
		List<ZoneInfoENTITY> entityList = mapper.findAllZoneListOnlyExist();
		List<ZoneInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneInfoVO>>() {
				}.getType());

		return voList;
	}

	public ZoneInfoVO findZoneWithNO(ZoneInfoVO vo) {
		ZoneInfoENTITY entity = modelMapper.map(vo, ZoneInfoENTITY.class);
		ZoneInfoENTITY retENTITY = mapper.findZoneWithNO(entity);
		ZoneInfoVO retVO = modelMapper.map(retENTITY, ZoneInfoVO.class);
		return retVO;
	}

	public List<ZoneNLicenseInfoVO> findAllZoneLicenseList() {
		List<ZoneNLicenseInfoENTITY> entityList = mapper.findAllZoneLicenseList();
		List<ZoneNLicenseInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneNLicenseInfoVO>>() {
				}.getType());
		return voList;
	}

	public boolean updateZoneWithID(ZoneInfoVO vo) {
		ZoneInfoENTITY entity = modelMapper.map(vo, ZoneInfoENTITY.class);
		int result = mapper.updateZoneWithID(entity);
		return result > 0;
	}

	public boolean updateTimelimitLicense(ZoneTimeLicenseVO vo) {
		ZoneTimeLicenseENTITY entity = modelMapper.map(vo, ZoneTimeLicenseENTITY.class);
		int result = mapper.updateTimelimitLicense(entity);
		return result > 0;
	}

	public boolean updateAllowValue(ZoneWetherLicenseVO vo) {
		ZoneWetherLicenseENTITY entity = modelMapper.map(vo,
				ZoneWetherLicenseENTITY.class);
		int result = mapper.updateAllowValue(entity);
		return result > 0;
	}

	public boolean updateRejectValue(ZoneWetherLicenseVO vo) {
		ZoneWetherLicenseENTITY entity = modelMapper.map(vo,
				ZoneWetherLicenseENTITY.class);
		int result = mapper.updateRejectValue(entity);
		return result > 0;
	}

	public boolean updateLicenseAllowInfo(ZoneLicenseAllowInfoVO vo) {
		ZoneLicenseAllowInfoENTITY entity = modelMapper.map(vo,
				ZoneLicenseAllowInfoENTITY.class);
		int result = mapper.updateLicenseAllowInfo(entity);
		return result > 0;
	}

	public boolean updateZoneLicenseInfo(ZoneLicenseUpdateVO vo) {
		ZoneLicenseUpdateENTITY entity = modelMapper.map(vo,
				ZoneLicenseUpdateENTITY.class);
		int result = mapper.updateZoneLicenseInfo(entity);
		return result > 0;
	}

	public boolean deleteZoneWithID(ZoneInfoVO vo) {
		ZoneInfoENTITY entity = modelMapper.map(vo, ZoneInfoENTITY.class);
		int result = mapper.deleteZoneWithID(entity);
		return result > 0;
	}

	public List<ZoneInfoVO> findZoneWithCompNo(ZoneInfoVO vo) {
		ZoneInfoENTITY entity = modelMapper.map(vo, ZoneInfoENTITY.class);
		List<ZoneInfoENTITY> entityList = mapper.findZoneWithCompNo(entity);
		List<ZoneInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneInfoVO>>() {
				}.getType());
		return voList;
	}

	public List<ZoneInfoVO> findZoneWithCompNoWithAlive(ZoneInfoVO vo) {
		ZoneInfoENTITY entity = modelMapper.map(vo, ZoneInfoENTITY.class);
		List<ZoneInfoENTITY> entityList = mapper.findZoneWithCompNoWithAlive(entity);
		List<ZoneInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneInfoVO>>() {
				}.getType());
		return voList;
	}

	public ZoneNLicenseInfoVO findAllZoneLicenseListAsZoneNO(ZoneLicenseInfoVO vo) {
		ZoneLicenseInfoENTITY entity = modelMapper.map(vo, ZoneLicenseInfoENTITY.class);
		ZoneNLicenseInfoENTITY retENTITY = mapper.findAllZoneLicenseListAsZoneNO(entity);
		ZoneNLicenseInfoVO retVOLIST = modelMapper.map(retENTITY,
				ZoneNLicenseInfoVO.class);
		return retVOLIST;
	}

	public boolean deleteZoneLicenseInfo(ZoneLicenseInfoVO vo) {
		ZoneLicenseInfoENTITY entity = modelMapper.map(vo, ZoneLicenseInfoENTITY.class);
		int result = mapper.deleteZoneLicenseInfo(entity);
		return result > 0;
	}

	public boolean saveZoneHistory(ZoneHistoryInfoVO vo) {
		ZoneHistoryInfoENTITY entity = modelMapper.map(vo, ZoneHistoryInfoENTITY.class);
		int result = mapper.saveZoneHistory(entity);
		return result > 0;
	}

	public ZoneInfoVO findZoneInfoAsZoneName(ZoneInfoVO vo) {
		ZoneInfoENTITY entity = modelMapper.map(vo, ZoneInfoENTITY.class);
		ZoneInfoENTITY retENTITY = mapper.findZoneInfoAsZoneName(entity);
		ZoneInfoVO retVO = modelMapper.map(retENTITY, ZoneInfoVO.class);
		return retVO;
	}

	public ZoneTimeLicenseHistoryVO findTimeLicenseInfoAsZoneName(
			ZoneTimeLicenseHistoryVO vo) {
		ZoneTimeLicenseHistoryENTITY entity = modelMapper.map(vo,
				ZoneTimeLicenseHistoryENTITY.class);
		ZoneTimeLicenseHistoryENTITY retENTITY = mapper
				.findTimeLicenseInfoAsZoneName(entity);
		ZoneTimeLicenseHistoryVO retVO = modelMapper.map(retENTITY,
				ZoneTimeLicenseHistoryVO.class);
		return retVO;
	}

	public List<ZoneHistoryInfoVO> findAllZoneHistoryAsZoneNo(ZoneInfoVO vo) {
		ZoneInfoENTITY entity = modelMapper.map(vo, ZoneInfoENTITY.class);
		List<ZoneHistoryInfoENTITY> entityList = mapper
				.findAllZoneHistoryAsZoneNo(entity);
		List<ZoneHistoryInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneHistoryInfoVO>>() {
				}.getType());
		return voList;
	}

	public List<ZoneLicenseCodeInfoVO> findAllZoneCodeInfo() {
		List<ZoneLicenseCodeInfoENTITY> entityList = mapper.findAllZoneCodeInfo();
		List<ZoneLicenseCodeInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneLicenseCodeInfoVO>>() {
				}.getType());
		return voList;
	}

	public boolean saveZoneLicenseHistory(ZoneLicenseHistoryInfoVO vo) {
		ZoneLicenseHistoryInfoENTITY entity = modelMapper.map(vo,
				ZoneLicenseHistoryInfoENTITY.class);
		int result = mapper.saveZoneLicenseHistory(entity);
		return result > 0;
	}

	public ZoneLicenseInfoVO findZoneLicenseAsZoneNONTaaIP(ZoneLicenseInfoVO vo) {
		ZoneLicenseInfoENTITY entity = modelMapper.map(vo, ZoneLicenseInfoENTITY.class);
		ZoneLicenseInfoENTITY retENTITY = mapper.findZoneLicenseAsZoneNONTaaIP(entity);
		ZoneLicenseInfoVO retVO = modelMapper.map(retENTITY, ZoneLicenseInfoVO.class);
		return retVO;
	}

	public List<ZoneLicenseHistoryInfoVO> findZoneLicenseHistoryInfo(ZoneInfoVO vo) {
		ZoneInfoENTITY entity = modelMapper.map(vo, ZoneInfoENTITY.class);
		List<ZoneLicenseHistoryInfoENTITY> entityList = mapper
				.findZoneLicenseHistoryInfo(entity);
		List<ZoneLicenseHistoryInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneLicenseHistoryInfoVO>>() {
				}.getType());
		return voList;
	}

	public List<ZoneLicenseStateHistoryInfoVO> findAllLicenseStateHistoryInfo() {
		List<ZoneLicenseStateHistoryInfoENTITY> entityList = mapper
				.findAllLicenseStateHistoryInfo();
		List<ZoneLicenseStateHistoryInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneLicenseStateHistoryInfoVO>>() {
				}.getType());
		return voList;
	}

	public List<ZoneLicenseHistoryInfoVO> findAllLicenseHistory(
			ZoneLicenseHistoryInfoVO vo) {
		ZoneLicenseHistoryInfoENTITY entity = modelMapper.map(vo,
				ZoneLicenseHistoryInfoENTITY.class);
		List<ZoneLicenseHistoryInfoENTITY> entityList = mapper
				.findAllLicenseHistory(entity);
		List<ZoneLicenseHistoryInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneLicenseHistoryInfoVO>>() {
				}.getType());
		return voList;
	}

	public List<ZoneTimeLicenseHistoryVO> findTimeLicenseHistory(
			ZoneTimeLicenseHistoryVO vo) {
		ZoneTimeLicenseHistoryENTITY entity = modelMapper.map(vo,
				ZoneTimeLicenseHistoryENTITY.class);

		List<ZoneTimeLicenseHistoryENTITY> entityList = mapper
				.findTimeLicenseHistory(entity);
		List<ZoneTimeLicenseHistoryVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneTimeLicenseHistoryVO>>() {
				}.getType());
		return voList;
	}

	public List<ZoneLicenseStateHistoryInfoVO> requestLicenseHistory(
			ZoneLicenseStateHistoryInfoVO vo) {
		ZoneLicenseStateHistoryInfoENTITY entity = modelMapper.map(vo,
				ZoneLicenseStateHistoryInfoENTITY.class);

		double startTime = System.currentTimeMillis();
		List<ZoneLicenseStateHistoryInfoENTITY> entityList = mapper
				.requestLicenseHistory(entity);
		double endTime = System.currentTimeMillis();
		double diff = endTime - startTime;
		List<ZoneLicenseStateHistoryInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneLicenseStateHistoryInfoVO>>() {
				}.getType());
		return voList;
	}

	public List<ZoneLicenseStateHistoryInfoVO> requestLicenseHistoryWithNoFilter(
			ZoneLicenseStateHistoryInfoVO vo) {
		ZoneLicenseStateHistoryInfoENTITY entity = modelMapper.map(vo,
				ZoneLicenseStateHistoryInfoENTITY.class);

		double startTime = System.currentTimeMillis();
		List<ZoneLicenseStateHistoryInfoENTITY> entityList = mapper
				.requestLicenseHistoryWithNoFilter(entity);
		double endTime = System.currentTimeMillis();
		double diff = endTime - startTime;
		List<ZoneLicenseStateHistoryInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneLicenseStateHistoryInfoVO>>() {
				}.getType());
		return voList;
	}

	public boolean isFindZone(ZoneInfoVO vo) {
		ZoneInfoENTITY entity = modelMapper.map(vo, ZoneInfoENTITY.class);
		int result = mapper.isFindZone(entity);
		return result > 0;
	}

	public List<ManagerCreateFileVO> findAllManagerName() {
		List<ManagerCreateFileENTITY> entityList = mapper.findAllManagerName();
		List<ManagerCreateFileVO> tamNameVOList = modelMapper.map(entityList,
				new TypeToken<List<ManagerCreateFileENTITY>>() {
				}.getType());
		return tamNameVOList;
	}

	public List<ZoneLicenseInfoVO> findTaaIp() {
		List<ZoneLicenseInfoENTITY> entityList = mapper.findTaaIp();
		List<ZoneLicenseInfoVO> taaIpList = modelMapper.map(entityList,
				new TypeToken<List<ZoneLicenseInfoENTITY>>() {
				}.getType());
		return taaIpList;
	}

	public List<ManagerInfoVO> findTamIp() {
		List<ManagerInfoENTITY> entityList = mapper.findTamIp();
		List<ManagerInfoVO> tamIpList = modelMapper.map(entityList,
				new TypeToken<List<ManagerInfoENTITY>>() {
				}.getType());
		return tamIpList;
	}

	public List<ManagerInfoVO> findTamLocalPort() {
		List<ManagerInfoENTITY> entityList = mapper.findTamLocalPort();
		List<ManagerInfoVO> tamLocalPortList = modelMapper.map(entityList,
				new TypeToken<List<ManagerInfoENTITY>>() {
				}.getType());
		return tamLocalPortList;
	}

	public List<ZoneWetherLicenseVO> licenseCount(ZoneWetherLicenseVO vo) {
		ZoneWetherLicenseENTITY entity = modelMapper.map(vo,
				ZoneWetherLicenseENTITY.class);
		List<ZoneWetherLicenseENTITY> entityList = mapper.licenseCount(entity);
		List<ZoneWetherLicenseVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneWetherLicenseENTITY>>() {
				}.getType());
		return voList;
	}

	public List<ZoneWetherLicenseVO> licensePublish(ZoneWetherLicenseVO vo) {
		ZoneWetherLicenseENTITY entity = modelMapper.map(vo,
				ZoneWetherLicenseENTITY.class);
		List<ZoneWetherLicenseENTITY> entityList = mapper.licensePublish(entity);
		List<ZoneWetherLicenseVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneWetherLicenseENTITY>>() {
				}.getType());
		return voList;
	}

	public List<ZoneWetherLicenseVO> tpllicensePublish(ZoneWetherLicenseVO vo) {
		ZoneWetherLicenseENTITY entity = modelMapper.map(vo,
				ZoneWetherLicenseENTITY.class);
		List<ZoneWetherLicenseENTITY> entityList = mapper.tpllicensePublish(entity);
		List<ZoneWetherLicenseVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneWetherLicenseENTITY>>() {
				}.getType());
		return voList;
	}

	public List<ZoneWetherLicenseVO> denyCount(ZoneWetherLicenseVO vo) {
		ZoneWetherLicenseENTITY entity = modelMapper.map(vo,
				ZoneWetherLicenseENTITY.class);
		List<ZoneWetherLicenseENTITY> entityList = mapper.denyCount(entity);
		List<ZoneWetherLicenseVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneWetherLicenseENTITY>>() {
				}.getType());
		return voList;
	}

	public List<ZoneWetherLicenseVO> denyTplCount(ZoneWetherLicenseVO vo) {
		ZoneWetherLicenseENTITY entity = modelMapper.map(vo,
				ZoneWetherLicenseENTITY.class);
		List<ZoneWetherLicenseENTITY> entityList = mapper.denyTplCount(entity);
		List<ZoneWetherLicenseVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneWetherLicenseENTITY>>() {
				}.getType());
		return voList;
	}

	public List<ZoneTimeLicenseVO> timeLicenseCount(ZoneTimeLicenseVO tmvo) {
		ZoneTimeLicenseENTITY entity = modelMapper.map(tmvo, ZoneTimeLicenseENTITY.class);
		List<ZoneTimeLicenseENTITY> entityList = mapper.timeLicenseCount(entity);
		List<ZoneTimeLicenseVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneTimeLicenseENTITY>>() {
				}.getType());
		return voList;
	}

	public List<ZoneTimeLicenseVO> timeLicensePublishCount(ZoneTimeLicenseVO tmvo) {
		ZoneTimeLicenseENTITY entity = modelMapper.map(tmvo, ZoneTimeLicenseENTITY.class);
		List<ZoneTimeLicenseENTITY> entityList = mapper.timeLicensePublishCount(entity);
		List<ZoneTimeLicenseVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneTimeLicenseENTITY>>() {
				}.getType());
		return voList;
	}

	public List<ZoneTimeLicenseVO> timeLicenseDenyCount(int zone_no) {
		List<ZoneTimeLicenseENTITY> entityList = mapper.timeLicenseDenyCount(zone_no);
		List<ZoneTimeLicenseVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneTimeLicenseENTITY>>() {
				}.getType());
		return voList;
	}

	public List<ZoneWetherLicenseVO> taaPlType(ZoneWetherLicenseVO vo) {
		ZoneWetherLicenseENTITY entity = modelMapper.map(vo,
				ZoneWetherLicenseENTITY.class);
		List<ZoneWetherLicenseENTITY> entityList = mapper.taaPlType(entity);
		List<ZoneWetherLicenseVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneWetherLicenseVO>>() {
				}.getType());
		return voList;
	}

	public List<ZoneWetherLicenseVO> taaTplType(ZoneWetherLicenseVO vo) {
		ZoneWetherLicenseENTITY entity = modelMapper.map(vo,
				ZoneWetherLicenseENTITY.class);
		List<ZoneWetherLicenseENTITY> entityList = mapper.taaTplType(entity);
		List<ZoneWetherLicenseVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneWetherLicenseVO>>() {
				}.getType());
		return voList;
	}

	public List<ZoneWetherLicenseVO> taaTmType(ZoneWetherLicenseVO vo) {
		ZoneWetherLicenseENTITY entity = modelMapper.map(vo,
				ZoneWetherLicenseENTITY.class);
		List<ZoneWetherLicenseENTITY> entityList = mapper.taaTmType(entity);
		List<ZoneWetherLicenseVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ZoneWetherLicenseVO>>() {
				}.getType());
		return voList;
	}

	public boolean updateLicenseInfo(ZoneLicenseUpdateDBVO vo) {
		ZoneLicenseUpdateDBENTITY entity = modelMapper.map(vo,
				ZoneLicenseUpdateDBENTITY.class);
		int result = mapper.updateLicenseInfo(entity);
		return result > 0;
	}

	public boolean verifyFile(ZoneSettingFileVO vo) throws TNAuthException {
		String zone_name = vo.getZone_name().trim();
		String tam_local_ip_1 = vo.getTam_local_ip_1().trim();
		String tam_local_port_1 = vo.getTam_local_port_1();
		String tam_local_ip_2 = vo.getTam_local_ip_2().trim();
		String tam_local_port_2 = vo.getTam_local_port_2();

		String decPORT = Integrity.tasDecrypt(zone_name, tam_local_port_1);
		int dec_port = Integer.parseInt(decPORT);
		String decPORT2 = Integrity.tasDecrypt(zone_name, tam_local_port_2);
		int dec_port2 = Integer.parseInt(decPORT2);

		String taa_ip = vo.getTaa_ip().trim();
		String license = vo.getLicense().trim();

		String decIP = Integrity.tasDecrypt(zone_name, tam_local_ip_1);
		String decIP2 = Integrity.tasDecrypt(zone_name, tam_local_ip_2);

		String hmac = Integrity.generateTAAConfIntegrity(zone_name, decIP, dec_port,
				decIP2, dec_port2, taa_ip, license); // Agent 환경파일 검증 성공

		return true;
	}

	public int countZoneLicenseHistory(String filter) {
		int result = mapper.countZoneLicenseHistory(filter);
		return result;
	}

	public int countZoneInfo() {
		int result = mapper.countZoneInfo();
		return result;
	}

	public int countZonetmLicenseHistory(String filter) {
		int result = mapper.countZonetmLicenseHistory(filter);
		return result;
	}

	public int countLicenseStateHistory(String filter) {
		int result = mapper.countLicenseStateHistory(filter);
		return result;
	}

	public int countLicenseStateHistoryWithNoFilter() {
		int result = mapper.countLicenseStateHistoryWithNoFilter();
		return result;
	}

	public int tmAllowedCount(int zone_no) {
		int result = mapper.tmAllowedCount(zone_no);
		return result;
	}

	public int tmPublishCount(int zone_no) {
		int result = mapper.tmPublishCount(zone_no);
		return result;
	}

	public int tmDenyCount(int zone_no) {
		int result = mapper.tmDenyCount(zone_no);
		return result;
	}

	public ZoneLicenseAllowInfoVO getOtherContent(ZoneLicenseAllowInfoVO vo) {
		ZoneLicenseAllowInfoENTITY entity = modelMapper.map(vo,
				ZoneLicenseAllowInfoENTITY.class);
		ZoneLicenseAllowInfoENTITY retEntity = mapper.getOtherContent(entity);
		ZoneLicenseAllowInfoVO retVO = modelMapper.map(retEntity,
				ZoneLicenseAllowInfoVO.class);
		return retVO;
	}

	public int getAgentStaticAllow(int zone_no) {

		int count = mapper.getAgentStaticAllow(zone_no);
		return count;
	}

	public int getAgentDynamicAllow(int zone_no) {
		int count = mapper.getAgentDynamicAllow(zone_no);
		return count;
	}

	public int getZoneNoWithZoneName(String name) {
		int zone_no = mapper.getZoneNoWithZoneName(name);
		return zone_no;
	}

	public ZoneInfoVO getZoneInfoWithNo(int zone_no) {
		ZoneInfoENTITY entity = mapper.getZoneInfoWithNo(zone_no);
		ZoneInfoVO retVO = modelMapper.map(entity, ZoneInfoVO.class);
		return retVO;
	}

}
