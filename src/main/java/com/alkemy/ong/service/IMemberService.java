package com.alkemy.ong.service;

import java.util.List;
import java.util.UUID;

import com.alkemy.ong.model.Member;

public interface IMemberService {

	public Member getMemberById(UUID id);
	public List<Member> getAllMembers();
	public void deleteMemberById(Long id);
	public void updateMember(Member member,Long id);
	
}
