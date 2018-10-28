package com.wizzstudio.substitute.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class IndentDTO implements Serializable {

    @NotNull
    private Boolean isCompusShoppingHelp;

    @NotNull
    private String requireGender;

    @NotNull
    private String content;

    public IndentDTO() {
    }

    public IndentDTO(Boolean isCampusShoppingHelp, String requireGender, String content) {
        this.isCompusShoppingHelp = isCampusShoppingHelp;
        this.requireGender = requireGender;
        this.content = content;
    }
}
