package br.com.klima.securityJwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.klima.data.vo.v1.security.TokenVO;
import jakarta.annotation.PostConstruct;


@Service
public class JwtTokenProvider {
	
	@Value("${security.jwt.token.secret-key:secret}")
	private String secretKey = "secret";
	
	@Value("${security.jwt.token.expire-lenght:3600000}")
	private long validityInMilliseconds = 3600000; // 1 hora
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	Algorithm algorithm = null;
	
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		algorithm = Algorithm.HMAC256(secretKey.getBytes());
	}
	
	public TokenVO createAccessToken(String username, List<String> roles) {
		Date now = new Date();
		Date validity = new Date(now.getTime()+ validityInMilliseconds);
		var accessToken = getAccessToken(username, roles, now, validity);
		var refreshToken = getRefreshToken(username, roles, now);
		return new TokenVO(username, true, now, validity, accessToken, refreshToken);
	}

	private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
		String issueUrl = ServletUriComponentsBuilder
				.fromCurrentContextPath().build().toUriString();
		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt("roles", roles)
				.withClaim("roles", roles)
				.withClaim("roles", roles)
				.withClaim("roles", roles)
				.strip;
	}

	private String getRefreshToken(String username, List<String> roles, Date now) {
		// TODO Auto-generated method stub
		return null;
	}
}
