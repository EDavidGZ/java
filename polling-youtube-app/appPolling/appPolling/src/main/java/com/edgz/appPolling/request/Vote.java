package com.edgz.appPolling.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    private Long pollId;
    private int optionIndex;
}
