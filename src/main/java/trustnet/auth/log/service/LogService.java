package trustnet.auth.log.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.log.repository.LogMapper;
import trustnet.auth.log.repository.entity.AuditLogInfoENTITY;
import trustnet.auth.log.repository.entity.ServiceLogInfoENTITY;
import trustnet.auth.log.service.vo.AuditLogInfoVO;
import trustnet.auth.log.service.vo.ServiceLogInfoVO;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogService {
	
	private final LogMapper logMapper;
	private final ModelMapper modelMapper;
	public List<AuditLogInfoVO> findAllAuditLogList() {
		
		List<AuditLogInfoENTITY> auditLogInfoList = logMapper.findAllAuditLogList();
		List<AuditLogInfoVO> voList =  
				modelMapper.map(auditLogInfoList, 	new TypeToken<List<AuditLogInfoVO>>() {}.getType());
	
		return voList;
	}
	
	public List<ServiceLogInfoVO> findAllServiceLogList() {
		
		List<ServiceLogInfoENTITY> serviceLogInfoList = logMapper.findAllServiceLogList();
		List<ServiceLogInfoVO> voList =  
				modelMapper.map(serviceLogInfoList, new TypeToken<List<ServiceLogInfoVO>>() {}.getType());
	
		return voList;
	}
	
	public int saveServiceLog(String description) {
		ServiceLogInfoVO vo = new ServiceLogInfoVO(description);
		ServiceLogInfoENTITY entity = modelMapper.map(vo, ServiceLogInfoENTITY.class);
		int result = logMapper.saveServiceLog(entity);
		return result;
	}
	
	public int saveAuditLog(String description) {
		AuditLogInfoVO vo = new AuditLogInfoVO(description);
		AuditLogInfoENTITY entity =  modelMapper.map(vo, AuditLogInfoENTITY.class);
		int result = logMapper.saveAuditLog(entity);
		return result;
	}
	
}
