package trustnet.auth.managerzone.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.service.CommonService;
import trustnet.auth.manager.repository.entity.ManagerInfoENTITY;
import trustnet.auth.manager.service.vo.ManagerInfoVO;
import trustnet.auth.managerzone.repository.ManagerZoneMapper;
import trustnet.auth.managerzone.repository.entity.ManagerZoneInfoENTITY;
import trustnet.auth.managerzone.repository.entity.ManagerZoneMatchInfoENTITY;
import trustnet.auth.managerzone.service.vo.ManagerZoneInfoVO;
import trustnet.auth.managerzone.service.vo.ManagerZoneMatchInfoVO;
import trustnet.auth.zone.service.vo.ZoneInfoVO;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManagerZoneService {

	private final ModelMapper modelMapper;
	private final ManagerZoneMapper managerZoneMapper;
	private final CommonService commonService;
	public ManagerInfoVO findManager(ManagerZoneInfoVO vo) {
		ManagerInfoENTITY entity = managerZoneMapper.findManager(vo);
		ManagerInfoVO managerInfoVO = modelMapper.map(entity, ManagerInfoVO.class);
		return managerInfoVO;
	}
	
	public boolean saveManagerZone(ManagerZoneInfoVO vo) {
		////////////////////// HMAC 계싼 //////////////////////
		vo.setHmac(commonService.getHMAC());
		/////////////////////////////////////////////////////
		ManagerZoneInfoENTITY entity =  
				modelMapper.map(vo, ManagerZoneInfoENTITY.class);
		int result = managerZoneMapper.saveManagerZone(entity);
		return result > 0;
	}
	
	public boolean isFindManagerZone(ManagerZoneInfoVO vo) {
		int result = managerZoneMapper.isFindManagerZone(vo);
		return result > 0;
	}
	
	public boolean deleteManagerZoneWithTAMNO(ManagerZoneInfoVO vo) {
		Integer result = managerZoneMapper.deleteManagerZoneWithTAMNO(vo);
		return result > 0;
	}

	public List<ManagerZoneMatchInfoVO> findAllHavingZone(ManagerZoneInfoVO vo) {
		List<ZoneInfoVO> zoneInfoVOList = null;
		List<ManagerZoneMatchInfoENTITY> entityList = managerZoneMapper.findAllHavingZone(vo);
		List<ManagerZoneMatchInfoVO> voList = modelMapper.map(entityList, new TypeToken<List<ManagerZoneMatchInfoVO>>() {}.getType());
		return voList;
	}
}
