package trustnet.auth.zone.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.service.CommonService;
import trustnet.auth.zone.repository.ZoneMapper;
import trustnet.auth.zone.repository.entity.ZoneHistoryInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneLicenseCodeInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneLicenseHistoryInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneLicenseInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneLicenseStateHistoryInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneLicenseUpdateENTITY;
import trustnet.auth.zone.repository.entity.ZoneNLicenseInfoENTITY;
import trustnet.auth.zone.service.vo.ZoneHistoryInfoVO;
import trustnet.auth.zone.service.vo.ZoneInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseCodeInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseHistoryInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseStateHistoryInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseUpdateVO;
import trustnet.auth.zone.service.vo.ZoneNLicenseInfoVO;
//import trustnet.auth.zone.service.vo.ZoneVerifyIntegrityVO;
//import trustnet.tas.except.TNAuthException;
//import trustnet.tas.valid.Integrity;

@Service
@Slf4j
@RequiredArgsConstructor
public class ZoneService {

	private final ZoneMapper mapper;
	private final CommonService commonService;
	private final ModelMapper modelMapper;
	
	public boolean saveZoneInfo(ZoneInfoVO vo) {
	///////////////////////////////////////////////////나중에 수정요망 
//		vo.setHmac(commonService.getHMAC());
	///////////////////////////////////////////////////
		int result = mapper.saveZoneInfo(vo);
		return result > 0;
	}
	
	public boolean saveZoneLicenseInfo(ZoneLicenseInfoVO vo) {
		int result = mapper.saveZoneLicenseInfo(vo);
		return result > 0;
	}
	
	public List<ZoneInfoVO> findAllZoneList() {
		List<ZoneInfoENTITY> entityList = mapper.findAllZoneList();
		List<ZoneInfoVO> voList =  
				modelMapper.map(entityList, new TypeToken<List<ZoneInfoVO>>() {}.getType());

		return voList;
	}
	
	public List<ZoneInfoVO> findAllZoneListOnlyExist(){
		List<ZoneInfoENTITY> entityList = mapper.findAllZoneListOnlyExist();
		List<ZoneInfoVO> voList =  
				modelMapper.map(entityList, new TypeToken<List<ZoneInfoVO>>() {}.getType());

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
		List<ZoneNLicenseInfoVO> voList = modelMapper.map(entityList, new TypeToken<List<ZoneNLicenseInfoVO>>() {}.getType());
		return voList;
	}
	
	public boolean updateZoneWithID(ZoneInfoVO vo) {
		///////////////////////////////////////////////////나중에 수정요망 
//		vo.setHmac(commonService.getHMAC());
	///////////////////////////////////////////////////
		ZoneInfoENTITY entity = modelMapper.map(vo, ZoneInfoENTITY.class);
		int result = mapper.updateZoneWithID(entity);
		return result > 0;
	}
	
	public boolean updateZoneLicenseInfo(ZoneLicenseUpdateVO vo) {
		ZoneLicenseUpdateENTITY entity = modelMapper.map(vo, ZoneLicenseUpdateENTITY.class);
		log.info(entity.toString());
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
		List<ZoneInfoVO> voList = modelMapper.map(entityList, new TypeToken<List<ZoneInfoVO>>() {}.getType());
		return voList;
	}
	
	public List<ZoneInfoVO> findZoneWithCompNoWithAlive(ZoneInfoVO vo) {
		ZoneInfoENTITY entity = modelMapper.map(vo, ZoneInfoENTITY.class);
		List<ZoneInfoENTITY> entityList = mapper.findZoneWithCompNoWithAlive(entity);
		List<ZoneInfoVO> voList = modelMapper.map(entityList, new TypeToken<List<ZoneInfoVO>>() {}.getType());
		return voList;
	}
	
	public ZoneNLicenseInfoVO findAllZoneLicenseListAsZoneNO(ZoneLicenseInfoVO vo) {
		ZoneLicenseInfoENTITY entity = modelMapper.map(vo, ZoneLicenseInfoENTITY.class);
		ZoneNLicenseInfoENTITY retENTITY = mapper.findAllZoneLicenseListAsZoneNO(entity);
		ZoneNLicenseInfoVO retVOLIST = modelMapper.map(retENTITY, ZoneNLicenseInfoVO.class);
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
	
//	List<ZoneHistoryInfoENTITY> findAllZoneHistoryAsZoneNo(ZoneInfoENTITY entity);
	public List<ZoneHistoryInfoVO> findAllZoneHistoryAsZoneNo(ZoneInfoVO vo) {
		ZoneInfoENTITY entity = modelMapper.map(vo, ZoneInfoENTITY.class);
		List<ZoneHistoryInfoENTITY> entityList = mapper.findAllZoneHistoryAsZoneNo(entity);
		List<ZoneHistoryInfoVO> voList = modelMapper.map(entityList, new TypeToken<List<ZoneHistoryInfoVO>>() {}.getType());
		return voList;
	}
	
	public List<ZoneLicenseCodeInfoVO> findAllZoneCodeInfo() {
		List<ZoneLicenseCodeInfoENTITY> entityList = mapper.findAllZoneCodeInfo();
		List<ZoneLicenseCodeInfoVO> voList = modelMapper.map(entityList, new TypeToken<List<ZoneLicenseCodeInfoVO>>() {}.getType());
		return voList;
	}
	
	public boolean saveZoneLicenseHistory(ZoneLicenseHistoryInfoVO vo) {
		ZoneLicenseHistoryInfoENTITY entity = modelMapper.map(vo, ZoneLicenseHistoryInfoENTITY.class);
		int result = mapper.saveZoneLicenseHistory(entity);
		return result > 0;
	}
	
	public ZoneLicenseInfoVO findZoneLicenseAsZoneNONTaaIP(ZoneLicenseInfoVO vo) {
		ZoneLicenseInfoENTITY entity = modelMapper.map(vo, ZoneLicenseInfoENTITY.class);
		ZoneLicenseInfoENTITY retENTITY = mapper.findZoneLicenseAsZoneNONTaaIP(entity);
		ZoneLicenseInfoVO retVO = modelMapper.map(retENTITY, ZoneLicenseInfoVO.class);
		return retVO;
	}
	
//	List<ZoneLicenseHistoryInfoENTITY> findZoneLicenseHistoryInfo(ZoneLicenseInfoENTITY entity);
	public List<ZoneLicenseHistoryInfoVO> findZoneLicenseHistoryInfo(ZoneInfoVO vo) {
		ZoneInfoENTITY entity = modelMapper.map(vo, ZoneInfoENTITY.class);
		List<ZoneLicenseHistoryInfoENTITY> entityList = mapper.findZoneLicenseHistoryInfo(entity);
		List<ZoneLicenseHistoryInfoVO> voList = modelMapper.map(entityList, new TypeToken<List<ZoneLicenseHistoryInfoVO>>() {}.getType());
		return voList;
	}
	
	public List<ZoneLicenseStateHistoryInfoVO> findAllLicenseStateHistoryInfo() {
		List<ZoneLicenseStateHistoryInfoENTITY> entityList = mapper.findAllLicenseStateHistoryInfo();
		List<ZoneLicenseStateHistoryInfoVO> voList = modelMapper.map(entityList, new TypeToken<List<ZoneLicenseStateHistoryInfoVO>>() {}.getType());
		return voList;
	}
	
	public boolean isFindZone(ZoneInfoVO vo) {
		ZoneInfoENTITY entity = modelMapper.map(vo, ZoneInfoENTITY.class);
		int result = mapper.isFindZone(entity);
		return result > 0;
	}

//	public boolean verifyZoneIntegrity(ZoneVerifyIntegrityVO ZVIvo) throws TNAuthException {
//		boolean bRet = Integrity.verifyZoneIntegrity(ZVIvo.getZone_name(), ZVIvo.getRevision_no(), ZVIvo.getPl_count(), ZVIvo.getTpl_count(), ZVIvo.getLimit_url(), ZVIvo.getIntegrity());
//		return bRet;
//	}
}
	
	
