package snnu.campusmarket.market.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.HashMap;
import com.alibaba.fastjson.*;
import org.springframework.security.core.context.SecurityContextHolder;

public class CheckUtils {

    private static Logger logger =LoggerFactory.getLogger(CheckUtils.class);
    private static HashMap<String, Authentication> hashMap = new HashMap<>();

    /*
    生成Token
     */

    public static String generateToken(Authentication authentication){
        hashMap.put("authentication",authentication);
        String token = Jwts.builder()
                //设置token的信息
                //将认证后的authentication写入token，验证时，直接验证它
                .claim("authentication",authentication)
                //设置主题
                .setSubject("主题")
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                //加密方式及秘钥
                .signWith(SignatureAlgorithm.HS512, "MyJWTtest")
                .compact();

        return token;
    }
    /*
    Token解析
     */
    public static void tokenParser(String token){
        Authentication authentication1 = hashMap.get("authentication");
        System.out.println(authentication1);
        // 解析token.
        Claims claims = Jwts.parser()
                .setSigningKey("MyJWTtest")
                .parseClaimsJws(token)
                .getBody();

//            获取suject
//            String subject = claims.getSubject();
//            User user = (User) claims.get("user");
        //获取过期时间
        Date claimsExpiration = claims.getExpiration();
        logger.info("过期时间"+claimsExpiration);
        //判断是否过期
        Date now = new Date();
        if (now.getTime() > claimsExpiration.getTime()) {
            throw new AuthenticationServiceException("凭证已过期，请重新登录！");
        }
        //获取保存在token中的登录认证成功的authentication，
        // 利用UsernamePasswordAuthenticationToken生成新的authentication
        // 放入到SecurityContextHolder，表示认证通过
        Object tokenInfo = claims.get("authentication");
        //通过com.alibaba.fastjson将其在转换。
        JsonToAuthtication toknAuthentication = JSONObject.parseObject(JSONObject.toJSONString(tokenInfo), JsonToAuthtication.class);
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(toknAuthentication.getPrincipal(),null,toknAuthentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(toknAuthentication);
    }

}
