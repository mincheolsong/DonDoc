import React from 'react'
import styles from './Account.module.css'
import TextField from '@mui/material/TextField';

function AccountNameSearch() {
    const [bankCode, setBankCode] = React.useState<string>('');
    const [accountNumber, setAccountNumber] = React.useState<string>('');
    
    
    const BankCodeChange = (e) => {
      setBankCode(e.target.value)
    }

    const AccountNumberChange = (e) => {
      setAccountNumber(e.target.value)
    }
  
    const SubmitCreate = (e) => {
      e.preventDefault()
      console.log(accountNumber)
      console.log(bankCode)
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
                <TextField className={styles.inputbox} id="outlined-basic" label="은행코드" variant="outlined" onChange={BankCodeChange} style={{marginTop : "10px"}}/><br />
                <TextField className={styles.inputbox} id="outlined-basic" label="계좌번호" variant="outlined" onChange={AccountNumberChange} style={{marginTop : "10px"}}/>
                <button className={styles.submitbutton} onClick={SubmitCreate}>계좌 생성</button>
              </form>
            </div>
  
          </div>
        </div>
      )
}

export default AccountNameSearch