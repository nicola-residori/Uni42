package com.uni42.channelapi.repository;

import com.uni42.channelapi.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, String> {
}
