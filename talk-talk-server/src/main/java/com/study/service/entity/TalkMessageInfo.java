package com.study.service.entity;

import lombok.Data;

import java.io.File;
import java.time.LocalDateTime;

@Data
public class TalkMessageInfo {
    private Integer id;
    private String messageId;
    private String messageData;
    private File messageFile;
    private LocalDateTime createTime;
}
