package com.Focus.Reddit.dto;

import com.Focus.Reddit.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoteDto {

    private VoteType voteType;
    private Long postId;

}