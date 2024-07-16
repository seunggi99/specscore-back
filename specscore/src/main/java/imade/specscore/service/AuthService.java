package imade.specscore.service;

import imade.specscore.domain.User;
import imade.specscore.dto.JwtRequest;
import imade.specscore.dto.JwtResponse;
import imade.specscore.dto.SigninRequest;
import imade.specscore.dto.SignupRequest;
import imade.specscore.exception.UsernameAlreadyExistsException;
import imade.specscore.repository.UserRepository;
import imade.specscore.utill.JWTUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.HashMap;
@Service
public class AuthService {

    @Autowired
    private UserRepository ourUserRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtResponse signUp(SignupRequest registrationRequest){
        JwtResponse resp = new JwtResponse();
        try {
            // 이름 중복 검사
            ourUserRepo.findByUsername(registrationRequest.getUsername())
                    .ifPresent(u -> {
                        throw new UsernameAlreadyExistsException("이미 존재하는 아이디입니다.");
                    });

            User users = new User();
            users.setUsername(registrationRequest.getUsername());
            users.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            users.setEmail(registrationRequest.getEmail());
            users.setNickname(registrationRequest.getNickname());
            users.setPhone(registrationRequest.getPhone());
            users.setBirthDate(registrationRequest.getBirthDate());
            users.setRegistrationDate(LocalDateTime.now());
            users.set_active(true);
            users.setInterestedJob(registrationRequest.getInterestedJob());
            users.setReason(registrationRequest.getReason());
            users.setProfile_img(registrationRequest.getProfile_img());
            users.setCertificate(registrationRequest.getCertificate());
            users.setEducation(registrationRequest.getEducation());
            users.setExperience(registrationRequest.getExperience());
            users.setContent(registrationRequest.getContent());
            users.setRole(registrationRequest.getRole());


            User ourUserResult = ourUserRepo.save(users);
            if (ourUserResult != null && ourUserResult.getId()>0) {

                resp.setOurUsers(ourUserResult);
                resp.setMessage("회원 저장 성공");
                resp.setStatusCode(200);
            }
        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }

        return resp;
    }

    public JwtResponse signIn(SigninRequest signinRequest){
        JwtResponse response = new JwtResponse();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(),signinRequest.getPassword()));
            var user = ourUserRepo.findByUsername(signinRequest.getUsername()).orElseThrow();
            System.out.println("USER IS: "+ user);
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setNickname(user.getNickname());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setMessage("로그인 성공");
        }catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public JwtResponse refreshToken(JwtRequest refreshTokenRequest){
        JwtResponse response = new JwtResponse();
        try {
            String ourName = jwtUtils.extractUsername(refreshTokenRequest.getToken());
            User users = ourUserRepo.findByUsername(ourName).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenRequest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("재발급 성공");
            } else {
                response.setStatusCode(401);
                response.setError("유효하지 않은 토큰");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }


    public JwtResponse validateToken(JwtRequest validateTokenRequest) {
        JwtResponse response = new JwtResponse();
        try {
            String ourName = jwtUtils.extractUsername(validateTokenRequest.getToken());
            User user = ourUserRepo.findByUsername(ourName).orElseThrow(() -> new Exception("사용자를 찾을 수 없습니다."));
            // 토큰 유효성 검사
            if (jwtUtils.isTokenValid(validateTokenRequest.getToken(), user)) {
                response.setStatusCode(200); // OK
                response.setMessage("토큰이 유효합니다.");
            } else {
                response.setStatusCode(401); // Unauthorized
                response.setMessage("유효하지 않은 토큰입니다.");
            }
        } catch (Exception e) {
            // 예외 발생 시
            response.setStatusCode(500); // Internal Server Error
            response.setError(e.getMessage());
        }
        return response;
    }


}
