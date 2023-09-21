import styles from "./MoimLinkAccount.module.css";
import ssafylogo from '../../../assets/ssafy_logo.png'
import { useState } from 'react'
import { useNavigate, useLocation } from "react-router-dom";

function MoimLinkAccount() {

  const [selectAccount, setSelectAccount] = useState<string>('')

  const navigate = useNavigate()

  const { state } = useLocation()
  const moimName = state.moimName
  const moimInfo = state.moimInfo
  
  const ChangeSelectAccount = () => {
    setSelectAccount('대구은행')
  }

  const ToBack = () => {
    navigate(-1)
  }

  const ToNext = () => {
    if (selectAccount) {
      navigate('/moimselect', {state: {moimName:moimName, moimInfo:moimInfo, account:selectAccount}})
    } else {
      console.log('선택해주세요')
    }

  }

  return (
    <div className={styles.container}>
      <div className={styles.content}>

        <div className={styles.topbar}>
          <div className={styles.backbutton}>
            <button className={styles.toback} onClick={ToBack}>
              back
            </button>
          </div>
          <div className={styles.pagename}>
            <h3>모임통장 생성</h3>
          </div>
        </div>

        <div className={styles.createcontent}>
          <div className={styles.createment}>
            <h1>연결할 계좌를 선택해주세요.</h1>
          </div>

          <div className={styles.accounts}>
            <label htmlFor="first" onClick={ChangeSelectAccount}>
              <div className={styles.accountunit}>
                <div className={styles.banklogo}>
                  <img src={ssafylogo} alt="" className={styles.ssafylogo}/>
                </div>
                <div className={styles.accountinfo}>
                  <p>대구은행 영플러스통장</p>
                  <p className={styles.accountnumber}>대구은행 237-128127-12478</p>
                </div>
                <div className={styles.selectcount}>
                  <input type="radio" id="first" />
                </div>
              </div>
            </label>
          </div>


        <div className={styles.buttondiv}>
            <button className={styles.submitbtn} onClick={ToNext}>다음</button>
        </div>
        
        </div>


      </div>
    </div>
  );
}

export default MoimLinkAccount;
