# restful-api-demo    
* spring boot 3.3.2
* thymeleaf
* hateoas
* java 21
---
# REST
## client-server
* 클라이언트와 서버는 서로 종속되지 않은 상태에서 발전될 수 있다.
* 클라이언트는 URL의 정보만 알고있다.
## stateless
> Each request from client to server must contain all of the information necessary to understand the request, and cannot take advantage of any stored context on the server. Session state is therefore kept entirely on the client.
* 클라이언트와 서버간 상호작용은 `stateless`하다
* 서버는 클라이언트의 최근 HTTP 요청에 대한 어떠한 것도 저장하지 않는다.
* No session, No history
* 만약에 `stateful`한 동작이 필요한 경우, 클라이언트의 각 요청에 권한에 대한 정보를 담는다.
* 클라이언트에서 애플리케이션의 상태를 관리해야한다.   
[session&stateless](https://www.baeldung.com/cs/rest-sessions)
## cache
* 캐싱은 클라이언트 쪽에 성능 향상을 가져온다.
* 캐싱이 가능한 자원은 반드시 `cacheable`을 선언해 캐싱이 이루어지도록 한다.   
[mdn web docs: Cache-Control](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cache-Control)
## uniform interface
* identification of resources 
	* 리소스는 식별 가능해야한다. (URI를 사용)
* manipulation of resources through representations 
	* 클라이언트는 무엇을 할지 전달하고 서버는 어떻게 할지 처리한다.
	* 클라이언트는 원하는 자원의 `representation`을 요청으로 보내고 서버는 이를 받아 실질적으로 처리하여 응답한다.
* **self-descriptive messages**
	- 메시지는 스스로를 설명해야한다.
	* 요청 메시지는 목적지를 담고 있어야한다.
	* **응답 메시지는 파싱할 타입에 대한 정보를 가지고 있어야한다.**
	* **메시지의 데이터에 대한 해석도 자의적으로 수행이 가능해야한다.**
* **hypermedia as the engine of application state (HATEOAS)**
	* **애플리케이션의 상태는 Hyperlink를 이용해 전이되어야한다.**
	* HTML로 응답을 하는경우는 이를 만족
	* JSON으로 표현하는 경우는 Link헤더 사용해서 만족
   
	[D2 Rest API 강연](https://www.youtube.com/watch?v=RP_f5dMoHFc&t=664s)   
[What RESTful actually means](https://codewords.recurse.com/issues/five/what-restful-actually-means)
## layered system
* REST를 사용해 `layered system architecture`적용할 수 있다.
* 클라이언트는 end point 서버와 연결하는지 아니면 중간 레이어와 연결하는지 알 수 없다.
## code-on-demand (Optional)   
* 해당 제약조건은 선택사항이다.
* 어플리케이션의 실행을 지원하기 위해 실행이 가능한 코드를 리턴할 수 있다.
   
[REST API Tutorial](https://restfulapi.net/rest-architectural-constraints/)

---
## DEMO 
### cache
#### Cache-Control
* HTTP 헤더에 Cache-Control을 사용해 브라우저가 응답 캐시를 얼마나 보유할지를 알려준다.
* **Etag 헤더는 만료가 지난 데이터에 대해서도 변경이 없으면 캐시 재할당을 통해 다운로드를 피할수 있게한다.**
* no-cache: 요청할 때 마다 Etag를 검사한다.
* no-store: 캐시를 하지 말라는 설정으로 매번 요청에 전체 응답이 다운로드 된다.
* public, private: 중간 CDN에 캐시를 저장하는지 여부
* max-age: 캐시의 유효기간
``` http response
accept-ranges: bytes
cache-control: max-age=31536000, private
content-length: 1362
content-type: text/javascript
date: Sun, 11 Aug 2024 11:43:31 GMT
last-modified: Sun, 11 Aug 2024 11:42:34 GMT
vary: Origin, Access-Control-Request-Method, Access-Control-Request-Headers
```
### uniform interface
#### self-descriptive messages
1. Media type
* IANA: HTTP media type
* 미디어 타입을 정의하고 IANA에 미디어 타입 등록
* 메시지를 보는 사람은 IANA에서 명세를 확인
2. Profile
* Link 헤더에 profile 명세를 작성한 링크 추가
* 해당 명세를 찾아가 해석가능

#### hypermedia as the engine of application state
1. HATEOAS 적용
* 응답에 현재 요청 결과에 적합한 링크 추가
``` java
EntityModel<Article> entityModel = EntityModel.of(selectedArticle,
                linkTo(methodOn(ArticleController.class).getArticle(selectedArticle.getId())).withSelfRel(),
                linkTo(methodOn(ArticleController.class).updateArticle(null)).withRel("update"),
                linkTo(methodOn(ArticleController.class).deleteArticle(selectedArticle.getId())).withRel("delete"));
```
* 응답 값
``` json
{
    "createdAt": "2024-08-08T00:26:01.591189",
    "updatedAt": "2024-08-08T00:26:01.591189",
    "id": 1,
    "title": " test",
    "content": "test content",
    "_links": {
        "self": {
            "href": "http://localhost:8080/api-v1/article/1"
        },
        "update": {
            "href": "http://localhost:8080/api-v1/article"
        },
        "delete": {
            "href": "http://localhost:8080/api-v1/article/1"
        }
    }
}
```
* web browser의 session storage를 사용하여 API 요청
``` js
sessionStorage.setItem('self', article._links.self.href);
sessionStorage.setItem('delete', article._links.delete.href);
sessionStorage.setItem('update', article._links.update.href);
```
``` js
const detailApi = sessionStorage.getItem('self');
const deleteApi = sessionStorage.getItem('delete');
const updateApi = sessionStorage.getItem('update');

fetch(detailApi)
	.then(response => response.json())
	.then(article => {
	    document.getElementById('title').value = article.title;
	    document.getElementById('content').value = article.content;
	    document.getElementById('createdAt').value = new Date(article.createdAt).toLocaleString();
	    document.getElementById('updatedAt').value = new Date(article.updatedAt).toLocaleString();
	})
	.catch(error => {
	    console.error('Error:', error);
	    alert('Failed to load article details.');
	});
```


    

