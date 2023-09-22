import React from 'react'
import styles from './Account.module.css'
import TextField from '@mui/material/TextField';
import MenuItem from '@mui/material/MenuItem';
import axios from 'axios'
import { BASE_URL, Bank_List } from '../../constants';

function PasswordReset() {

    const [accountNumber, setAccountNumber] = React.useState<string>('');
    const [bankCode, setBankCode] = React.useState<string>('');
    const [identificationNumber, setIdentificationNumber] = React.useState<string>('');
    const [password, setPassword] = React.useState<string>('');
  
    const AccountNumberChange = (e: React.ChangeEvent<HTMLInputElement>) => {
      setAccountNumber(e.target.value)
    }

const BankCodeChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
  setBankCode(e.target.value);
}

    const IdentificationNumberChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setIdentificationNumber(e.target.value)
      }
    const PasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
      setPassword(e.target.value)
    }

    const code = parseInt(bankCode,10)
    
    const data = {
      "accountNumber": accountNumber,
      "bankCode": code,
      "identificationNumber": identificationNumber,
      "newPassword": password
    }

    const SubmitCreate = async(e: React.FormEvent<HTMLFormElement>) => {
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
              <TextField
            className={styles.inputbox}
          id="outlined-select"
          select
          defaultValue=''
          label='은행목록'
          helperText="해당 계좌의 은행을 선택해주세요."
          onChange={BankCodeChange}
          style={{marginTop : "10px"}}
        >
            {Bank_List.map((bank) => (
              <MenuItem key={bank.code} value={bank.code}>
                {bank.bank}
              </MenuItem>
            ))}
            </TextField>
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
              <button className={styles.submitbutton}>비밀번호 재설정</button>
            </form>
            {/* <div className={styles.inputform}>
            </div> */}
          </div>
  
        </div>
      </div>
    )
}

export default PasswordReset