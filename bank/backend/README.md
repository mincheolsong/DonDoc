### 계좌 목록조회 API
- `POST` /bank/account/list  
- request 
    ```
    { "identificationNumber" : ["01095530160","asdf1234"] }
    ```  
- response
    ```
    [
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
    ```
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
