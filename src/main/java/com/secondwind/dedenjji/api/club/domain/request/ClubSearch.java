package com.secondwind.dedenjji.api.club.domain.request;

import com.secondwind.dedenjji.common.dto.PaginationDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class ClubSearch extends PaginationDto {
    private String clubName;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public ClubSearch(Integer limit, Integer offset, String orderBy, Sort.Direction direction, String clubName) {
        super(limit, offset, orderBy, direction);
        this.clubName = clubName;
    }
}
