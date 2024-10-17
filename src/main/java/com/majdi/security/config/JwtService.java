package com.majdi.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.antlr.v4.runtime.Token;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String secretkey ="HgObdgmv2hZn4aEhPpJrFYkNZ64yNg4fjDM/bNDxUdll4ALFLAmNp6ZaKjCUTZ4dZnNmpUjzV/GVJh75Grk79NbnFTgM+BeDcqHt3Sz6bf9XyHLsrmviAPteK9MfFgak00qT7wg/a5WduX5grf7SH7PbIVQAAo5hvD0edJOg9GHmEpRpFVzHdarBGgX66OadFubs175PD3RdCCAGJMCsKSSGXBaLlY1Zep8ck+1vpI12HlxPzUG83GOVHGEmU1YIGP6Lm9S8uKz6Ez6VitUkROXj7IHieOFD8O+C0rlW5QUus51vquFkHKoWILb8OP4Y9nATS10CdQd8nqhtkiKMX2p9ifYn+oA46qa2rq2xC8w="
    ;

    public String extractEmail(String token) {

return extractClaim(token, Claims::getSubject);
    }




    public <T> T extractClaim(String token, Function<Claims,T> claimResorver) {
        final Claims claims=extractClaims(token);
        return claimResorver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }

    public String generateToken(Map<String,Object> extractclaims,
                                UserDetails userDetails){
        return Jwts.builder().setClaims(extractclaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24*7))
                .signWith(getsignkey(), SignatureAlgorithm.HS256)
                .compact();

    }
    public boolean isTkenValid(String token,UserDetails userDetails) {
        final String username=extractEmail(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpired(token).before(new Date());
    }

    private Date extractExpired(String token) {
    return extractClaim(token, Claims::getExpiration);
    }


    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(getsignkey()).
                build().
                parseClaimsJws(token).
                getBody();
}

    private Key getsignkey() {
    byte[] keybytes = Decoders.BASE64.decode(secretkey);
    return Keys.hmacShaKeyFor(keybytes);
    }

}
