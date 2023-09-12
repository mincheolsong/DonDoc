import React from 'react'
import styles from './Account.module.css'
import TextField from '@mui/material/TextField';
import axios from 'axios'
import { BASE_URL } from '../../constants';

function AccountNameSearch() {
    const [bankCode, setBankCode] = React.useState<string>('');
    const [accountNumber, setAccountNumber] = React.useState<string>('');
    
    
    const BankCodeChange = (e) => {
      setBankCode(e.target.value)
    }

    const AccountNumberChange = (e) => {
      setAccountNumber(e.target.value)
    }
  
    const data = {
      "accountNumber": accountNumber,
      "bankCode": bankCode
    }

    const SubmitCreate = async(e) => {
      e.preventDefault()
      try {
        const response = await axios.post(`${BASE_URL}/bank/account/certification`, data)
        console.log('complete! :', response)
      } catch {
        console.log('fail')
      }
    }
    
      return (
        <div className={styles.container}>
          <div className={styles.content}>
            
            <div className={styles.contentbanner}>
              <div className={styles.title}>계좌 실명 조회</div>
              <div className={styles.information}>계좌의 실명을 조회해보세요</div>
            </div>
  
            <div className={styles.contentbox}>
              <form onSubmit={SubmitCreate} className={styles.inputform}>
                <TextField className={styles.inputbox} id="outlined-basic" label="은행코드" variant="outlined" onChange={BankCodeChange} style={{marginTop : "10px"}}/><br />
                <TextField className={styles.inputbox} id="outlined-basic" label="계좌번호" variant="outlined" onChange={AccountNumberChange} style={{marginTop : "10px"}}/>
                <button className={styles.submitbutton} onClick={SubmitCreate}>실명 조회</button>
              </form>
            </div>
  
          </div>
        </div>
      )
}

export default AccountNameSearch