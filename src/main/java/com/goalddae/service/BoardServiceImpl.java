package com.goalddae.service;

import com.goalddae.dto.board.BoardListDTO;
import com.goalddae.dto.board.BoardUpdateDTO;
import com.goalddae.dto.board.HeartInfoDTO;
import com.goalddae.dto.board.MyBoardListDTO;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.CommunicationHeart;
import com.goalddae.entity.ReportedBoard;
import com.goalddae.repository.*;
import com.goalddae.util.S3Uploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService{

    BoardJPARepository boardJPARepository;
    ReplyJPARepository replyJPARepository;
    HeartJPARepository heartJPARepository;
    ReportedBoardJPARepository reportedBoardJPARepository;

    S3Uploader s3Uploader;

    private final JdbcTemplate jdbcTemplate;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String jdbcUsername;

    @Value("${spring.datasource.password}")
    private String jdbcPassword;

    @Autowired
    public BoardServiceImpl(BoardJPARepository boardJPARepository, ReplyJPARepository replyJPARepository, HeartJPARepository heartJPARepository, ReportedBoardJPARepository reportedBoardJPARepository, JdbcTemplate jdbcTemplate, S3Uploader s3Uploader){
        this.boardJPARepository = boardJPARepository;
        this.replyJPARepository = replyJPARepository;
        this.heartJPARepository = heartJPARepository;
        this.reportedBoardJPARepository = reportedBoardJPARepository;
        this.jdbcTemplate = jdbcTemplate;
        this.s3Uploader = s3Uploader;
    }

    @Override
    public Page<BoardListDTO> findAllBoardListDTOs(Integer page, String type, String name) {

        final int PAGE_SIZE = 10;

        Pageable pageable = PageRequest.of(getCalibratedPno(page), PAGE_SIZE, Sort.by("id").descending());

        if(Objects.equals(type, "writer")){
            return getBoardListWithCounts(boardJPARepository.findAllBoardListDTOByWriter(name, pageable));
        } else if(Objects.equals(type, "title")){
            return getBoardListWithCounts(boardJPARepository.findAllBoardListDTOByTitle(name, pageable));
        } else {
            return getBoardListWithCounts(boardJPARepository.findAllBoardListDTO(pageable));
        }

    }

    public Page<BoardListDTO> getBoardListWithCounts(Page<BoardListDTO> pagedBoardList) {
        List<Long> boardIds = new ArrayList<>();
        for (BoardListDTO dto : pagedBoardList) {
            boardIds.add(dto.getId());
        }

        List<Object[]> replyCounts = replyJPARepository.countRepliesByBoardIds(boardIds);
        List<Object[]> heartCounts = heartJPARepository.countHeartsByBoardIds(boardIds);

        Map<Long, Long> replyCountsMap = new HashMap<>();
        for (Object[] arr : replyCounts) {
            Long boardId = (Long) arr[0];
            Long replyCount = (Long) arr[1];
            replyCountsMap.put(boardId, replyCount);
        }

        Map<Long, Long> heartCountsMap = new HashMap<>();
        for (Object[] arr : heartCounts) {
            Long boardId = (Long) arr[0];
            Long heartCount = (Long) arr[1];
            heartCountsMap.put(boardId, heartCount);
        }


        for (BoardListDTO dto : pagedBoardList) {
            Long boardId = dto.getId();
            Long replyCount = replyCountsMap.getOrDefault(boardId, 0L);
            Long heartCount = heartCountsMap.getOrDefault(boardId, 0L);

            dto.setReplyCount(replyCount);
            dto.setHeart(heartCount);

        }

        return pagedBoardList;
    }

    @Override
    public CommunicationBoard findById(Long boardId) {
        return boardJPARepository.findById(boardId).orElse(null);
    }

    @Override
    public void deleteById(Long boardId) {
        boardJPARepository.deleteById(boardId);
    }

    @Override
    public void save(CommunicationBoard communicationBoard) {

        boardJPARepository.save(communicationBoard);
    }


    @Override
    public void update(BoardUpdateDTO boardUpdateDTO) {

        CommunicationBoard updatedPost = boardJPARepository.findById(boardUpdateDTO.getId()).get();

        System.out.println(updatedPost.getId());

        updatedPost = CommunicationBoard.builder()
                .id(updatedPost.getId())
                .userId(updatedPost.getUserId())
                .writer(updatedPost.getWriter())
                .title(boardUpdateDTO.getTitle())
                .content(boardUpdateDTO.getContent())
                .img1(boardUpdateDTO.getImg1())
                .img2(boardUpdateDTO.getImg2())
                .img3(boardUpdateDTO.getImg3())
                .img4(boardUpdateDTO.getImg4())
                .img5(boardUpdateDTO.getImg5())
                .boardSortation(updatedPost.getBoardSortation())
                .updateDate(LocalDateTime.now())
                .writeDate(updatedPost.getWriteDate())
                .count(updatedPost.getCount())
                .build();

            boardJPARepository.save(updatedPost);

    }

    @Transactional
    @Override
    public void viewCountUp(long boardId) {
        CommunicationBoard communicationBoard = boardJPARepository.findById(boardId).get();
        communicationBoard.viewCountUp();
    }

    @Override
    public HeartInfoDTO getHeartInfo(long boardId, long userId) {
        List<CommunicationHeart> communicationHeartList = heartJPARepository.findByBoardId(boardId);

        boolean containsUserId = false;

        for (CommunicationHeart communicationHeart : communicationHeartList) {
            if (communicationHeart.getUserId() == userId) {
                containsUserId = true;
                break;
            }
        }

        return new HeartInfoDTO(communicationHeartList.size(), containsUserId);
    }

    @Override
    public void heartSave(long boardId, long userId) {
        Optional<CommunicationHeart> communicationHeart = heartJPARepository.findByBoardIdAndUserId(boardId, userId);
        if(communicationHeart.isEmpty()){
            CommunicationHeart newHeart = CommunicationHeart.builder()
                    .boardId(boardId)
                    .userId(userId)
                    .build();
            heartJPARepository.save(newHeart);
        }
    }

    @Override
    public void heartDelete(long boardId, long userId) {
        Optional<CommunicationHeart> communicationHeart = heartJPARepository.findByBoardIdAndUserId(boardId, userId);
        communicationHeart.ifPresent(heart -> heartJPARepository.delete(heart));
    }

    @Override
    public List<ReportedBoard> findAllReportedBoard() {
        return reportedBoardJPARepository.findAll();
    }

    @Transactional
    @Override
    public void saveReportedBoard(ReportedBoard reportedBoard) {

        Optional<ReportedBoard> reportedBoardOptional =
                reportedBoardJPARepository.findByBoardIdAndReporterUserId(reportedBoard.getBoardId(), reportedBoard.getReporterUserId());

        if(reportedBoardOptional.isEmpty()){
            reportedBoardJPARepository.save(reportedBoard);
        }
    }

    @Transactional
    @Override
    public void rejectReportedBoard(long reportId) {
        reportedBoardJPARepository.deleteById(reportId);
    }

    @Transactional
    @Override
    public void approveReportedBoard(long reportId) {
        ReportedBoard reportedBoard = reportedBoardJPARepository.findById(reportId).get();

        boardJPARepository.deleteById(reportedBoard.getBoardId());
        reportedBoardJPARepository.delete(reportedBoard);
    }

    @Transactional
    @Override
    public String uploadImage(MultipartFile multipartFile) {

        String uploadImageUrl = null;

        try {
            uploadImageUrl = s3Uploader.uploadFiles(multipartFile, "board");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return uploadImageUrl;
    }

    // 금일 조회수 탑5 게시글 조회
    @Override
    public List<BoardListDTO> findTop5Board() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        List<BoardListDTO> top5Boards = boardJPARepository.findTop5ByOrderByCountDesc(pageRequest);
        return getCountTop5Board(top5Boards);
    }

    // 게시판id의 댓글 수 조회
    public List<BoardListDTO> getCountTop5Board(List<BoardListDTO> boardLists) {
        List<Long> boardIds = boardLists.stream()
                .map(BoardListDTO::getId)
                .collect(Collectors.toList());

        Map<Long, Long> replyCountsMap = replyJPARepository.countRepliesByBoardIds(boardIds).stream()
                .collect(Collectors.toMap(arr -> (Long) arr[0], arr -> (Long) arr[1]));

        boardLists.forEach(dto -> dto.setReplyCount(replyCountsMap.getOrDefault(dto.getId(), 0L)));

        return boardLists;
    }

    public int getCalibratedPno(Integer pno){
        if (pno <= 0) {
            return 0;
        }
        return pno - 1;
    }


    @Override
    public List<MyBoardListDTO> getUserCommunicationBoardPosts(long userId) {
        String sql = "SELECT c.id, c.writer, c.title, c.content, " +
                "COUNT(DISTINCT h.id) AS heart_count, COUNT(DISTINCT r.id) AS reply_count, c.count, c.write_date " +
                "FROM communication_board c " +
                "LEFT JOIN communication_heart h ON c.id = h.board_id " +
                "LEFT JOIN communication_reply r ON c.id = r.board_id " +
                "WHERE c.user_id = ? " +
                "GROUP BY c.id, c.writer, c.title, c.content, c.count, c.write_date";

        return jdbcTemplate.query(sql, new Object[]{userId}, (resultSet, rowNum) -> {
            MyBoardListDTO dto = new MyBoardListDTO();
            dto.setId(resultSet.getLong("id"));
            dto.setWriter(resultSet.getString("writer"));
            dto.setTitle(resultSet.getString("title"));
            dto.setContent(resultSet.getString("content"));
            dto.setHeartCount(resultSet.getInt("heart_count"));
            dto.setReplyCount(resultSet.getInt("reply_count"));
            dto.setCount(resultSet.getLong("count"));
            dto.setWriteDate(resultSet.getTimestamp("write_date").toLocalDateTime());
            return dto;
        });
    }
}
