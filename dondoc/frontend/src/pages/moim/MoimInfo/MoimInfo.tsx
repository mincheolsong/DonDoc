import { useState } from 'react';
import styles from "./MoimInfo.module.css";
import { useNavigate, useLocation } from "react-router-dom";
import TermsOfUse from '../CreateResult/TermsOfUse/TermsOfUse';
import SelectAccount from './SelectAccount/SelectAccount';
import { useSelector } from 'react-redux';
import { UserType } from '../../../store/slice/userSlice';
import { BASE_URL } from '../../../constants';
import axios from 'axios';
import BackLogoHeader from '../../toolBox/BackLogoHeader/BackLogoHeader';

type selectedAccount = {
  accountId: number,
  accountName: string,
  accountNumber: string,
  balance: number,
  bankCode: number,
  bankName: string
}

const defaultAccount: selectedAccount = {
  accountId: 0,
  accountName: '',
  accountNumber: '',
  balance: 0,
  bankCode: 0,
  bankName: ''
}


function MoimInfo() {

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken

  const navigate = useNavigate()

  const { state } = useLocation()
  const invite = state.invite

  const [selectAccountOpen, setSelectAccountOpen] = useState<boolean>(false)
  const [termsOpen, setTermsOpen] = useState<boolean>(false)
  const [selectedAccount, setSelectedAccount] = useState<selectedAccount>(defaultAccount)

  const OpenAccounts = () => {
    setSelectAccountOpen(true)
  }
  const CloseAccounts = () => {
    setSelectAccountOpen(false)
  }
  const OpenTerms = () => {
    setTermsOpen(true)
  }
  const CloseTerms = () => {
    setTermsOpen(false)
  }
  // const ToBack = () => {
  //   navigate(-1)
  // }

  const [agreeTerms, setAgreeTerms] = useState<boolean>(false); // 약관 동의 상태를 저장하는 상태 변수

  const AcceptInvite = async() => {
    const data = {
      "accept": true,
      "accountId": selectedAccount.accountId,
      "moimMemberId": invite.moimMemberId
    }
    if (agreeTerms) {
        try {
          const res = await axios.patch(`${BASE_URL}/api/moim/invite/check`, data, {
            headers: {
              'Content-Type': 'application/json', 
              'Authorization': 'Bearer ' + token
            }
          })
          // console.log('전송데이터', data)
          // console.log(res.data)
          if (!res.data.success) {
            alert(res.data.error.message)
          } else{
            navigate("/moimhome")
          }
        } catch(err) {
          // console.log(err)
        }
      } else {
        alert('약관에 동의해주세요.');
      }
    }
  const RefuseInvite = async() => {
    const data = {
      "accept": false,
      "moimMemberId": invite.moimMemberId
    }
    try {
      const res = await axios.patch(`${BASE_URL}/api/moim/invite/check`, data, {
        headers: {
          'Content-Type': 'application/json', 
          'Authorization': 'Bearer ' + token
        }
      })
      // console.log(res.data)
      navigate("/moimhome")
    } catch(err) {
      // console.log(err)
    }
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
        <BackLogoHeader name="모임통장 가입"fontSize="2rem" left="5rem" top="0.8rem"/>

        <div className={styles.createcontent}>
          <div className={styles.createment}>
            <h2>모임 가입 정보</h2>
          </div>

          <div className={styles.accountcontent}>
            <div className={styles.accountinfo}>
              <div className={styles.moimcontent}>
                <p className={styles.title}>모임이름</p>
                <p>{invite.moimName}</p>
              </div>
              <div className={styles.moimcontent}>
                <p className={styles.title} onClick={OpenAccounts}>연결계좌</p>
                <button onClick={OpenAccounts}>연결계좌</button>
              </div>
              <div className={styles.nowselectedAccount}>
                <p>{selectedAccount.accountNumber}</p>
              </div>
              <div className={styles.moimcontent}>
                <p className={styles.title}>계좌유형</p>
                <p>{invite.moimType}</p>
              </div>
              <div className={styles.moiminfo}>
                <p className={styles.title}>모임소개</p>
                <p className={styles.moimtext}>{invite.introduce}</p>
              </div>
              <div className={styles.watchtermsbtn}>
                <button className={styles.openterms} onClick={OpenTerms}>
                  약관보기
                </button>
              </div>
              <div className={styles.checkbox}>
                <input
                  type="checkbox"
                  id="scales"
                  name="sclaes"
                  checked={agreeTerms}
                  onChange={() => setAgreeTerms(!agreeTerms)}
                />
                <label htmlFor="scales">약관에 동의합니다</label>
              </div>
            </div>
          </div>

        <div className={styles.buttondiv}>
            <button className={styles.acceptbtn} onClick={AcceptInvite}>모임 가입하기</button>
            <button className={styles.refusebtn} onClick={RefuseInvite}>초대 거절하기</button>
        </div>
        
        </div>

        {selectAccountOpen && (
            <>
              <div className={styles.backgroundOverlay} onClick={CloseAccounts}/>
              <SelectAccount setSelectAccountOpen={setSelectAccountOpen} setSelectedAccount={setSelectedAccount} selectedAccount={selectedAccount}/>
            </>
          )}
        {termsOpen && (
            <>
              <div className={styles.backgroundOverlay} onClick={CloseTerms}/>
              <TermsOfUse setTermsOpen={setTermsOpen}/>
            </>
          )}

      </div>
    </div>
  );
}

export default MoimInfo;
