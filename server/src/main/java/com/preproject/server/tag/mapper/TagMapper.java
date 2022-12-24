package com.preproject.server.tag.mapper;

import com.preproject.server.tag.dto.TagResponseDto;
import com.preproject.server.tag.entity.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagResponseDto tagToTagResponseDto(Tag tag);

    List<TagResponseDto> tagListToTagResponseDtoList(List<Tag> tags);

}
