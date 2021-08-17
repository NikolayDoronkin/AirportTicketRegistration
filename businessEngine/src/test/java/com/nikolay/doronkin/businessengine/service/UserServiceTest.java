package com.nikolay.doronkin.businessengine.service;

import com.nikolay.doronkin.businessengine.dto.TokenDto;
import com.nikolay.doronkin.businessengine.dto.UserDto;
import com.nikolay.doronkin.businessengine.enumeration.UserRole;
import com.nikolay.doronkin.businessengine.enumeration.UserStatus;
import com.nikolay.doronkin.businessengine.jwt.JwtTokenProvider;
import com.nikolay.doronkin.businessengine.jwt.JwtUtil;
import com.nikolay.doronkin.businessengine.mapper.UserDtoToUserEntityMapper;
import com.nikolay.doronkin.businessengine.model.User;
import com.nikolay.doronkin.businessengine.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private JwtUtil jwtTokenUtil;

    @Mock
    private UserRepository userRepository;

    @Spy
    private UserDtoToUserEntityMapper userMapper = Mappers.getMapper(UserDtoToUserEntityMapper.class);

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDto expectedUserDto;
    private List<UserDto> expectedUsers;
    private List<User> users;
    private static final Long ID = 1L;
    private static final Long PHONE_NUMBER = 80298209912L;
    private static final String TEST_STRING = "test";
    private static final String TEST_EMAIL = "test@gmail.com";
    private static final String TOKEN = "testToken";

    @BeforeEach
    void setUp() {
        user = User.builder()
                .userName(TEST_STRING)
                .surName(TEST_STRING)
                .lastName(TEST_STRING)
                .email(TEST_EMAIL)
                .password(TEST_STRING)
                .phoneNumber(PHONE_NUMBER)
                .roles(UserRole.ROLE_ADMIN)
                .status(UserStatus.ACTIVE)
                .tickets(new ArrayList<>())
                .build();
        user.setId(ID);
        users = List.of(user);

        expectedUserDto = new UserDto();
        expectedUserDto.setId(user.getId());
        expectedUserDto.setUserName(user.getUserName());
        expectedUserDto.setSurName(user.getSurName());
        expectedUserDto.setLastName(user.getLastName());
        expectedUserDto.setEmail(user.getEmail());
        expectedUserDto.setPassword(user.getPassword());
        expectedUserDto.setPhoneNumber(user.getPhoneNumber());
        expectedUserDto.setRoles(user.getRoles());
        expectedUserDto.setStatus(user.getStatus());
        expectedUserDto.setTickets(new ArrayList<>());

        expectedUsers = List.of(expectedUserDto);
    }

    @Test
    void testFindByIdCorrect() {
        Mockito.when(userRepository.findById(ID)).thenReturn(Optional.of(user));

        UserDto actualUserDto = userService.findById(ID);

        Assertions.assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    void testFindByIdWrong() {
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                userService.findById(ID)
        );
    }

    @Test
    void testFindAll() {
        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<UserDto> actualUsers = userService.findAll();

        Assertions.assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void findByNameWhenUserExists() {
        Mockito.when(userRepository.findUserByUserName(TEST_STRING)).thenReturn(user);

        UserDto actualUserDto = userService.findByName(TEST_STRING);

        Assertions.assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    void findByNameWhenUserDoesntExists() {
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                userService.findByName(TEST_STRING)
        );
    }

    @Test
    void testAuthorizationSuccess() {
        Mockito.doReturn(user).when(userRepository).findUserByUserName(TEST_STRING);
        Mockito.doReturn(TOKEN).when(jwtTokenProvider)
                .createToken(TEST_STRING, Collections.singletonList(user.getRoles()));

        TokenDto tokenDto = userService.authorization(user.getUserName(), user.getPassword());
        String expectedUserName = user.getUserName();
        String actualUserName = tokenDto.getUserName();

        Assertions.assertEquals(expectedUserName, actualUserName);
    }

    @Test
    void testAuthorizationFailure() {
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.authorization(user.getUserName(), user.getPassword()));
    }

    @Test
    void testDeleteByIdSuccess() {
        Mockito.doReturn(true).when(userRepository).existsById(ID);
        userService.deleteById(ID);
        Mockito.verify(userRepository, times(1)).deleteById(ID);
    }

    @Test
    void testDeleteByIdFailure() {
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                userService.deleteById(ID)
        );
    }
}