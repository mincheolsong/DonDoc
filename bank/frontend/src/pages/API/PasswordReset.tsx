import React from 'react'
import styles from './Account.module.css'
import TextField from '@mui/material/TextField';
import axios from 'axios'
import { BASE_URL } from '../../constants';

function PasswordReset() {

    const [accountNumber, setAccountNumber] = React.useState<string>('');
    const [bankCode, setBankCode] = React.useState<number>(0);
    const [identificationNumber, setIdentificationNumber] = React.useState<string>('');
    const [password, setPassword] = React.useState<string>('');
  
    const AccountNumberChange = (e) => {
      setAccountNumber(e.target.value)
    }
    const BankCodeChange = (e) => {
      setBankCode(e.target.value)
    }
    const IdentificationNumberChange = (e) => {
        setIdentificationNumber(e.target.value)
      }
    const PasswordChange = (e) => {
      setPassword(e.target.value)
    }

    const data = {
      "accountNumber": accountNumber,
      "bankCode": bankCode,
      "identificationNumber": identificationNumber,
      "newPassword": password
    }

    const SubmitCreate = async(e) => {
      e.preventDefault()
      try {
        const response = await axios.post(`${BASE_URL}/bank/account/password`, data)
        console.log('complete! :', response)
      } catch {
        console.log('fail')
      }
    }
  
  
    return (
      <div className={styles.container}>
        <div className={styles.content}>
          
          <div className={styles.contentbanner}>
            <div className={styles.title}>비밀번호 재설정</div>
            <div className={styles.information}>계좌의 비밀번호를 재설정합니다</div>
          </div>
  
          <div className={styles.contentbox}>
            <form onSubmit={SubmitCreate} className={styles.inputform}>

              <TextField className={styles.inputbox} id="outlined-basic" label="계좌번호" variant="outlined" onChange={AccountNumberChange} style={{marginTop : "10px"}}/><br />
              <TextField className={styles.inputbox} id="outlined-basic" label="은행코드" variant="outlined" onChange={BankCodeChange} style={{marginTop : "10px"}}/><br />
              <TextField className={styles.inputbox} id="outlined-basic" label="식별번호" variant="outlined" onChange={IdentificationNumberChange} style={{marginTop : "10px"}}/><br />
              <TextField
                className={styles.inputbox} 
                id="outlined-password-input"
                label="새 비밀번호"
                type="password"
                autoComplete="current-password"
                onChange={PasswordChange}
                style={{marginTop : "10px"}}
              />
              <button className={styles.submitbutton} onClick={SubmitCreate}>비밀번호 재설정</button>
            </form>
            {/* <div className={styles.inputform}>
            </div> */}
          </div>
  
        </div>
      </div>
    )
}

export default PasswordReset