package trustnet.auth.managerzone.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import trustnet.auth.manager.repository.entity.ManagerInfoENTITY;
import trustnet.auth.managerzone.repository.entity.ManagerZoneInfoENTITY;
import trustnet.auth.managerzone.repository.entity.ManagerZoneMatchInfoENTITY;
import trustnet.auth.managerzone.service.vo.ManagerZoneInfoVO;

@Repository
@Mapper
public interface ManagerZoneMapper {

	ManagerInfoENTITY findManager(ManagerZoneInfoVO vo);
	int saveManagerZone(ManagerZoneInfoENTITY entity);
	int isFindManagerZone(ManagerZoneInfoVO vo);
	int deleteManagerZoneWithTAMNO(ManagerZoneInfoVO vo);
	int deleteManagerZoneWithZONENO(ManagerZoneInfoVO vo);
	List<ManagerZoneMatchInfoENTITY> findAllHavingZone(ManagerZoneInfoVO vo);
}
