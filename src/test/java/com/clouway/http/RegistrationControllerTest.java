//package com.clouway.http;
//
//import com.clouway.core.*;
//import com.clouway.core.UserRepository;
//import org.jmock.Expectations;
//import org.jmock.Mockery;
//import org.jmock.integration.junit4.JUnit4Mockery;
//import org.jmock.integration.junit4.JUnitRuleMockery;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.core.Is.is;
//
///**
// * Created by Stanislav Valov <hisazzul@gmail.com>
// */
//public class RegistrationControllerTest {
//
//    @Rule
//    public JUnitRuleMockery context = new JUnitRuleMockery();
//
//    RegistrationController registrationController;
//    User user;
//
//    UserRepository userRepository = context.mock(UserRepository.class);
//    UserValidator userValidator = context.mock(UserValidator.class);
//    SiteMap siteMap = context.mock(SiteMap.class);
//
//    @Before
//    public void setUp() throws Exception {
//        user = new User("Stanislav");
//        user.setPassword("123456");
//        registrationController = new RegistrationController(userRepository, userValidator, siteMap);
//        registrationController.setUser(user);
//    }
//
//    @Test
//    public void createAccountSuccessful() {
//
//        context.checking(new Expectations() {
//            {
//                oneOf(userValidator).userIsCorrect(user);
//                will(returnValue(true));
//
//                oneOf(userRepository).exists(user.getUserName());
//                will(returnValue(false));
//
//                oneOf(userRepository).register(user);
//
//                oneOf(siteMap).loginForm();
//                will(returnValue("/bank/Login.html"));
//            }
//        });
//        assertThat(registrationController.register(), is("/bank/Login.html"));
//    }
//
//    @Test
//    public void validateDataFailed() {
//
//        context.checking(new Expectations() {
//            {
//                oneOf(userValidator).userIsCorrect(user);
//                will(returnValue(false));
//
//                oneOf(siteMap).registrationError();
//                will(returnValue("/bank/RegistrationError.html"));
//            }
//        });
//        assertThat(registrationController.register(),is("/bank/RegistrationError.html"));
//    }
//
//    @Test
//    public void userAlreadyExists() {
//
//        context.checking(new Expectations() {
//            {
//                oneOf(userValidator).userIsCorrect(user);
//                will(returnValue(true));
//
//                oneOf(userRepository).exists(user.getUserName());
//                will(returnValue(true));
//
//                oneOf(siteMap).registrationError();
//                will(returnValue("/bank/RegistrationError.html"));
//            }
//        });
//        assertThat(registrationController.register(),is("/bank/RegistrationError.html"));
//    }
//}