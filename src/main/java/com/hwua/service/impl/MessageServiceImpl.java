package com.hwua.service.impl;

import com.hwua.mapper.MessageMapper;
import com.hwua.pojo.Message;
import com.hwua.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<Message> queryMessageByLoginUser(Long loginid) throws Exception {
            return messageMapper.queryMessageByLoginUser(loginid);
    }

    @Override
    public Message queryMessageById(String id) throws Exception {
        Message msg = messageMapper.queryMessageById(id);
        msg.setState(0);
        //调用到层进行更新
        messageMapper.updateMessage(msg);
        return msg;

    }

    @Override
    public int sendMessage(Message msg) throws Exception {
        return messageMapper.saveMessage(msg);
    }

    @Override
    public int deleteMsgById(int id) throws Exception {
        return messageMapper.deleteMsgById(id);
    }

    @Override
    public Long queryMsgCount(int loginid) throws Exception {
        return messageMapper.queryMsgCount(loginid);
    }
}
