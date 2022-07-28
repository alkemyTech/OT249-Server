package com.alkemy.ong.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.alkemy.ong.model.Member;
import com.alkemy.ong.service.IMemberService;

@Service
public class MemberServiceImpl implements IMemberService {

	@Override
	public Member getMemberById(String id) {

		return null;
	}

	@Override
	public List<Member> getAllMembers() {

		return null;
	}

	@Override
	public void deleteMemberById(String id) {

	}

	@Override
	public void updateMember(Member member, String id) {

	}

}
