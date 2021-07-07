package trustnet.auth.common.service;

import java.io.IOException;
import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trustnet.auth.common.vo.SecurityAuthInfoVO;
import trustnet.auth.user.service.vo.UserInfoVO;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityService {
	private static final String SECRET_KEY = "BfmuvkdeN0ACLoaPTk1LdsUeBkHKk9j0";
	private final ObjectMapper objectMapper;

	private final long ttlMillis = 3600 * 1000;

	public SecurityAuthInfoVO createToken(SecurityAuthInfoVO authInfoVO)
			throws JsonProcessingException {
		String tokenInputValue = this.objectMapper.writeValueAsString(authInfoVO);
		String value = makeToken(tokenInputValue);
		SecurityAuthInfoVO vo = new SecurityAuthInfoVO(true, value);
		return vo;
	}

	public SecurityAuthInfoVO checkTokenValidation(HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException {
		String tokenValue = getTokenValue(request);
		String tokenSub = tokenValue.length() > 0 ? getSubject(tokenValue) : "";
		SecurityAuthInfoVO retVO = tokenSub.length() > 0
				? objectMapper.readValue(tokenSub, SecurityAuthInfoVO.class)
				: new SecurityAuthInfoVO(false, null);

		//		log.info("tokenValue ==> {}", tokenValue);
		//		log.info("tokenSub ==> {}", tokenSub);
		//		log.info("retVO ==> {}", retVO);

		return retVO;
	}

	public boolean checkAdminValidation(String tokenValue)
			throws JsonMappingException, JsonProcessingException {
		String tokenSub = tokenValue.length() > 0 ? getSubject(tokenValue) : "";
		SecurityAuthInfoVO retVO = null;
		retVO = tokenSub.length() > 0
				? objectMapper.readValue(tokenSub, SecurityAuthInfoVO.class)
				: new SecurityAuthInfoVO(false, null);

		boolean result = retVO.getUser_id().equalsIgnoreCase("1") ? true : false;
		//		 boolean result = retVO.getUser_id().equalsIgnoreCase("admin") ? true : false;
		return result;
	}

	public SecurityAuthInfoVO checkTokenValidation(String tokenValue) {
		String tokenSub = tokenValue.length() > 0 ? getSubject(tokenValue) : "";
		SecurityAuthInfoVO retVO = null;
		try {
			retVO = tokenSub.length() > 0
					? objectMapper.readValue(tokenSub, SecurityAuthInfoVO.class)
					: new SecurityAuthInfoVO(false, null);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return retVO;
	}

	public boolean passwordLengthValidation(UserInfoVO userInfoVO) {
		String tobePW = userInfoVO.getPasswd();
		boolean result = tobePW.length() > 7 ? true : false;
		return result;
	}

	public boolean passwordValidation(UserInfoVO userInfoVO) {
		String passwd = userInfoVO.getPasswd();
		String repasswd = userInfoVO.getRepasswd();
		boolean result = passwd.equalsIgnoreCase(repasswd) ? true : false;
		return result;
	}

	private String makeToken(String subject) throws JsonProcessingException {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		Key signingKey = new SecretKeySpec(secretKeyBytes,
				signatureAlgorithm.getJcaName());
		JwtBuilder builder = Jwts.builder().setSubject(subject)
				.signWith(signatureAlgorithm, signingKey);

		long nowMillis = System.currentTimeMillis();
		String value = builder.setExpiration(new Date(nowMillis + ttlMillis)).compact();
		return value;
	}

	private String getSubject(String token) {
		Claims claims = null;

		try {
			claims = Jwts.parser()
					.setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
					.parseClaimsJws(token).getBody();
		} catch (Exception e) {
			log.info("Exception e ==> {}", e.getMessage());
			return "";
		}

		return claims.getSubject();
	}

	private String getTokenValue(HttpServletRequest request) {
		String tokenVal = "";
		Cookie[] cookies = request.getCookies();
		if (cookies == null)
			return tokenVal;

		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("UNETAUTHTOKEN")) {
				tokenVal = cookie.getValue();
				break;
			}
			tokenVal = "";
		}

		if (tokenVal.length() <= 0 || tokenVal == null)
			return "";
		return tokenVal;
	}

}
