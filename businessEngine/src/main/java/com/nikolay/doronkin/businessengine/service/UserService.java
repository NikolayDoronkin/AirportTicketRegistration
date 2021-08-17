package com.nikolay.doronkin.businessengine.service;

import com.nikolay.doronkin.businessengine.dto.TokenDto;
import com.nikolay.doronkin.businessengine.dto.UserDto;
import com.nikolay.doronkin.businessengine.dto.request.SignupRequest;
import com.nikolay.doronkin.businessengine.exception.ExceptionMessages;
import com.nikolay.doronkin.businessengine.jwt.JwtTokenProvider;
import com.nikolay.doronkin.businessengine.mapper.UserDtoToUserEntityMapper;
import com.nikolay.doronkin.businessengine.model.User;
import com.nikolay.doronkin.businessengine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDtoToUserEntityMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ExceptionMessages.NO_SUCH_ELEMENT_BY_ID)
        );
        return userMapper.userEntityToUserDto(user);
    }

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::userEntityToUserDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(ExceptionMessages.USER_NOT_FOUND_WITH_ID + id);
        }
        userRepository.deleteById(id);
    }

    public UserDto findByName(String userName) {
        User user = userRepository.findUserByUserName(userName);
        if(user == null){
            throw new EntityNotFoundException(ExceptionMessages.USER_NOT_FOUND_WITH_NAME + userName);
        }
        return userMapper.userEntityToUserDto(user);
    }

    public TokenDto authorization(String userName, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        var expectedUser = findByName(userName);
        String token = jwtTokenProvider.createToken(userName, List.of(expectedUser.getRoles()));
        return new TokenDto(userName, token);
    }

    @Transactional
    public void register(SignupRequest request) {
        if(userRepository.existsByUserName(request.getUserName())){
            return;
        }
        User registeredUser = User.builder()
                .userName(request.getUserName())
                .surName(request.getSurName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phoneNumber(Long.parseLong(request.getPhoneNumber()))
                .roles(request.getUserRole())
                .status(request.getUserStatus())
                .build();
        userRepository.save(registeredUser);
    }
}
