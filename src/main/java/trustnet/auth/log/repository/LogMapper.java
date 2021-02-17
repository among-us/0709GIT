package trustnet.auth.log.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import trustnet.auth.log.repository.entity.AuditLogInfoENTITY;
import trustnet.auth.log.repository.entity.ServiceLogInfoENTITY;
import trustnet.auth.log.service.vo.AuditLogInfoVO;
import trustnet.auth.log.service.vo.ServiceLogInfoVO;

@Repository
@Mapper
public interface LogMapper {

	List<AuditLogInfoENTITY> findAllAuditLogList();
	List<ServiceLogInfoENTITY> findAllServiceLogList();
	
	int saveServiceLog(ServiceLogInfoENTITY vo);
	int saveAuditLog(AuditLogInfoENTITY vo);
}
