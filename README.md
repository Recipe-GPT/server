# Recipe-GPT
```
GPT 3.5 model을 이용해 집에 남는 음식 재료를 조합해 음식 
레시피를 추천하는 서비스입니다.
```

## 프로젝트 목표
1. 집에 있는 재료를 활용하여 창의적인 레시피를 제작하고 공유함으로써 **요리 스킬을 향상**시킬 수 있습니다.  
2. 식품의 낭비를 줄이고 지속 가능 한 **식품 생산의 가치**를 강조할 수 있습니다.  
3. **집에서 건강하고 균형 잡힌 식사**를 쉽게 만들어 먹을 수 있습니다.

## 주 사용 기술
<img width="590" alt="image" src="https://github.com/Recipe-GPT/server/assets/45661217/a217202b-daf7-4e5e-b5ac-3311923d17cf">

## 프로젝트 팀원
<img width="822" alt="image" src="https://github.com/Recipe-GPT/server/assets/45661217/f2b7f5ba-c973-45a1-a22b-7ff5fe3db02d">

## 서버 사용 기술
```
- Kotiln / Spring / JPA
- Authorization : Spring Security
- DOCS : Swagger
- AWS: S3, CloudFront
- DB : MySql, Redis
```

## AWS 아키텍쳐
<img width="577" alt="image" src="https://github.com/Novel-Cloud/server/assets/45661217/0b3f6758-79b1-4df2-8890-18c27771894c">

## 프로젝트 실행 방법

application.properties 파일에 설정한 환경 변수를 모두 입력해주세요.

```java
-Dspring.profiles.active=local
```
vm 옵션으로 실행합니다. MySQL 데이터베이스는 properties의 ddl-auto 설정을 통해 스키마를 만들 수 있습니다.  

**프로젝트 실행하기**   
Recipe-GPT 서비스는 프론트와 백엔드가 분리되어있습니다.  
백엔드 서비스인 이 프로젝트를 먼저 실행한 후, 프론트 앱을 실행하면 앱 구동이 완료됩니다.  

Backend API Server (this repository).  
[Frontend repository](https://github.com/Recipe-GPT/app)

### UseCase
**레시피 질문**
1. 레시피 질문 페이지에서 재료를 이용한 레시피 질문이 가능합니다.
2. 채팅방을 생성하고 삭제할 수 있습니다.
3. 마음에 드는 요리를 선택하면 추가 질문을 해 요리의 자세한 정보가 나타납니다.

**레시피 커뮤니티**
1. 레시피를 AI에게 받았으면 좋은 레시피를 공유할 수 있습니다.
2. 레시피 커뮤니티 페이지에서 다른 사람이 공유한 레시피를 확인할 수 있습니다.

**레시피 질문 flow**
1. 재료 조미료 입력
<img width="1719" alt="image (3)" src="https://github.com/Recipe-GPT/server/assets/45661217/b13af39e-6069-46c7-bb52-75f1b05b16fe">

2. 레시피 선택 & 확인
<img width="1724" alt="image (5)" src="https://github.com/Recipe-GPT/server/assets/45661217/69c8f080-0429-4f80-8773-9abbf8ce4e77">

3. 레시피 공유
<img width="1718" alt="image (6)" src="https://github.com/Recipe-GPT/server/assets/45661217/836b33a3-32dc-494b-81e1-66117f87868b">

## ERD
![board](https://github.com/Recipe-GPT/server/assets/45661217/70cbb07a-d8dc-45e8-ba20-debe3a4d7738)
