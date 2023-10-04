import styles from "./DetailSecond.module.css";
import BackLogoHeader from "../../toolBox/BackLogoHeader/BackLogoHeader";
import {useState} from 'react'
import MissionIcon from "/src/assets/MoimLogo/missionicon.svg"
import MoneyIcon from "/src/assets/MoimLogo/moneyicon.svg"

function DetailSecond() {

  const [nowSelected, setNowSelected] = useState<boolean>(true)
  const ClickMissionTab = () => {
    setNowSelected(false)
  }
  const ClickMoneyTab = () => {
    setNowSelected(true)
  }
  return (
    <div className={styles.container}>
      <BackLogoHeader name="오늘부터 다이어트" fontSize="2rem" left="5rem" top="0.8rem"/>
      <div className={styles.content}>

      <div className={styles.requests}>
        <div className={styles.pagetitle}>
          <h1>요청 관리하기</h1>
        </div>

        <div className={styles.maincontent}>
          <div className={styles.toptab}>
            <div className={styles.requestmoney} onClick={ClickMoneyTab}>
              <div className={styles.icon}>
                <img src={MoneyIcon} alt="" />
              </div>
              <div className={styles.requesttext}>
                <p style={{color: nowSelected ? '#7677E8' : '', borderBottom: nowSelected ? '2px solid #7677E8' : ''}}>요청하기</p>
              </div>
            </div>

            <div className={styles.requestmission} onClick={ClickMissionTab}>
              <div className={styles.icon}>
                <img src={MissionIcon} alt="" />
              </div>
              <div className={styles.requesttext}>
                <p style={{color: !nowSelected ? '#DD7979' : '', borderBottom: !nowSelected ? '2px solid #DD7979' : ''}}>미션등록</p>
              </div>
            </div>
          </div>




        </div>




      </div>


      </div>
    </div>
  );
}

export default DetailSecond;
