import { useState } from 'react';
import styles from "./MoimInfo.module.css";
import { useNavigate, useLocation } from "react-router-dom";
import TermsOfUse from '../CreateResult/TermsOfUse/TermsOfUse';
import SelectAccount from './SelectAccount/SelectAccount';

function MoimInfo() {

  const navigate = useNavigate()

  const { state } = useLocation()
  const invite = state.invite

  const [selectAccountOpen, setSelectAccountOpen] = useState<boolean>(false)
  const [termsOpen, setTermsOpen] = useState<boolean>(false)

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
  const ToBack = () => {
    navigate(-1)
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
            <h2>모임 개설준비가 완료 되었습니다</h2>
          </div>

          <div className={styles.accountcontent}>
            <div className={styles.accountinfo}>
              <div className={styles.moimcontent}>
                <p className={styles.title}>모임이름</p>
                <p>{invite.moimId}</p>
              </div>
              <div className={styles.moimcontent}>
                <p className={styles.title} onClick={OpenAccounts}>연결계좌</p>
                <button onClick={OpenAccounts}>연결계좌</button>
              </div>
              <div className={styles.moimcontent}>
                <p className={styles.title}>계좌유형</p>
                <p>aaaa</p>
              </div>
              <div className={styles.moiminfo}>
                <p className={styles.title}>모임소개</p>
                <p className={styles.moimtext}>aaaa</p>
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
            <button className={styles.submitbtn}>모임 가입하기</button>
        </div>
        
        </div>

        {selectAccountOpen && (
            <>
              <div className={styles.backgroundOverlay} onClick={CloseAccounts}/>
              <SelectAccount setSelectAccountOpen={setSelectAccountOpen}/>
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
