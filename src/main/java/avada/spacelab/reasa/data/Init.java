package avada.spacelab.reasa.data;

import avada.spacelab.reasa.model.*;
import avada.spacelab.reasa.repo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class Init implements CommandLineRunner {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CountryRepo countryRepo;
    private final LanguageRepo languageRepo;
    private final OwnerRepo ownerRepo;
    private final HelpCenterRepo helpCenterRepo;
    private final RealEstateRepo realEstateRepo;
    private final GalleryRepo galleryRepo;
    private final ImageRepo imageRepo;

    @Override
    @Transactional
    public void run(String[] args) {
        log.info("Initial startup checks:");
        checkTestUser();
        checkTestOwners();
        checkTestHelpCenter();
        createTestRealEstates();
        log.info("Initial startup checks end");
    }

    private void checkTestUser() {
        log.info("Checking for test user");
        if (!userRepo.existsUserByEmail("test@gmail.com")) {
            log.info("Test user does NOT exist");
            log.info("Creating test user");
            User user = new User();
            user.setEmail("test@gmail.com");
            user.setPassword(passwordEncoder.encode("test"));

//            log.info("Checking for user role");
//            Optional<Role> role = roleRepo.findRoleByName("ROLE_USER");
//            if (role.isPresent()) {
//                log.info("User role found");
//                user.setRole(role.get());
//            } else {
//                log.info("User role NOT found");
//                log.info("Creating user role");
//                Role userRole = new Role("ROLE_USER");
//                roleRepo.save(userRole);
//                log.info("User role was successfully created");
//                user.setRole(userRole);
//            }

            log.info("Creating profile for test user");
            UserProfile userProfile = new UserProfile();
            userProfile.setFullName("Sponge Bob");
            userProfile.setNickname("Squarepants");
            userProfile.setBirthday(LocalDate.of(2000, 6, 30));
            userProfile.setPhoneNumber("+380671234567");

            log.info("Checking for countries");
            Optional<Country> anyCountry = countryRepo.findAll().stream().findAny();
            if (anyCountry.isPresent()) {
                log.info("Random country found");
                userProfile.setCountry(anyCountry.get());
            } else {
                log.info("There is no any country found");
                createInitialCountries();
                userProfile.setCountry(countryRepo.findAll()
                        .stream().findAny().orElse(new Country("UA", "Ukraine")));
            }
            userProfile.setGender(UserProfile.Gender.MALE);
            userProfile.setProfilePhoto("testAvatar.jpg");
            userProfile.setDarkMode(true);

            log.info("Checking for languages");
            Optional<Language> anyLanguage = languageRepo.findAll().stream().findAny();
            if (anyLanguage.isPresent()) {
                log.info("Random language found");
                userProfile.setLanguage(anyLanguage.get());
            } else {
                log.info("There is no any language found");
                createInitialLanguages();
                userProfile.setLanguage(languageRepo.findAll()
                        .stream().findAny().orElse(new Language("UA", "Ukraine")));
            }
            user.setUserProfile(userProfile);
            UserIdentification identification =
                    new UserIdentification("testIdCard.jpg", "testSelfieWithIdCard.jpg");
            user.setUserIdentification(identification);

            UserNotificationSettings notificationSettings =
                    new UserNotificationSettings(true, true, true);
            user.setUserNotificationSettings(notificationSettings);

            UserSecuritySettings securitySettings =
                    new UserSecuritySettings(true, true);
            user.setUserSecuritySettings(securitySettings);

            UserPayment userPayment = new UserPayment();

            List<BankingCard> bankingCards = userPayment.getBankingCards();

            bankingCards.addAll(Arrays.asList(
                    new BankingCard(
                            "First Card",
                            "1111222233334444",
                            LocalDate.of(LocalDate.now().getYear() + new Random().nextInt(10),
                                    new Random().nextInt(11) + 1, 1),
                            "123"),
                    new BankingCard(
                            "Second Card",
                            "5555666677778888",
                            LocalDate.of(LocalDate.now().getYear() + new Random().nextInt(10),
                                    new Random().nextInt(11) + 1, 1),
                            "456"),
                    new BankingCard(
                            "Third Card",
                            "9999000011112222",
                            LocalDate.of(LocalDate.now().getYear() + new Random().nextInt(10),
                                    new Random().nextInt(11) + 1, 1),
                            "789")));

            user.setUserPayment(userPayment);

            userRepo.save(user);
            log.info("Test user successfully created");
        } else {
            log.info("Test user was found");
        }
    }

    private void createInitialCountries() {
        log.info("Creating table of countries");
        String[] countryCodes = Locale.getISOCountries();
        List<Country> countries = new ArrayList<>(countryCodes.length);
        for (String countryCode : countryCodes) {
            String locale = new Locale("", countryCode).getDisplayCountry(Locale.US);
            countries.add(new Country(countryCode, locale));
        }
        countries.sort(Comparator.comparing(Country::getCode));
        countryRepo.saveAll(countries);
        log.info("Countries successfully created");
    }

    private void createInitialLanguages() {
        log.info("Creating table of languages");
        String[] languageCodes = Locale.getISOLanguages();
        List<Language> languages = new ArrayList<>(languageCodes.length);
        for (String languageCode : languageCodes) {
            String locale = new Locale(languageCode).getDisplayLanguage(Locale.US);
            languages.add(new Language(languageCode, locale));
        }
        languages.sort(Comparator.comparing(Language::getCode));
        languageRepo.saveAll(languages);
        log.info("Languages successfully created");
    }

    private void checkTestOwners() {
        log.info("Checking for test owners:");
        Optional<Owner> anyOwner = ownerRepo.findAll().stream().findAny();
        if (anyOwner.isPresent()) {
            log.info("Some owner was found");
        } else {
            log.info("No owner found");
            ownerRepo.saveAll(
                    Arrays.asList(
                            new Owner(new ArrayList<>(), "Elon", "Musk", "owner1.jpg", "elon.musk@gmail.com"),
                            new Owner(new ArrayList<>(), "Michael", "Jackson", "owner2.jpg", "michael.jackson@gmail.com"),
                            new Owner(new ArrayList<>(), "Terminator", "T800", "owner3.jpg", "terminator@gmail.com")
                    )
            );
            log.info("Owners were successfully created");
        }
    }

    private void checkTestHelpCenter() {
        log.info("Checking for test help center:");
        Optional<HelpCenter> anyHelpCenter = helpCenterRepo.findAll().stream().findAny();
        if (anyHelpCenter.isPresent()) {
            log.info("Some help center was found");
        } else {
            log.info("No help center found");
            String answer = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque ut risus mauris. Vestibulum mattis neque eget lacus porttitor efficitur id ut odio. Suspendisse vel justo leo. Pellentesque sed ipsum sit amet velit bibendum ornare id eu ipsum. Integer faucibus felis vel condimentum ullamcorper. Fusce pretium feugiat arcu, sed ornare nunc ultricies vel. Cras consequat auctor rutrum. Quisque interdum porttitor eros, non pellentesque eros faucibus commodo. Nullam maximus, metus id rhoncus feugiat, tortor dui feugiat mauris, vitae elementum leo tellus vel diam. Donec id condimentum mauris. Fusce lobortis eros ac quam placerat vehicula. Sed ac nunc auctor, porta turpis ac, semper velit. Praesent libero enim, fringilla vitae egestas nec, molestie vel sem. Sed sed rhoncus turpis.\n" +
                    "\n" +
                    "Interdum et malesuada fames ac ante ipsum primis in faucibus. Nunc vulputate odio vitae dolor malesuada viverra. Nam tempus metus eget ipsum volutpat pellentesque. Integer vel enim nec nunc eleifend malesuada. Nam eu odio nec risus malesuada feugiat nec sit amet leo. Curabitur vitae imperdiet nibh, tempor faucibus enim. Nullam et quam justo. Cras molestie lorem eget fermentum semper. Maecenas turpis nisi, fermentum et suscipit sit amet, consectetur nec nisl. Aenean vel convallis libero, vel viverra orci. Suspendisse sit amet lacinia odio. In mattis posuere lorem.\n" +
                    "\n" +
                    "Sed lacinia a urna ut fermentum. Etiam sed molestie metus, eu mattis nunc. Aenean quis orci tempus, mollis sapien a, egestas urna. Donec accumsan est vitae quam sodales convallis. Aenean tempus mauris eget nisl consectetur, non interdum nunc pharetra. Nunc ut lacus consequat, congue odio ac, interdum augue. Morbi suscipit justo sed consectetur tincidunt. Sed feugiat posuere dui nec mollis. Vestibulum vitae massa ac justo commodo facilisis. Integer sed velit id risus varius imperdiet. Proin dictum leo ac odio hendrerit finibus. Sed cursus dignissim metus a ullamcorper.\n" +
                    "\n" +
                    "Aenean sit amet dolor sed arcu pharetra varius a ac nisi. Donec viverra eros vitae nibh consequat, vel dignissim dolor nam.";
            List<Faq> faqList = List.of(
                    new Faq("Question 1", answer),
                    new Faq("Question 2", answer),
                    new Faq("Question 3", answer),
                    new Faq("Question 4", answer),
                    new Faq("Question 5", answer)
            );
            ContactUs contactUs = new ContactUs(
                    null,
                    "https://wa.me/<number>",
                    "https://youtube.com",
                    "https://facebook.com",
                    "https://twitter.com/elonmusk",
                    "https://instagram.com/teslamotors"
            );
            HelpCenter helpCenter = new HelpCenter(faqList, contactUs);
            helpCenterRepo.save(helpCenter);
            log.info("Help center was successfully created");
        }
    }

    private void createTestRealEstates() {
        RealEstate realEstate = RealEstate.builder()
                .name(
                        List.of("Grandmother house", "VITA-house", "Just a house", "SOME NAME OF HOUSE",
                                        "BILLY-BOB", "Your daddy's home", "White House", "Hotel Blue")
                                .get(new Random().nextInt(7))
                )
                .price(new Random().nextInt(300))
                .type(RealEstate.Type.values()[new Random().nextInt(2)])
                .beds(new Random().nextInt(7) + 1)
                .bathrooms(new Random().nextInt(7) + 1)
                .sqft(new Random().nextInt(2000) + 20)
                .location(
                        new Location(
                                BigDecimal.valueOf(-90 + 180 * new Random().nextDouble()).setScale(6, RoundingMode.HALF_UP).doubleValue(),
                                BigDecimal.valueOf(180 * new Random().nextDouble()).setScale(6, RoundingMode.HALF_UP).doubleValue()
                        )
                )
                .owner(ownerRepo.getOwnerByEmail
                                (
                                        List.of("elon.musk@gmail.com", "michael.jackson@gmail.com", "terminator@gmail.com")
                                                .get(new Random().nextInt(2))
                                )
                        .orElse(null))
                .image(List.of("main1.jpg", "main2.jpg", "main3.jpg").get(new Random().nextInt(2)))
                .build();
        Gallery mainGallery = new Gallery("main", realEstate, List.of
                (
                        imageRepo.save(new Image(List.of("main1-1.jpg", "main1-2.jpg", "main1-3.jpg").get(new Random().nextInt(2)))),
                        imageRepo.save(new Image(List.of("main1-1.jpg", "main1-2.jpg", "main1-3.jpg").get(new Random().nextInt(2)))),
                        imageRepo.save(new Image(List.of("main1-1.jpg", "main1-2.jpg", "main1-3.jpg").get(new Random().nextInt(2))))
                )
        );
        galleryRepo.save(mainGallery);
        realEstate.setGalleries(List.of(mainGallery));
        realEstateRepo.save(realEstate);
    }


}
