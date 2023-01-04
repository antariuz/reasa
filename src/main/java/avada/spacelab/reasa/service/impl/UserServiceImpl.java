package avada.spacelab.reasa.service.impl;

import avada.spacelab.reasa.repo.UserRepo;
import avada.spacelab.reasa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public boolean isUserExistByEmail(String email) {
        return userRepo.existsUserByEmail(email);
    }

}
