import React from 'react'
import styles from './Account.module.css'
import TextField from '@mui/material/TextField';
import MenuItem from '@mui/material/MenuItem';
import axios from 'axios'
import { BASE_URL,Bank_List } from '../../constants';

function CreateAccount() {

  const [identificationNumber, setIdentificationNumber] = React.useState<string>('');
  const [accountName, setAccountName] = React.useState<string>('');
  const [bankCode, setBankCode] = React.useState<string>('');
  const [password, setPassword] = React.useState<string>('');

  const IdentificationNumberChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setIdentificationNumber(e.target.value)
  }
  const AccountNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setAccountName(e.target.value)
  }
  const BankCodeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setBankCode(e.target.value)
  }
  const PasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value)
  }

  const Code = parseInt(bankCode, 10)

  const data = {
    "accountName": accountName,
    "bankCode": Code,
    "identificationNumber": identificationNumber,
    "password": password
  }

  const SubmitCreate = async(e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    try {
      const response = await axios.post(`${BASE_URL}/bank/account/create`, data)
      console.log('complete! :', response)
      if(response.data.error) {
        alert(response.data.error.message)
      } else {
        alert(response.data.response)
      }
    } catch {
      console.log('fail!')
    }
  }


  return (
    <div className={styles.container}>
      <div className={styles.content}>
        
        <div className={styles.contentbanner}>
          <div className={styles.title}>계좌 개설</div>
          <div className={styles.information}>은행업무에 필요한 DD Bank의 계좌를 개설하세요</div>
        </div>

        <div className={styles.contentbox}>
          <form onSubmit={SubmitCreate} className={styles.inputform}>
            <TextField className={styles.inputbox} id="outlined-basic" label="식별번호" variant="outlined" onChange={IdentificationNumberChange} style={{marginTop : "10px"}}/><br />
            <TextField className={styles.inputbox} id="outlined-basic" label="계좌 이름" variant="outlined" onChange={AccountNameChange} style={{marginTop : "10px"}}/><br />
            <TextField
            className={styles.inputbox}
          id="outlined-select"
          select
          defaultValue=''
          label='은행목록'
          helperText="계좌를 개설할 은행을 선택해주세요."
          onChange={BankCodeChange}
          style={{marginTop : "10px"}}
        >
            {Bank_List.map((bank) => (
              <MenuItem key={bank.code} value={bank.code}>
                {bank.bank}
              </MenuItem>
            ))}
            </TextField>
            <TextField
              className={styles.inputbox} 
              id="outlined-password-input"
              label="계좌 비밀번호"
              type="password"
              autoComplete="current-password"
              onChange={PasswordChange}
              style={{marginTop : "10px"}}
            />
            <button className={styles.submitbutton} >계좌 생성</button>
          </form>
          {/* <div className={styles.inputform}>
          </div> */}
        </div>

      </div>
    </div>
  )
}

export default CreateAccount


