package trustnet.auth.zone.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import trustnet.auth.zone.repository.entity.ZoneHistoryInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneLicenseCodeInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneLicenseHistoryInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneLicenseInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneLicenseStateHistoryInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneLicenseUpdateENTITY;
import trustnet.auth.zone.repository.entity.ZoneNLicenseInfoENTITY;
import trustnet.auth.zone.service.vo.ZoneInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseInfoVO;
import trustnet.auth.zone.service.vo.ZoneVerifyIntegrityVO;
import trustnet.auth.zone.service.vo.ZoneVerifyIntegrityVO;

@Repository
@Mapper
public interface ZoneMapper {

	List<ZoneInfoENTITY> findAllZoneList();
	List<ZoneInfoENTITY> findAllZoneListOnlyExist();
	ZoneInfoENTITY findZoneWithNO(ZoneInfoENTITY entity);
	int deleteZoneWithID(ZoneInfoENTITY entity);
	int saveZoneInfo(ZoneInfoVO vo);
	int saveZoneLicenseInfo(ZoneLicenseInfoVO vo);
	List<ZoneNLicenseInfoENTITY> findAllZoneLicenseList();
	int updateZoneWithID(ZoneInfoENTITY entity);
	List<ZoneInfoENTITY> findZoneWithCompNo(ZoneInfoENTITY entity);
	List<ZoneInfoENTITY> findZoneWithCompNoWithAlive(ZoneInfoENTITY entity);
	ZoneNLicenseInfoENTITY findAllZoneLicenseListAsZoneNO(ZoneLicenseInfoENTITY entity);
	int updateZoneLicenseInfo(ZoneLicenseUpdateENTITY entity);
	int deleteZoneLicenseInfo(ZoneLicenseInfoENTITY entity);
	int saveZoneHistory(ZoneHistoryInfoENTITY entity);
	ZoneInfoENTITY findZoneInfoAsZoneName(ZoneInfoENTITY entity);
	List<ZoneHistoryInfoENTITY> findAllZoneHistoryAsZoneNo(ZoneInfoENTITY entity);
	List<ZoneLicenseCodeInfoENTITY> findAllZoneCodeInfo();
	int saveZoneLicenseHistory(ZoneLicenseHistoryInfoENTITY entity);
	ZoneLicenseInfoENTITY findZoneLicenseAsZoneNONTaaIP(ZoneLicenseInfoENTITY entity);
	List<ZoneLicenseHistoryInfoENTITY> findZoneLicenseHistoryInfo(ZoneInfoENTITY entity);
	List<ZoneLicenseStateHistoryInfoENTITY> findAllLicenseStateHistoryInfo();
//	<select id="isFindZone" parameterType="ZoneInfoENTITY" resultType="int">
	int isFindZone(ZoneInfoENTITY entity);

	int verifyZoneIntegrity(ZoneVerifyIntegrityVO ZVIvo);
//	List<ZoneVerifyIntegrityVO> verifyZoneIntegrity(ZoneVerifyIntegrityVO ZVIvo);
	
}
