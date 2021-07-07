package trustnet.auth.zone.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import trustnet.auth.manager.repository.entity.ManagerCreateFileENTITY;
import trustnet.auth.manager.repository.entity.ManagerInfoENTITY;
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
import trustnet.auth.zone.service.vo.ZoneInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseAllowInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseInfoVO;
import trustnet.auth.zone.service.vo.ZoneTimeLicenseHistoryVO;
import trustnet.auth.zone.service.vo.ZoneTimeLicenseVO;

@Repository
@Mapper
public interface ZoneMapper {
	List<ZoneInfoENTITY> findAllZone();
	List<ZoneLicenseAllowInfoENTITY> findAllZoneList(ZoneLicenseAllowInfoVO vo);
	List<ZoneInfoENTITY> findAllZoneListOnlyExist();
	ZoneInfoENTITY findZoneWithNO(ZoneInfoENTITY entity);
	int deleteZoneWithID(ZoneInfoENTITY entity);
	int saveZoneInfo(ZoneInfoVO vo);
	int saveTimeLicenseHistory(ZoneTimeLicenseHistoryVO vo);
	int saveZoneLicenseInfo(ZoneLicenseInfoVO vo);
	List<ZoneNLicenseInfoENTITY> findAllZoneLicenseList();
	int updateZoneWithID(ZoneInfoENTITY entity);
	int updateTimelimitLicense(ZoneTimeLicenseENTITY entity);
	int updateAllowValue(ZoneWetherLicenseENTITY entity);
	int updateRejectValue(ZoneWetherLicenseENTITY entity);
	int updateLicenseAllowInfo(ZoneLicenseAllowInfoENTITY entity);
	List<ZoneInfoENTITY> findZoneWithCompNo(ZoneInfoENTITY entity);
	List<ZoneInfoENTITY> findZoneWithCompNoWithAlive(ZoneInfoENTITY entity);
	ZoneNLicenseInfoENTITY findAllZoneLicenseListAsZoneNO(ZoneLicenseInfoENTITY entity);
	int updateZoneLicenseInfo(ZoneLicenseUpdateENTITY entity);
	int deleteZoneLicenseInfo(ZoneLicenseInfoENTITY entity);
	int saveZoneHistory(ZoneHistoryInfoENTITY entity);
	int saveTimeLicenseHistory(ZoneTimeLicenseHistoryENTITY entity);
	ZoneInfoENTITY findZoneInfoAsZoneName(ZoneInfoENTITY entity);
	ZoneTimeLicenseHistoryENTITY findTimeLicenseInfoAsZoneName(
			ZoneTimeLicenseHistoryENTITY entity);
	List<ZoneHistoryInfoENTITY> findAllZoneHistoryAsZoneNo(ZoneInfoENTITY entity);
	List<ZoneLicenseCodeInfoENTITY> findAllZoneCodeInfo();
	int saveZoneLicenseHistory(ZoneLicenseHistoryInfoENTITY entity);
	ZoneLicenseInfoENTITY findZoneLicenseAsZoneNONTaaIP(ZoneLicenseInfoENTITY entity);
	List<ZoneLicenseHistoryInfoENTITY> findZoneLicenseHistoryInfo(ZoneInfoENTITY entity);
	List<ZoneLicenseStateHistoryInfoENTITY> findAllLicenseStateHistoryInfo();
	List<ZoneLicenseHistoryInfoENTITY> findAllLicenseHistory(ZoneLicenseHistoryInfoENTITY entity);
	List<ZoneTimeLicenseHistoryENTITY> findTimeLicenseHistory(
			ZoneTimeLicenseHistoryENTITY entity);
	List<ZoneLicenseStateHistoryInfoENTITY> requestLicenseHistory(
			ZoneLicenseStateHistoryInfoENTITY entity);
	List<ZoneLicenseStateHistoryInfoENTITY> requestLicenseHistoryWithNoFilter(
			ZoneLicenseStateHistoryInfoENTITY entity);
	int isFindZone(ZoneInfoENTITY entity);
	List<ManagerCreateFileENTITY> findAllManagerName();
	List<ZoneLicenseInfoENTITY> findTaaIp();
	List<ManagerInfoENTITY> findTamIp();
	List<ManagerInfoENTITY> findTamLocalPort();
	List<ZoneWetherLicenseENTITY> licenseCount(ZoneWetherLicenseENTITY entity);
	List<ZoneWetherLicenseENTITY> licensePublish(ZoneWetherLicenseENTITY entity);
	List<ZoneWetherLicenseENTITY> tpllicensePublish(ZoneWetherLicenseENTITY entity);
	List<ZoneWetherLicenseENTITY> denyCount(ZoneWetherLicenseENTITY entity);
	List<ZoneWetherLicenseENTITY> denyTplCount(ZoneWetherLicenseENTITY entity);
	List<ZoneTimeLicenseENTITY> timeLicenseCount(ZoneTimeLicenseENTITY entity);
	List<ZoneTimeLicenseENTITY> timeLicensePublishCount(ZoneTimeLicenseENTITY entity);
	List<ZoneTimeLicenseENTITY> timeLicenseDenyCount(int zone_no);
	List<ZoneWetherLicenseENTITY> taaPlType(ZoneWetherLicenseENTITY entity); // agent pl license type table
	List<ZoneWetherLicenseENTITY> taaTplType(ZoneWetherLicenseENTITY entity); // agent tpl license type table
	List<ZoneWetherLicenseENTITY> taaTmType(ZoneWetherLicenseENTITY entity); // agent tpl license type table
	int updateLicenseInfo(ZoneLicenseUpdateDBENTITY entity); // agent tpl license type table
	int countZoneLicenseHistory(String filter);
	int countZoneInfo();
	int countZonetmLicenseHistory(String filter);
	int countLicenseStateHistory(String filter);
	int countLicenseStateHistoryWithNoFilter();
	int tmAllowedCount(int zone_no);
	int tmPublishCount(int zone_no);
	int tmDenyCount(int zone_no);
	ZoneLicenseAllowInfoENTITY getOtherContent(ZoneLicenseAllowInfoENTITY entity);
	int getAgentStaticAllow(int zone_no);
	int getAgentDynamicAllow(int zone_no);
	int getZoneNoWithZoneName(String name);
	ZoneInfoENTITY getZoneInfoWithNo(int zone_no);
	
	
}
