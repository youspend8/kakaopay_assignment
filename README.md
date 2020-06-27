# Kakaopay 과제 (서버)

## 목차
- [개발도구 및 환경](#개발도구-및-환경)
- [실행](#실행)
- [API Reference](#API-Reference)
- [개발 제약사항](#개발-제약사항)
- [해결방법](#해결방법)

---

## 개발도구 및 환경
- environment
    - OS : Window, OS X
    - IDE : IntelliJ IDEA
- Stack
    - Java 8
    - Spring Boot 2.3.1
    - Maven
    - JPA + Hibernate
    - H2
    - Junit 5 Testing Framework
    
## 실행
```
$ mvn spring-boot:run
```
##API Reference

- ###뿌리기 생성
#####Request

```
GET /sprinkle/generate HTTP/1.1

X-ROOM-ID: XXXXX
X-USER-ID: XXXXX
```
#####Parameter
|Name|Type|Description|Required|
|---|:---:|:---|:---:|
|money|Integer|뿌리기 금액|O|
|division|Integer|뿌리기 인원|O|

#####Response Example
```
{
  "code": 0,
  "message": "정상처리",
  "data": "Kaf"
}
```
|Name|Type|Description|
|:---:|:---:|:---|
|code|Integer|결과 코드|
|message|String|결과 메세지|
|data|String|뿌리기 생성시 발급된 코드|

***

- ### 뿌리기 줍기
#####Request

```
GET /sprinkle/acquire HTTP/1.1

X-ROOM-ID: XXXXX
X-USER-ID: XXXXX
```
#####Parameter
|Name|Type|Description|Required|
|:---:|:---:|:---|:---:|
|token|String|뿌리기 생성시 발급된 코드|O|

#####Response Example
```
{
  "code": 0,
  "message": "정상처리",
  "data": 818
}
```
|Name|Type|Description|
|:---:|:---:|:---|
|code|Integer|결과 코드|
|message|String|결과 메세지|
|data|String|줍기 성공한 금액|

***

- ### 뿌리기 조회
#####Request

```
GET /sprinkle/info HTTP/1.1

X-ROOM-ID: XXXXX
X-USER-ID: XXXXX
```
#####Parameter
|Name|Type|Description|Required|
|:---:|:---:|:---|:---:|
|token|String|뿌리기 생성시 발급된 코드|O|

#####Response Example
```
{
  "code": 0,
  "message": "정상처리",
  "data": {
    "token": "grH",
    "money": 1000,
    "division": 2,
    "createDate": "2020-06-26T15:19:20.702+00:00",
    "generatedInfo": {
      "genUserId": "999",
      "genRoomId": "294583"
    },
    "sprinkleDetail": {
      "list": [
        {
          "divMoney": 990,
          "isAcquire": false,
          "acquiredUserId": null
        },
        {
          "divMoney": 10,
          "isAcquire": false,
          "acquiredUserId": null
        }
      ],
      "soldOut": false
    }
  }
}
```

***
- ####결과코드

|code|message|
|:---:|:---|
|0|정상처리|
|10|유효하지 않은 토큰값니다.|
|11|요청 헤더 정보가 부족합니다.|
|20|자신이 뿌리기한 건은 자신이 받을 수 없습니다.|
|21|뿌리기 당 한 사용자는 한번만 받을 수 있습니다.|
|22|뿌린 건은 10분간만 유효합니다.|
|23|뿌리기가 호출된 대화방과 동일한 대화방에 속한 사용자만이 받을 수 있습니다.|
|24|뿌리기 건이 마감되었습니다.|
|30|뿌리기 금액은 최소 지정한 인원수 이상이어야 됩니다.|

***
##문제해결전략

>- 설계
>    - 뿌리기, 받기, 조회 API의 URI Mapping을 각각 `/generate`, `/acquire`, `/info` 로 선언
>    - Controller 클래스에서 요청 처리에 결과에 따라 응답 메세지만 처리할 수 있도록 구성
>    - Service 클래스에서 요청 처리에 관한 비즈니스 로직을 집중하도록 구성
>    - 하나의 뿌리기 건의 상세 데이터를 일급 컬렉션을 사용하여 상태와 행위를 손쉽게 관리하도록 구성
>    
>- 이슈
>   - Parent Entity - Child Entity 간의 양방향 매핑을 하는 과정에서 StackOverflow 오류 발생
>       1. Lombok의 @ToString 어노테이션에 의해 발생했던 문제 -> ToString exclude로 Child에서 Parent필드 제외
>       2. Parent Type에서 가지는 Child List type setter 메서드를 별도로 Override하여 Child List를 Traversal하여 직접 Parent를 주입하여 해결
>
        
        