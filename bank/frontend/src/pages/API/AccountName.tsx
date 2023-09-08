import React from 'react'
import styles from './Account.module.css'
import TextField from '@mui/material/TextField';

function AccountName() {
  const [bankCode, setBankCode] = React.useState<string>('');
  const [identificationNumber, setIdentificationNumber] = React.useState<string>('');
  
  
    const BankCodeChange = (e) => {
      setBankCode(e.target.value)
    }
    const IdentificationNumberChange = (e) => {
      setIdentificationNumber(e.target.value)
    }
  
  
    return (
      <div className={styles.container}>
        <div className={styles.content}>
          
          <div className={styles.contentbanner}>
            <div className={styles.title}>계좌 실명 조회</div>
            <div className={styles.information}>송금 직전 계좌가 존재하는지 확인해 보세요</div>
          </div>

          <div className={styles.contentbox}>
            <div className={styles.inputform}>
              <TextField className={styles.inputbox} id="outlined-basic" label="은행코드" variant="outlined" onChange={BankCodeChange} style={{marginTop : "10px"}}/><br />
              <TextField className={styles.inputbox} id="outlined-basic" label="계좌번호" variant="outlined" onChange={IdentificationNumberChange} style={{marginTop : "10px"}}/>
            </div>
          </div>

        </div>
      </div>
    )
}

export default AccountName