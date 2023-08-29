package com.goalddae.config.oauth;

import com.goalddae.dto.user.ChangeUserInfoDTO;
import com.goalddae.entity.User;
import com.goalddae.repository.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final UserJPARepository userJPARepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user);
        return user;
    }

    public User saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attirbutes = oAuth2User.getAttributes();
        String email = (String) attirbutes.get("email");
        String name = (String) attirbutes.get("name");
        User user = userJPARepository.findByEmail(email);

        if(user == null){
            user = User.builder()
                    .email(email)
                    .name(name)
                    .authority("user")
                    .build();
        }else{
            ChangeUserInfoDTO changeUserInfoDTO = new ChangeUserInfoDTO(user);
            user = changeUserInfoDTO.toEntity();
        }

        return userJPARepository.save(user);
    }


}
