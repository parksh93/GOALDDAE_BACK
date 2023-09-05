package com.goalddae.controller;

import com.goalddae.dto.friend.*;
import com.goalddae.dto.friend.friendAccept.FindFriendAcceptDTO;
import com.goalddae.dto.friend.friendAccept.FriendRejectionDTO;
import com.goalddae.dto.friend.friendAdd.FindFriendAddDTO;
import com.goalddae.dto.friend.friendList.FindFriendListResponseDTO;
import com.goalddae.dto.friend.friendList.SearchFriendDTO;
import com.goalddae.dto.friend.friendList.SelectFriendListDTO;
import com.goalddae.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/friend")
public class FriendController {
    private final FriendService friendService;

    @Autowired
    public FriendController(FriendService friendService){
        this.friendService = friendService;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Map<String, List<SearchFriendDTO>> search(@RequestBody SelectFriendListDTO selectFriendListDTO) {
        return friendService.searchFriend(selectFriendListDTO);
    }

    @PostMapping("/findFriendList")
    public List<FindFriendListResponseDTO> findFriendList(@RequestBody FindFriendRequestDTO findFriendListRequestDTO) {
        return friendService.findFriendList(findFriendListRequestDTO);
    }

    @PostMapping("/findFriendAccept")
    public List<FindFriendAcceptDTO> findFriendAccept(@RequestBody FindFriendRequestDTO findFriendRequestDTO) throws Exception{
        return friendService.findAcceptList(findFriendRequestDTO);
    }

    @PutMapping("/addFriendRequest")
    public void addFriendRequest(@RequestBody AddFriendRequestDTO addFriendRequestDTO){
        friendService.addFriendRequest(addFriendRequestDTO);
    }

    @PostMapping("/findFriendAdd")
    public List<FindFriendAddDTO> findFriendAddDTOList(@RequestBody FindFriendRequestDTO findFriendRequestDTO){
        return friendService.findFriendAddList(findFriendRequestDTO);
    }

    @PatchMapping("/friendRejection")
    public void friendRejection(@RequestBody FriendRejectionDTO friendRejectionDTO){
        friendService.friendRejection(friendRejectionDTO);
    }

    @DeleteMapping("/deleteFriendRequest")
    public void deleteFriendRequest(@RequestBody FriendRequestDTO deleteFriendRequestDTO){
        friendService.deleteFriendRequest(deleteFriendRequestDTO);
    }
}
