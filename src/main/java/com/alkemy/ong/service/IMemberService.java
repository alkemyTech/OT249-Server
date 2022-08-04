package com.alkemy.ong.service;

import java.util.List;
import java.util.UUID;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.model.Member;
import org.springframework.data.domain.Page;

public interface IMemberService {

	Member getMemberById(String id);
	Page<MemberDto> getAllMembers(int page, String order);
	void deleteMemberById(String id);
	void updateMember(Member member,String id);
	void createMember(Member member);
	
}
