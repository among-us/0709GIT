package trustnet.auth.company.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import trustnet.auth.company.repository.entity.CompNoInfoENTITY;
import trustnet.auth.company.repository.entity.CompProjZoneListENTITY;
import trustnet.auth.company.repository.entity.CompProjZoneListHistoryENTITY;
import trustnet.auth.company.repository.entity.CompanyHistoryInfoENTITY;
import trustnet.auth.company.repository.entity.CompanyInfoENTITY;
import trustnet.auth.company.repository.entity.CompanyLicenseENTITY;
import trustnet.auth.company.repository.entity.CompanyMatchingENTITY;
import trustnet.auth.company.repository.entity.CompanyNGroupInfoENTITY;
import trustnet.auth.company.repository.entity.CompanyProjZoneENTITY;
import trustnet.auth.company.repository.entity.CompanyProjectHistoryENTITY;
import trustnet.auth.company.repository.entity.CompanyProjectInfoENTITY;
import trustnet.auth.company.repository.entity.GroupInfoENTITY;
import trustnet.auth.company.service.vo.CompProjZoneListVO;
import trustnet.auth.company.service.vo.CompanyInfoVO;
import trustnet.auth.company.service.vo.CompanyProjectInfoVO;
import trustnet.auth.manager.repository.entity.ManagerHistoryInfoENTITY;
import trustnet.auth.user.repository.entity.MainDashboardCountENTITY;
import trustnet.auth.zone.repository.entity.ZoneInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneLicenseHistoryInfoENTITY;

@Repository
@Mapper
public interface CompanyMapper {

//	public List<GroupInfoENTITY> findAllGroupInfoAll();
	public List<CompanyInfoENTITY> findAllCompanyInfoAll();
//	public List<CompProjZoneListENTITY> getProjNameByComp(int user_no);
//	public String[] getProjNameByComp(int user_no);
//	public List<CompProjZoneListENTITY> getProjNameByComp(int user_no);
	public List<CompanyInfoENTITY> findAllCompanyInfo();
	public List<CompanyProjectInfoENTITY> findAllProjectInfo();
	public List<CompanyNGroupInfoENTITY> findAllCompanyNGroupInfo();
	public List<CompProjZoneListENTITY> getProjectWithCompNo(CompProjZoneListVO vo);
	public CompanyNGroupInfoENTITY findCompanyInfoAsCompNO(CompanyInfoENTITY entity);
	public int saveCompanyInfo(CompanyInfoENTITY entity);
	public int saveMatchingProZone(CompanyMatchingENTITY entity);
	public int saveMatchZoneHistory(CompProjZoneListHistoryENTITY entity);
	public int existNumber(int project_number);
	public int existZoneNumber(int zone_no);
	public int saveCompanyProjectInfo(CompanyProjectInfoENTITY entity);
	public int saveMatchList(CompProjZoneListENTITY entity);
	public int saveMatchHistory(CompProjZoneListHistoryENTITY entity);
	List<CompanyLicenseENTITY> findAllCompanyLicenseWithCompNO(CompNoInfoENTITY entity);
	List<CompanyLicenseENTITY> findTmlicensePubCnt(CompNoInfoENTITY entity);
	public int updateCompanyInfo(CompanyInfoENTITY entity);
	public int deleteCompanyInfo(CompanyInfoENTITY entity);
	int isFindGroup(CompanyInfoVO vo);
	int isFindProject(CompanyProjectInfoVO vo);
	int isDupCompZoneMatching(CompanyProjectInfoVO vo);
	List<CompanyInfoENTITY> findAllGroup();
	List<CompanyHistoryInfoENTITY> findCompanyHistoryAll(CompanyHistoryInfoENTITY entity);
	List<CompanyProjectHistoryENTITY> findCompanyProjectHistoryAll(CompanyProjectHistoryENTITY entity);
	List<CompanyProjZoneENTITY> getProjectZoneMatchingList(CompanyProjZoneENTITY entity);
	List<CompanyProjectInfoENTITY> findAllProjectList(CompanyProjectInfoENTITY entity);
	MainDashboardCountENTITY getDashboardCount();
	String[] getProjZoneMatchName(int comp_no);
//	List<CompanyProjectInfoENTITY> getProjZoneMatchName(CompanyProjectInfoENTITY entity);
	int countProjectList();
	int countHistoryList();
	int countProjectHistoryList();
	int countProjectZoneMatchingList();
	int saveCompanyHistory(CompanyHistoryInfoENTITY entity);
	int deleteCompanyHistory(CompanyHistoryInfoENTITY entity);
	int saveCompanyProjectHistory(CompanyProjectHistoryENTITY entity);
	int getProjectNoWithName(String proj_name);
	
	CompanyProjectInfoENTITY getProjectInfo(CompanyProjectInfoVO vo);
	boolean updateProject(CompanyProjectInfoENTITY entity);
	boolean updateProjectHistory(CompanyProjectHistoryENTITY entity);
	
	String getCompNameWithCompNo(int comp_no);
	int checkUpdateGroupName(CompanyInfoVO vo);
	
}
