package com.alkemy.ong.service.impl;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.IMemberService;

@Service
public class MemberServiceImpl implements IMemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	

	@Override
	public Member getMemberById(String id) {

		return null;
	}

	@Override
	public List<Member> getAllMembers() {

		return memberRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteMemberById(String id) {

		Member member = memberRepository.findById(id).get();
		memberRepository.delete(member);
	}

	@Override
	public void updateMember(Member member, String id) {

	}

	@Override
	public void createMember(Member member) {
		
		memberRepository.save(member);
		
	}

}
