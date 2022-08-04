package com.alkemy.ong.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import com.alkemy.ong.Utils.PageUtils;
import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.model.Category;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.IMemberService;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements IMemberService {
	private final ModelMapper modelMapper;

	@Autowired
	private MemberRepository memberRepository;
	

	@Override
	public Member getMemberById(String id) {

		return null;
	}

	@Override
	public Page<MemberDto> getAllMembers(int page, String order) {
		Page<Member> members = memberRepository.findAll( PageUtils.getPageable( page, order ) );
		Page<MemberDto> membersDto = members.map( cat -> this.modelMapper.map( cat, MemberDto.class ));
		return PageUtils.getPageDto( membersDto, "members" );
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
