package trustnet.auth.manager.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import trustnet.auth.manager.repository.entity.ManagerHistoryInfoENTITY;
import trustnet.auth.manager.repository.entity.ManagerInfoENTITY;
import trustnet.auth.manager.service.vo.ManagerInfoVO;

@Repository
@Mapper
public interface ManagerMapper {
	
	List<ManagerInfoENTITY> findAllManagerList();
	List<ManagerInfoENTITY> findAllManagerListOnlyExist();
	ManagerInfoENTITY findManager(ManagerInfoVO vo);
	ManagerInfoENTITY findManagerWithNO(ManagerInfoENTITY vo);
	int isFindManager(ManagerInfoVO vo);
	int saveManagerInfo(ManagerInfoVO vo);
	int updateManagerWithID(ManagerInfoENTITY ENTITY);
	int deleteManagerWithID(ManagerInfoENTITY ENTITY);
	ManagerInfoENTITY findManagerAsName(ManagerInfoENTITY entity);
	int saveManagerHistoryInfo(ManagerHistoryInfoENTITY entity);
	List<ManagerHistoryInfoENTITY> findAllManagerHistoryAsTamNo(ManagerInfoENTITY entity);
}
