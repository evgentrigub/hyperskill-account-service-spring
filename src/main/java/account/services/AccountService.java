package account.services;

import account.models.UserData;
import account.models.UserDataResponseDto;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    public UserDataResponseDto getUserSignUp(UserData userData) {
        UserDataResponseDto dto = new UserDataResponseDto();
        dto.setName(userData.getName());
        dto.setLastname(userData.getLastname());
        dto.setEmail(userData.getEmail());

        return dto;
    }

}
