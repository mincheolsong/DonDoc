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
