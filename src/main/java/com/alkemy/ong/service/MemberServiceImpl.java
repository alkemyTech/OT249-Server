package com.alkemy.ong.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.alkemy.ong.model.Member;

@Service
public class MemberServiceImpl implements IMemberService {

	@Override
	public Member getMemberById(UUID id) {

		return null;
	}

	@Override
	public List<Member> getAllMembers() {

		return null;
	}

	@Override
	public void deleteMemberById(UUID id) {

	}

	@Override
	public void updateMember(Member member, UUID id) {

	}

}
