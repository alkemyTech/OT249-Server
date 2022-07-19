package com.alkemy.ong.service;

import java.util.List;

import com.alkemy.ong.model.Member;

public interface IMemberService {

	public Member getMemberById(Long id);
	public List<Member> getAllMembers();
	public void deleteMemberById(Long id);
	public void updateMember(Member member,Long id);
	
}
