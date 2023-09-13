import React from 'react'
import styles from './Account.module.css'
import TextField from '@mui/material/TextField';
import axios from 'axios';
import { BASE_URL } from '../../constants';

function AccountList() {
  const [identificationNumber, setIdentificationNumber] = React.useState<string>('');
  const [accountInfo, setAccountInfo] = React.useState([])


  const IdentificationNumberChange = (e) => {
    setIdentificationNumber(e.target.value)
  }
  
  
  // 리스트로 감싸서 보내야 하는데 입력폼을 하나로 만들어서 나눠 담는 방법 생각해야 함
  const data = {
    "identificationNumber": [
      identificationNumber
    ]
  }

  

  const SubmitCreate = async(e) => {
    e.preventDefault()
    try {
      const response = await axios.post(`${BASE_URL}/bank/account/list`, data)
      console.log('complete! :', response.data.response)
      if(response.data.error) {
        alert(response.data.error.message)
      } else {
        setAccountInfo(response.data.response)
        // console.log('Save', accountInfo)
      }
      // alert('accountName :', response.data.response)
    } catch {
      console.log('fail')
    }
  }

  
    return (
      <div className={styles.container}>
        <div className={styles.content}>
          
          <div className={styles.contentbanner}>
            <div className={styles.title}>계좌 목록 조회</div>
            <div className={styles.information}>계좌목록을 확인해보세요</div>
          </div>

          <div className={styles.contentbox}>

            <form onSubmit={SubmitCreate} className={styles.inputform}>
              <TextField className={styles.inputbox} id="outlined-basic" label="식별번호" variant="outlined" onChange={IdentificationNumberChange} style={{marginTop : "10px"}}/><br />
              <button className={styles.submitbutton} onClick={SubmitCreate}>계좌 생성</button>
            </form>
          </div>
          
          {accountInfo.length >= 1 && (
            <div className={styles.accountInfoContainer}>
              <h2>계좌 정보</h2>
              <ul>
                {accountInfo.map((account, index) => (
                  <div key={index}>
                    <li>계좌 아이디 : {account.accountId}</li>
                    <li>계좌 이름 : {account.accountName}</li>
                    <li>계좌 번호: {account.accountNumber}</li>
                    <li>계좌 잔액: {account.balance}</li>
                    <li>은행: {account.bankName}</li>
                  </div>
                ))}
              </ul>
            </div>
          )}

        </div>
      </div>
    )
}

export default AccountList