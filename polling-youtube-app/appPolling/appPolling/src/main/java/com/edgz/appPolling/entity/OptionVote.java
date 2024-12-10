package com.edgz.appPolling.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@NoArgsConstructor
@Embeddable
public class OptionVote  {
    private String optionText;
    private Long voteCount = 0L;
}
