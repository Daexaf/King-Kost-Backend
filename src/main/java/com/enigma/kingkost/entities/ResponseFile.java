package com.enigma.kingkost.entities;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class ResponseFile {
    private String name;
    private String url;
    private String type;
    private long size;
}
