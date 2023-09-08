import React from 'react'
import styles from './Account.module.css'
import TextField from '@mui/material/TextField';

function AccountTransfer() {
    const [userName, setUserName] = React.useState<string>('');
    const [userNumber, setUserNumber] = React.useState<number>(0);
    const [accountName, setAccountName] = React.useState<string>('');
    const [bankCode, setBankCode] = React.useState<number>(0);
    const [password, setPassword] = React.useState<string>('');
  
  
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
  
  
    return (
      <div className={styles.container}>
        <div className={styles.content}>
          
          <div className={styles.contentbanner}>
            <div className={styles.title}>계좌 이체</div>
            <div className={styles.information}>DD Bank의 송금 시스템을 사용해보세요</div>
          </div>
  
          <div className={styles.inputform}>
            <TextField id="outlined-basic" label="예금주" variant="outlined" onChange={UserNameChange} /><br />
            <TextField id="outlined-basic" label="식별번호" variant="outlined" onChange={UserNumberChange} /><br />
            <TextField id="outlined-basic" label="계좌 이름" variant="outlined" onChange={AccountNameChange} /><br />
            <TextField id="outlined-basic" label="은행 코드" variant="outlined" onChange={BankCodeChange} /><br />
            <TextField
            id="outlined-password-input"
            label="계좌 비밀번호"
            type="password"
            autoComplete="current-password"
            onChange={PasswordChange}
          />
            
          </div>
        </div>
      </div>
    )
}

export default AccountTransfer