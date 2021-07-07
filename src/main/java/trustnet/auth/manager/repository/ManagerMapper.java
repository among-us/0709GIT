package trustnet.auth.manager.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import trustnet.auth.manager.repository.entity.ControllerHistoryENTITY;
import trustnet.auth.manager.repository.entity.ControllerInfoENTITY;
import trustnet.auth.manager.repository.entity.DBInfoHistoryENTITY;
import trustnet.auth.manager.repository.entity.ManagerCreateFileENTITY;
import trustnet.auth.manager.repository.entity.ManagerDBInfoENTITY;
import trustnet.auth.manager.repository.entity.ManagerGenerateHmacENTITY;
import trustnet.auth.manager.repository.entity.ManagerHistoryInfoENTITY;
import trustnet.auth.manager.repository.entity.ManagerInfoENTITY;
import trustnet.auth.manager.service.vo.ControllerInfoVO;
import trustnet.auth.manager.service.vo.DBInfoHistoryVO;
import trustnet.auth.manager.service.vo.ManagerCreateFileVO;
import trustnet.auth.manager.service.vo.ManagerDBInfoVO;
import trustnet.auth.manager.service.vo.ManagerGenerateHmacVO;
import trustnet.auth.manager.service.vo.ManagerHistoryInfoVO;
import trustnet.auth.manager.service.vo.ManagerInfoVO;

@Repository
@Mapper
public interface ManagerMapper {
	
	List<ManagerInfoENTITY> findAllManagerList();
	List<ControllerInfoENTITY> findAllControllerList();
	List<ManagerHistoryInfoENTITY> findAllManagerHistory(ManagerHistoryInfoENTITY entity);
	List<DBInfoHistoryENTITY> getDBHistory(DBInfoHistoryENTITY entity);
	List<ManagerInfoENTITY> findAllManagerListOnlyExist();
	ManagerInfoENTITY findManager(ManagerInfoVO vo);
	ControllerInfoENTITY findController (ControllerInfoVO vo);
	ManagerDBInfoENTITY findDBManager(ManagerDBInfoVO vo);
	ManagerInfoENTITY findManagerWithNO(ManagerInfoENTITY vo);
	ControllerInfoENTITY findControllerWithNO(ControllerInfoENTITY vo);
	int isFindManager(ManagerInfoVO vo);
	int isFindController(ControllerInfoVO vo);
	int verifySettingFile(ManagerCreateFileVO vo);
	int isFindSvcName(ManagerDBInfoVO vo);
	int saveManagerInfo(ManagerInfoVO vo);
	int saveControllerInfo(ControllerInfoVO vo);
	int saveManagerDBInfo(ManagerDBInfoVO vo);
	int saveDBInfoHistory(DBInfoHistoryVO vo);
	int saveDBInfoHistoryDel(DBInfoHistoryVO vo);
	int updateManagerWithID(ManagerInfoENTITY ENTITY);
	int updateControllerWithID(ControllerInfoENTITY ENTITY);
	int updateDB(ManagerDBInfoENTITY ENTITY);
	int deleteManagerWithID(ManagerInfoENTITY ENTITY);
	ManagerInfoENTITY findManagerAsName(ManagerInfoENTITY entity);
	int saveManagerHistoryInfo(ManagerHistoryInfoENTITY entity);
	int saveControllerHistoryInfo(ControllerHistoryENTITY entity);
	List<ManagerHistoryInfoENTITY> findAllManagerHistoryAsTamNo(ManagerInfoENTITY entity);
	List<ManagerCreateFileENTITY> findAllManagerName(ManagerCreateFileENTITY entity);
//	String createHMAC();
	ManagerGenerateHmacENTITY genHmac(ManagerGenerateHmacVO vo);
	List<ManagerCreateFileENTITY> findAllManagerName();
	List<ManagerCreateFileENTITY> findAllServiceName();
	ManagerInfoENTITY getTamInfowithNo(int tam_no);
	ControllerInfoENTITY getCtrlInfowithNo(ControllerInfoVO vo);
	ManagerInfoENTITY getPrivateIp(ManagerInfoVO vo);
	ManagerInfoENTITY getAgentPrivate(ManagerInfoVO vo);
	ManagerDBInfoENTITY getDBInfo(ManagerDBInfoVO vo);
	List<ManagerDBInfoENTITY> findAllDBList();
	ManagerDBInfoENTITY findDBWithNO(ManagerDBInfoENTITY vo);
	int deleteDBInfo(ManagerDBInfoENTITY entity);
	
	int countManagerHistory(String filter);
	int countDBHistory();
}

