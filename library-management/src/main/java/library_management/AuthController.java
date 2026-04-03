package library_management;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username already taken", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(), user.getPassword())
            );
            String token = jwtUtil.generateToken(user.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

}
