<img src='https://capsule-render.vercel.app/api?type=Slice&color=auto&height=200&section=header&text=GOALDDAE&fontsize=90'/>
<aside>
💡
<b>안녕하세요! 저희는 팀 “GOALDDAE”입니다.</b>

**저희가 준비한 프로젝트는 풋볼 & 축구 매칭 및 소통 사이트 입니다.**
**골때라는 이름은 “골대” 와 “골 차러갈때” 라는 두개의 단어를 결합해서 골 차러갈때는 저희 “골때”를 연상시키도록 만들어졌습니다.**

</aside>
<h2>Project Detail</h2>

---
<details>
    <summary>
        <h3 style="display: inline">1. 동적테이블</h3>
    </summary>
    저희는 정적테이블과 동적테이블을 모두 활용하여 DB를 구성했습니다.

<b>동적테이블을 사용한 이유</b><br/>
게시판을 예로 들어 각 게시글별 댓글을 한번에 저장할경우 하나의 글의 댓글을 조회해야 할 때 정적 테이블로 모든 댓글을 한번에 저장 시 조회 처리에 시간이 걸릴 수 있습니다.
그렇기 때문에 각 게시글별로 댓글 동적테이블을 구성해 조회 시간을 단축하기 위해 동적 테이블을 사용했습니다.

또한, 동적 테이블 사용시 MyBatis를 이용하게 되는데 이때 SQL Injection 문제를 보완하기 위해 
`
$
`
대신 
`
    #  
`
을 사용해 사용자가 쿼리를 날리더라도 문제가 발생하지 않도록 처리했습니다.
</details>
<br/>
<details>
    <summary>
      <h3 style="display: inline">2. Spring Security</h3>
    </summary>
    <b>JWT</b> 토큰을 이용해 사용자가 로그인 했을 때 해당 사용자의 권한이 담겨 있는 JWT 토큰을 발급해 권한에 따라 서비스 이용 제한
    <br>또한, 해당 JWT 토큰을 쿠키에 <b>HTTPOnly</b>로 저장해 <b style="color:red">XSS</b> 공격에 대비했습니다.
    
    Cookie cookie = new Cookie("token", token);
    cookie.setHttpOnly(true);
    cookie.setPath("/"); // 모든 페이지에서 접근 가능

<b>Refresh Token 사용</b><br/>
Access Token의 유효기간을 짧게 두어 Access Token의 탈취를 최소화했습니다. Access Token이 만료되었을 경우 사용자의 재로그인을 최소화 하기 위해 
Refresh Token을 DB에 저장해 두고 만료 시 Refresh Token을 통해 Access Token을 재발급해주도록 설정했습니다.

        if(!token.equals("")) {
            if (tokenProvider.validToken(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                if(!refreshToken.equals("")){
                    Long userId = tokenProvider.getUserId(refreshToken);
                    RefreshToken refreshTokenEntity = refreshTokenService.findByUserId(userId);
                    boolean validRefreshToken = tokenProvider.validToken(refreshTokenEntity.getRefreshToken());

                    if (validRefreshToken && refreshToken.equals(refreshTokenEntity.getRefreshToken())) {
                        User user = userJPARepository.findById(userId).get();
                        String newAccessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
                        String newRefreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);

                        refreshTokenService.saveRefreshToken(userId, newRefreshToken);
                        CookieUtil.addCookie(response, ACCESS_TOKEN_COOKIE_NAME, newAccessToken);
                        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, newRefreshToken);

                        Authentication authentication = tokenProvider.getAuthentication(newAccessToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    }
                }
            }
        }
</details>
<br>
<details>
    <summary>
         <h3 style="display: inline">3. WebSocket </h3>
    </summary>
    채팅 및 친구 기능 사용시 실시간 반영을 위해 WebSocket을 사용했습니다. 해당 기능들은 1대1 통신이기 떄문에 queue 방식을 이용해 구현했습니다.

    @Configuration
    @EnableWebSocketMessageBroker
    public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
        @Override
        public void registerStompEndpoints(StompEndpointRegistry registry) {
            registry.addEndpoint("/friend").setAllowedOrigins("http://localhost:3000").withSockJS();
        }
    
        @Override
        public void configureMessageBroker(MessageBrokerRegistry registry) {
    
            registry.enableSimpleBroker("/queue");
            registry.setApplicationDestinationPrefixes("/app");
    
        }
    }
---
    @RestController
    public class FriendWebSocketController {
        private static Set<Integer> userList = new HashSet<>();
    
        private SimpMessagingTemplate simpMessagingTemplate;
    
        @Autowired
        public FriendWebSocketController(SimpMessagingTemplate simpMessagingTemplate){
            this.simpMessagingTemplate = simpMessagingTemplate;
        }
    
        @MessageMapping("/friend/{id}")
        public void sendMessage(@Payload FriendSocketMsgDTO friendSocketMsgDTO, @DestinationVariable Integer id){
            this.simpMessagingTemplate.convertAndSend("/queue/FriendRequestToClient/"+ id, friendSocketMsgDTO);
        }
    
        @MessageMapping("/friend/join")
        public void joinUser(@Payload Integer userId){
            userList.add(userId);
        }
    }
EndPoint를 지정해 해당 EndPoint로 요청을 받습니다.<br/>
`
join
`
을 통해 Socket 서버와 연결한 후 연결된 사용자로 부터 메시지를 받아 응답을 받을 사용자에게 메시지를 전송합니다. 
</details>
<br/>
<details>
    <summary>
        <h3 style="display: inline">4. Crawling</h3>
    </summary>
저희는 네이버 해외 축구 기사를 크롤링해 사용자에게 현재의 핫이슈를 보여주고자 하였습니다.<br/>
사용자는 기사를 5개씩 볼 수 있으며, 15초 마다 자동으로 넘어가며 총 25개의 기사를 보여줍니다.
데이터는 3일 이상이 되면 자동으로 삭제되게끔 하였고, 중점적으로 생각한 부분은 축구는 최신 기사에 민감하므로

`
@Scheduled 
`
을 이용하여 하루에 4번 6시, 12시, 18시, 24시 크롤링되게끔 적용하였습니다. 그리고 DB에는 생성 일자 내림차순으로 저장되며, 내림차순으로 노출되게 구현했습니다.
</details>
<br/>
<details>
    <summary><h3 style="display:inline">5. CI/CD</h3></summary>
<img src="https://file.notion.so/f/f/733a0dda-2518-4e22-ae92-d6463456c372/57250a8d-1a85-437b-a62c-93f8e5aaeb7f/Untitled.png?id=d6b915b2-0c7d-4bae-bcdf-21476d8482a9&table=block&spaceId=733a0dda-2518-4e22-ae92-d6463456c372&expirationTimestamp=1696694400000&signature=GSIXuYMJwB6Jpu6zbtwsWlLSy7fwONHtBqrw0kMCg2g&downloadName=Untitled.png"/>
Jenkins와 Docker를 이용해 배포를 진행했습니다.
깃허브를 통해 이벤트가 발생하게 되면 웹훅을 통해 Jenkins에서 요청을 캐치해 도커 이미지를 빌드합니다. 빌드가 완료되면 빌드된 이미지를 DockerHub로 push해 각 서버에서 해당 이미지를 pull 받아 서버를 실행합니다.
사용자의 요청이 들어왔을때 바로 서버로 요청을 보내는 것이 아닌 nginx를 거쳐 헬스 체크를 통해 로드밸런싱 처리하여 트래픽을 분산 시켰습니다.
또한, 버전 업으로 인해 새롭게 배포를 진행해야 할때 서버의 중단을 막기 위해 롤링 방식을 도입해 무중단 배포를 수행했습니다.
</details>

<h2>Tech & Tool</h2>

---

<h3>Backend</h3>
<img src="https://img.shields.io/badge/java-CC0000?style=flat&logo=java&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat&logo=Spring Boot&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat&logo=Spring Security&logoColor=white"/>
<img src="https://img.shields.io/badge/MyBatis-ED1F35?style=flat&logo=MyBatis&logoColor=white"/>
<img src="https://img.shields.io/badge/JPA-Hibernate-59666C?style=flat&logo=Hibernate&logoColor=white"/>
<img src="https://img.shields.io/badge/REST-042133?style=flat&logo=REST&logoColor=white"/>

<h3>Frontend</h3>
<img src="https://img.shields.io/badge/react-61DAFB?style=flat&logo=react&logoColor=white"/>
<img src="https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=HTML5&logoColor=white"/>
<img src="https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=css3&logoColor=white"/>
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=JavaScript&logoColor=white"/><br/>

<h3>DB</h3>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white"/>


<h3>Server & CI/CD</h3>
<img src="https://img.shields.io/badge/Naver Cloud-03C75A?style=flat&logo=Naver&logoColor=white"/>
<img src="https://img.shields.io/badge/Jenkins-D24939?style=flat&logo=Jenkins&logoColor=white"/>
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=Docker&logoColor=white"/><br/>

<h3>Tools</h3>
<img src="https://img.shields.io/badge/IntelliJ-000000?style=flat&logo=IntelliJ IDEA&logoColor=white"/>
<img src="https://img.shields.io/badge/Visual Studio Code-007ACC?style=flat&logo=Visual Studio Code&logoColor=white"/>
<a href="https://climbing-alley-625.notion.site/Project-GOALDDAE-95357a09e6784ace932685fc57a54b0e?pvs=4"><img src="https://img.shields.io/badge/Notion-000000?style=flat&logo=Notion&logoColor=white"/></a>