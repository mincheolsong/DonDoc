### 계좌 목록조회 API

- `POST` /bank/account/list
- request
  ```
  { "identificationNumber" : ["01095530160","asdf1234"] }
  ```
- response
  `    [
        {
            Long accountId; // 계좌 ID
            String accountName; // 계좌이름
            String accountNumber; // 계좌번호
            int balance; // 잔액
            Long bankCode; // 은행코드
            String bankName; // 은행이름
        },
        {
            ...
        }
    ]
   `
  <br>

### 계좌 상세조회 API

- `GET` /bank/account/detail/{accountId}
- request
  ```
  PathVariable : accountId (계좌 ID)
  ```
- response
  ```
  {
      Long accountId; // ID
      String accountName; // 계좌이름
      String accountNumber; // 계좌번호
      int balance; // 잔액
      Long bankCode; // 은행코드
      String bankName; // 은행이름
  }
  ```

<br>

### 전체 거래 내역 조회 API

- `POST` /bank/history<br>

- Request

  ```aidl
  {
       "identificationNumber" : String, // 회원 식별번호
       "accountNumber" : String // 회원 계좌번호
  }
  ```

- Response
  ```aidl
  {
     Long historyId; // 거래 ID
     String toAccount; // 상대 계좌번호
     "toCode": {
          int bankCodeId, // 상대 은행코드
          String bankName // 상대 은행이름
     },
     int type, // 입/출금 타입
     int transferAmount, // 거래 금액
     int afterBalance, // 거래 후 잔액
     String memo, // 나에게 표시하는 메모
     String toMemo, // 상대에게 표시할 메모
     LocalDateTime createdAt // 거래 시간
  }
  ```

<br>

### 상세 거래 내역 조회 API

- `POST` /bank/detail_history<br>

- Request

  ```aidl
  {
       "identificationNumber" : String, // 회원 식별번호
       "accountNumber" : String, // 회원 계좌번호
       "historyId": int // 거래 ID
  }
  ```

- Response
  ```aidl
  {
     Long historyId; // 거래 ID
     String toAccount; // 상대 계좌번호
     "toCode": {
          int bankCodeId, // 상대 은행코드
          String bankName // 상대 은행이름
     },
     int type, // 입/출금 타입
     int transferAmount, // 거래 금액
     int afterBalance, // 거래 후 잔액
     String memo, // 나에게 표시하는 메모
     String toMemo, // 상대에게 표시할 메모
     LocalDateTime createdAt // 거래 시간
  }
  ```

<br>

### 예금주 생성 API

- `POST` /bank/owner/create<br>

- Request

  ```aidl
  {
     "identificationNumber": "String", // 식별번호(핸드폰번호)
     "ownerName": "String" // 예금주 명
  }
  ```

- Response
  ```aidl
  {
     "success": true,
     "response": "예금주 등록이 완료되었습니다.",
     "error": null
  }
  ```

<br>

### 계좌 개설 API

- `POST` /bank/account/create<br>

- Request

  ```aidl
  {
     "accountName": "String", // 계좌이름
     "bankCode": Long, // 은행코드
     "identificationNumber": "String", // 식별번호(핸드폰번호)
     "password": "String" // 비밀번호 숫자 4자리
  }
  ```

- Response
  ```aidl
  {
     "success": true,
     "response": "계좌 생성이 완료되었습니다.",
     "error": null
  }
  ```

<br>

### 계좌 실명조회 API

- `POST` /bank/account/certification<br>

- Request

  ```aidl
  {
     "accountNumber": "string", // 계좌번호
     "bankCode": Long // 은행코드
  }
  ```

- Response
  ```aidl
  {
     "success": true,
     "response": {
         "accountNumber": "7091511443528",
         "ownerName": "테스트",
         "bankName": "케이뱅크",
         "msg": "계좌 조회에 성공했습니다."
     },
     "error": null
  }
  ```

<br>

### 계좌이체 API

- `POST` /bank/account/transfer<br>

- Request

  ```aidl
  {
     "accountId": Long, // 내 계좌 ID
     "identificationNumber": "string", // 식별번호(핸드폰 번호)
     "password": "string", // 비밀번호(4자리)
     "toAccount": "string", // 송금 계좌 번호
     "toCode": Long, // 은행코드
     "sign": "string", // 내 계좌에 남는 표시
     "toSign": "string", // 상대 계좌에 남는 표시
     "transferAmount": Integer // 거래금액
  }
  ```

- Response
  ```aidl
  {
     "success": true,
     "response": "이체가 정상적으로 수행되었습니다.",
     "error": null
  }
  ```

<br>

### 비밀번호 재설정 API

- `POST` /bank/detail_history<br>

- Request

  ```aidl
  {
     "accountNumber": "string", // 계좌번호
     "bankCode": Long, // 은행코드
     "identificationNumber": "string", // 식별번호
     "newPassword": "string" // 새로운 비밀번호
  }
  ```

- Response
  ```aidl
  {
     "success": true,
     "response": {
         "msg": "비밀번호가 재설정 되었습니다."
     },
     "error": null
  }
  ```

<br>

### 메모 작성 API

- `POST` /bank/detail_history/bank<br>

- Request

  ```aidl
  {
     "identificationNumber" : "String", // 식별번호(핸드폰 번호)
     "accountNumber" : "String", // 계좌 번호
     "historyId": Long, // 거래 ID
     "content" : "String" // 메모 내용
  }
  ```

- Response
  ```aidl
  {
     "success": true,
     "response": {
         "id": 메모 ID,
         "historyId": {
             "id": 거래 ID,
             "toAccount": 상대 계좌번호,
             "type": 입/출금 타입,
             "transferAmount": 거래 금액,
             "afterBalance": 거래 후 잔액,
             "sign": 나에게 표시,
             "toSign": 상대에게 표시,
             "createdAt": 거래 시간
         },
         "content": 메모 내용
     },
     "error": null
  }
  ```
