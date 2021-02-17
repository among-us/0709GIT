package trustnet.auth.user.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.service.CommonService;
import trustnet.auth.user.repository.UserMapper;
import trustnet.auth.user.repository.entity.UserHistoryInfoENTITY;
import trustnet.auth.user.repository.entity.UserInfoENTITY;
import trustnet.auth.user.service.vo.UserHistoryInfoVO;
import trustnet.auth.user.service.vo.UserInfoVO;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

	private final UserMapper mapper;
	private final ModelMapper modelMapper;
	private final CommonService commonService;
	private final PasswordEncoder passwordEncoder;
	public boolean saveUser(UserInfoVO vo) {
		
		String rawPassword = vo.getPasswd();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		log.info(encodedPassword);
		
		////////////////////////////////
		vo.setPasswd(encodedPassword);
		vo.setHmac(commonService.getHMAC());
		vo.setPermissions(commonService.getPermissions());
		////////////////////////////////
		UserInfoENTITY entity = modelMapper.map(vo, UserInfoENTITY.class);
		int result = mapper.saveUserVO(entity);
		return result > 0;
	}

	public List<UserInfoVO> findUserENTITYAll() {
		// TODO Auto-generated method stub
		
		List<UserInfoENTITY> entityList = mapper.findUserAll();
		List<UserInfoVO> voList = 
				modelMapper.map(entityList, new TypeToken<List<UserInfoVO>>() {}.getType());
		
		return voList;
	}

	public List<UserInfoVO> findAllUserWithComp() {
		List<UserInfoENTITY> entityList = mapper.findAllUserWithComp();
		List<UserInfoVO> voList = modelMapper.map(entityList, new TypeToken<List<UserInfoVO>>() {}.getType());
		return voList;
	}
	
	public UserInfoVO findAllUserWithCompASUserNO(UserInfoVO vo) {
		UserInfoENTITY entity = modelMapper.map(vo, UserInfoENTITY.class);
		UserInfoENTITY retEntity = mapper.findAllUserWithCompASUserNO(entity);
		UserInfoVO retVO =modelMapper.map(retEntity, UserInfoVO.class);
		return retVO;
	}
	
	public UserInfoVO doLogin(UserInfoVO userInfoVO) {
		// TODO Auto-generated method stub
		UserInfoVO vo = new UserInfoVO();
		UserInfoENTITY userInfoENTITY = new UserInfoENTITY();
		userInfoENTITY = mapper.findUser(userInfoVO);
		boolean isExistAccount = userInfoENTITY != null ? true : false;
		log.info("isExistAccount : {}", isExistAccount);
		if(isExistAccount == false) {
			vo.setLogin(false);
			return vo;
		}
		
		vo = modelMapper.map(userInfoENTITY, UserInfoVO.class);
		boolean isPWCorrect = passwordEncoder.matches(userInfoVO.getPasswd(), vo.getPasswd()) ? true : false;
		boolean retDB = false;
		if(!isPWCorrect)
			retDB = updateUserForAuthERRIncrease(vo);
		else {
			retDB = updateUserForAuthERRInit(vo);
			retDB = updateUserForLastLogin(vo);
		}

		vo.setLogin(isPWCorrect);
		return vo;
	}
	
	boolean updateUserForAuthERRIncrease(UserInfoVO userInfoVO) {
		UserInfoENTITY entity = modelMapper.map(userInfoVO, UserInfoENTITY.class);
		int result = mapper.updateUserForAuthERRIncrease(entity);
		return result > 0;
	}
	
	boolean updateUserForAuthERRInit(UserInfoVO userInfoVO) {
		UserInfoENTITY entity = modelMapper.map(userInfoVO, UserInfoENTITY.class);
		int result = mapper.updateUserForAuthERRInit(entity);
		return result > 0;
	}
	
	boolean updateUserForLastLogin(UserInfoVO userInfoVO) {
		UserInfoENTITY entity = modelMapper.map(userInfoVO, UserInfoENTITY.class);
		int result = mapper.updateUserForLastLogin(entity);
		return result > 0;
	}
	
	public boolean updateUserInfo(UserInfoVO vo) {
		UserInfoENTITY entity = modelMapper.map(vo, UserInfoENTITY.class);
		int result = mapper.updateUserInfo(entity);
		return result > 0;
	}
	
	public boolean updateUserPW(UserInfoVO vo) {
		String rawPassword = vo.getPasswd();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		log.info(encodedPassword);
		////////////////////////////////
		vo.setPasswd(encodedPassword);
		vo.setHmac(commonService.getHMAC());
		vo.setPermissions(commonService.getPermissions());
		////////////////////////////////
		
		UserInfoENTITY entity = modelMapper.map(vo, UserInfoENTITY.class);
		int result = mapper.updateUserPW(entity);
		return result > 0;
	}
	
	public boolean saveUserHistory(UserHistoryInfoVO vo) {

		UserHistoryInfoENTITY entity = modelMapper.map(vo, UserHistoryInfoENTITY.class);
		int result = mapper.saveUserHistory(entity);
		return result > 0;
	}
	
	public UserInfoVO findUserInfoAsUserID(UserInfoVO vo) {
		UserInfoENTITY entity = modelMapper.map(vo, UserInfoENTITY.class);
		UserInfoENTITY retENTITY = mapper.findUserInfoAsUserID(entity);
		UserInfoVO retVO = modelMapper.map(retENTITY, UserInfoVO.class);
		return retVO;
	}
	
	public List<UserHistoryInfoVO> findAllUserHistory() {
		List<UserHistoryInfoENTITY> entityList = mapper.findAllUserHistory();
		List<UserHistoryInfoVO> voList = modelMapper.map(entityList, new TypeToken<List<UserHistoryInfoVO>>() {}.getType());
		return voList;
	}
	
	public UserInfoVO findUserInfoAsUserNO(UserInfoVO vo) {
		UserInfoENTITY entity = modelMapper.map(vo, UserInfoENTITY.class);
		UserInfoENTITY retENTITY = mapper.findUserInfoAsUserNO(entity);
		UserInfoVO retVO = modelMapper.map(retENTITY, UserInfoVO.class);
		return retVO;
	}
	
//	List<UserHistoryInfoENTITY> findAllUserHistoryAsUserNo(UserInfoENTITY entity);
	public List<UserHistoryInfoVO> findAllUserHistoryAsUserNo(UserInfoVO vo) {
		UserInfoENTITY entity = modelMapper.map(vo, UserInfoENTITY.class);
		List<UserHistoryInfoENTITY> entityList = mapper.findAllUserHistoryAsUserNo(entity);
		List<UserHistoryInfoVO> voList = modelMapper.map(entityList, new TypeToken<List<UserHistoryInfoVO>>() {}.getType());
		return voList;
	}
	
	public boolean isFindUser(UserInfoVO vo) {
		UserInfoENTITY entity = modelMapper.map(vo, UserInfoENTITY.class);
		int result = mapper.isFindUser(entity);
		return result > 0;
	}
	
}
