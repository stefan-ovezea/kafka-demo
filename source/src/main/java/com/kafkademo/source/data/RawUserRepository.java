package com.kafkademo.source.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RawUserRepository extends JpaRepository<RawUser, Long> {
}
