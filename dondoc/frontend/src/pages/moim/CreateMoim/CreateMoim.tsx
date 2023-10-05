import React from 'react'
import styles from "./CreateMoim.module.css";
import { useNavigate } from "react-router-dom";
import { BackLogoHeader } from '../../toolBox/BackLogoHeader/BackLogoHeader';

function CreateMoim() {

  const [moimName, setMoimName] = React.useState<string>('');
  const [moimInfo, setMoimInfo] = React.useState<string>('');

  const ChangeMoimName = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setMoimName(e.target.value)
  }
  const ChangeMoimInfo = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setMoimInfo(e.target.value)
  }
  
  const navigate = useNavigate()


  const ToNext = () => {
    if (moimName && moimInfo) {
      navigate('/moimlink', {state: {moimName:moimName, moimInfo:moimInfo}})
    } else {
      alert('모임 정보를 입력해주세요')
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
        <BackLogoHeader name="모임 생성"fontSize="2rem" left="5rem" top="0.8rem"/>

        <div className={styles.createcontent}>
          <div className={styles.createment}>
            <h1>모임 통장을 만들어 보아요.</h1>
          </div>

          <div className={styles.inputs}>
            <div className={styles.moimname}>
              <textarea className={styles.inputbox} placeholder="모임 이름을 입력해 주세요" onChange={ChangeMoimName} />
            </div>
            <div className={styles.moiminfo}>
              <textarea className={styles.inputbox} placeholder="모임을 소개해 주세요&#13;&#10;초대되는 인원들이 보게 되요!"
               rows={5} onChange={ChangeMoimInfo} />
            </div>
          </div>

          <div className={styles.buttondiv}>
            <button className={styles.submitbtn} onClick={ToNext}>다음</button>
          </div>


        </div>

      </div>
    </div>
  );
}

export default CreateMoim;
