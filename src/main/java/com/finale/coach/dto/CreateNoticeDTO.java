package com.finale.coach.dto;

import com.finale.entity.Notice;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class CreateNoticeDTO {
    private String contents;

    public Notice toEntity() {
        return new Notice(this.contents);
    }
}
