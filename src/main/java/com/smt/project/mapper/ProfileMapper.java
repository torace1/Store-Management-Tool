package com.smt.project.mapper;

import com.smt.project.dto.ProfileDto;
import com.smt.project.model.Profile;

public final class ProfileMapper {
    public static Profile profileDtoToProfile(ProfileDto profileDto) {
        Profile profile = new Profile();
        profile.setFirstName(profileDto.getFirstName());
        profile.setLastName(profileDto.getLastName());
        profile.setAge(profileDto.getAge());
        return profile;
    }
}
