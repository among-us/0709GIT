package trustnet.auth.user.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jdk.internal.org.jline.utils.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.service.CommonService;
import trustnet.auth.user.controller.except.UserExistException;
import trustnet.auth.user.repository.UserMapper;
import trustnet.auth.user.repository.entity.ChartDailyENTITY;
import trustnet.auth.user.repository.entity.ChartENTITY;
import trustnet.auth.user.repository.entity.UserHistoryInfoENTITY;
import trustnet.auth.user.repository.entity.UserInfoENTITY;
import trustnet.auth.user.service.vo.ChartDailyVO;
import trustnet.auth.user.service.vo.ChartVO;
import trustnet.auth.user.service.vo.UserHistoryInfoVO;
import trustnet.auth.user.service.vo.UserInfoVO;
import trustnet.tas.except.TNAuthException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

	private final UserMapper mapper;
	private final ModelMapper modelMapper;
	private final CommonService commonService;
	private final PasswordEncoder passwordEncoder;

	public UserInfoVO getUserInfo(int user_no) {
		UserInfoVO vo = mapper.getUserInfo(user_no);
		return vo;
	}

	public boolean saveUser(UserInfoVO vo) throws TNAuthException {

		String rawPassword = vo.getPasswd(); // plain 비밀번호
		String encodedPassword = passwordEncoder.encode(rawPassword);
		vo.setPasswd(encodedPassword);
		UserInfoENTITY entity = modelMapper.map(vo, UserInfoENTITY.class);
		int result = mapper.saveUserVO(entity);
		return result > 0;
	}

	public List<UserInfoVO> findUserENTITYAll() {
		List<UserInfoENTITY> entityList = mapper.findUserAll();
		List<UserInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<UserInfoVO>>() {
				}.getType());

		return voList;
	}

	public List<UserInfoVO> findAllUserWithComp() {
		List<UserInfoENTITY> entityList = mapper.findAllUserWithComp();
		List<UserInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<UserInfoVO>>() {
				}.getType());
		return voList;
	}

	public UserInfoVO findAllUserWithCompASUserNO(UserInfoVO vo) {
		UserInfoENTITY entity = modelMapper.map(vo, UserInfoENTITY.class);
		UserInfoENTITY retEntity = mapper.findAllUserWithCompASUserNO(entity);
		UserInfoVO retVO = modelMapper.map(retEntity, UserInfoVO.class);
		return retVO;
	}

	public UserInfoVO doLogin(UserInfoVO userInfoVO) {
		UserInfoVO vo = new UserInfoVO();
		UserInfoENTITY userInfoENTITY = new UserInfoENTITY();
		userInfoENTITY = mapper.findUser(userInfoVO); // user_id 필요

		boolean isExistAccount = userInfoENTITY != null ? true : false; // null이 아니어야 true  &  null이면 false
		if (isExistAccount == false) {
			vo.setLogin(false);
			return vo;
		}

		vo = modelMapper.map(userInfoENTITY, UserInfoVO.class);

		boolean isPWCorrect = passwordEncoder.matches(userInfoVO.getPasswd(),
				vo.getPasswd()) ? true : false;
		boolean retDB = false;
		if (!isPWCorrect) //비밀번호가 틀렸으면 ? auth_err +1 
			retDB = updateUserForAuthERRIncrease(vo);
		else {
			// 비밀번호 맞추면 auth_err 초기화 & last_login update 
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

	public boolean updateUserPW(UserInfoVO vo) throws TNAuthException {
		String rawPassword = vo.getPasswd();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		vo.setPasswd(encodedPassword);
		vo.setHmac(commonService.getHMAC());
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
		List<UserHistoryInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<UserHistoryInfoVO>>() {
				}.getType());
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
		List<UserHistoryInfoENTITY> entityList = mapper
				.findAllUserHistoryAsUserNo(entity);
		List<UserHistoryInfoVO> voList = modelMapper.map(entityList,
				new TypeToken<List<UserHistoryInfoVO>>() {
				}.getType());
		return voList;
	}

	public boolean isFindUser(UserInfoVO vo) {
		UserInfoENTITY entity = modelMapper.map(vo, UserInfoENTITY.class);
		int result = mapper.isFindUser(entity);
		return result > 0;
	}

	public String[] getProjectByCompName(int comp_no) {
		String[] entityList = mapper.getProjectByCompName(comp_no);
		return entityList;
	}

	public boolean checkPwd(UserInfoVO userInfoVO) {

		UserInfoVO vo = new UserInfoVO();
		UserInfoENTITY userInfoENTITY = new UserInfoENTITY();
		userInfoENTITY = mapper.findUser(userInfoVO); // user_id 필요

		boolean isExistAccount = userInfoENTITY != null ? true : false;

		vo = modelMapper.map(userInfoENTITY, UserInfoVO.class);

		boolean isPWCorrect = passwordEncoder.matches(userInfoVO.getPasswd(),
				vo.getPasswd()) ? true : false;
		boolean retDB = false;
		if (isPWCorrect == true) {
			return true;
		} else {
			return false;
		}
		//		vo.setLogin(isPWCorrect);
		//		return vo;
	}

	public String getExist(String ID) {
		String exist = mapper.getExist(ID);
		if (exist == null) {
			return "NONE";
		} else {
			return exist;
		}
	}

	public int getPermNo(String id) {
		int perm_no = mapper.getPermNo(id);
		return perm_no;
	}

	public List<ChartVO> getChartInfo(ChartVO vo) {
		List<ChartENTITY> entityList = mapper.getChartInfo(vo);
		List<ChartVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ChartVO>>() {
				}.getType());

		return voList;
	}

	public ChartVO getTotalCount(ChartVO vo) {
		ChartENTITY entity = modelMapper.map(vo, ChartENTITY.class);
		ChartENTITY entityList = mapper.getTotalCount(entity);
		ChartVO voList = modelMapper.map(entityList, ChartVO.class);
		return voList;
	}

	public UserInfoVO getUserInfoWithUserNo(UserInfoVO vo) {
		UserInfoENTITY entity = modelMapper.map(vo, UserInfoENTITY.class);
		try {
			UserInfoENTITY resultENTITY = mapper.getUserInfoWithUserNo(entity);
			UserInfoVO resultVO = modelMapper.map(resultENTITY, UserInfoVO.class);
			return resultVO;
		} catch (Exception e) {
			UserInfoVO exceptResultVO = new UserInfoVO();
			return exceptResultVO;
		}
	}

	public List<ChartDailyVO> getDailyChartInfo(ChartDailyVO vo) {
		List<ChartDailyENTITY> entityList = mapper.getDailyChartInfo(vo);
		List<ChartDailyVO> voList = modelMapper.map(entityList,
				new TypeToken<List<ChartDailyVO>>() {
				}.getType());
		return voList;
	}

	public ChartDailyVO getDailyChartTotalInfo(ChartDailyVO vo) {
		ChartDailyENTITY entityList = mapper.getDailyChartTotalInfo(vo);
		ChartDailyVO voList = modelMapper.map(entityList, ChartDailyVO.class);
		return voList;
	}

	public String getZoneNameWithUserNo(int user_no) {
		String z_name = mapper.getZoneNameWithUserNo(user_no);

		return z_name;
	}

	public int getPermNoWithUserNo(int user_no) {
		int perm_no = mapper.getPermNoWithUserNo(user_no);

		return perm_no;
	}

} // END SERVICE 
