import React from 'react'
import styles from './Account.module.css'
import TextField from '@mui/material/TextField';
import MenuItem from '@mui/material/MenuItem';
import { Bank_List } from '../../constants';

function AccountName() {
  const [bankCode, setBankCode] = React.useState<string>('');
  const [identificationNumber, setIdentificationNumber] = React.useState<string>('');
  
  
    const BankCodeChange = (e) => {
      setBankCode(e.target.value)
    }
    const IdentificationNumberChange = (e) => {
      setIdentificationNumber(e.target.value)
    }

    const SubmitCreate = (e) => {
      e.preventDefault()
      console.log(bankCode)
      console.log(identificationNumber)
    }
  
  
    return (
      <div className={styles.container}>
        <div className={styles.content}>
          
          <div className={styles.contentbanner}>
            <div className={styles.title}>계좌 실명 조회</div>
            <div className={styles.information}>송금 직전 계좌가 존재하는지 확인해 보세요</div>
          </div>

          <div className={styles.contentbox}>
            <form onSubmit={SubmitCreate} className={styles.inputform}>
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
              <TextField className={styles.inputbox} id="outlined-basic" label="계좌번호" variant="outlined" onChange={IdentificationNumberChange} style={{marginTop : "10px"}}/>
              <button className={styles.submitbutton} onClick={SubmitCreate}>계좌 생성</button>
            </form>
          </div>

        </div>
      </div>
    )
}

export default AccountName