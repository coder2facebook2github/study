package com.spring.boot.study.model.master.vo;


import com.spring.boot.study.common.validate.group.GroupA;
import com.spring.boot.study.common.validate.group.GroupB;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@GroupSequence({GroupA.class, GroupB.class, SendMailVo.class})
public class SendMailVo implements Serializable {
    private static final long serialVersionUID = -4049602745849160208L;

    @NotBlank(message = "收件人不能为空", groups = {GroupA.class, GroupB.class})
    private String sendTo;
    @NotBlank(message = "标题不能为空", groups = {GroupA.class})
    private String title;
    @NotBlank(message = "内容不能为空", groups = {GroupA.class})
    private String content;

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
