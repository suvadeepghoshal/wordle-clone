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
public class Meaning {
    private String partOfSpeech;
    private List<Defintion> definitions;
}
