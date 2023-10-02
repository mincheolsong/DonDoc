import { useState } from 'react';
import styles from "./CreateResult.module.css";
import { useNavigate, useLocation } from "react-router-dom";
import TermsOfUseModal from './TermsOfUse/TermsOfUse';
import axios from 'axios';
import { useSelector } from "react-redux";
import { UserType } from '../../../store/slice/userSlice';
import { BackLogoHeader } from '../../toolBox/BackLogoHeader/BackLogoHeader';

function CreateResult() {

  const navigate = useNavigate()

  const { state } = useLocation()
  const moimName = state.moimName
  const moimInfo = state.moimInfo
  const account = state.account
  const category = state.category
  const manager = state.manager
  const password = state.password

  const [termsOpen, setTermsOpen] = useState<boolean>(false)

  const OpenTerms = () => {
    setTermsOpen(true)
  }
  const CloseTerms = () => {
    setTermsOpen(false)
  }

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken

  const data = {
    "accountId": account.accountId,
    "introduce": moimInfo,
    "manager": manager,
    "moimName": moimName,
    "moimType": category.code,
    "password": password
  }

  const CreateMoim = async () => {

    const BASE_URL = 'http://j9d108.p.ssafy.io:9999'

    try {
      const response = await axios.post(`${BASE_URL}/api/moim/create`, data, {
        headers: {
          'Content-Type': 'application/json', 
          'Authorization': 'Bearer ' + token
        }
      });
      console.log(response.data)
      alert('모임이 생성되었습니다.')
      navigate('/moimhome')
    } catch(error) {
      console.log('error:', error)
    }
  }

  const WatchInfo = () => {
    console.log('state:',state)
    console.log('data:', data)
    console.log('token:', token)
  }

  return (
    <div className={styles.container}>
      <div className={styles.content}>

        {/* <div className={styles.topbar}>
          <div className={styles.backbutton}>
            <button className={styles.toback} onClick={ToBack}>
              back
            </button>
          </div>
          <div className={styles.pagename}>
            <h3>모임통장 생성</h3>
          </div>
        </div> */}
        <BackLogoHeader name="연결계좌"fontSize="2rem" left="5rem" top="0.8rem"/>

        <div className={styles.createcontent}>
          <div className={styles.createment}>
            <h2>모임 개설준비가 완료 되었습니다</h2>
          </div>

          <div className={styles.accountcontent}>
            <div className={styles.accountinfo}>
              <div className={styles.moimcontent}>
                <p className={styles.title}>모임이름</p>
                <p>{moimName}</p>
              </div>
              <div className={styles.moimcontent}>
                <p className={styles.title}>연결계좌</p>
                <p>{account.bankName} {account.accountNumber}</p>
              </div>
              <div className={styles.moimcontent}>
                <p className={styles.title}>계좌유형</p>
                <p>{category.name}</p>
              </div>
              <div className={styles.moiminfo}>
                <p className={styles.title}>모임소개</p>
                <p className={styles.moimtext}>{moimInfo}</p>
              </div>
              <div className={styles.watchtermsbtn}>
                <button className={styles.openterms} onClick={OpenTerms}>
                  약관보기
                </button>
              </div>
              <div className={styles.checkbox}>
                <input type="checkbox" id="scales" name="sclaes" /><label htmlFor="scales">약관에 동의합니다</label>
              </div>
            </div>
          </div>

        <div className={styles.buttondiv}>
            <button className={styles.submitbtn} onClick={CreateMoim}>계좌 개설하기</button>
        </div>
        
        </div>

        <button onClick={WatchInfo}></button>

        {termsOpen && (
            <>
              <div className={styles.backgroundOverlay} onClick={CloseTerms}/>
              <TermsOfUseModal setTermsOpen={setTermsOpen} />
            </>
          )}

      </div>
    </div>
  );
}

export default CreateResult;
