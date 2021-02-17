package trustnet.auth.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import trustnet.auth.user.repository.entity.UserHistoryInfoENTITY;
import trustnet.auth.user.repository.entity.UserInfoENTITY;
import trustnet.auth.user.service.vo.UserInfoVO;

@Repository
@Mapper
public interface UserMapper {

	int saveUserVO(UserInfoENTITY entity);
	List<UserInfoENTITY> findUserAll();
	UserInfoENTITY findUser(UserInfoVO userInfoVO);
	int updateUserForAuthERRIncrease(UserInfoENTITY entity);
	int updateUserForAuthERRInit(UserInfoENTITY entity);
	int updateUserForLastLogin(UserInfoENTITY entity);
	List<UserInfoENTITY> findAllUserWithComp();
	UserInfoENTITY findAllUserWithCompASUserNO(UserInfoENTITY entity);
	int updateUserInfo(UserInfoENTITY entity);
	int updateUserPW(UserInfoENTITY entity);
	int saveUserHistory(UserHistoryInfoENTITY entity);
	UserInfoENTITY findUserInfoAsUserID(UserInfoENTITY entity);
	List<UserHistoryInfoENTITY> findAllUserHistory();
	UserInfoENTITY findUserInfoAsUserNO(UserInfoENTITY entity);
	List<UserHistoryInfoENTITY> findAllUserHistoryAsUserNo(UserInfoENTITY entity);
	int isFindUser(UserInfoENTITY entity);
	

}
