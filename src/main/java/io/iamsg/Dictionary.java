package io.iamsg;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Dictionary {
    private String word;
    private String phonetic;
    private List<Phonetic> phonetics;
    private List<Meaning> meanings;
    private License license;
    private List<Object> sourceUrls;
}
