package com.study.service.dto.uc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqUserRelationDto {
        private Integer id;
        private Integer userId;
        private Integer firendId;
}
