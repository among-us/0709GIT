package trustnet.auth.company.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.company.repository.CompanyMapper;
import trustnet.auth.company.repository.entity.CompNoInfoENTITY;
import trustnet.auth.company.repository.entity.CompanyInfoENTITY;
import trustnet.auth.company.repository.entity.CompanyLicenseENTITY;
import trustnet.auth.company.repository.entity.CompanyNGroupInfoENTITY;
import trustnet.auth.company.repository.entity.GroupInfoENTITY;
import trustnet.auth.company.service.vo.CompNoInfoVO;
import trustnet.auth.company.service.vo.CompanyInfoVO;
import trustnet.auth.company.service.vo.CompanyLicenseVO;
import trustnet.auth.company.service.vo.CompanyNGroupInfoVO;
import trustnet.auth.company.service.vo.GroupInfoVO;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyService {

	private final CompanyMapper mapper;
	private final ModelMapper modelMapper;
	
	public List<GroupInfoVO> findAllGroupInfoAll() {
		List<GroupInfoENTITY> entityList = mapper.findAllGroupInfoAll();
		List<GroupInfoVO> voList = modelMapper.map(entityList, new TypeToken<List<GroupInfoVO>>() {}.getType());
		return voList;
	}
	
	public List<CompanyInfoVO> findAllCompanyInfoAll() {
		List<CompanyInfoENTITY> entityList = mapper.findAllCompanyInfoAll();
		List<CompanyInfoVO> voList = modelMapper.map(entityList, new TypeToken<List<CompanyInfoVO>>() {}.getType());
		return voList;
	}
	
	public boolean saveCompanyInfo(CompanyInfoVO vo) {
		CompanyInfoENTITY entity = modelMapper.map(vo, CompanyInfoENTITY.class);
		int result = mapper.saveCompanyInfo(entity);
		return result > 0;
	}
	
	public List<CompanyLicenseVO> findAllCompanyLicenseWithCompNO(CompNoInfoVO vo) {
		CompNoInfoENTITY entity =  modelMapper.map(vo, CompNoInfoENTITY.class);
		List<CompanyLicenseENTITY> entityList = mapper.findAllCompanyLicenseWithCompNO(entity);
		List<CompanyLicenseVO> retVoList = modelMapper.map(entityList, new TypeToken<List<CompanyLicenseVO>>() {}.getType());
		return retVoList;
	}
	
	public List<CompanyNGroupInfoVO> findAllCompanyNGroupInfo() {
		List<CompanyNGroupInfoENTITY> companyNGroupInfoENTITYList = mapper.findAllCompanyNGroupInfo();
		List<CompanyNGroupInfoVO> companyNGroupInfoVOList = modelMapper.map(companyNGroupInfoENTITYList, new TypeToken<List<CompanyNGroupInfoVO>>() {}.getType());
		return companyNGroupInfoVOList;
	}

	public CompanyNGroupInfoVO findCompanyInfoAsCompNO(CompanyInfoVO vo) {
		// TODO Auto-generated method stub
		CompanyInfoENTITY entity = modelMapper.map(vo, CompanyInfoENTITY.class);
		CompanyNGroupInfoENTITY retENTITY =  mapper.findCompanyInfoAsCompNO(entity);
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
	
	
}
