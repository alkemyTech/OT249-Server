package com.alkemy.ong.service;

import java.util.List;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.model.Member;

public interface IMemberService {

	public Member getMemberById(String id);
	public List<Member> getAllMembers();
	public void deleteMemberById(String id);
	public String updateMember(MemberDto memberDto, String id);
	public void createMember(Member member);
	
}
