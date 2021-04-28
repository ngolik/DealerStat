package by.golik.dealerstat.service.impl;

import by.golik.dealerstat.entity.ReplyCode;
import by.golik.dealerstat.entity.Role;
import by.golik.dealerstat.entity.Token;
import by.golik.dealerstat.entity.User;
import by.golik.dealerstat.repository.ReplyCodeRepository;
import by.golik.dealerstat.repository.RoleRepository;
import by.golik.dealerstat.repository.TokenRepository;
import by.golik.dealerstat.repository.UserRepository;
import by.golik.dealerstat.service.UserService;
import by.golik.dealerstat.service.dto.NewPasswordDTO;
import by.golik.dealerstat.service.dto.UserDTO;
import by.golik.dealerstat.service.util.Mapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Nikita Golik
 */
@Log4j2
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final TokenRepository tokenRepository;

    private final ReplyCodeRepository replyCodeRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${emailAddress}")
    private  String emailAddress;

    @Value("${emailPassword}")
    private  String emailPassword;

    @Value("${adminPassword}")
    private String adminPassword;

    @Value("${confirmSubject}")
    private String confirmSubject;

    @Value("${confirmMessage}")
    private String confirmMessage;

    @Value("${codeSubject}")
    private String codeSubject;

    @Value("${codeMessage}")
    private String codeMessage;

    @Value("${successConfirmSubject}")
    private String successConfirmSubject;

    @Value("${successConfirmMessage}")
    private String successConfirmMessage;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           TokenRepository tokenRepository,
                           ReplyCodeRepository replyCodeRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
        this.replyCodeRepository = replyCodeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    public void init() {
        Role role;
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role("ROLE_ADMIN"));
            roleRepository.save(new Role("ROLE_TRADER"));
            roleRepository.save(new Role("ROLE_ANON"));
            log.info("Roles were created.");
        }
        role = roleRepository.findByName("ROLE_ADMIN");
        if (!userRepository.existsByRoleAndEnabledTrue(role)) {
            User user = new User(null, null ,
                    bCryptPasswordEncoder.encode(adminPassword),
                    emailAddress, role);

            user.setEnabled(true);
            userRepository.save(user);
            log.info("User " + user + " was created.");
        }
        deleteExpiryTokensAndCodes();
    }

    @SneakyThrows
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)  {
        if (userRepository.findByEmailAndEnabledTrue(username).isPresent()) {
            return userRepository.findByEmailAndEnabledTrue(username).get();
        } else {
            log.info("User with email " + username + " doesn't exist!");
            throw new Exception("User with this email doesn't exist!");
        }
    }

    @Override
    public void createUser(UserDTO userDTO)  {
        User user;
        Role role;
        Token token;

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            log.info("User with email " + userDTO.getEmail() + " already exist!");
//            throw new ResourceAlreadyExistException("User with this email already exist!");
        }
        role = roleRepository.findByName("ROLE_READER");
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user = Mapper.convertToUser(userDTO, role);
        userRepository.save(user);
        token = new Token(user);
        tokenRepository.save(token);
        confirmMessage += token.getToken();
        sendEmail(confirmSubject, confirmMessage, user.getEmail());
//        deleteAsync(Calendar.DAY_OF_MONTH, 1);
        log.info("User " + user + " has been created.");
    }

    @Override
    public void createCode(String email) {
        User user;
        ReplyCode replyCode;

        user = getUserByEmailAndEnabled(email);
        replyCode = user.getReplyCode();
        if (replyCode == null) {
            replyCode = new ReplyCode(user);
            replyCodeRepository.save(replyCode);
            deleteAsync(Calendar.MINUTE, 15);
        }
        codeMessage += " " + replyCode.getCode();
        sendEmail(codeSubject, codeMessage, email);
        log.info("ResetCode " + replyCode + " has been created.");
    }

    @Override
    public void confirmUser(String token) {
        Optional<Token> optionalToken =
                tokenRepository.findFirstByToken(token);

        if (optionalToken.isPresent()) {
            Token verificationToken = optionalToken.get();
            User user = verificationToken.getUser();

            user.setEnabled(true);
            userRepository.save(user);
            tokenRepository.delete(verificationToken);
            sendEmail(successConfirmSubject, successConfirmMessage, user.getEmail());
            log.info("User " + user + " has been confirmed.");
        } else {
            log.info("VerificationToken " + token + " doesn't exist!");
//            throw new ResourceNotFoundException("This token doesn't exist!");
        }
    }

    @Override
    public void confirmCode(NewPasswordDTO newPasswordDTO) {
        Optional<ReplyCode> optionalCode = replyCodeRepository.findByCode(
                newPasswordDTO.getCode());
        ReplyCode replyCode;
        User user;

        if (!optionalCode.isPresent()) {
            log.info("Code " + newPasswordDTO.getCode() + " is wrong.");
//            throw new ResourceNotFoundException("This code is wrong");
        }
        replyCode = optionalCode.get();
        user = replyCode.getUser();
        user.setPassword(bCryptPasswordEncoder.encode(newPasswordDTO.
                getNewPassword()));
        userRepository.save(user);
        replyCodeRepository.delete(replyCode);
        log.info("Password for user " + user + " has been changed.");
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(long id) {
        Optional<User> optionalUser = userRepository.findByIdAndEnabledTrue(id);
        User user;

        if (!optionalUser.isPresent()) {
            log.info("User with id " + id + " doesn't exist!");
//            throw new ResourceNotFoundException("User with this id doesn't exist!");
        }
        user = optionalUser.get();
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmailAndEnabled(String email) {
        Optional<User> optionalUser = userRepository.findByEmailAndEnabledTrue(email);

        if (!optionalUser.isPresent()) {
            log.info("User with email " + email + " doesn't exist!");
//            throw new ResourceNotFoundException(
//                    "User with this email doesn't exist!");
        }
        return optionalUser.get();
    }

    @Override
    @Transactional(readOnly = true)
    public User getByEmailAndPassword(String email, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmailAndEnabledTrue(email);
        User user;

        if (!optionalUser.isPresent()) {
            log.info("User with email " + email + " doesn't exist.");
//            throw new ResourceNotFoundException("User with this email doesn't exist.");
        }
        user = optionalUser.get();
        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        else {
            log.info("This password is wrong!");
            throw new Exception();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllAnons() {
        return userRepository.findAllAnons();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllTraders() {
        return userRepository.findAllNonReaders();
    }

    @Override
    public boolean isAdmin(User user) {
        return (user.getRole().getName().equals("ROLE_ADMIN"));
    }

    @Override
    public void updateUser(User user, UserDTO userDTO) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        log.info("User " + user + " has been updated.");
    }

    @Override
    public void changeRole(User user, String roleName) {
        Role role = roleRepository.findByName(roleName);

        user.setRole(role);
        userRepository.save(user);
        log.info("Role of User " + user + " has been changed.");
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
        log.info("User " + user + " has been deleted.");
    }

//    @Override
//    @Transactional(readOnly = true)
//    public void calculateRating(User user) {
//        if (!user.getRole().getName().equals("ROLE_READER")) {
//            user.setRate(userRepository.findRateByUser(user));
//        }
//    }

    @Async
    public void deleteAsync(int period, int value) {
        TaskScheduler scheduler;
        ScheduledExecutorService localExecutor = Executors
                .newSingleThreadScheduledExecutor();
        scheduler = new ConcurrentTaskScheduler(localExecutor);
        Runnable runnable = this::deleteExpiryTokensAndCodes;
        Calendar calendar = Calendar.getInstance();
        Date date;

        calendar.add(period, value);
        date = calendar.getTime();
        scheduler.schedule(runnable, date);
    }

    public void deleteExpiryTokensAndCodes() {
        tokenRepository.deleteByExpiryDateBeforeCurrent();
        userRepository.deleteByTokenIsNullAndEnabledFalse();
        replyCodeRepository.deleteByExpiryDateBeforeCurrent();
        log.info("Expiry codes and tokens have been deleted.");
    }

    private void sendEmail(String subject, String message, String to)  {
        Properties props;
        Session session;
        MimeMessage mimeMessage;

        try {
            props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailAddress, emailPassword);
                }
            });
            mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(emailAddress));
            mimeMessage.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            Transport.send(mimeMessage);
            log.info("Email with text " + message + " was sent to address " +  to
                    + ".");
        }
        catch (MessagingException e) {
            log.error("Email with text " + message + "wasn't sent!");
//            throw new UnknownServerException();
        }
    }

    @Override
    public void calculateRate(User user) {
        if (!user.getRole().getName().equals("ROLE_ANON")) {
            user.setRate(userRepository.findRateByUser(user));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllTradersByGames(List<Long> idList) {
        return userRepository.findUserByGames(idList);
    }
}