package com.goalddae.controller;

import com.goalddae.dto.board.BoardListDTO;
import com.goalddae.dto.board.BoardUpdateDTO;
import com.goalddae.dto.board.HeartInfoDTO;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.CommunicationHeart;
import com.goalddae.entity.ReportedBoard;
import com.goalddae.entity.UsedTransactionBoard;
import com.goalddae.service.BoardService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/board")
public class BoardController {

    BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> list(@RequestParam(required = false, defaultValue = "") String type,
                                                    @RequestParam(required = false, defaultValue = "") String name,
                                                    @RequestParam(required = false, defaultValue = "1") Integer page) {

        final int PAGE_BTN_NUM = 10;

        Page<BoardListDTO> pageInfo;

        pageInfo = boardService.findAllBoardListDTOs(page, type, name);


        int currentPageNum = pageInfo.getNumber() + 1;
        int endPageNum = (int) Math.ceil(currentPageNum / (double) PAGE_BTN_NUM) * PAGE_BTN_NUM;
        int startPageNum = endPageNum - PAGE_BTN_NUM + 1;
        endPageNum = Math.min(endPageNum, pageInfo.getTotalPages());
        startPageNum = Math.min(startPageNum, pageInfo.getTotalPages());
        currentPageNum = Math.min(currentPageNum, pageInfo.getTotalPages());

        Map<String, Object> response = new HashMap<>();
        response.put("pageInfo", pageInfo);
        response.put("currentPageNum", currentPageNum);
        response.put("endPageNum", endPageNum);
        response.put("startPageNum", startPageNum);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/detail/{boardId}")
    public ResponseEntity<?> detail(@PathVariable long boardId, HttpServletRequest request, HttpServletResponse response){

        String cookieName = "viewCount_" + boardId;

        Cookie[] cookies = request.getCookies();
        boolean isNewView = true;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    isNewView = false;
                    break;
                }
            }
        }

        if(isNewView){
            boardService.viewCountUp(boardId);

            Cookie newCookie = new Cookie(cookieName, "1");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }

        CommunicationBoard communicationBoard = boardService.findById(boardId);

        return ResponseEntity.ok(communicationBoard);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteById(boardId);
        return ResponseEntity.ok("게시글이 삭제되었습니다.");
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveBoard(@RequestBody CommunicationBoard communicationBoard) {
        boardService.save(communicationBoard);
        return ResponseEntity.ok("게시글이 저장되었습니다.");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateBoard(@RequestBody BoardUpdateDTO boardUpdateDTO) {
        boardService.update(boardUpdateDTO);
        return ResponseEntity.ok("게시글이 수정되었습니다.");
    }


    // 게시글 신고
    @GetMapping("/report")
    public ResponseEntity<List<ReportedBoard>> getReportedBoardByBoardId() {
        List<ReportedBoard> reportedBoardList = boardService.findAllReportedBoard();
        return ResponseEntity.ok(reportedBoardList);
    }


    @PostMapping("/report")
    public ResponseEntity<String> addReport(@RequestBody ReportedBoard reportedBoard) {
        boardService.saveReportedBoard(reportedBoard);
        return ResponseEntity.ok("신고가 접수되었습니다.");
    }

    @DeleteMapping("/report/reject/{reportId}")
    public ResponseEntity<String> rejectReport(@PathVariable long reportId) {
        boardService.rejectReportedBoard(reportId);
        return ResponseEntity.ok("신고가 거절되었습니다.");
    }

    @DeleteMapping("/report/approve/{reportId}")
    public ResponseEntity<String> approveReport(@PathVariable long reportId) {
        boardService.approveReportedBoard(reportId);
        return ResponseEntity.ok("신고가 승인되었습니다.");
    }

    @PostMapping("/heart")
    public ResponseEntity<HeartInfoDTO> getHeartInfo(@RequestBody CommunicationHeart communicationHeart) {
        return ResponseEntity.ok(boardService.getHeartInfo(communicationHeart.getBoardId(), communicationHeart.getUserId()));
    }

    @PostMapping("/heart/save")
    public ResponseEntity<String> saveHeart(@RequestBody CommunicationHeart communicationHeart) {
        boardService.heartSave(communicationHeart.getBoardId(), communicationHeart.getUserId());
        return ResponseEntity.ok("좋아요를 추가합니다.");
    }

    @DeleteMapping("/heart/delete")
    public ResponseEntity<String> deleteHeart(@RequestBody CommunicationHeart communicationHeart) {
        boardService.heartDelete(communicationHeart.getBoardId(), communicationHeart.getUserId());
        return ResponseEntity.ok("좋아요를 취소합니다.");
    }

    @RequestMapping(value = "/mylist/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> viewUserPosts(@PathVariable long userId) {
        List<CommunicationBoard> communicationBoardsList = boardService.getUserCommunicationBoardPosts(userId);
        return ResponseEntity.ok(communicationBoardsList);
    }

}
