import React from 'react'
import styles from './Account.module.css'
import TextField from '@mui/material/TextField';
import MenuItem from '@mui/material/MenuItem';
import { Bank_List } from '../../constants';
import { useLocation } from 'react-router';

function AccountTransfer() {
  const [userName, setUserName] = React.useState<string>('');
  const [userNumber, setUserNumber] = React.useState<number>(0);
  const [accountName, setAccountName] = React.useState<string>('');
  const [bankCode, setBankCode] = React.useState<number>(0);
  const [password, setPassword] = React.useState<string>('');

  const location = useLocation()
  const state = location.state
  
  const dataload = () => {
    console.log(state)
  }

  const UserNameChange = (e) => {
    setUserName(e.target.value)
  }
  const UserNumberChange = (e) => {
    setUserNumber(e.target.value)
  }
  const AccountNameChange = (e) => {
    setAccountName(e.target.value)
  }
  const BankCodeChange = (e) => {
    setBankCode(e.target.value)
  }
  const PasswordChange = (e) => {
    setPassword(e.target.value)
  }

  const SubmitCreate = (e) => {
    e.preventDefault()
    console.log(password)
    console.log(bankCode)
    console.log(accountName)
    console.log(userNumber)
    console.log(userName)
  }
  
  
  return (
    <div className={styles.container}>
      <div className={styles.content}>
        
        <div className={styles.contentbanner}>
          <div className={styles.title}>계좌 이체</div>
          <div className={styles.information}>DD Bank의 송금 시스템을 사용해보세요</div>
        </div>

        <div className={styles.contentbox}>
          <form onSubmit={SubmitCreate} className={styles.inputform}>
            <TextField className={styles.inputbox} id="outlined-basic" label="예금주" variant="outlined" onChange={UserNameChange} style={{marginTop : "10px"}}/><br />
            <TextField className={styles.inputbox} id="outlined-basic" label="식별번호" variant="outlined" onChange={UserNumberChange} style={{marginTop : "10px"}}/><br />
            <TextField className={styles.inputbox} id="outlined-basic" label="계좌번호" variant="outlined" onChange={UserNumberChange} style={{marginTop : "10px"}}/><br />
            <hr />
            <TextField
            className={styles.inputbox}
          id="outlined-select"
          select
          defaultValue=''
          label='은행목록'
          helperText="이체받을 계좌의 은행을 선택해주세요."
          onChange={BankCodeChange}
          style={{marginTop : "10px"}}
        >
            {Bank_List.map((bank) => (
              <MenuItem key={bank.code} value={bank.code}>
                {bank.bank}
              </MenuItem>
            ))}
            </TextField>
            <TextField className={styles.inputbox} id="outlined-basic" label="계좌 번호" variant="outlined" onChange={AccountNameChange} style={{marginTop : "10px"}}/><br />
            
            <TextField className={styles.inputbox} id="outlined-basic" label="이체할 금액" variant="outlined" onChange={AccountNameChange} style={{marginTop : "10px"}}/><br />
            <button className={styles.submitbutton} onClick={SubmitCreate}>이 체</button>
          </form>
        </div>
              <button onClick={dataload}>데이터</button>
      </div>
    </div>
  )
}

export default AccountTransfer