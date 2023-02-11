package com.ProTeen.backend.community.service;

import com.ProTeen.backend.community.dto.PageDTO;
import com.ProTeen.backend.community.dto.BoardDTO;
import com.ProTeen.backend.community.model.BoardEntity;
import com.ProTeen.backend.community.repository.BoardRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository repository;

    private final static String VIEWCOOKIENAME = "alreadyViewCookie";

    @Transactional
    public void updateView(Long boardId, HttpServletRequest request, HttpServletResponse response) {
        
        Cookie[] cookies = request.getCookies();
        boolean checkCookie = false;
        if(cookies != null){
            for (Cookie cookie : cookies)
            {
                // 이미 조회를 한 경우 체크
                if (cookie.getName().equals(VIEWCOOKIENAME+boardId)) checkCookie = true;

            }
            if(!checkCookie){
                Cookie newCookie = createCookieForForNotOverlap(boardId);
                response.addCookie(newCookie);
                repository.updateView(boardId);
            }
        } else {
            Cookie newCookie = createCookieForForNotOverlap(boardId);
            response.addCookie(newCookie);
            repository.updateView(boardId);
        }
    }

    private Cookie createCookieForForNotOverlap(Long postId) {
        Cookie cookie = new Cookie(VIEWCOOKIENAME+postId, String.valueOf(postId));
        cookie.setMaxAge(getRemainSecondForTomorrow()); 	// 하루를 준다.
        cookie.setHttpOnly(true);				// 서버에서만 조작 가능
        return cookie;
    }

    private int getRemainSecondForTomorrow() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1L).truncatedTo(ChronoUnit.DAYS);
        return (int) now.until(tomorrow, ChronoUnit.SECONDS);
    }


    public PageDTO searchAllPaging(int pageNo, int pageSize, String sortBy) {

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        Page<BoardEntity> boardPage = repository.findAll(pageable);

        List<BoardEntity> listBoards = boardPage.getContent();

        List<BoardDTO.Summary> content = listBoards.stream().map(BoardDTO.Summary::new).toList();

        return PageDTO.builder()
                .content(content)	// todoDtoPage.getContent()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalElements(boardPage.getTotalElements())
                .totalPages(boardPage.getTotalPages())
                .build();
    }

    public PageDTO searchPage(String titleKeyWord, int pageNo, int pageSize, String sortBy){

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        Page<BoardEntity> boardPage =  repository.findByTitleContainingIgnoreCase(titleKeyWord, pageable);

        List<BoardEntity> listBoards = boardPage.getContent();

        List<BoardDTO.Summary> content = listBoards.stream().map(BoardDTO.Summary::new).toList();

        return PageDTO.builder()
                .content(content)	// todoDtoPage.getContent()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalElements(boardPage.getTotalElements())
                .totalPages(boardPage.getTotalPages())
                .build();

    }
    public List<BoardEntity> create(final BoardEntity entity){
        //Validation
        ValidationService.boardValidate(entity);

        repository.save(entity);

        return repository.findByAuthor(entity.getAuthor());
    }

    public List<BoardEntity> retrieve(){
        return repository.findAll();
    }

    public List<BoardEntity> retrieveByCategory(final String category){
        return repository.findByCategory(category);
    }
    public BoardEntity read(Long id){return repository.findById(id).get();}

    public List<BoardEntity> update(final String userId, final Long id, final BoardEntity entity){
        final Optional<BoardEntity> original = repository.findById(id); // Id 필요

        ValidationService.boardMatchUser(userId, original);
        ValidationService.boardValidate(entity);

        final BoardEntity board = original.get();
        board.setTitle(entity.getTitle());
        board.setAuthor(entity.getAuthor());
        board.setContent(entity.getContent());
        board.setCategory(entity.getCategory());
        board.setView(entity.getView());
        board.setModifiedTime(LocalDateTime.now());
        repository.save(board);

        return retrieve();
    }

    public List<BoardEntity> delete(final String userId, final Long id){

        final Optional<BoardEntity> original = repository.findById(id); // Id 필요

        ValidationService.boardMatchUser(userId, original);

        try{
            repository.deleteById(id);

            System.out.println("delete");
        }catch (Exception e){
            log.error("error deleting entity");

            throw new RuntimeException("error deleting entity " + id);
        }

        return retrieve();
    }
}
