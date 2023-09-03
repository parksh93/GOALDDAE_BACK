package com.goalddae.controller;

import com.goalddae.dto.friend.SearchFriendDTO;
import com.goalddae.dto.friend.SelectFriendListDTO;
import com.goalddae.entity.User;
import com.goalddae.service.FriendService;
import com.goalddae.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("friend")
public class FriendController {
    private FriendService friendService;
    private UserService userService;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<?> search(SelectFriendListDTO selectFriendListDTO) {
        List<User> userList = userService.searchUserList(selectFriendListDTO.getNickname());
        List<SearchFriendDTO> friendList = friendService.searchFriend(selectFriendListDTO);

        List<User> noFriendList = new ArrayList<>();
        List<List> responseList = new ArrayList<>();

        for (User user : userList) {
            if(!friendList.contains(user.getNickname())){
                noFriendList = List.of(user);
            }
        }
        responseList = List.of(noFriendList, friendList);

        System.out.println(responseList.toString());
        return responseList;
    }

}
