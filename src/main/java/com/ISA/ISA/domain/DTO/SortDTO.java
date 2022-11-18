package com.ISA.ISA.domain.DTO;

import com.ISA.ISA.domain.enums.SortMode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SortDTO {
    private String field;
    private SortMode sortMode;
}
