package com.smt.project.mapper;

import com.smt.project.dto.ProfileDto;
import com.smt.project.model.Profile;

public final class ProfileMapper {
    public static Profile profileDtoToProfile(ProfileDto profileDto) {
        Profile profile = new Profile();
        profile.setFirstName(profileDto.firstName());
        profile.setLastName(profileDto.lastName());
        profile.setAge(profileDto.age());
        return profile;
    }
}
