import React from 'react'
import styles from './Account.module.css'
import TextField from '@mui/material/TextField';
import axios from 'axios';
import { BASE_URL } from '../../constants';

function AccountDetail() {
  // const [accountNumber, setAccountNumber] = React.useState<string>('');
  const [accountId, setAccountId] = React.useState<string>('');
  const [accountInfo, setAccountInfo] = React.useState({})
   
  // const AccountNumberChange = (e) => {
  //   setAccountNumber(e.target.value)
  // }
  const AccountIdChange = (e) => {
    setAccountId(e.target.value)
  }


  const SubmitCreate = async(e) => {
    e.preventDefault()
    try {
      const response = await axios.get(`${BASE_URL}/bank/account/detail/${accountId}`)
      console.log('complete! :', response.data.response)
      if(response.data.error) {
        alert(response.data.error.message)
      } else {
        setAccountInfo(response.data.response)
        // console.log(response.data.response)
      }
    } catch {
      console.log('fail')
    }
  }
  
    return (
      <div className={styles.container}>
        <div className={styles.content}>
          
          <div className={styles.contentbanner}>
            <div className={styles.title}>계좌 상세 조회</div>
            <div className={styles.information}>계좌를 상세히 조회해보세요</div>
          </div>

          <div className={styles.contentbox}>
            <form onSubmit={SubmitCreate} className={styles.inputform}>
              <TextField className={styles.inputbox} id="outlined-basic" label="계좌 아이디" variant="outlined" onChange={AccountIdChange} style={{marginTop : "10px"}}/><br />
              {/* <TextField className={styles.inputbox} id="outlined-basic" label="계좌번호" variant="outlined" onChange={AccountNumberChange} style={{marginTop : "10px"}}/> */}
              <button className={styles.submitbutton} onClick={SubmitCreate}>계좌 상세 조회</button>
            </form>
          </div>

          {accountInfo && (
            <div className={styles.accountInfoContainer}>
              <h2>계좌 정보</h2>
              <ul>
                  <div>
                    <li>계좌 아이디 : {accountInfo.accountId}</li>
                    <li>계좌 이름 : {accountInfo.accountName}</li>
                    <li>계좌 번호: {accountInfo.accountNumber}</li>
                    <li>계좌 잔액: {accountInfo.balance}</li>
                    <li>은행: {accountInfo.bankName}</li>
                  </div>
              </ul>
            </div>
          )}

        </div>
      </div>
    )
}

export default AccountDetail