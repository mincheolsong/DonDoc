import React from 'react'
import styles from './Account.module.css'
import TextField from '@mui/material/TextField';
import axios from 'axios';
import { BASE_URL } from '../../constants';
import { useNavigate } from 'react-router-dom';


function AccountList() {
  const [identificationNumber, setIdentificationNumber] = React.useState<string>('');
  const [accountInfo, setAccountInfo] = React.useState([])
  const [complete, setComplete] = React.useState<boolean>(false)

  const navigate = useNavigate()

  const goToTransAll = ({account}) => {
    navigate('/account-trans', {state: {
      identificationNumber: identificationNumber,
      accountNumber: account.accountNumber
    }})
  }

  const goToTrans = ({account}) => {
    navigate('/account-transfer', {state: {
      identificationNumber: identificationNumber,
      accountNumber: account.accountNumber
    }})
  }

  const goToPasswordChange = () => {
    navigate('/password-reset')
  }

  const PageReload = (e) => {
    e.preventDefault()
    location.reload()
  }

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
      const response = await axios.post(`${BASE_URL}/bank/account/list`, data)
      console.log('complete! :', response.data.response)
      if(response.data.error) {
        alert(response.data.error.message)
      } else {
        setAccountInfo(response.data.response)
        setComplete(true)
        // console.log('Save', accountInfo)
      }
      // alert('accountName :', response.data.response)
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
              <TextField className={styles.inputbox} id="outlined-basic" label="식별번호" variant="outlined" onChange={IdentificationNumberChange} style={{marginTop : "10px"}}
              disabled={complete}/><br />
              <button className={styles.submitbutton} onClick={SubmitCreate}>계좌 목록 조회</button>
              {complete ? <button className={styles.submitbutton} onClick={PageReload}>다른 번호 조회</button> : null}
            </form>
          </div>
          
          {accountInfo.length >= 1 && (
            <div>
              <h2>계좌 정보</h2>
              <ul className={styles.accountInfoContainer}>
                {accountInfo.map((account, index) => (
                  <div key={index} >
                    <li>계좌 이름 : {account.accountName}</li>
                    <li>계좌 번호: {account.accountNumber}</li>
                    <li>계좌 잔액: {account.balance}</li>
                    <li>은행: {account.bankName}</li>
                    <button className={styles.movebutton} onClick={() => goToTransAll({account})}>계좌 상세 조회</button>
                    <button className={styles.movebutton} onClick={() => goToTrans({account})}>이체하기</button>
                    <button className={styles.movebutton} onClick={goToPasswordChange}>비밀번호 변경</button>
                  </div>
                ))}
              </ul>
            </div>
          )}

        </div>
      </div>
    )
}

export default AccountList