import React from 'react'
import styles from './Account.module.css'
import TextField from '@mui/material/TextField';
import axios from 'axios';
import { BASE_URL } from '../../constants';

function AccountList() {
  const [identificationNumber, setIdentificationNumber] = React.useState<string>('');


  const IdentificationNumberChange = (e) => {
    setIdentificationNumber(e.target.value)
  }
  
  
  // 리스트로 감싸서 보내야 하는데 입력폼을 하나로 만들어서 나눠 담는 방법 생각해야 함
  const data = {
    "identificationNumber": [
      identificationNumber
    ]
  }

  const SubmitCreate = async(e) => {
    e.preventDefault()
    try {
      const response = await axios.post(`${BASE_URL}/bank/owner/create`, data)
      console.log('complete! :', response)
    } catch {
      console.log('fail')
    }
  }

  
    return (
      <div className={styles.container}>
        <div className={styles.content}>
          
          <div className={styles.contentbanner}>
            <div className={styles.title}>계좌 목록 조회</div>
            <div className={styles.information}>계좌목록을 확인해보세요</div>
          </div>

          <div className={styles.contentbox}>

            <form onSubmit={SubmitCreate} className={styles.inputform}>
              <TextField className={styles.inputbox} id="outlined-basic" label="식별번호" variant="outlined" onChange={IdentificationNumberChange} style={{marginTop : "10px"}}/><br />
              <button className={styles.submitbutton} onClick={SubmitCreate}>계좌 생성</button>
            </form>

          </div>

        </div>
      </div>
    )
}

export default AccountList