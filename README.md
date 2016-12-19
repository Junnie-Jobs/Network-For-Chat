# network_final

##1. compile/installation guide

STS에서 git clone을 합니다. 이후 프로젝트 우클릭 후 gradle build를 한 다음, 서버를 시작합니다.

##2. user guide
채팅을 하기 위해 기억할 것은 2가지 입니다.

###먼저 http://junniejobs.xyz:6565에 접속 후 로그인을 해주세요.

![](https://github.com/Junnie-Jobs/ImageRepository/blob/master/network%20final/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202016-12-20%2000.09.03.png?raw=true)

###로그인을 하고나서 채팅을 시작하면 됩니다.

![](https://github.com/Junnie-Jobs/ImageRepository/blob/master/network%20final/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202016-12-20%2000.09.29.png?raw=true)

##3. bug report

1) 연결이 끊어진 유저에 대한 처리

연결이 끊어진 유저에 대한 처리가 제대로 이루어지지 않은 경우, 여러 버그상황이 발생함  
ex) 새로고침할 때마다 유저가 복제되는 상황, 브라우저를 나간 유저가 여전히 접속자 줄에 남아 있는 상황

해결  
SessionDisconnectEvent 객체를 파라미터로 받는 이벤트리스터는 생성.
이 이벤트리스너는 Session이 Disconnect되는 이벤트가 발생하면, 그에 해당하는 유저를 Active된 상태에서 remove함.

