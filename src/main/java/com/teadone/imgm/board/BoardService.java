package com.teadone.imgm.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService implements BoardMapper {
	@Autowired
	BoardMapper mapper;
	
	public List<BoardVO> getPosts() {
		return mapper.getPosts();
	}

	@Override
	public BoardVO getPost(BoardVO vo) {
		return mapper.getPost(vo);
	}

	@Override
	public int writePost(BoardVO vo) {
		return mapper.writePost(vo);
	}
	 
}
