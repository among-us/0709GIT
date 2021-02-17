package trustnet.auth.company.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import trustnet.auth.company.repository.entity.CompNoInfoENTITY;
import trustnet.auth.company.repository.entity.CompanyInfoENTITY;
import trustnet.auth.company.repository.entity.CompanyLicenseENTITY;
import trustnet.auth.company.repository.entity.CompanyNGroupInfoENTITY;
import trustnet.auth.company.repository.entity.GroupInfoENTITY;

@Repository
@Mapper
public interface CompanyMapper {

	public List<GroupInfoENTITY> findAllGroupInfoAll();
	public List<CompanyInfoENTITY> findAllCompanyInfoAll();
	public List<CompanyNGroupInfoENTITY> findAllCompanyNGroupInfo();
	public CompanyNGroupInfoENTITY findCompanyInfoAsCompNO(CompanyInfoENTITY entity);
	public int saveCompanyInfo(CompanyInfoENTITY entity);
	List<CompanyLicenseENTITY> findAllCompanyLicenseWithCompNO(CompNoInfoENTITY entity);
	public int updateCompanyInfo(CompanyInfoENTITY entity);
	public int deleteCompanyInfo(CompanyInfoENTITY entity);
}
