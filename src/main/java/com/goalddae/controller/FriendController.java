package com.goalddae.controller;

import com.goalddae.dto.friend.FindFriendRequestDTO;
import com.goalddae.dto.friend.FindFriendListResponseDTO;
import com.goalddae.dto.friend.SearchFriendDTO;
import com.goalddae.dto.friend.SelectFriendListDTO;
import com.goalddae.service.FriendService;
import com.goalddae.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/friend")
public class FriendController {
    private final FriendService friendService;
    private final UserService userService;

    @Autowired
    public FriendController(FriendService friendService, UserService userService){
        this.friendService = friendService;
        this.userService = userService;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Map<String, List<SearchFriendDTO>> search(@RequestBody SelectFriendListDTO selectFriendListDTO) {
        return friendService.searchFriend(selectFriendListDTO);
    }

    @PostMapping("/findFriendList")
    public List<FindFriendListResponseDTO> findFriendList(@RequestBody FindFriendRequestDTO findFriendListRequestDTO) {
        return friendService.findFriendList(findFriendListRequestDTO);
    }

}
