import { useState } from 'react';
import styles from "./CreateResult.module.css";
import { useNavigate, useLocation } from "react-router-dom";
import TermsOfUseModal from './TermsOfUse/TermsOfUse';
import axios from 'axios';
import { useSelector } from "react-redux";
import { UserType } from '../../../store/slice/userSlice';
import { BackLogoHeader } from '../../toolBox/BackLogoHeader/BackLogoHeader';
import { BASE_URL } from '../../../constants';
import InviteManagerModal from './InviteManagerModal';

type manager = {
  userId: number,
  phoneNumber: string,
  nickName: string
}
const initialSearchResult: manager = {
  userId: 0,
  phoneNumber: "",
  nickName: ""
};

function CreateResult() {

  const navigate = useNavigate()

  const { state } = useLocation()
  const moimName = state.moimName
  const moimInfo = state.moimInfo
  const account = state.account
  const category = state.category
  const password = state.password

  const [termsOpen, setTermsOpen] = useState<boolean>(false)
  const [inviteModalOpen, setInviteModalOpen] = useState<boolean>(false)
  const [manager, setManager] = useState<manager>(initialSearchResult)

  const OpenTerms = () => {
    setTermsOpen(true)
  }
  const CloseTerms = () => {
    setTermsOpen(false)
  }
  const OpenInviteModal = () => {
    setInviteModalOpen(true)
  }
  const CloseInviteModal = () => {
    setInviteModalOpen(false)
  }


  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken

  const data = {
    "accountId": account.accountId,
    "introduce": moimInfo,
    "manager": [{ "userId": manager.userId }],
    "moimName": moimName,
    "moimType": category.code,
    "password": password
  }

  const showdata = () => {
    console.log(data)
  }

  const [agreeTerms, setAgreeTerms] = useState<boolean>(false); // 약관 동의 상태를 저장하는 상태 변수

  const CreateMoim = async () => {
    if (category.code == 2 && manager.phoneNumber) {
      if (agreeTerms) {
        try {
          const response = await axios.post(`${BASE_URL}/api/moim/create`, data, {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ' + token,
            },
          });
          console.log(response.data);
          alert('모임이 생성되었습니다.');
          navigate('/moimhome');
        } catch (error) {
          console.log('error:', error);
        }
      } else {
        // 약관에 동의하지 않은 경우 경고 메시지 표시
        alert('약관에 동의해주세요.');
      }
    } else {
      alert('매니저를 초대해 주세요.')
    }
  }

  return (
    <div className={styles.container}>
      <div className={styles.content}>

        <BackLogoHeader name="모임통장 생성"fontSize="2rem" left="5rem" top="0.8rem"/>

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
              {category.code == 2 ? (
                <>
                  <div className={styles.moimcontent}>
                    <p className={styles.title}>매니저</p>
                    <button className={styles.invitebtn} onClick={OpenInviteModal}>매니저 초대</button>
                  </div>
                  <h3 style={{marginTop:'0', marginBottom:'2rem', textAlign:'end'}}>{manager.nickName} {manager.phoneNumber}</h3>
                </>
              ):(
                <></>
              )}
              <div className={styles.moiminfo}>
                <p className={styles.title}>모임소개</p>
                <p className={styles.moimtext}>{moimInfo}</p>
              </div>
              <div className={styles.watchtermsbtn}>
                <button className={styles.openterms} onClick={OpenTerms}>
                  약관보기
                </button>
              </div>
              {/* <div className={styles.checkbox}>
                <input type="checkbox" id="scales" name="sclaes" /><label htmlFor="scales">약관에 동의합니다</label>
              </div> */}
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
            <button className={styles.submitbtn} onClick={CreateMoim}>계좌 개설하기</button>
        </div>
        
        </div>

        {termsOpen && (
            <>
              <div className={styles.backgroundOverlay} onClick={CloseTerms}/>
              <TermsOfUseModal setTermsOpen={setTermsOpen} />
            </>
          )}
        {inviteModalOpen && (
            <>
              <div className={styles.backgroundOverlay} onClick={CloseInviteModal}/>
              <InviteManagerModal setInviteModalOpen={setInviteModalOpen} token={token} setManager={setManager}/>
            </>
          )}

      </div>
      <button onClick={showdata}></button>
    </div>
  );
}

export default CreateResult;
