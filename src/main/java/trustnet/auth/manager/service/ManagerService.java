package trustnet.auth.manager.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.service.CommonService;
import trustnet.auth.manager.repository.ManagerMapper;
import trustnet.auth.manager.repository.entity.ControllerHistoryENTITY;
import trustnet.auth.manager.repository.entity.ControllerInfoENTITY;
import trustnet.auth.manager.repository.entity.DBInfoHistoryENTITY;
import trustnet.auth.manager.repository.entity.ManagerCreateFileENTITY;
import trustnet.auth.manager.repository.entity.ManagerDBInfoENTITY;
import trustnet.auth.manager.repository.entity.ManagerGenerateHmacENTITY;
import trustnet.auth.manager.repository.entity.ManagerHistoryInfoENTITY;
import trustnet.auth.manager.repository.entity.ManagerInfoENTITY;
import trustnet.auth.manager.service.vo.ControllerHistoryVO;
import trustnet.auth.manager.service.vo.ControllerInfoVO;
import trustnet.auth.manager.service.vo.DBInfoHistoryVO;
import trustnet.auth.manager.service.vo.ManagerCreateFileVO;
import trustnet.auth.manager.service.vo.ManagerDBInfoVO;
import trustnet.auth.manager.service.vo.ManagerGenerateHmacVO;
import trustnet.auth.manager.service.vo.ManagerHistoryInfoVO;
import trustnet.auth.manager.service.vo.ManagerInfoVO;
import trustnet.auth.zone.repository.entity.ZoneInfoENTITY;
import trustnet.auth.zone.service.vo.ZoneInfoVO;
import trustnet.tas.except.TNAuthException;
import trustnet.tas.valid.Integrity;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerService {

	private final ManagerMapper mapper;
	private final ModelMapper modelMapper;
	private final CommonService commonService;

	public boolean saveManagerInfo(ManagerInfoVO vo) throws TNAuthException {
		int result = mapper.saveManagerInfo(vo);
		return result > 0;
	}

	public boolean saveControllerInfo(ControllerInfoVO vo) throws TNAuthException {
		int result = mapper.saveControllerInfo(vo);
		return result > 0;
	}

	public boolean saveManagerDBInfo(ManagerDBInfoVO vo) {
		int result = mapper.saveManagerDBInfo(vo);
		return result > 0;
	}

	public boolean saveDBInfoHistory(DBInfoHistoryVO vo) {
		int result = mapper.saveDBInfoHistory(vo);
		return result > 0;
	}

	public boolean saveDBInfoHistoryDel(DBInfoHistoryVO vo) {
		int result = mapper.saveDBInfoHistoryDel(vo);
		return result > 0;
	}

	public List<ManagerInfoVO> findAllManagerList() {
		List<ManagerInfoENTITY> entityList = mapper.findAllManagerList();
		//		log.info("SERVICE 단 HISTORY TEST : " + entityList.toString());
		List<ManagerInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ManagerInfoVO>>() {
				}.getType());
		return voList;
	}

	public List<ControllerInfoVO> findAllControllerList() {
		List<ControllerInfoENTITY> entityList = mapper.findAllControllerList();
		List<ControllerInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ControllerInfoVO>>() {
				}.getType());
		return voList;
	}

	public List<ManagerHistoryInfoVO> findAllManagerHistory(ManagerHistoryInfoVO vo) {
		ManagerHistoryInfoENTITY entity = modelMapper.map(vo,
				ManagerHistoryInfoENTITY.class);

		// ORDERING VALUE TEST 
		log.info("HISTORY SERVICE ORDERING VALUE ?:" + entity.getOrdering());

		log.info("함수태우기전 : service 단 history entity value : " + entity);

		List<ManagerHistoryInfoENTITY> entityList = mapper.findAllManagerHistory(entity);
		log.info("함수태운후 : SERVICE 단 HISTORY TEST : " + entityList.toString());
		List<ManagerHistoryInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ManagerHistoryInfoVO>>() {
				}.getType());
		return voList;
	}

	public List<DBInfoHistoryVO> getDBHistory(DBInfoHistoryVO vo) {
		DBInfoHistoryENTITY entity = modelMapper.map(vo, DBInfoHistoryENTITY.class);

		List<DBInfoHistoryENTITY> entityList = mapper.getDBHistory(entity);
		List<DBInfoHistoryVO> voList = modelMapper.map(entityList,
				new TypeToken<List<DBInfoHistoryVO>>() {
				}.getType());
		return voList;
	}

	public List<ManagerInfoVO> findAllManagerListOnlyExist() {
		List<ManagerInfoENTITY> entityList = mapper.findAllManagerListOnlyExist();
		List<ManagerInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ManagerInfoVO>>() {
				}.getType());

		return voList;
	}

	public boolean updateManagerWithID(ManagerInfoVO vo) throws TNAuthException {
		ManagerInfoENTITY entity = modelMapper.map(vo, ManagerInfoENTITY.class);
		int result = mapper.updateManagerWithID(entity);
		return result > 0;
	}

	public boolean updateControllerWithID(ControllerInfoVO vo) throws TNAuthException {
		ControllerInfoENTITY entity = modelMapper.map(vo, ControllerInfoENTITY.class);
		int result = mapper.updateControllerWithID(entity);
		return result > 0;
	}

	public boolean updateDB(ManagerDBInfoVO vo) {
		ManagerDBInfoENTITY entity = modelMapper.map(vo, ManagerDBInfoENTITY.class);
		int result = mapper.updateDB(entity);
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

	public ControllerInfoVO findController(ControllerInfoVO vo) {
		ControllerInfoENTITY entity = mapper.findController(vo);
		ControllerInfoVO retVO = modelMapper.map(entity, ControllerInfoVO.class);
		return retVO;
	}

	public ManagerDBInfoVO findDBManager(ManagerDBInfoVO vo) {
		ManagerDBInfoENTITY entity = mapper.findDBManager(vo);
		ManagerDBInfoVO retVO = modelMapper.map(entity, ManagerDBInfoVO.class);
		return retVO;
	}

	public ManagerInfoVO findManagerWithNO(ManagerInfoVO vo) {
		ManagerInfoENTITY entity = modelMapper.map(vo, ManagerInfoENTITY.class);
		ManagerInfoENTITY retEntity = mapper.findManagerWithNO(entity);
		ManagerInfoVO retVO = modelMapper.map(retEntity, ManagerInfoVO.class);
		return retVO;
	}

	public ControllerInfoVO findControllerWithNO(ControllerInfoVO vo) {
		ControllerInfoENTITY entity = modelMapper.map(vo, ControllerInfoENTITY.class);
		ControllerInfoENTITY retEntity = mapper.findControllerWithNO(entity);
		ControllerInfoVO retVO = modelMapper.map(retEntity, ControllerInfoVO.class);
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

	public boolean isFindController(ControllerInfoVO VO) {
		int ret = mapper.isFindController(VO);
		return ret > 0;
	}

	// ==============================================================================================
	public boolean verifySettingFile(ManagerCreateFileVO vo) throws TNAuthException {
		log.info("=====================================================");
		log.info(vo.toString());
		String tam_name = vo.getTam_name().trim();
		String tam_ip = vo.getTam_ip().trim();
		String comm_type = vo.getComm_type().trim();
		String db_type = vo.getDb_type().trim();
		String db_addr = vo.getDb_addr().trim();
		String dbport = vo.getDb_port().trim();
		String dec_port = Integrity.tasDecrypt(tam_name, dbport);
		int port = Integer.parseInt(dec_port);

		String db_id = vo.getDb_id().trim();
		String db_pwd = vo.getDb_pwd().trim();
		String db_name = vo.getDb_name().trim();
		String dec_addr = Integrity.tasDecrypt(tam_name, db_addr);
		String dec_id = Integrity.tasDecrypt(tam_name, db_id);
		String dec_pwd = Integrity.tasDecrypt(tam_name, db_pwd);
		String dec_name = Integrity.tasDecrypt(tam_name, db_name);

		String hmac = Integrity.generateTAMConfIntegrity(tam_name, tam_ip, comm_type,
				db_type, dec_addr, port, dec_id, dec_pwd, dec_name);
		log.info("HMAC VALUE = " + hmac);
		return true;
		//		boolean ret;
		//		if ( hmac.length() != 0 ) {
		//			return ret = true;
		//		}else {
		//			return ret = false;
		//		}

	}
	// ==============================================================================================

	public boolean isFindSvcName(ManagerDBInfoVO vo) {
		int ret = mapper.isFindSvcName(vo);
		return ret > 0;
	}

	public boolean saveManagerHistoryInfo(ManagerHistoryInfoVO vo) {
		ManagerHistoryInfoENTITY entity = modelMapper.map(vo,
				ManagerHistoryInfoENTITY.class);
		int result = mapper.saveManagerHistoryInfo(entity);
		return result > 0;
	}

	public boolean saveControllerHistoryInfo(ControllerHistoryVO vo) {
		ControllerHistoryENTITY entity = modelMapper.map(vo,
				ControllerHistoryENTITY.class);
		int result = mapper.saveControllerHistoryInfo(entity);
		return result > 0;
	}

	public List<ManagerHistoryInfoVO> findAllManagerHistoryAsTamNo(ManagerInfoVO vo) {
		ManagerInfoENTITY entity = modelMapper.map(vo, ManagerInfoENTITY.class);
		List<ManagerHistoryInfoENTITY> entityList = mapper
				.findAllManagerHistoryAsTamNo(entity);
		List<ManagerHistoryInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ManagerHistoryInfoVO>>() {
				}.getType());
		return voList;
	}

	public List<ManagerCreateFileVO> findAllManagerName(ManagerCreateFileVO vo) {
		ManagerCreateFileENTITY entity = modelMapper.map(vo,
				ManagerCreateFileENTITY.class);
		List<ManagerCreateFileENTITY> entityList = mapper.findAllManagerName(entity);
		List<ManagerCreateFileVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ManagerCreateFileVO>>() {
				}.getType());
		return voList;
	}

	public List<ManagerCreateFileVO> findAllServiceName(ManagerCreateFileVO vo) {
		ManagerDBInfoENTITY entity = modelMapper.map(vo, ManagerDBInfoENTITY.class);
		List<ManagerCreateFileENTITY> entityList = mapper.findAllServiceName();
		List<ManagerCreateFileVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ManagerCreateFileVO>>() {
				}.getType());
		return voList;
	}

	public ManagerGenerateHmacVO genHmac(ManagerGenerateHmacVO vo)
			throws TNAuthException {
		ManagerGenerateHmacENTITY entity = mapper.genHmac(vo);
		ManagerGenerateHmacVO vohmac = modelMapper.map(entity,
				ManagerGenerateHmacVO.class);
		return vohmac;
	}

	public List<ManagerCreateFileVO> findAllManagerName() {
		List<ManagerCreateFileENTITY> entityList = mapper.findAllManagerName();
		List<ManagerCreateFileVO> tamNameVOList = modelMapper.map(entityList,
				new TypeToken<List<ManagerCreateFileENTITY>>() {
				}.getType());
		return tamNameVOList;
	}

	public List<ManagerCreateFileVO> findAllServiceName() {
		List<ManagerCreateFileENTITY> entityList = mapper.findAllServiceName();
		List<ManagerCreateFileVO> tamNameVOList = modelMapper.map(entityList,
				new TypeToken<List<ManagerCreateFileENTITY>>() {
				}.getType());
		return tamNameVOList;
	}

	public ManagerInfoVO getTamInfowithNo(int tam_no) {
		ManagerInfoENTITY entityList = mapper.getTamInfowithNo(tam_no);
		ManagerInfoVO tamInfoList = modelMapper.map(entityList, ManagerInfoVO.class);
		return tamInfoList;
	}

	public ControllerInfoVO getCtrlInfowithNo(ControllerInfoVO vo) {
		ControllerInfoENTITY entity = mapper.getCtrlInfowithNo(vo);
		ControllerInfoVO afterVO = modelMapper.map(entity, ControllerInfoVO.class);
		return afterVO;
	}

	public ManagerInfoVO getPrivIp(ManagerInfoVO vo) {
		ManagerInfoENTITY entity = mapper.getPrivateIp(vo);
		ManagerInfoVO privIp = modelMapper.map(entity, ManagerInfoVO.class);

		return privIp;
	}

	public ManagerInfoVO getAgentPrivate(ManagerInfoVO vo) {
		ManagerInfoENTITY entity = mapper.getAgentPrivate(vo);
		ManagerInfoVO privIp = modelMapper.map(entity, ManagerInfoVO.class);

		return privIp;
	}

	public ManagerDBInfoVO getDBInfo(ManagerDBInfoVO vo) {
		ManagerDBInfoENTITY entity = mapper.getDBInfo(vo);
		ManagerDBInfoVO info = modelMapper.map(entity, ManagerDBInfoVO.class);
		log.info("---------DB INFO---------------" + info.toString());
		return info;
	}

	public List<ManagerDBInfoVO> findAllDBList() {
		List<ManagerDBInfoENTITY> entityList = mapper.findAllDBList();
		List<ManagerDBInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ManagerDBInfoVO>>() {
				}.getType());
		return voList;
	}

	public ManagerDBInfoVO findDBWithNO(ManagerDBInfoVO vo) {
		ManagerDBInfoENTITY entity = modelMapper.map(vo, ManagerDBInfoENTITY.class);
		ManagerDBInfoENTITY retEntity = mapper.findDBWithNO(entity);
		ManagerDBInfoVO retVO = modelMapper.map(retEntity, ManagerDBInfoVO.class);
		return retVO;
	}

	public boolean deleteDBInfo(ManagerDBInfoVO vo) {
		ManagerDBInfoENTITY entity = modelMapper.map(vo, ManagerDBInfoENTITY.class);
		int result = mapper.deleteDBInfo(entity);
		return result > 0;
	}

	public int countManagerHistory(String filter) {
		int result = mapper.countManagerHistory(filter);
		return result;
	}

	public int countDBHistory() {
		int result = mapper.countDBHistory();
		return result;
	}
}
