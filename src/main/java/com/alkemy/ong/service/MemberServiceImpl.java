package com.alkemy.ong.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alkemy.ong.model.Member;

@Service
public class MemberServiceImpl implements IMemberService {

	@Override
	public Member getMemberById(Long id) {

		return null;
	}

	@Override
	public List<Member> getAllMembers() {

		return null;
	}

	@Override
	public void deleteMemberById(Long id) {

	}

	@Override
	public void updateMember(Member member, Long id) {

	}

}
