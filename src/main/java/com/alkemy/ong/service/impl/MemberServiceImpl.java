package com.alkemy.ong.service.impl;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ong.utils.PageUtils;
import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.dto.PageDto;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.IMemberService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

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
	public PageDto<MemberDto> getAllMembers(int page, String order) {
		Page<Member> members = memberRepository.findAll( PageUtils.getPageable( page, order ) );
		Page<MemberDto> membersDto = members.map( cat -> this.modelMapper.map( cat, MemberDto.class ));
		return PageUtils.getPageDto( membersDto, "members" );
	}

	@Override
	@Transactional
	public void deleteMemberById(String id) {

		Member member = memberRepository.findById(id).orElseThrow();
		memberRepository.delete(member);
	}

	@Override
	public String updateMember(MemberDto memberDto, String id) throws EntityNotFoundException{
		memberRepository.save(this.fillEntity(memberRepository.getById(id), memberDto));
		return "Miembro Actualizado Correctamente";
	}

	@Override
	public void createMember(Member member) {
		
		memberRepository.save(member);
		
	}

	public Member fillEntity(Member memberExist, MemberDto memberDto){
		if(memberDto.getName() != null)
			memberExist.setName(memberDto.getName());
		if(memberDto.getFacebookUrl() != null)
			memberExist.setFacebookUrl(memberDto.getFacebookUrl());
		if(memberDto.getInstagramUrl() != null)
			memberExist.setInstagramUrl(memberDto.getInstagramUrl());
		if(memberDto.getLinkedinUrl() != null)
			memberExist.setLinkedinUrl(memberDto.getLinkedinUrl());
		if(memberDto.getImage() != null)
			memberExist.setImage(memberDto.getImage());
		if(memberDto.getDescription() != null)
			memberExist.setDescription(memberDto.getDescription());
		return memberExist;
	}

}
