package com.ProTeen.backend.controller;

import com.ProTeen.backend.dto.BoardDTO;
import com.ProTeen.backend.dto.ImageDTO;
import com.ProTeen.backend.dto.ResponseDTO;
import com.ProTeen.backend.model.BoardEntity;
import com.ProTeen.backend.model.ImageEntity;
import com.ProTeen.backend.repository.BoardRepository;
import com.ProTeen.backend.service.BoardService;
import com.ProTeen.backend.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("board")
@Slf4j
public class BoardController {
    @Value("${spring.servlet.multipart.location}")
    private String path;
    private final BoardService boardService;
    private final ImageService imageService;

    private String uploadFile(String originalName, byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID();
        System.out.println(path);
        String savedName = uuid.toString() + "." + originalName.split("\\.")[1];
        File target =new File(path, savedName);

        FileCopyUtils.copy(fileData, target);

        return savedName;
    }

    @PostMapping("/post")
    public ResponseEntity<?> createBoard(@AuthenticationPrincipal String userId, @RequestPart(value = "dto") BoardDTO.Total dto
    ,@RequestPart(value = "files", required = false) List<MultipartFile> files) {
        try{
            BoardEntity boardEntity = BoardDTO.Total.toEntity(dto);

            boardEntity.setId(null);
            boardEntity.setUserId(userId);

            boardEntity.setCreateTime(LocalDateTime.now());
            boardEntity.setModifiedTime(LocalDateTime.now());


            List<BoardEntity> entities = boardService.create(boardEntity);

            if(!files.isEmpty()){
                for (MultipartFile file : files) {
                    ImageEntity imgEntity = ImageEntity.builder().build();

                    String imgPath = uploadFile(file.getOriginalFilename(), file.getBytes());

                    imgEntity.setImgPath(imgPath);
                    imgEntity.setId(null);
                    imgEntity.setBoard(boardEntity);

                    imageService.create(imgEntity);
                }
            }

            List<BoardDTO.Summary> dtos = entities.stream().map(BoardDTO.Summary::new).toList();

            ResponseDTO<BoardDTO.Summary> response = ResponseDTO.<BoardDTO.Summary>builder().data(dtos).build();

            return ResponseEntity.ok().body("저장에 성공했습니다.");
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/read")
    public ResponseEntity<?> retrieveBoardList(){

        List<BoardEntity> entities = boardService.retrieve();

        List<BoardDTO.Summary> dtos = entities.stream().map(BoardDTO.Summary::new).toList();

        ResponseDTO<BoardDTO.Summary> response = ResponseDTO.<BoardDTO.Summary>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/read/category/{category}")
    public ResponseEntity<?> readByCategory(@PathVariable String category){

        List<String> categoryList = Arrays.asList("information", "question", "help", "free");
        if(!categoryList.contains(category)){
            return ResponseEntity.badRequest().body("wrong path");
        }

        List<BoardEntity> entities = boardService.retrieveByCategory(category);

        List<BoardDTO.Summary> dtos = entities.stream().map(BoardDTO.Summary::new).toList();

        ResponseDTO<BoardDTO.Summary> response = ResponseDTO.<BoardDTO.Summary>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }
    @GetMapping(value = "/read/{id}")
    public ResponseEntity<?> read(@PathVariable Long id){
        try{
            BoardEntity entity = boardService.read(id);

            BoardDTO.Total response = new BoardDTO.Total(entity);

            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error = e.getMessage();

            ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping(value = "/downloadFile/{file}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getFile(@PathVariable String file) throws IOException {
        Resource resource = new FileSystemResource(path + file);
        return new ResponseEntity<Resource>(resource, HttpStatus.OK);
    }


    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateBoard(@AuthenticationPrincipal String userId, @PathVariable Long id ,@RequestBody BoardDTO.Total dto){
        try{
            BoardEntity entity = BoardDTO.Total.toEntity(dto);

            System.out.println(userId);

            List<BoardEntity> entities = boardService.update(userId, id, entity);

            List<BoardDTO.Total> dtos = entities.stream().map(BoardDTO.Total::new).toList();

            ResponseDTO<BoardDTO.Total> response = ResponseDTO.<BoardDTO.Total>builder().data(dtos).build();

            return ResponseEntity.ok().body("수정에 성공하였습니다.");
        }catch (Exception e){
            String error = e.getMessage();

            ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBoard(@AuthenticationPrincipal String userId, @PathVariable Long id){
        try {
            List<BoardEntity> entities = boardService.delete(userId, id);

            List<BoardDTO.Summary> dtos = entities.stream().map(BoardDTO.Summary::new).toList();

            ResponseDTO<BoardDTO.Summary> response = ResponseDTO.<BoardDTO.Summary>builder().data(dtos).build();

            return ResponseEntity.ok().body("삭제 성공하였습니다.");
        }catch (Exception e){
            System.out.println(e.getMessage());

            String error = e.getMessage();

            ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }
}
