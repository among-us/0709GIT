package trustnet.auth.company.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.company.repository.CompanyMapper;
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
import trustnet.auth.company.service.vo.CompNoInfoVO;
import trustnet.auth.company.service.vo.CompProjZoneListHistoryVO;
import trustnet.auth.company.service.vo.CompProjZoneListVO;
import trustnet.auth.company.service.vo.CompanyHistoryInfoVO;
import trustnet.auth.company.service.vo.CompanyInfoVO;
import trustnet.auth.company.service.vo.CompanyLicenseVO;
import trustnet.auth.company.service.vo.CompanyMatchingVO;
import trustnet.auth.company.service.vo.CompanyNGroupInfoVO;
import trustnet.auth.company.service.vo.CompanyProjZoneVO;
import trustnet.auth.company.service.vo.CompanyProjectHistoryVO;
import trustnet.auth.company.service.vo.CompanyProjectInfoVO;
import trustnet.auth.company.service.vo.GroupInfoVO;
import trustnet.auth.manager.repository.entity.ManagerHistoryInfoENTITY;
import trustnet.auth.manager.service.vo.ManagerHistoryInfoVO;
import trustnet.auth.manager.service.vo.ManagerInfoVO;
import trustnet.auth.user.repository.entity.MainDashboardCountENTITY;
import trustnet.auth.user.service.vo.MainDashboardCountVO;
import trustnet.auth.zone.repository.entity.ZoneInfoENTITY;
import trustnet.auth.zone.repository.entity.ZoneLicenseHistoryInfoENTITY;
import trustnet.auth.zone.service.vo.ZoneInfoVO;
import trustnet.auth.zone.service.vo.ZoneLicenseHistoryInfoVO;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyService {

	private final CompanyMapper mapper;
	private final ModelMapper modelMapper;

	public List<CompanyInfoVO> findAllCompanyInfoAll() {
		List<CompanyInfoENTITY> entityList = mapper.findAllCompanyInfoAll();
		List<CompanyInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<CompanyInfoVO>>() {
				}.getType());
		return voList;
	}

	public List<CompanyInfoVO> findAllCompanyInfo() {
		List<CompanyInfoENTITY> entityList = mapper.findAllCompanyInfo();
		List<CompanyInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<CompanyInfoVO>>() {
				}.getType());
		return voList;
	}

	public List<CompanyProjectInfoVO> findAllProjectInfo() {
		List<CompanyProjectInfoENTITY> entityList = mapper.findAllProjectInfo();
		List<CompanyProjectInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<CompanyProjectInfoVO>>() {
				}.getType());
		return voList;
	}

	public boolean saveCompanyInfo(CompanyInfoVO vo) {
		CompanyInfoENTITY entity = modelMapper.map(vo, CompanyInfoENTITY.class);
		int result = mapper.saveCompanyInfo(entity);
		return result > 0;
	}

	public boolean saveMatchingProZone(CompanyMatchingVO vo) {
		CompanyMatchingENTITY entity = modelMapper.map(vo, CompanyMatchingENTITY.class);
		int result = mapper.saveMatchingProZone(entity);
		return result > 0;
	}

	public boolean saveMatchZoneHistory(CompProjZoneListHistoryVO vo) {
		CompProjZoneListHistoryENTITY entity = modelMapper.map(vo,
				CompProjZoneListHistoryENTITY.class);
		int result = mapper.saveMatchZoneHistory(entity);
		return result > 0;
	}

	public int existNumber(int project_number) {
		int result = mapper.existNumber(project_number);
		return result;
	}

	public int existZoneNumber(int zone_no) {
		int result = mapper.existZoneNumber(zone_no);
		return result;
	}

	public boolean saveCompanyProjectInfo(CompanyProjectInfoVO vo) {
		CompanyProjectInfoENTITY entity = modelMapper.map(vo,
				CompanyProjectInfoENTITY.class);
		int result = mapper.saveCompanyProjectInfo(entity);
		return result > 0;
	}

	public boolean saveMatchList(CompProjZoneListVO vo) {
		CompProjZoneListENTITY entity = modelMapper.map(vo, CompProjZoneListENTITY.class);
		int result = mapper.saveMatchList(entity);
		return result > 0;
	}

	public boolean saveMatchHistory(CompProjZoneListHistoryVO vo) {
		CompProjZoneListHistoryENTITY entity = modelMapper.map(vo,
				CompProjZoneListHistoryENTITY.class);
		int result = mapper.saveMatchHistory(entity);
		return result > 0;
	}

	public List<CompanyLicenseVO> findAllCompanyLicenseWithCompNO(CompNoInfoVO vo) {
		CompNoInfoENTITY entity = modelMapper.map(vo, CompNoInfoENTITY.class);
		List<CompanyLicenseENTITY> entityList = mapper
				.findAllCompanyLicenseWithCompNO(entity);
		List<CompanyLicenseVO> retVoList = modelMapper.map(entityList,
				new TypeToken<List<CompanyLicenseVO>>() {
				}.getType());
		return retVoList;
	}

	public List<CompanyLicenseVO> findTmlicensePubCnt(CompNoInfoVO vo) {
		CompNoInfoENTITY entity = modelMapper.map(vo, CompNoInfoENTITY.class);
		List<CompanyLicenseENTITY> entityList = mapper.findTmlicensePubCnt(entity);
		List<CompanyLicenseVO> retVoList = modelMapper.map(entityList,
				new TypeToken<List<CompanyLicenseVO>>() {
				}.getType());
		return retVoList;
	}

	public List<CompanyNGroupInfoVO> findAllCompanyNGroupInfo() {
		List<CompanyNGroupInfoENTITY> companyNGroupInfoENTITYList = mapper
				.findAllCompanyNGroupInfo();
		List<CompanyNGroupInfoVO> companyNGroupInfoVOList = modelMapper.map(
				companyNGroupInfoENTITYList, new TypeToken<List<CompanyNGroupInfoVO>>() {
				}.getType());
		return companyNGroupInfoVOList;
	}

	public List<CompProjZoneListVO> getProjectWithCompNo(CompProjZoneListVO vo) {
		List<CompProjZoneListENTITY> entityList = mapper.getProjectWithCompNo(vo);
		List<CompProjZoneListVO> voList = modelMapper.map(entityList,
				new TypeToken<List<CompProjZoneListVO>>() {
				}.getType());
		return voList;
	}

	public CompanyNGroupInfoVO findCompanyInfoAsCompNO(CompanyInfoVO vo) {
		CompanyInfoENTITY entity = modelMapper.map(vo, CompanyInfoENTITY.class);
		CompanyNGroupInfoENTITY retENTITY = mapper.findCompanyInfoAsCompNO(entity);
		CompanyNGroupInfoVO retVO = modelMapper.map(retENTITY, CompanyNGroupInfoVO.class);
		return retVO;
	}

	public boolean updateCompanyInfo(CompanyInfoVO vo) {
		CompanyInfoENTITY entity = modelMapper.map(vo, CompanyInfoENTITY.class);
		int result = mapper.updateCompanyInfo(entity);
		return result > 0;
	}

	public boolean deleteCompanyInfo(CompanyInfoVO vo) {
		CompanyInfoENTITY entity = modelMapper.map(vo, CompanyInfoENTITY.class);
		int result = mapper.deleteCompanyInfo(entity);
		return result > 0;
	}

	public boolean isFindGroup(CompanyInfoVO vo) {
		int ret = mapper.isFindGroup(vo);
		return ret > 0;
	}

	public boolean isFindProject(CompanyProjectInfoVO vo) {
		int ret = mapper.isFindProject(vo);
		return ret > 0;
	}

	public boolean isDupCompZoneMatching(CompanyProjectInfoVO vo) {
		int ret = mapper.isDupCompZoneMatching(vo);
		return ret > 0;
	}

	public List<CompanyInfoVO> findAllGroup() {
		List<CompanyInfoENTITY> entityList = mapper.findAllGroup();
		List<CompanyInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<CompanyInfoVO>>() {
				}.getType());

		return voList;
	}

	public List<CompanyHistoryInfoVO> findCompanyHistoryAll(CompanyHistoryInfoVO vo) {
		CompanyHistoryInfoENTITY entity = modelMapper.map(vo,
				CompanyHistoryInfoENTITY.class);
		List<CompanyHistoryInfoENTITY> entityList = mapper.findCompanyHistoryAll(entity);
		List<CompanyHistoryInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<CompanyHistoryInfoVO>>() {
				}.getType());

		return voList;
	}

	public List<CompanyProjectHistoryVO> findCompanyProjectHistoryAll(
			CompanyProjectHistoryVO vo) {
		CompanyProjectHistoryENTITY entity = modelMapper.map(vo,
				CompanyProjectHistoryENTITY.class);
		List<CompanyProjectHistoryENTITY> entityList = mapper
				.findCompanyProjectHistoryAll(entity);
		List<CompanyProjectHistoryVO> voList = modelMapper.map(entityList,
				new TypeToken<List<CompanyProjectHistoryVO>>() {
				}.getType());

		return voList;
	}

	public List<CompanyProjZoneVO> getProjectZoneMatchingList(CompanyProjZoneVO vo) {
		CompanyProjZoneENTITY entity = modelMapper.map(vo, CompanyProjZoneENTITY.class);
		List<CompanyProjZoneENTITY> entityList = mapper
				.getProjectZoneMatchingList(entity);
		List<CompanyProjZoneVO> voList = modelMapper.map(entityList,
				new TypeToken<List<CompanyProjZoneVO>>() {
				}.getType());

		return voList;
	}

	public List<CompanyProjectInfoVO> findAllProjectList(CompanyProjectInfoVO vo) {
		CompanyProjectInfoENTITY entity = modelMapper.map(vo,
				CompanyProjectInfoENTITY.class);

		List<CompanyProjectInfoENTITY> entityList = mapper.findAllProjectList(entity);
		List<CompanyProjectInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<CompanyProjectInfoVO>>() {
				}.getType());
		return voList;
	}

	public MainDashboardCountVO getDashboardCount() {
		MainDashboardCountENTITY entityList = mapper.getDashboardCount();
		MainDashboardCountVO voList = modelMapper.map(entityList,
				MainDashboardCountVO.class);
		return voList;
	}

	public String[] getProjZoneMatchName(int comp_no) {

		String[] entityList = mapper.getProjZoneMatchName(comp_no);
		return entityList;
	}

	public int countProjectList() {
		int result = mapper.countProjectList();
		return result;
	}

	public int countHistoryList() {
		int result = mapper.countHistoryList();
		return result;
	}

	public int countProjectHistoryList() {
		int result = mapper.countProjectHistoryList();
		return result;
	}

	public int countProjectZoneMatchingList() {
		int result = mapper.countProjectZoneMatchingList();
		return result;
	}

	public boolean saveCompanyHistory(CompanyHistoryInfoVO vo) {
		CompanyHistoryInfoENTITY entity = modelMapper.map(vo,
				CompanyHistoryInfoENTITY.class);
		int result = mapper.saveCompanyHistory(entity);
		return result > 0;
	}

	public boolean deleteCompanyHistory(CompanyHistoryInfoVO vo) {
		CompanyHistoryInfoENTITY entity = modelMapper.map(vo,
				CompanyHistoryInfoENTITY.class);
		int result = mapper.deleteCompanyHistory(entity);
		return result > 0;
	}

	public boolean saveCompanyProjectHistory(CompanyProjectHistoryVO vo) {
		CompanyProjectHistoryENTITY entity = modelMapper.map(vo,
				CompanyProjectHistoryENTITY.class);
		int result = mapper.saveCompanyProjectHistory(entity);
		return result > 0;
	}

	public int getProjectNoWithName(String proj_name) {
		int result = mapper.getProjectNoWithName(proj_name);
		return result;
	}

	public CompanyProjectInfoVO getProjectInfo(CompanyProjectInfoVO vo) {
		CompanyProjectInfoENTITY entityList = mapper.getProjectInfo(vo);
		CompanyProjectInfoVO voList = modelMapper.map(entityList,
				CompanyProjectInfoVO.class);
		return voList;
	}

	public boolean updateProject(CompanyProjectInfoVO vo) {
		CompanyProjectInfoENTITY entity = modelMapper.map(vo,
				CompanyProjectInfoENTITY.class);
		boolean result = mapper.updateProject(entity);
		return result;
	}

	public boolean updateProjectHistory(CompanyProjectHistoryVO vo) {
		CompanyProjectHistoryENTITY entity = modelMapper.map(vo,
				CompanyProjectHistoryENTITY.class);
		boolean result = mapper.updateProjectHistory(entity);
		return result;
	}
	
	public String getCompNameWithCompNo(int comp_no) {
		String asis_comp_name = mapper.getCompNameWithCompNo(comp_no);
		
		return asis_comp_name;
	}
	
	public int checkUpdateGroupName(CompanyInfoVO vo) {
		int result = mapper.checkUpdateGroupName(vo);
		
		return result;
	}

}
