package com.secondwind.dedenjji.api.club.domain.response;

import com.secondwind.dedenjji.api.club.domain.entity.Club;
import com.secondwind.dedenjji.api.club.clubMember.domain.response.ClubMemberResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ClubDetail {
    private Long id;
    private String name;
    private List<ClubMemberResponse> clubMemberResponses;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public ClubDetail(Long id, String name, List<ClubMemberResponse> clubMemberResponses) {
        this.id = id;
        this.name = name;
        this.clubMemberResponses = clubMemberResponses;
    }

    public static ClubDetail toDTO(Club club, List<ClubMemberResponse> clubMemberResponses) {
        return ClubDetail.of()
                .id(club.getId())
                .name(club.getName())
                .clubMemberResponses(clubMemberResponses)
                .build();
    }
}
