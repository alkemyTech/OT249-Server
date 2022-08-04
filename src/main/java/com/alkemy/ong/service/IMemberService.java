package com.alkemy.ong.service;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.model.Member;
public interface IMemberService {

	Member getMemberById(String id);
	PageDto<MemberDto> getAllMembers(int page, String order);
	void deleteMemberById(String id);
	public String updateMember(MemberDto memberDto, String id);
	void createMember(Member member);
	
}
