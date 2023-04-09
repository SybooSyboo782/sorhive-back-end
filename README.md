# SorHive Backend

# 회고
1. [방 생성](https://github.com/MA-Dot-COM/MA-Dot-COM-Back-End/issues/13)
    <details>
    <summary>회고</summary>
    
    - RDBMS로 문제를 해결하려다가 NoSQL을 알게 되었습니다.
      - 그 중 AWS DynamoDB를 이용하려다가 문법이 너무 생소해서 MongoDB Atlas로 문제를 해결했습니다
    </details>
    
2. [아바타 이미지 전송](https://github.com/MA-Dot-COM/MA-Dot-COM-Back-End/issues/60)
    <details>
    <summary>회고</summary>
    
    - Json 형태로 전송하는 문제해서 어려움이 있었지만 JsonObject를 이용하여 해결했습니다.
    
    - RestTemplate를 사용할 때 url 경로에 http를 붙여야 한다는 점을 배웠습니다.
        - 만약 http를 안 붙이면 uri로 인식합니다.
        
    </details>
3. [라이핑 이미지 전송](https://github.com/MA-Dot-COM/MA-Dot-COM-Back-End/issues/81)
    <details>
    <summary>회고</summary>
    <div markdown="1">
    
    - 이 라이핑 이미지는 끝날 때까지 힘들었습니다. 아무래도 메인 기능 중 하나이다 보니 이 라이핑 이미지를 위해서 나중에는 배치서버도 만들었습니다 
        - 문제를 해결하기 위해서 컬럼도 계속 늘렸습니다. 처음에 도메인 정의를 할 때 고려를 했어야 했는데 이 부분을 못한 것이 아쉬웠습니다
        - 다시 문제로 돌아와서 라이핑 이미지 같은 경우에는 라이핑 이미지를 프론트에게서 전달 받고 이를 AI 서버에 분석 요청을 합니다.
        - 이렇게 분석 요청을 다시 받고 나면 분석된 라이핑 상위 카테고리 번호가 나오는데 이것을 프론트에 주어야 했습니다. 

    - 처음에 프론트가 라이핑 이미지를 분석받았다는 표시를 어떻게 하면 좋을까 상의하여 lifingyn이라는 변수를 만들었고 이는 회원에다가 저장을 했습니다. 
        - 회원에 저장을 해야 나중에 메인 페이지에 갈 때 회원이 라이핑을 24시간 이내에 올렸는지를 확인하기 쉽다는 생각이었습니다.
    
    - 문제는 이 때는 발생은 안했지만 나중에 배치서버를 이용하여 10분마다 24시간 ~ 2일 후의 라이핑들을 조회하여 lifingyn을 n으로 바꾸어주는 함수를 만들면서 일어났습니다. 
    
    - 이로 인하여 방금 올린 사진들의 lifingyn이 y에서 n으로 바뀌는 문제가 있어서 이를 해결하기 위해 라이핑에 24시간내에 올렸는지 여부를 체킹하는 컬럼을 따로 만들어서 해결할 수 있었다.
    
    </div>
    </details>
    
4. [룸인 조회](https://github.com/MA-Dot-COM/MA-Dot-COM-Back-End/issues/128)
    <details>
    <summary>회고</summary>
    <div markdown="1">
    
    - 이 룸인 조회랑 이후 메인뷰 조회가 가장 힘들었었는데 룸인 조회 같은 경우에는 회원 정보를 조회하고 회원의 팔로잉 목록, 그리고 6명이 아니면 랜덤으로 다른 사람들을 채워넣어야 해서 애를 먹었습니다. 
    <br/>
    
    - 이 때 당시에는 6명이 아니면 랜덤으로 채우는 과정에서 중복이 발생하는 문제가 있었는데 한 1주 정도 지나고 나서 6명이든 아니든 랜덤을 뽑아넣고 비교를 하면 어떻냐는 의견에 바로 바꾸었고 성공적이었습니다.
    </div>
    </details>
    
5. [가구 이미지 생성](https://github.com/MA-Dot-COM/MA-Dot-COM-Back-End/issues/152)
    <details>
    <summary>회고</summary>
    <div markdown="1">
    
    - 가구 상호작용을 하면서 이미지를 추가하는 문제였는데 내 쪽에서는 만들기는 했지만 연동이 안되서 잘 될지는 모르겠습니다. 
      <br/>나랑 Unity를 하는 프론트 담당하는 형이 같이 이야기 할 때는 문제가 없었지만 실제 연동하면 다를 것 같다는 생각이 듭니다.
   
    </details>
    
6. [이메일 인증](https://github.com/MA-Dot-COM/MA-Dot-COM-Back-End/issues/153)
    <details>
    <summary>회고</summary>
    <div markdown="1">
    
    - 이메일 인증을 하기 위해 Amazon Simple Email Service(SES)를 도전했다가 받는 회원의 이메일도 인증을 받아야 한다는 것을 
      <br/>4~5시간만에 깨닫고 다시 스프링 메일 스타터로 바꾸었고 Thymeleaf를 이용한 이메일 인증 방법이 있어서 이를 이용했습니다.
    </div>
    </details>
    
7. [채팅 내역 불러오기](https://github.com/MA-Dot-COM/MA-Dot-COM-Back-End/issues/167)
    <details>
    <summary>회고</summary>
    <div markdown="1">
    
    - 채팅을 처음에는 어떻게 저장을 할까 생각을 했는데 결국 NoSQL인 몽고 DB를 이용하여 저장을 하기로 했고 이것을 하면서 JPA에서 save와 insert의 차이를 제대로 실감했습니다.
        - save의 경우에는 수정이 포함되어 있다는 점이 제일 중요한 것 같습니다.
    <br/>
    
    - 그리고 키가 같으면 이걸 그냥 덮어 씌우는 문제가 발생하였고, 추가적으로 MongoDB Atlas의 크기 제한 때문에 기존에 있는 값을 제거하고 그전 채팅 로그를 합쳐서 다시 돌려주는 함수를 만들어서 해결하였습니다.
    </div>
    </details>
    
8. [메인뷰 조회](https://github.com/MA-Dot-COM/MA-Dot-COM-Back-End/issues/251)
    <details>
    <summary>회고</summary>
    <div markdown="1">
    
    - 기존의 메인뷰 조회기능은 팔로우한 사람들 중에서 라이핑을 24시간 내에 올렸는지 여부에 따라 정렬이 되는 조회였습니다.
      - 이제는 중간에 AI 서버에게 받아온 추천정렬(배치서버를 이용해 추천 정렬을 받아왔습니다)을 이용하여 다시 회원 목록 조회를 하는 문제였습니다
    <br/>
    
    - 이를 해결하기 위해서 거의 총 2주 가량이 소비되었는데 매우 어려웠지만 해결 하고 나서 제일 뿌듯한 코드였습니다. 
    </div>
    </details>
    
9. 전체적인 데이터 불변성 유지 문제 
    <details>
    <summary>회고</summary>
    
    - 전송객체들의 Setter 제거를 통해 해결
    
    </details>

# Contents
- [참고자료](https://github.com/MA-Dot-COM/MA-Dot-COM-Back-End/wiki/%EC%B0%B8%EA%B3%A0-%EC%9E%90%EB%A3%8C)

# Built With
- [AWS RDS - MySQL](https://aws.amazon.com/ko/rds/mysql/?nc=sn&loc=1)
- [MongoDB - Atlas](https://www.mongodb.com/ko-kr/cloud/atlas/efficiency)
- [AWS Ec2](https://aws.amazon.com/ko/ec2/)
- [AWS S3](https://aws.amazon.com/ko/s3/?nc=sn&loc=0)
- [AWS ElasticBeanstalk](https://aws.amazon.com/ko/elasticbeanstalk/)
- [SpringBoot](https://spring.io/projects/spring-boot)
- [JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring Batch](https://spring.io/projects/spring-batch)
- [MyBatis](https://mybatis.org/mybatis-3/)

# Issue 관리
- [진행중인 이슈](https://github.com/MA-Dot-COM/MA-Dot-COM-Back-End/issues)
- [완료된 이슈](https://github.com/MA-Dot-COM/MA-Dot-COM-Back-End/issues?q=is%3Aissue+is%3Aclosed)
    
# Continuous Integration Builds
깃허브 액션을 통해 프로젝트를 진행하면서 지속적인 CI를 진행했습니다.

# Author
- 부시연 - [Github](https://github.com/SybooSyboo782)
