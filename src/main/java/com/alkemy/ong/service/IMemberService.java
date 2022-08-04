package com.alkemy.ong.service;

import java.util.List;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.model.Member;
import org.springframework.data.domain.Page;

public interface IMemberService {

	Member getMemberById(String id);
	Page<MemberDto> getAllMembers(int page, String order);
	void deleteMemberById(String id);
	public String updateMember(MemberDto memberDto, String id);
	void createMember(Member member);
	
}
