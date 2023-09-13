import React from 'react';
import styles from './Account.module.css';
import TextField from '@mui/material/TextField';
import axios from 'axios';
import { BASE_URL } from '../../constants';

function AccountMaster() {
  const [identificationNumber, setIdentificationNumber] = React.useState<string>('');
  const [accountMaster, setAccountMaster] = React.useState<string>('');


  const IdentificationNumberChange = (e) => {
    setIdentificationNumber(e.target.value)
  }

  const AccountMasterChange = (e) => {
    setAccountMaster(e.target.value)
  }

  const data = {
      "identificationNumber": identificationNumber,
      "ownerName": accountMaster
  }

  const SubmitCreate = async(e) => {
    e.preventDefault()
    try {
      const response = await axios.post(`${BASE_URL}/bank/owner/create`, data)
      console.log('complete! :', response)
    }
    catch {
      console.log('fail')
    }
  }
  
    return (
      <div className={styles.container}>
        <div className={styles.content}>
          
          <div className={styles.contentbanner}>
            <div className={styles.title}>예금주 생성</div>
            <div className={styles.information}>예금주를 만들어 보세요</div>
          </div>
          
          <div className={styles.contentbox}>
            <form onSubmit={SubmitCreate} className={styles.inputform}>
              <TextField className={styles.inputbox} id="outlined-basic" label="예금주이름" variant="outlined" onChange={AccountMasterChange} style={{marginTop : "10px"}}/>
              <TextField className={styles.inputbox} id="outlined-basic" label="식별번호" variant="outlined" onChange={IdentificationNumberChange} style={{marginTop : "10px"}}/><br />
              <button className={styles.submitbutton} onClick={SubmitCreate}>예금주 생성</button>
            </form>
          </div>

        </div>
      </div>
    )
}

export default AccountMaster