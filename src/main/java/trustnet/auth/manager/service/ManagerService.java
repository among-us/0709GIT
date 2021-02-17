package trustnet.auth.manager.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.service.CommonService;
import trustnet.auth.manager.repository.ManagerMapper;
import trustnet.auth.manager.repository.entity.ManagerHistoryInfoENTITY;
import trustnet.auth.manager.repository.entity.ManagerInfoENTITY;
import trustnet.auth.manager.service.vo.ManagerHistoryInfoVO;
import trustnet.auth.manager.service.vo.ManagerInfoVO;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerService {

	private final ManagerMapper mapper;
	private final ModelMapper modelMapper;
	private final CommonService commonService;
	public boolean saveManagerInfo(ManagerInfoVO vo) {
		////////////HMAC////////////////
		vo.setHmac(commonService.getHMAC());
		////////////////////////////////
		int result = mapper.saveManagerInfo(vo);
		return result > 0;
	}
	
	public List<ManagerInfoVO> findAllManagerList() {
		List<ManagerInfoENTITY> entityList = mapper.findAllManagerList();
		List<ManagerInfoVO> voList =  
				modelMapper.map(entityList, new TypeToken<List<ManagerInfoVO>>() {}.getType());
		
		return voList;
	}
	
	public List<ManagerInfoVO> findAllManagerListOnlyExist() {
		List<ManagerInfoENTITY> entityList = mapper.findAllManagerListOnlyExist();
		List<ManagerInfoVO> voList =  
				modelMapper.map(entityList, new TypeToken<List<ManagerInfoVO>>() {}.getType());
		
		return voList;
	}
	
	public boolean updateManagerWithID(ManagerInfoVO vo) {
		////////////HMAC////////////////
		vo.setHmac(commonService.getHMAC());
		////////////////////////////////
		ManagerInfoENTITY entity = modelMapper.map(vo, ManagerInfoENTITY.class);
		int result = mapper.updateManagerWithID(entity);
		return result > 0;
	}
	
	public boolean deleteManagerWithID(ManagerInfoVO vo) {
		
		ManagerInfoENTITY entity = modelMapper.map(vo, ManagerInfoENTITY.class);
		int result = mapper.deleteManagerWithID(entity);
		return result > 0;
	}
	
	
	public ManagerInfoVO findManager(ManagerInfoVO managerInfoVO) {
		ManagerInfoENTITY entity = mapper.findManager(managerInfoVO);
		ManagerInfoVO retVO = modelMapper.map(entity, ManagerInfoVO.class);
		return retVO;
	}
	
	public ManagerInfoVO findManagerWithNO(ManagerInfoVO vo) {
		ManagerInfoENTITY entity = modelMapper.map(vo, ManagerInfoENTITY.class);
		ManagerInfoENTITY retEntity = mapper.findManagerWithNO(entity);
		ManagerInfoVO retVO = modelMapper.map(retEntity, ManagerInfoVO.class);
		return retVO;
	}
	
	public ManagerInfoVO findManagerAsName(ManagerInfoVO vo) {
		ManagerInfoENTITY entity = modelMapper.map(vo, ManagerInfoENTITY.class);
		ManagerInfoENTITY retENTITY = mapper.findManagerAsName(entity);
		ManagerInfoVO retVO = modelMapper.map(retENTITY, ManagerInfoVO.class);
		return retVO;
	}
	
	public boolean isFindManager(ManagerInfoVO managerInfoVO) {
		int ret = mapper.isFindManager(managerInfoVO);
		return ret > 0;
	}
	
	public boolean saveManagerHistoryInfo(ManagerHistoryInfoVO vo) {
		ManagerHistoryInfoENTITY entity = modelMapper.map(vo, ManagerHistoryInfoENTITY.class);
		int result = mapper.saveManagerHistoryInfo(entity);
		return result > 0 ;
	}
	
//	List<ManagerHistoryInfoENTITY> findAllManagerHistoryAsTamNo(ManagerInfoENTITY entity);
	public List<ManagerHistoryInfoVO> findAllManagerHistoryAsTamNo(ManagerInfoVO vo) {
		ManagerInfoENTITY entity = modelMapper.map(vo, ManagerInfoENTITY.class);
		List<ManagerHistoryInfoENTITY> entityList = mapper.findAllManagerHistoryAsTamNo(entity);
		List<ManagerHistoryInfoVO> voList = modelMapper.map(entityList, new TypeToken<List<ManagerHistoryInfoVO>>() {}.getType());
		return voList;
	}
}
