package com.goalddae.service;

import com.goalddae.dto.chat.GetChannelListDTO;
import com.goalddae.dto.chat.MessageDTO;
import com.goalddae.entity.ChannelUser;
import com.goalddae.entity.Message;
import com.goalddae.entity.User;
import com.goalddae.repository.UserJPARepository;
import com.goalddae.repository.chat.ChannelRepository;
import com.goalddae.repository.chat.ChannelUserRepository;
import com.goalddae.repository.chat.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
    private final ChannelRepository channelRepository;
    private final ChannelUserRepository channelUserRepository;
    private final UserJPARepository userJPARepository;
    private final MessageRepository messageRepository;

    @Override
    public List<GetChannelListDTO> getChennelList(Long userId) {
        List<ChannelUser> channelUserList = channelUserRepository.findByUserId(userId);
        List<GetChannelListDTO> channelList = new ArrayList<>();

        for (ChannelUser channelUser:channelUserList) {
            List<ChannelUser> userList = channelUserRepository.findByChannelId(channelUser.getChannelId());
            for (ChannelUser channelUser1:userList) {
                if(channelUser1.getUserId() != userId){
                    User user = userJPARepository.findById(channelUser1.getUserId()).get();
                    GetChannelListDTO channelListDTO = GetChannelListDTO.builder()
                                    .channelId(channelUser1.getChannelId())
                                    .friendId(channelUser1.getUserId())
                                    .channelName(user.getNickname())
                                    .channelImgUrl(user.getProfileImgUrl())
                                    .build();

                    channelList.add(channelListDTO);
                }

            }
        }

        return channelList;
    }

    @Override
    public void saveNewMessage(MessageDTO messageDTO) {
        Message message = Message.builder()
                .channelId(messageDTO.getChannelId())
                .content(messageDTO.getContent())
                .sendDate(messageDTO.getSendDate())
                .userId(messageDTO.getUserId())
                .build();

        messageRepository.save(message);
    }

    @Override
    public List<MessageDTO> getMessageList(Long channelId) {
        List<Message> messageList = messageRepository.findByChannelIdOrderBySendDateAsc(channelId);
        List<MessageDTO> messageDTOList = new ArrayList<>();

        for (Message message : messageList) {
            User user = userJPARepository.findById(message.getUserId()).get();

            MessageDTO messageDTO = MessageDTO.builder()
                    .userId(message.getUserId())
                    .channelId(message.getChannelId())
                    .content(message.getContent())
                    .senderName(user.getNickname())
                    .sendDate(message.getSendDate())
                    .senderProfileImgUrl(user.getProfileImgUrl())
                    .build();

            messageDTOList.add(messageDTO);
        }

        return messageDTOList;
    }
}
