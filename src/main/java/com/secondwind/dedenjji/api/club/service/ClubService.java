package com.secondwind.dedenjji.api.club.service;

import com.secondwind.dedenjji.api.club.clubMember.domain.request.AddClubMemberRequest;
import com.secondwind.dedenjji.api.club.domain.request.ClubSearch;
import com.secondwind.dedenjji.api.club.domain.request.CreateClubRequest;
import com.secondwind.dedenjji.api.club.domain.request.UpdateClubRequest;
import com.secondwind.dedenjji.api.club.domain.response.ClubDetail;
import com.secondwind.dedenjji.api.club.domain.response.ClubResponse;
import org.springframework.data.domain.Slice;

public interface ClubService {

    Long createClub(CreateClubRequest makeClubRequest);

    Slice<ClubResponse> getClubs(ClubSearch clubSearch);

    ClubDetail getClub(Long id);

    Long updateClub(UpdateClubRequest updateClubRequest);

    void deleteClub(Long id);
}
