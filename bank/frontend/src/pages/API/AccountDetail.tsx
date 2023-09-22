import React, { useEffect } from 'react'
import styles from './Account.module.css'
import axios from 'axios';
import { BASE_URL } from '../../constants';
import { useLocation } from 'react-router-dom';

interface Transaction {
  historyId : {
    afterBalance: number
  }
  // 다른 필드들을 여기에 추가
}


function AccountDetail() {
  // const [accountNumber, setAccountNumber] = React.useState<string>('');
  // const [identificationNumber, setIdentificationNumber] = React.useState<string>('');
  const [TransLog, setTransLog] = React.useState<Transaction[]>([])
  
  useEffect(() => {
    SubmitCreate()
  },[])

  const location = useLocation()
  const state = {...location.state}


  const data = {
    "identificationNumber": state.identificationNumber,
    "accountNumber": state.accountNumber
  }

  const SubmitCreate = async() => {
    try {
      const response = await axios.post(`${BASE_URL}/bank/history`, data)
      console.log('complete! :', response.data.response)
      if(response.data.error) {
        alert(response.data.error.message)
      } else {
        console.log(response.data.response[0].historyId.afterBalance)
        setTransLog(response.data.response)
      }
    } catch {
      console.log('fail')
    }
  }
  
  
    return (
      <div className={styles.container}>
        <div className={styles.content}>
          
          <div className={styles.contentbanner}>
            <div className={styles.title}>계좌 상세 조회</div>
            <div className={styles.information}>계좌 거래 내역을 확인해보세요</div>
          </div>

          <div className={styles.contentbox}>
            <ul className={styles.accountInfoContainer}>
                {TransLog.length ? TransLog.map((log) => (
                  <li>{log.historyId.afterBalance}</li>
                )) : <span>거래 내역이 없습니다.</span>}
              </ul>
          </div>
      {/* <button onClick={DataLoad}>데이터</button> */}
        </div>
      </div>
    )
}

export default AccountDetail