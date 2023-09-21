import { useState } from 'react';
import styles from "./MoimSelectAccount.module.css";
import { useNavigate, useLocation } from "react-router-dom";

function MoimSelectAccount() {

  const [selectCategory, setSelectCategory] = useState<string>('')

  const ChangeCategory = () => {
    setSelectCategory('한명관리')
  }

  const navigate = useNavigate()

  const { state } = useLocation()
  const moimName = state.moimName
  const moimInfo = state.moimInfo
  const account = state.account

  const ToBack = () => {
    navigate(-1)
  }

  const ToNext = () => {
    navigate('/createresult', {state: {moimName:moimName, moimInfo:moimInfo, account:account, category:selectCategory}})
  }

  const ShowProp = () => {
    console.log(moimName, moimInfo, account)
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
            <h1>계좌 유형을 선택해주세요.</h1>
          </div>

          <div className={styles.accountscategory}>
            <div className={styles.categoryinfo}>
              <div className={styles.accountinfo}>
                <p>한명 관리 계좌는 관리자 한명이 계좌의 이체권한을 가지는 모임통장 형식입니다</p>
              </div>

              <div className={styles.selectcategories}>
                <label htmlFor="oneman" onClick={ChangeCategory}>
                  <div className={styles.selectunit}>
                    <div>한명 관리</div>
                    <input type="radio" id="oneman" />
                  </div>
                </label>
                <label htmlFor="twoman">
                  <div className={styles.selectunit}>
                    <div>두명 관리</div>
                    <input type="radio" id="twoman" />
                  </div>
                </label>
                <label htmlFor="everybody">
                  <div className={styles.selectunit}>
                    <div>공동 관리</div>
                    <input type="radio" id="everybody" />
                  </div>
                </label>
              </div>
            </div>
          </div>

        <div className={styles.buttondiv}>
            <button className={styles.submitbtn} onClick={ToNext}>다음</button>
        </div>
        
        <button onClick={ShowProp}></button>

        </div>

      </div>
    </div>
  );
}

export default MoimSelectAccount;
