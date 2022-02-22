package io.iamsg;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Phonetic {
    private String text;
    private String audio;
    private String sourceUrl;
    License license;
}
